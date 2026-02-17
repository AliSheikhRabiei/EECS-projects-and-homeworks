from __future__ import annotations
from typing import Optional, Tuple

from abstractions import Apartment, ApartmentReader

import math  # This may be useful here!
import pickle  # for test cases in doctests

def argsort(seq: list[int]) -> list[int]:
    """Return the indices of values in a sequence
    such that the values at each index are
    in ascending order. You don't need to use this function,
    but it may be helpful to find median values
    and partition data sets when you implement the KDTree.
    >>> argsort([3, 1, 2])
    [1, 2, 0]
    >>> argsort([8, 6, 7, 5, 3, 0, 9])
    [5, 4, 3, 1, 2, 0, 6]
    """
    # pass # replace this line!
    return [i for i, _ in sorted(enumerate(seq), key=lambda x: x[1])]


class _KDTNode(object):
    """A node to store some data in a KD-tree. A KD-tree is a binary tree
    that will be built of these nodes.

    === Attributes ===
    Information stored in a node:
    _apartment: The Apartment data that is stored in this node.
    _pivot: The pivot value of this node. At alternating levels of a KD tree,
            this will be a lon or lat coordinate. All nodes in the left subtree
            will have a value less than the pivot, and all nodes in the right subtree
            will have a value greater than the pivot.

    Children of this node:
    _left: the left subtree of this node.
    _right: the right subtree of this node.
    """
    _apartment: Apartment
    _pivot: float  # the pivot value of this node, this will be either the lon or lat coordinate
    _left: Optional[_KDTNode]
    _right: Optional[_KDTNode]

    def __init__(self, apt: Apartment, pivot: float) -> None:
        """Initialize a new _KDTNode storing <tree>, with no left or right nodes. """
        self._apartment = apt
        # all the nodes in the right subtree will have a value greater than the pivot
        self._pivot = pivot  # all the nodes in the left subtree will have a value less than the pivot
        self._left = None  # Initially pointing to nothing
        self._right = None  # Initially pointing to nothing

    def distance(self, lat: float, lon: float) -> float:
        """Return the distance between the _apartment in this node
        and a point (at lat, lon).
        >>> node = _KDTNode(Apartment( 1973, 'SOCIAL HOUSING', 86, 1, 1 ),0)
        >>> node.distance(1, 1)
        0.0
        >>> node.distance(1, 2)
        1.0
        >>> node.distance(11, 1)
        10.0
        """
        # pass  # replace this line!
    return math.sqrt((lat - self.apartment.lat) ** 2 + (lon - self.apartment.lon) ** 2)

    #Useful getter and setter methods below!
    @property
    def apartment(self) -> Apartment:
        return self._apartment

    @property
    def left(self) -> _KDTNode:
        return self._left

    @property
    def right(self) -> _KDTNode:
        return self._right

    @property
    def pivot(self) -> float:
        return self._pivot


