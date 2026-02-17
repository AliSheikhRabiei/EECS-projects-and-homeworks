# -*- coding: utf-8 -*-
"""
Apartment Inspector 3000: Toronto Edition
Author: Ali "The Data Whisperer" Sheikh Rabiei
Date: When Toronto was still considering building the Ontario Line

Mission: Predict if buildings are "meh" or "fancy" based on city data
Warning: May cause sudden urges to become an urban planner
"""

#Adding some necessary and unnecessary libraries
import pandas as pd  
import numpy as np  
import matplotlib.pyplot as plt  
import seaborn as sns  
from sklearn.model_selection import train_test_split, KFold  
from sklearn.linear_model import LinearRegression  
from sklearn.tree import DecisionTreeRegressor, DecisionTreeClassifier  
from sklearn.metrics import (mean_squared_error, r2_score,confusion_matrix, classification_report) 
import statsmodels.api as sm 
import os  
from scipy import stats  

# Create output directory 
os.makedirs('output', exist_ok=True)  # exist_ok=True means no yelling if folder exists

#%% 2. Data Loading 
def load_data():
    """
    Loads data while pretending to be adventurous
    """
    try:
        # Main dataset - the holy grail
        data = pd.read_csv('data/apartment_evaluations.csv')
        
        np.random.seed(42)  # The answer to life, the universe, and reproducibility
        data['DISTANCE_TO_SUBWAY'] = np.random.uniform(0.1, 5, size=len(data))  # Magic numbers!
        data['PARK_PROXIMITY'] = np.random.uniform(0.05, 3, size=len(data))  # More magic!
        
        # Quick sanity check - did we get anything?
        if data.empty:
            raise ValueError("DataFrame came back empty! Did the raccoons steal your data?")
            
        return data
    
    except FileNotFoundError:
        print("Couldn't find the data file! Did you:")
        raise

#%% 3. Data Cleaning - Where we play data janitor
def preprocess_data(data):
    """
    Cleans data like a Marie Kondo disciple
    Returns: Features (X), Regression Target (y_reg), Classification Target (y_clf), and the cleaned data
    """
    # Handle missing construction years - because some buildings are shy about their age
    if 'YEAR_BUILT' not in data.columns:
        raise KeyError("YEAR_BUILT column missing! Was this dataset built in the future?")
    
    data['YEAR_BUILT'].fillna(data['YEAR_BUILT'].median(), inplace=True)  # Median because average ages are weird
    
    # Elevator status - the great binary divider of buildings
    if 'ELEVATOR' not in data.columns:
        raise KeyError("ELEVATOR column missing! How do we know which buildings are fancy?")
    
    data['HAS_ELEVATOR'] = data['ELEVATOR'].map({'Yes': 1, 'No': 0, np.nan: 0})  # Assume no elevator if data is missing
    
    # Calculate building age - because even buildings have midlife crises
    current_year = pd.Timestamp.now().year
    data['BUILDING_AGE'] = current_year - data['YEAR_BUILT']
    
    # Categorize scores - because everything needs labels these days
    bins = [0, 59, 79, 100]  # Poor, Fair, Good
    labels = ['"Needs TLC"', '"Not Bad"', '"Toronto Fancy"']  # More fun labels
    
    if 'SCORE' not in data.columns:
        raise KeyError("SCORE column missing! How can we judge buildings without scores?")
    
    data['SCORE_CATEGORY'] = pd.cut(data['SCORE'],
                                   bins=bins,
                                   labels=labels)
    
    # Feature selection - picking the all-star team
    features = [
        'BUILDING_AGE',  # The elder statesman
        'NUMBER_OF_UNITS',  # How many people can cram in
        'NUMBER_OF_STOREYS',  # Vertical ambition
        'PARKING_SPACES',  # The Toronto luxury
        'HAS_ELEVATOR',  # The class divider
        'DISTANCE_TO_SUBWAY',  # Transit proximity
        'PARK_PROXIMITY'  # Nature closeness
    ]
    
    # Final safety check
    missing_features = [f for f in features if f not in data.columns]
    if missing_features:
        raise KeyError(f"Missing critical features: {missing_features}. Time to check your data!")
    
    X = data[features]
    y_reg = data['SCORE']  # For regression (exact scores)
    y_clf = data['SCORE_CATEGORY']  # For classification (categories)
    
    return X, y_reg, y_clf, data

#%% 4. EDA - Where we make pretty pictures
def perform_eda(X, y_reg, data):
    """
    Exploratory Data Analysis - the "getting to know you" phase
    Generates plots that would make your high school math teacher proud
    """
    print("\n Starting EDA - Prepare for enlightenment!")
    
    # 1. Target distribution - the big reveal
    plt.figure(figsize=(12, 5))
    
    # Continuous scores
    plt.subplot(1, 2, 1)
    sns.histplot(y_reg, bins=20, kde=True)
    plt.title("How Fancy Are These Buildings?")
    
    # Categories - because we love boxes
    plt.subplot(1, 2, 2)
    sns.countplot(x='SCORE_CATEGORY', data=data, 
                 order=['"Needs TLC"', '"Not Bad"', '"Toronto Fancy"'])
    plt.title("Building Report Cards")
    plt.tight_layout()  # Prevents label fights
    plt.savefig('output/score_distributions.png')
    plt.close()
    print("Saved score distribution plots!")
    
    # 2. Age vs Score - the drama plot
    plt.figure(figsize=(10, 6))
    sns.regplot(x='BUILDING_AGE', y='SCORE', data=data,
                order=2,  # Quadratic fit - for that curvy relationship
                scatter_kws={'alpha':0.3},  # Make points see-through
                line_kws={'color':'red'})  # Trend line in angry red
    plt.title("Do Buildings Have Midlife Crises?")
    plt.savefig('output/age_vs_score_poly.png')
    plt.close()
    print("Saved age-score relationship plot!")
    
    # 3. Correlation matrix - the relationship status update
    plt.figure(figsize=(12, 8))
    corr_matrix = X.join(y_reg).corr(method='pearson')
    sns.heatmap(corr_matrix, annot=True, cmap='coolwarm',
                mask=np.triu(np.ones_like(corr_matrix)),  # Hide upper triangle (it's shy)
                center=0)
    plt.title("Feature Relationship Drama")
    plt.tight_layout()
    plt.savefig('output/correlation_heatmap.png')
    plt.close()
    print("Saved correlation matrix!")

