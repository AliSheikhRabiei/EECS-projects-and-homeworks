"""Utilities"""
from __future__ import annotations

from typing import Any, Callable
from abstractions import ApartmentBuilding
from data import APARTMENT_DATA


def filter_and_map(s: list[Any], map_fn: Callable, filter_fn: Callable) -> list[ApartmentBuilding]:
    """Accept a list. Return a new list containing
    the result of calling `map_fn` on each item in the sequence `s`
    for which `filter_fn` returns a true value.
    We will demonstrate this in the doctest on
    lists of ApartmentBuilding, but this method
    should work with lists of other types.
    >>> getname = lambda x: x.type
    >>> is_good = lambda x: x.apartment_overall_rating() > 80
    >>> filter_and_map(APARTMENT_DATA[:20], getname, is_good)
    ['PRIVATE', 'SOCIAL HOUSING', 'SOCIAL HOUSING', 'TCHC', 'PRIVATE', 'PRIVATE', 'PRIVATE', 'PRIVATE']
    >>> is_bad = lambda x: x.apartment_overall_rating() >= 80
    >>> filter_and_map(APARTMENT_DATA[:20], getname, is_bad)
    ['PRIVATE', 'PRIVATE', 'SOCIAL HOUSING', 'SOCIAL HOUSING', 'TCHC', 'PRIVATE', 'PRIVATE', 'PRIVATE', 'PRIVATE', 'PRIVATE']
    """
    # pass # replace this line

    return [map_fn(x) for x in s if filter_fn(x)]

def sorted_apartments(apartments: list[ApartmentBuilding]) -> list[tuple[str, float]]:
    """Accept a list of restaurants.  Return a list of tuples of restaurant names
    and the corrsesponding average rating of the restaurants.  The
    list that is returned should be sorted by average rating, from lowest
    to highest.
    >>> r = sorted_apartments(APARTMENT_DATA)
    >>> print("lowest rating: " + str(r[0]))
    lowest rating: ('PRIVATE', 43.0)
    >>> print("highest rating: " + str(r[-1]))
    highest rating: ('PRIVATE', 100.0)
    """
    # pass # replace this line

    return sorted([(a.type, a.apartment_overall_rating()) for a in apartments], key=lambda x: x[1])


def average_apartment_rating(apartments: list[ApartmentBuilding]) -> float:
    """Return the average overall ratig of all buildings in the
    input list of apartment buildings.
    >>> r = round(average_apartment_rating(APARTMENT_DATA),1)
    >>> print("average overall rating: " + str(r))
    average overall rating: 88.5
    """
    # pass # replace this line

    if not apartments:
        return 0.0
    total = sum(a.apartment_overall_rating() for a in apartments)
    return total / len(apartments)


if __name__ == "__main__":
    import doctest
    doctest.testmod()
