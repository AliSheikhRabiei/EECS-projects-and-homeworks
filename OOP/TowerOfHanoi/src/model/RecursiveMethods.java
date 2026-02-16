package model;

public class RecursiveMethods {
	
	/*
	 * Assumption: 'disks' denote names of disks that
	 * are sorted in increasing sizes, to simulate 
	 * a stack of increasing-sized disks.
	 * For example, {"A", "B", "C"} means that
	 * "A" is the smallest disk on the top, and
	 * "C" is the biggest disk on the bottom. 
	 * There are 3 pegs. 
	 */
	public static void towerOfHanoi(String[] disks) {
		tohHelper(disks, 0, disks.length - 1, 1, 3);
	}
	
	/*
	 * towerOfHanoiHelper(disks, from, to, p1, p2) means
	 * to move {disks[from], disks[from+1], ..., disks[to]} from peg p1 to peg p2, 
	 * using peg (6 - p1 - p2) as the intermediate peg. 
	 */
	private static void tohHelper(String[] disks, int from, int to, int p1, int p2) {
		if(from > to) {
			
		}
		else if(from == to) {
			System.out.println("move disk " + disks[to] + " from peg " + p1 + " to peg " + p2);
		}
		else {
			int intermediate = 6 - p1 - p2;
			tohHelper(disks, from, to - 1, p1, intermediate);
			System.out.println("move disk " + disks[to] + " from peg " + p1 + " to peg " + p2);
			tohHelper(disks, from, to - 1, intermediate, p2);
		}
	}
}