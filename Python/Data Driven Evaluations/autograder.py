from matplotlib import pyplot as plt

from data import APARTMENT_DATA
from recommend import LinearRegression
from utils import filter_and_map
from abstractions import Review, ApartmentBuilding

test_mapfilter = True
test_apartment = True
test_rate_all = True
test_predict = True

# map_and_filter test
def test_mapfilterA():
    square = lambda x: x * x
    is_odd = lambda x: x % 2 == 1
    assert ([1, 9, 25] == filter_and_map([1, 2, 3, 4, 5], square, is_odd))
    assert (['o', 'd'] == filter_and_map(['hi', 'hello', 'hey', 'world'], lambda x: x[4], lambda x: len(x) > 4))


# Apartment test
def test_apartmentA():
    aptmin = ApartmentBuilding('SOCIAL HOUSING', 1, 1973, [Review('MODERATE RISK', 4), Review('COSMETIC', 1),
                                                       Review('OVERALL', 86)]).apartment_min_rating()
    assert (1 == aptmin[1])
    assert ('COSMETIC' == aptmin[0])


# Apartment test
def test_apartmentB():
    apt = ApartmentBuilding('SOCIAL HOUSING', 1, 1973, [Review('MODERATE RISK', 4), Review('COSMETIC', 1),
                                                       Review('OVERALL', 86)])
    assert (apt.num_ratings() == 3)  # should be an integer
    assert (type(apt.num_ratings()) == int)  # should be an integer
    assert (apt.apartment_overall_rating() == 86)  # should be a decimal
    assert (type(apt.apartment_overall_rating()) == int)  # should be a integer


# Regression test
def test_predictA():
    lr = LinearRegression()
    apartments = APARTMENT_DATA[1:10]
    lr.train(apartments,'COSMETIC')

    apt = APARTMENT_DATA[11]

    assert (round(lr.predict(apt), 2) == 80.9)
    assert (round(lr.r_squared, 2) == 0.22)

# Regression test
def test_predictB():
    lr = LinearRegression()
    apartments = APARTMENT_DATA[11:20]
    lr.train(apartments,'COSMETIC')

    apt = APARTMENT_DATA[21]

    assert (round(lr.predict(apt), 2) == 88.65)
    assert (round(lr.r_squared, 2) == 0.6)

# Regression test
def test_predictC():
    lr = LinearRegression()
    apartments = APARTMENT_DATA[21:30]
    lr.train(apartments,'COSMETIC')

    apt = APARTMENT_DATA[31]

    assert (round(lr.predict(apt), 2) == 65.55)
    assert (round(lr.r_squared, 2) == 0.63)


# Rate all tests
def test_rateallA():
    training = APARTMENT_DATA[21:]
    to_rate = APARTMENT_DATA[:20]
    c = LinearRegression()

    c.train(training,'COSMETIC')

    predictions = [round(n, 1) for n in list(c.make_predictions(to_rate).values())]
    cosmetics = [r.apartment_cosmetic_rating() for r in to_rate]
    overall = [r.apartment_overall_rating() for r in to_rate]
    correct = [81.2, 91.4, 84.1, 75.0, 68.5, 68.5, 65.3, 89.5, 68.5, 89.9, 88.5, 89.9, 78.2, 92.7, 65.3, 71.7, 75.0, 83.0, 92.7, 84.6]

    plt.plot(cosmetics, predictions, 'bo')
    plt.plot(cosmetics, overall, 'ro')
    plt.xlabel('Cosmetic Rating for Apartment')
    plt.ylabel('Overall Ratings')
    plt.legend(['Predicted Rating', 'Actual Rating'])
    plt.show()

    for c in range(len(correct)):
        assert (abs(correct[c] - predictions[c]) < 0.5)


def test_rateallB():
    training = APARTMENT_DATA[40:]
    to_rate = APARTMENT_DATA[20:40]
    c = LinearRegression()

    c.train(training,'COSMETIC')

    predictions = [round(n, 1) for n in list(c.make_predictions(to_rate).values())]
    cosmetics = [r.apartment_cosmetic_rating() for r in to_rate]
    overall = [r.apartment_overall_rating() for r in to_rate]
    correct = [78.2, 92.7, 89.5, 84.2, 72.6, 84.2, 88.5, 74.0, 88.5, 88.5, 88.5, 68.2, 84.2, 78.4, 90.0, 87.1, 88.5, 88.5, 76.9, 76.9]

    plt.plot(cosmetics, predictions, 'bo')
    plt.plot(cosmetics, overall, 'ro')
    plt.xlabel('Cosmetic Rating for Apartment')
    plt.ylabel('Overall Ratings')
    plt.legend(['Predicted Rating', 'Actual Rating'])
    plt.show()

    for c in range(len(correct)):
        assert (abs(correct[c] - predictions[c]) < 0.5)


if __name__ == '__main__':

    if test_mapfilter:
        test_mapfilterA()
    if test_apartment:
        test_apartmentA()
        test_apartmentB()
    if test_predict:
        test_predictA()
        test_predictB()
        test_predictC()
    if test_rate_all:
        test_rateallA()
        test_rateallB()
