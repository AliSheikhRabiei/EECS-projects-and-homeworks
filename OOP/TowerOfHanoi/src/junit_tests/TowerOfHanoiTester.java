package junit_tests;
import model.RecursiveMethods;

//made this to replicate professor Jacki Wang example, it is not as good as his but it is working!
public class TowerOfHanoiTester {

	public static void main(String[] args) {
		System.out.println("******************");
		System.out.println("Move 0 disk: ");
		System.out.println("******************");
		String[] disks0 = {};
		RecursiveMethods.towerOfHanoi(disks0);
		
		System.out.println("******************");
		System.out.println("Move 1 disk: A");
		System.out.println("******************");
		String[] disks1 = {"A"};
		RecursiveMethods.towerOfHanoi(disks1);
		
		System.out.println("******************");
		System.out.println("Move 2 disks: A, B");
		System.out.println("******************");
		String[] disks2 = {"A", "B"};
		RecursiveMethods.towerOfHanoi(disks2);
		
		System.out.println("******************");
		System.out.println("Move 3 disks: A, B, C");
		System.out.println("******************");
		String[] disks3 = {"A", "B", "C"};
		RecursiveMethods.towerOfHanoi(disks3);
		
		System.out.println("******************");
		System.out.println("Move 3 disks: A, B, C, D");
		System.out.println("******************");
		String[] disks4 = {"A", "B", "C", "D"};
		RecursiveMethods.towerOfHanoi(disks4);
	}
}
