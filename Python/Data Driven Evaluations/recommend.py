import math
from matplotlib import pyplot as plt

from data import APARTMENT_DATA, TESTING_DATA
from abstractions import *


##################################
# Phase 2: Supervised Learning #
##################################
class LinearRegression:
    """A linear regression model to predict ratings
    an evaluator might give to a apartment."""

    def __init__(self) -> None:
        """Return a LinearRegression abstraction."""
        self.type = ''
        self.a = 0  # intercept of regression line
        self.b = 0  # slope of regression line
        self.xs = []  # x values used to train model
        self.ys = []  # y values used to train model
        self.r_squared = 0  # r-squared value of model (how well it fits the training data)

    def train(self, apartments: list[ApartmentBuilding], type:str) -> None:
        """Train a rating predictor (a function mapping apartment ratings of the type
         in TYPE onto OVERALL ratings), by performing least-squares linear regression.
         Save the R^2 value and the parameters of this model as attributes of `self`.

        Arguments:
        apartments -- A sequence of apartments
        >>> apartments = APARTMENT_DATA[:10]
        >>> r = LinearRegression()
        >>> r.train(apartments, 'COSMETIC')
        >>> round(r.a,1)
        68.6
        >>> round(r.b,1)
        4.6
        """
        # pass #replace with your code

        self.type = type
        xs = []
        ys = []

        for apt in apartments:
            x_rating = None
            y_rating = None
            for review in apt.reviews:
                if review.type == self.type:
                    x_rating = review.rating
                if review.type == 'OVERALL':
                    y_rating = review.rating
            if x_rating is not None and y_rating is not None:
                xs.append(x_rating)
                ys.append(y_rating)

        m = len(xs)
        if m == 0:
            self.a = 0
            self.b = 0
            self.r_squared = 0
            return

        mean_x = sum(xs) / m
        mean_y = sum(ys) / m

        Sxx = sum((x - mean_x) ** 2 for x in xs)
        Syy = sum((y - mean_y) ** 2 for y in ys)
        Sxy = sum((x - mean_x) * (y - mean_y) for x, y in zip(xs, ys))

        if Sxx == 0:
            self.b = 0
        else:
            self.b = Sxy / Sxx
        self.a = mean_y - self.b * mean_x

        if Sxx * Syy == 0:
            self.r_squared = 0
        else:
            self.r_squared = (Sxy ** 2) / (Sxx * Syy)

    def predict(self, apartment: ApartmentBuilding) -> float:
        """Use the parameters of the regression model to predict an overall rating for
        `apartment`.

        Arguments:
        apartment -- An apartment abstraction

        A linear regression model is a function from apartment to evaluation.
        It can be calculated using the formula:
        y = b * x + a
        where x is the log rating of the apartment, y is the predicted evaluation,
        a is the intercept of the model, and b is the slope of the model.
        >>> apartment = ApartmentBuilding('SOCIAL HOUSING', 1, 1973, [Review('MODERATE RISK', 6), Review('COSMETIC', 11), Review('OVERALL', 86)])
        >>> r = LinearRegression()
        >>> r.type = 'COSMETIC'
        >>> r.a = 1
        >>> r.b = 2
        >>> round(r.predict(apartment), 1)
        23
        """
        # pass #replace with your code

        x = 0.0
        for review in apartment.reviews:
            if review.type == self.type:
                x = review.rating
                break
        return self.a + self.b * x

    def make_predictions(self, apartments: list[ApartmentBuilding]) -> dict[ApartmentBuilding, float]:
        """Return the predicted rating of `apartments`.  Note
        that this method can only be called after `train` has been called.
        You will have to train the predictor using the apartments in
        the user has reviewed in the past; you will then use that model
        to predict the user's ratings for restaurants they have not
        reviewed.

        Arguments:
        apartments -- A list of apartments to be reviewed
        >>> apartments = APARTMENT_DATA[:10]
        >>> r = LinearRegression()
        >>> r.type = 'COSMETIC'
        >>> r.a = 1
        >>> r.b = 2
        >>> list(r.make_predictions(apartments).values())
        [5.2, 6.6, 5.6, 4.34, 3.44, 3.44, 3.0, 6.34, 3.44, 6.4]
        """
        pass #replace with your code

        return {apt: self.predict(apt) for apt in apartments}

def calculate_mse(predictions: dict[ApartmentBuilding, float]) -> float:
    """Calculate the mean squared error between predicted ratings and actual ratings

     Arguments:
     predictions -- A dictonary wherein keys are apartment buildings (containing actual
     rating data) and values are predictions of the overall ratings for these buildings.
     >>> apartments = APARTMENT_DATA[:10]
     >>> r = LinearRegression()
     >>> r.type = 'COSMETIC'
     >>> r.a = 1
     >>> r.b = 2
     >>> predictions = r.make_predictions(apartments)
     >>> round(calculate_mse(predictions),1)
     72.9
     """
    # pass  # replace with your code

    sse = 0.0
    count = 0
    for apt, predicted in predictions.items():
        actual = apt.apartment_overall_rating()
        sse += (actual - predicted) ** 2
        count += 1
    if count == 0:
        return 0.0
    return sse / count

if __name__ == "__main__":
    import doctest
    doctest.testmod()

    # Uncomment the lines below to train and visualize a regression
    # c = LinearRegression()
    # c.train(APARTMENT_DATA, 'COSMETIC')
    # ratings = c.make_predictions(TESTING_DATA)
    #
    # xs = [t.apartment_cosmetic_rating() for t in TESTING_DATA]
    # ys = [t.apartment_overall_rating() for t in TESTING_DATA]
    #
    # plt.plot(xs, ys, 'ro')
    # plt.plot(xs, ratings.values(), 'bo')
    # plt.xlabel('Cosmetic Rating for Apartment')
    # plt.ylabel('Overall Ratings')
    # plt.legend(['Actual Rating', 'Predicted Rating'])
    # plt.plot(xs, ratings.values(), 'b')
    # plt.show()