class KDTree:
    """A KDTree that stores information about MunicipalTrees.
    It is made up of _KDTNode objects, and each _KDTNode object
    contains a MunicipalTree object.

    === Attributes ===
    tree_data: The data about trees stored in this KDTree.
    """
    KDTree: _KDTNode  # the root of the KD tree

    def __init__(self, data: list[Apartment]) -> None:
        """Initialize a new KD Tree (KDTree) using the input data.
        If <KDTree> is None, the KDTree is empty.
        """
        # build the tree from the apartment list, starting at depth 0
        self.KDTree = self.build_tree(data, 0)
        self.average_score = self.calculate_score_average() #initiali

    def calculate_score_average(self) -> float:
        """
        Calculate the average `score' of the apartment buildings contained
        within the tree.  You will want to write a recursive helper function
        for this!
        >>> with open('kdtree.pkl', 'rb') as file: loaded_object = pickle.load(file)
        >>> print(loaded_object.calculate_average())
        80.2
        """
        # pass # replace this line with your code!

        def _helper(node: Optional[_KDTNode]) -> Tuple[float, int]:
            if node is None:
                return 0.0, 0
            left_sum, left_count = _helper(node.left)
            right_sum, right_count = _helper(node.right)
            total_sum = left_sum + right_sum + node.apartment.score
            total_count = left_count + right_count + 1
            return total_sum, total_count

    total_sum, total_count = _helper(self.KDTree)
    return total_sum / total_count if total_count > 0 else 0.0

    def display_tree(self) -> None:
        """ Recursive method to DISPLAY (i.e. print to the console) a KDTREE.
        You may want to study this code in order to see how to write a recursive
        HELPER method. """
        lines, *_ = self._display_helper(self.KDTree)
        for line in lines:
            print(line)

    def _display_helper(self, node: _KDTNode) -> tuple[list[str], int, int, int]:
        """ Recursive helper method to DISPLAY a KDTREE."""
        # No child.
        if node.right is None and node.left is None:
            line = f'{round(float(node.apartment.lat), 3)},{round(float(node.apartment.lon), 3)}'
            width = len(line)
            height = 1
            middle = width // 2
            return [line], width, height, middle

        # Only left child.
        if node.right is None:
            lines, n, p, x = self._display_helper(node.left)
            s = f'{round(float(node.apartment.lat), 3)},{round(float(node.apartment.lon), 3)}'

            u = len(s)
            first_line = (x + 1) * ' ' + (n - x - 1) * '_' + s
            second_line = x * ' ' + '|' + (n - x - 1 + u) * ' '
            shifted_lines = [line + u * ' ' for line in lines]
            return [first_line, second_line] + shifted_lines, n + u, p + 2, n + u // 2

        # Only right child.
        if node.left is None:
            lines, n, p, x = self._display_helper(node.right)
            s = f'{round(float(node.apartment.lat), 3)},{round(float(node.apartment.lon), 3)}'

            u = len(s)
            first_line = s + x * '_' + (n - x) * ' '
            second_line = (u + x) * ' ' + '|' + (n - x - 1) * ' '
            shifted_lines = [u * ' ' + line for line in lines]
            return [first_line, second_line] + shifted_lines, n + u, p + 2, u // 2

        # Two children.
        left, n, p, x = self._display_helper(node.left)
        right, m, q, y = self._display_helper(node.right)
        s = f'{round(float(node.apartment.lat), 3)},{round(float(node.apartment.lon), 3)}'

        u = len(s)
        first_line = (x + 1) * ' ' + (n - x - 1) * '_' + s + y * '_' + (m - y) * ' '
        second_line = x * ' ' + '|' + (n - x - 1 + u + y) * ' ' + '|' + (m - y - 1) * ' '
        if p < q:
            left += [n * ' '] * (q - p)
        elif q < p:
            right += [m * ' '] * (p - q)
        zipped_lines = zip(left, right)
        lines = [first_line, second_line] + [a + u * ' ' + b for a, b in zipped_lines]
        return lines, n + m + u, max(p, q) + 2, n + u // 2

    def build_tree(self, data: list[Apartment], depth: int) -> Optional[_KDTNode]:
        """Build a KDTree from the input data. Build the tree using the median
        of the data at each level of the tree. The depth of the tree is used to
        determine whether to split the data by latitude or longitude.
        Split by latitude if depth is even, and by longitude if depth is odd.
        Your implementation will be recursive.
        >>> t = KDTree([Apartment( 1973, 'SOCIAL HOUSING', 86, 20, 20 ) , Apartment( 1973, 'SOCIAL HOUSING', 16, 10, 10 ), Apartment(1973, 'SOCIAL HOUSING', 87, 30, 30 )])
        >>> t.display_tree() # doctest: +NORMALIZE_WHITESPACE
        ____20.0,20.0____
        |                 |
        10.0,10.0         30.0,30.0
        """
        # pass #replace this line with your code!
        if not data:
            return None

        axis = depth % 2
        sorted_data = sorted(data, key=lambda apt: apt.lat if axis == 0 else apt.lon)
        median_idx = len(sorted_data) // 2
        median_apt = sorted_data[median_idx]
        pivot = median_apt.lat if axis == 0 else median_apt.lon

        node = _KDTNode(median_apt, pivot)
        node._left = self.build_tree(sorted_data[:median_idx], depth + 1)
        node._right = self.build_tree(sorted_data[median_idx + 1:], depth + 1)
        return node

    def lookup(self, lat: float, lon: float) -> bool:
        """Return True if there is a tree with the given latitude and longitude coordinated
        in the KDTree.
        >>> with open('kdtree.pkl', 'rb') as file: loaded_object = pickle.load(file)
        >>> print(loaded_object.lookup(43.6492006854,-79.4505020154))
        True
        >>> print(loaded_object.lookup(44, -79))
        False
        """
        # pass #replace this line with your code!
        def _helper(node: Optional[_KDTNode], depth: int) -> bool:
            if node is None:
                return False
            if node.apartment.lat == lat and node.apartment.lon == lon:
                return True
            axis = depth % 2
            if (axis == 0 and lat < node.pivot) or (axis == 1 and lon < node.pivot):
                return _helper(node.left, depth + 1)
            else:
                return _helper(node.right, depth + 1)
        return _helper(self.KDTree, 0)


    def get_nearest(self, lat: float, lon: float) -> Optional[Apartment]:
        """ Return the nearest tree to the given latitude and longitude.
        >>> with open('kdtree.pkl', 'rb') as file: loaded_object = pickle.load(file)
        >>> print(loaded_object.get_nearest(43.64,-79.43))
        SOCIAL HOUSING built in 1910 at (43.64, -79.436) with score 78.0
        >>> print(loaded_object.get_nearest(43.752,-79.607))
        PRIVATE built in 1910 at (43.65, -79.484) with score 89.0
        """
        # pass #replace this line with your code!
        if self.KDTree is None:
            return None
        best = None
        best_dist = float('inf')

        def _search(node: Optional[_KDTNode], depth: int) -> None:
            nonlocal best, best_dist
            if node is None:
                return

            current_dist = node.distance(lat, lon)
            if current_dist < best_dist:
                best = node.apartment
                best_dist = current_dist

            axis = depth % 2
            if (axis == 0 and lat < node.pivot) or (axis == 1 and lon < node.pivot):
                _search(node.left, depth + 1)
                if best_dist > abs(lat - node.pivot if axis == 0 else lon - node.pivot):
                    _search(node.right, depth + 1)
            else:
                _search(node.right, depth + 1)
                if best_dist > abs(lat - node.pivot if axis == 0 else lon - node.pivot):
                    _search(node.left, depth + 1)

            _search(self.KDTree, 0)
            return best

    def make_predictions(self, test_data: list[Apartment]) -> list[str]:
        """ Classify each apartment building in the input list test_data as being either `ABOVE AVERAGE'
        or `BELOW AVERAGE'. Classifications will be stored in a list of strings that is returned.
        >>> with open('kdtree.pkl', 'rb') as file: loaded_object = pickle.load(file)
        >>> aptlist = [Apartment( 1973, 'SOCIAL HOUSING', 86, 43, 79 )]
        >>> loaded_object.make_predictions(aptlist)
        ['BELOW AVERAGE']
        """
        # pass #replace this line with your code!

        predictions = []
        for apt in test_data:
            nearest = self.get_nearest(apt.lat, apt.lon)
            if nearest and nearest.score > self.average_score:
                predictions.append("ABOVE AVERAGE")
            else:
                predictions.append("BELOW AVERAGE")
        return predictions


