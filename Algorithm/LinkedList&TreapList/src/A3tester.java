import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.MethodOrderer;

@TestMethodOrder(MethodOrderer.MethodName.class)
class A3Tester {

	@Test 
	@Timeout(1)
	void test01() throws IOException {
		Files.deleteIfExists(new File ("input").toPath());
		Files.deleteIfExists(new File ("output").toPath());
		generateRandom("input", 1);
		A3.mergeSort("input", "output");
		assertTrue(isSorted("output"));
	}

	@Test
	@Timeout(1)
	void test02() {
		generateRandom("input", 2);
		A3.mergeSort("input", "output");
		assertTrue(isSorted("output"));
	}

	@Test
	@Timeout(1)
	void test03() {
		generateRandom("input", 3);
		A3.mergeSort("input", "output");
		assertTrue(isSorted("output"));
	}

	@Test
	@Timeout(1)
	void test04() {
		generateRandom("input", 4);
		A3.mergeSort("input", "output");
		assertTrue(isSorted("output"));
	}

	@Test
	@Timeout(1)
	void test05_sorted() {
		generateSorted("input", 31);
		A3.mergeSort("input", "output");
		assertTrue(isSorted("output"));
	}

	@Test
	@Timeout(1)
	void test06_large() {
		generateRandom("input", 1021);
		A3.mergeSort("input", "output");
		assertTrue(isSorted("output"));
	}

	@Test
	@Timeout(10)
	void test07_larger() {
		generateRandom("input", 524289);
		A3.mergeSort("input", "output");
		assertTrue(isSorted("output"));
	}

	@Test
	@Timeout(60)
	void test08_Part2_30pts() {
		try {
			Part2.main(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private static boolean isSorted(String inputFile) {
		int previous = Integer.MIN_VALUE;
		int current;

		try {
			FileInputStream fis = new FileInputStream(inputFile);
			ObjectInputStream ois = new ObjectInputStream(fis);

			while(ois.available() > 0) {
				current = ois.readInt();
				if (current < previous) { ois.close(); return false;}
				previous = current;
			}
			fis.close();
			ois.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	private static void generateRandom(String outputFile, long count) {
		Random r = new Random();

		//write random numbers into a specified file
		try {
			FileOutputStream fos = new FileOutputStream(outputFile);
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			for (long i = 0; i < count; i++)
				oos.writeInt(r.nextInt());

			oos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void generateSorted(String outputFile, long count) {
		try {
			FileOutputStream fos = new FileOutputStream(outputFile);
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			for (long i = 0; i < count; i++)
				oos.writeInt((int) i);

			oos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

//	private static void printFile(String inputFile) {
//		try {
//			FileInputStream fis = new FileInputStream(inputFile);
//			ObjectInputStream ois = new ObjectInputStream(fis);
//
//			System.out.println("File Size: " + fis.getChannel().size() + " bytes");
//			int count = 0;
//			while(ois.available() > 0) {
//				//System.out.println(ois.readInt());
//				count++;
//				System.out.print(ois.readInt() +", ");
//			}
//			System.out.println(": " + count + " total");
//
//			fis.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	public static void main(String[] args) {
//		//generateSorted("t.tmp", 10);
//		generateRandom("input", 50000000);
//		//printFile("input");
//		//System.out.println("Is sorted: " + isSorted("t.tmp"));		
//		
//		//A2.mergeSort("input", "output");
//		
//		//printFile("output");
//		System.out.println("Is sorted: " + isSorted("output"));		
//	}
}