#%% 5. Statistical Tests - The "is this real?" phase
def statistical_analysis(data):
    """Runs tests that would make a statistician nod approvingly"""
    print("\nRunning Statistical Tests...")
    
    # Elevator privilege check
    elevator_scores = data[data['HAS_ELEVATOR'] == 1]['SCORE']
    no_elevator_scores = data[data['HAS_ELEVATOR'] == 0]['SCORE']
    
    # Handle case where groups might be empty
    if len(elevator_scores) == 0 or len(no_elevator_scores) == 0:
        print("Warning: One elevator group is empty! Check your data.")
        return
    
    t_stat, p_value = stats.ttest_ind(elevator_scores, no_elevator_scores)
    
    print(f"\nElevator Privilege Check:")
    print(f"  t-statistic: {t_stat:.2f}")
    print(f"  p-value: {p_value:.4f}")
    
    if p_value < 0.05:
        print("Significant difference! Elevators DO make buildings fancier!")
    else:
        print("No significant difference. Maybe elevators are overrated?")
    
    # Age-Score relationship
    age_score_corr = data['BUILDING_AGE'].corr(data['SCORE'])
    print(f"\nAge vs. Score Relationship:")
    print(f"  Pearson's r: {age_score_corr:.2f}")
    
    if abs(age_score_corr) > 0.3:
        print("Strong relationship! Older buildings ARE judged harder!")
    else:
        print("Age is just a number! (According to the data)")

#%% 6. Modeling - Where we play data god
def train_enhanced_models(X, y_reg, y_clf):
    """Trains models smarter than your average Toronto raccoon"""
    print("\nTraining Models...")
    
    # Interaction terms - because features should mingle
    X = X.copy()  # Avoid SettingWithCopyWarning
    X['AGE_X_UNITS'] = X['BUILDING_AGE'] * X['NUMBER_OF_UNITS']  # Does age affect big buildings differently?
    X['AGE_X_ELEVATOR'] = X['BUILDING_AGE'] * X['HAS_ELEVATOR']  # Do old elevators help/hurt?
    
    # Train-test split - like dividing your LEGO sets
    X_train, X_test, y_train_reg, y_test_reg, y_train_clf, y_test_clf = \
        train_test_split(X, y_reg, y_clf, test_size=0.2, random_state=42)  # 42: The answer to everything
        
    # 1. Linear Regression with StatsModels - for the stats nerds
    print("\nRunning Linear Regression...")
    X_sm = sm.add_constant(X_train)  # Adds intercept term
    model_sm = sm.OLS(y_train_reg, X_sm).fit()
    
    print("\nRegression Summary:")
    print(model_sm.summary())  # The moment of truth
    
    # Save coefficients plot
    plt.figure(figsize=(10, 6))
    coefficients = model_sm.params.drop('const')  # Ignore intercept
    coefficients.sort_values().plot(kind='barh')
    plt.title("What Actually Matters? (Coefficient Magnitudes)")
    plt.tight_layout()
    plt.savefig('output/regression_coefficients.png')
    plt.close()
    print("Saved coefficient plot!")
    
    # 2. Decision Tree Classifier - the branching storyteller
    print("\nGrowing Decision Tree...")
    tree_clf = DecisionTreeClassifier(max_depth=3, random_state=42)  # Keep it simple
    tree_clf.fit(X_train, y_train_clf)
    
    # Classification report
    y_pred = tree_clf.predict(X_test)
    print("\nClassification Report:")
    print(classification_report(y_test_clf, y_pred))
    
    # Confusion matrix - because we love to be confused
    cm = confusion_matrix(y_test_clf, y_pred)
    plt.figure(figsize=(8, 6))
    sns.heatmap(cm, annot=True, fmt='d',
                xticklabels=tree_clf.classes_,
                yticklabels=tree_clf.classes_)
    plt.title("How Often Are We Wrong?")
    plt.tight_layout()
    plt.savefig('output/confusion_matrix.png')
    plt.close()
    print("Saved confusion matrix!")
    
    return model_sm, tree_clf

#%% 7. Main Execution - The grand finale!
if __name__ == '__main__':
    print("\n" + "="*50)
    print("APARTMENT INSPECTOR 3000 ACTIVATED")
    print("="*50)
    
    try:
        print("\nLoading data...")
        data = load_data()
        
        print("\nPreprocessing data...")
        X, y_reg, y_clf, data = preprocess_data(data)
        
        print("\nPerforming EDA...")
        perform_eda(X, y_reg, data)
        
        print("\nRunning statistical tests...")
        statistical_analysis(data)
        
        print("\nTraining models...")
        model_sm, tree_clf = train_enhanced_models(X, y_reg, y_clf)
        
        print("ALYSIS COMPLETE!")
        print(f"Outputs saved to: {os.path.abspath('output')}")
        
    except Exception as e:
        print("OH NO! SOMETHING WENT WRONG!")
        raise