def get_ground_truth(apartments: list[Apartment], threshold: float) -> list[str]:
    """
    Helper method to get ground truth labels from a list of apartments as
    well as a threshold denoting the average score for an apartment.
    Apartments with scores that are strictly over the average (or threshold) will be
    considered 'ABOVE AVERAGE' and others 'BELOW AVERAGE'.
    """
    ground_truth = []
    for i in apartments:
        if i.score > threshold:
            ground_truth.append('ABOVE AVERAGE')
        else:
            ground_truth.append('BELOW AVERAGE')
    return ground_truth

def calculate_accuracy(predictions: list[str], truth: list[str]) -> float:
    """
    Helper method to allow you to calculate the accuracy of predictions.
    Input is a list of ground truth labels and a list of predited labels.
    Output is a number between 0 and 100 representing the percent of predicted
    labels that are correct.
    """
    correct = 0
    counter = 0
    for i in predictions:
        if i == truth[counter]:
            correct += 1
    return (correct/len(predictions))*100

if __name__ == '__main__':

    print("Below is an example of a KD Tree:")
    with open('kdtree.pkl', 'rb') as file:
        loaded_object = pickle.load(file)
        loaded_object.display_tree()

    # Once you write code to build your own KD Tree,
    # you can test it using the doctests below.
    import doctest
    doctest.testmod()

    # You can also test your implementation using the following code.

    # Step 1: Build a Tree with 'Training Data'
    training_data = ApartmentReader.read_apartments('apartment-data/training.csv')
    kd = KDTree(training_data)

    # Step 2: Use tree to Predict Quality of apartments in a 'Test Set'
    test_data = ApartmentReader.read_apartments('apartment-data/testing.csv')
    predictions = kd.make_predictions(test_data)

    # Step 3: Ask ... do our predictions seem good?
    ground_truth = get_ground_truth(test_data, kd.average_score)
    accuracy = calculate_accuracy(predictions, ground_truth)
    print("\nThe accuracy of your Nearest Neighbour Classifier is: " + str(round(accuracy,2)) + " percent.\n")
