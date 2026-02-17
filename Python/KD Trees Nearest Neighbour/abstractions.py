"""Data Abstractions"""
import csv

#############################
# Data Abstractions #
#############################
# Apartments
class Apartment:
    """An apartment, with a location (or ward), and a city review.
    You can decide on the names and attribute types."""
    _type: str
    _year: int
    _lon: float
    _lat: float
    _score: float

    def __init__(self, year: int, type: str, score: float, lat: float, lon: float) -> None:
        """Return a apartment abstraction.
        >>> Apartment( 1973, 'SOCIAL HOUSING', 86, 30, 30 ).type
        'SOCIAL HOUSING'
        """
        self._type = type
        self._lon = float(lon)
        self._lat = float(lat)
        if year != '':
            self._year = int(year)
        else:
            self._year = 1900
        self._score = float(score)

    def __str__(self) -> str:
        """ Return a string representation of this apartment.
        Format: <species> at (<longitude>, <latitude>) with diameter <diameter>
        """
        a = round(float(self._lat), 3)
        b = round(float(self._lon), 3)
        return f'{self._type} built in {self._year} at ({a}, {b}) with score {self._score}'

    @property
    def lon(self) -> float:
        return self._lon

    @property
    def lat(self) -> float:
        return self._lat

    @property
    def score(self) -> float:
        return self._score

    @property
    def year(self) -> int:
        return self._year

    @property
    def type(self) -> str:
        return self._type

# ApartmentReader
class ApartmentReader:
    """A class to read the apartment data from a csv file.
    We will use static methods to read the data,
    so there will be no need to create an instance of
    this class."""

    @staticmethod  # static! We do not need an instance of this class to use this method.
    def read_apartments(filename: str) -> list[Apartment]:
        """Read the tree data from the csv file and return a
        list of trees."""
        apartments = []

        with open(filename, newline='') as csvfile:
            apartmentreader = csv.reader(csvfile, delimiter=',')
            count = 0
            for row in apartmentreader:
                if count > 0:
                    apartments.append(Apartment(*row))
                count += 1

        return apartments

if __name__ == "__main__":
    import doctest
    doctest.testmod()
