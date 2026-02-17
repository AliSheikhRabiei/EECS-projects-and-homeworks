from kdtree import KDTree, _KDTNode, argsort
from abstractions import Apartment

import pickle  # for test cases in doctests


def test_lookup_fun():
    score = 0
    details = '\n************\nMarking Lookup\n'
    with open('kdtree.pkl', 'rb') as file:
        loaded_object = pickle.load(file)

        if loaded_object.lookup(43.640425518, -79.436224414):
            score += 1
        else:
            details += "Did not find (43.640425518,-79.436224414 )\n"

        if loaded_object.lookup(43.653685383, -79.466255527):
            score += 1
        else:
            details += "Did not find (43.653685383,-79.466255527)\n"

    details += f"Passed {score} of 2 tests."

    return score, details


def test_get_nearest_fun():
    score = 0
    details = '\n************\nMarking Nearest Neighbour\n'

    with open('kdtree.pkl', 'rb') as file:
        loaded_object = pickle.load(file)
        if loaded_object.get_nearest(43.64, -79.436).type == 'SOCIAL HOUSING':
            score += 1
        else:
            details += "Nearest to (43.64,-79.436) should be SOCIAL HOUSING.\n"

        if loaded_object.get_nearest(43.654, -79.466).type == 'PRIVATE':
            score += 1
        else:
            details += "Nearest to (43.654,-79.466) should be PRIVATE.\n"

        if loaded_object.get_nearest(43.65, -79.484).type == 'PRIVATE':
            score += 1
        else:
            details += "Nearest to (43.65,-79.484) should be PRIVATE.\n"

        if loaded_object.get_nearest(43.643, -79.432).type == 'SOCIAL HOUSING':
            score += 1
        else:
            details += "Nearest to (43.643,-79.432) should be SOCIAL HOUSING.\n"

        if loaded_object.get_nearest(43.752, -79.607).type == 'PRIVATE':
            score += 1
        else:
            details += "Nearest to (43.752,-79.607) should be PRIVATE.\n"

    details += f"Passed {score} of 5 tests."

    return score, details


def test_build_tree_fun():
    score = 0
    details = '\n************\nMarking Build Tree\n'
    t = KDTree([Apartment(1973, 'SOCIAL HOUSING', 86, 2, 2), Apartment(1973, 'SOCIAL HOUSING', 86, 3, 3),
                Apartment(1973, 'SOCIAL HOUSING', 86, 1, 1)])
    lines, *_ = t._display_helper(t.KDTree)
    correct = ['    ___2.0,2.0___    ', '   |             |   ', '1.0,1.0       3.0,3.0']
    i = 0

    for line in lines:
        if i <= 2 and line == correct[i]:
            score += 1
        elif i <= 2:
            details += "Line " + str(i) + " should be: " + correct[i] + "\n"
        i += 1

    details += f"Passed {score} of 3 tests."

    return score, details


def test_argsort_fun():
    score = 0
    details = '\n************\nMarking Argsort\n'

    if argsort([3, 1, 2]) == [1, 2, 0]:
        score += 1
    else:
        details += "Sorted should be [1, 2, 0]\n"
    if argsort([8, 6, 7, 5, 3, 0, 9]) == [5, 4, 3, 1, 2, 0, 6]:
        score += 1
    else:
        details += "Sorted should be [5, 4, 3, 1, 2, 0, 6]\n"

    details += f"Passed {score} of 2 tests."

    return score, details


def test_distance_fun():
    score = 0
    details = '\n************\nMarking Distance\n'

    apt = _KDTNode(Apartment(1973, 'SOCIAL HOUSING', 86, 1, 1), 0)

    if abs(apt.distance(1, 1) - 0) < 0.01:
        score += 1
    else:
        details += "distance_to(1, 1) should be 0\n"
    if abs(apt.distance(2, 2) - 1.41421) < 0.01:
        score += 1
    else:
        details += "distance_to(2, 2) should be 1.41421\n"
    if abs(apt.distance(3, 3) - 2.82843) < 0.01:
        score += 1
    else:
        details += "distance_to(3, 3) should be 2.82843\n"

    details += f"Passed {score} of 3 tests."

    return score, details


if __name__ == '__main__':
    # RUN THE TESTS TO TEST YOUR CODE1
    # ALSO RUN THE DOCTESTS IN KDTREE.PY!

    score = [0, 0, 0, 0, 0]
    details = ["", "", "", "", ""]

    try:
        (score[0], notes) = test_argsort_fun()
        details[0] = notes
    except Exception:
        print("issue encountered during test_argsort_fun")

    try:
        (score[1], notes) = test_distance_fun()
        details[1] = notes
    except Exception:
        print("issue encountered during test_distance_fun")

    try:
        (score[2], notes) = test_build_tree_fun()
        details[2] = notes
    except Exception:
        print("issue encountered during test_build_tree_fun")

    try:
        (score[3], notes) = test_lookup_fun()
        details[3] = notes
    except Exception:
        print("issue encountered during test_lookup_fun")

    try:
        (score[4], notes) = test_get_nearest_fun()
        details[4] = notes
    except Exception:
        print("issue encountered during test_get_nearest_fun")

    possible = [2, 3, 3, 2, 5]
    for i in range(0, 5):
        print(f'score for test number {i + 1}: {score[i]} of {possible[i]}\n{details[i]}\n************\n')



