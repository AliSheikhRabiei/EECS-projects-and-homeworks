package Lab6;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContainerTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testList_1() {
		MyList myarray = new MyList(); 
		myarray.add("a");
		int expected = 1; 
		int actual = myarray.getSize();
		assertEquals(actual, expected, "MyList: The add() method does not correctly add to the array and/or getSize() is not correct.");
	}
	
	@Test
	void testList_2() {
		MyList myarray = new MyList(); 
		boolean expected = true; 
		boolean actual = myarray.isEmpty();
		assertEquals(actual, expected, "MyList: isEmpty() is not correct.");
	}
	
	@Test
	void testList_3() {
		MyList myarray = new MyList(); 
		myarray.add("a");
		boolean expected = false; 
		boolean actual = myarray.isEmpty();
		assertEquals(actual, expected, "MyList: isEmpty() is not correct.");
	}

	@Test
	void testList_4() {
		MyList myarray = new MyList(); 
		for( int i = 0; i < 11; i++)
			myarray.add("a");
		int expected = 11; 
		int actual = myarray.getSize();
		assertEquals(actual, expected, "MyList: The add method does not grow the size of the list, when it is needed.");
	}
	
	@Test
	void testList_5() {
		MyList myarray = new MyList(); 
		for( int i = 0; i < 22; i++)
			myarray.add("a");
		int expected = 22; 
		int actual = myarray.getSize();
		assertEquals(actual, expected, "MyList: The add method does not grow the size of the list, when it is needed.");
	}

	@Test
	void testList_6() {
		MyList myarray = new MyList(); 
		for( int i = 0; i < 5; i++)
			myarray.add(String.valueOf(i));
		assertEquals(myarray.toString(), "[0 1 2 3 4]", "MyList: toString() for MyList is not properly implemented.");
	}
	@Test
	void testList_7() {
		MyList myarray = new MyList(); 
		for( int i = 0; i < 5; i++)
			myarray.add(String.valueOf(i));
		myarray.remove("0");
		assertEquals(myarray.toString(), "[1 2 3 4]", "MyList: remove() for MyList does not remove the first element correctly.");
	}
	
	@Test
	void testList_8() {
		MyList myarray = new MyList(); 
		for( int i = 0; i < 5; i++)
			myarray.add(String.valueOf(i));
		myarray.remove("4");
		assertEquals(myarray.toString(), "[0 1 2 3]", "MyList: remove() for MyList does not remove the last element correctly.");
	}

	@Test
	void testList_9() {
		MyList myarray = new MyList(); 
		for( int i = 0; i < 5; i++)
			myarray.add(String.valueOf(i));
		myarray.remove("2");
		assertEquals(myarray.toString(), "[0 1 3 4]", "MyList: remove() for MyList does not remove the element in the middle.");
	}
	
	@Test
	void testList_10() {
		MyList myarray = new MyList(); 
		for( int i = 0; i < 5; i++)
			myarray.add(String.valueOf(i));
		assertEquals(myarray.get(0), "0", "MyList: get(0) does not return the correct element.");
	}
	
	@Test
	void testList_11() {
		MyList myarray = new MyList(); 
		for( int i = 0; i < 5; i++)
			myarray.add(String.valueOf(i));
		assertEquals(myarray.get(4), "4", "MyList: get() does not return the last element correctly.");
	}
	
	@Test
	void testList_12() {
		MyList myarray = new MyList(); 
		for( int i = 0; i < 5; i++)
			myarray.add(String.valueOf(i));
		assertEquals(myarray.get(2), "2", "MyList: get() does not return the element in the middle of the list, correctly.");
	}
	@Test
	void testSet_1() {
		MySet myarray = new MySet(); 
		for( int i = 0; i < 11; i++)
			myarray.add("a");
		int expected = 1; 
		int actual = myarray.getSize();
		assertEquals(actual, expected, "MySet: The add() method does not correctly add to the set and/or getSize() is not correct.");
	}
	
	@Test
	void testSet_2() {
		MySet myarray = new MySet(); 
		boolean expected = true; 
		boolean actual = myarray.isEmpty();
		assertEquals(actual, expected, "MySet: isEmpty() is not correct.");
	}
	
	@Test
	void testSet_3() {
		MySet myarray = new MySet(); 
		myarray.add("a");
		boolean expected = false; 
		boolean actual = myarray.isEmpty();
		assertEquals(actual, expected, "MySet: isEmpty() is not correct.");
	}

	@Test
	void testSet_4() {
		MySet myarray = new MySet(); 
		for( int i = 0; i < 11; i++)
			myarray.add(String.valueOf(i));
		int expected = 11; 
		int actual = myarray.getSize();
		assertEquals(actual, expected, "MySet: The add method does not grow the size of the set, when it is needed.");
	}
	
	@Test
	void testSet_5() {
		MySet myarray = new MySet(); 
		for( int i = 0; i < 22; i++)
			myarray.add(String.valueOf(i));
		int expected = 22; 
		int actual = myarray.getSize();
		assertEquals(actual, expected, "MySet: The add method does not grow the size of the set, when it is needed.");
	}

	@Test
	void testSet_6() {
		MySet myarray = new MySet(); 
		for( int i = 0; i < 5; i++)
			myarray.add(String.valueOf(i));
		assertEquals(myarray.toString(), "[0 1 2 3 4]", "MySet: toString() for MySet is not properly implemented.");
	}
	@Test
	void testSet_7() {
		MySet myarray = new MySet(); 
		for( int i = 0; i < 5; i++)
			myarray.add(String.valueOf(i));
		myarray.remove("0");
		assertEquals(myarray.toString(), "[1 2 3 4]", "MySet: remove() for MySet does not remove the first element correctly.");
	}
	
	@Test
	void testSet_8() {
		MySet myarray = new MySet(); 
		for( int i = 0; i < 5; i++)
			myarray.add(String.valueOf(i));
		myarray.remove("4");
		assertEquals(myarray.toString(), "[0 1 2 3]", "MySet: remove() for MySet does not remove the last element correctly.");
	}

	@Test
	void testSet_9() {
		MySet myarray = new MySet(); 
		for( int i = 0; i < 5; i++)
			myarray.add(String.valueOf(i));
		myarray.remove("2");
		assertEquals(myarray.toString(), "[0 1 3 4]", "MySet: remove() for MySet does not remove the element in the middle.");
	}

	@Test
	void testSet_10() {
		MySet myarray = new MySet(); 
		for( int i = 0; i < 5; i++)
			myarray.add("a");
		for( int i = 0; i < 5; i++)
			myarray.add("b");
		for( int i = 0; i < 5; i++)
			myarray.add("c");
		int expected = 3; 
		int actual = myarray.getSize();
		assertEquals(actual, expected, "MySet: The add() method does not correctly add to the set and/or getSize() is not correct.");
	}

	@Test
	void testSet_11() {
		MySet myarray = new MySet(); 
		for( int i = 0; i < 5; i++)
			myarray.add("a");
		for( int i = 0; i < 5; i++)
			myarray.add("b");
		for( int i = 0; i < 5; i++)
			myarray.add("c");
		String expected = "[a b c]"; 
		String actual = myarray.toString();
		assertEquals(actual, expected, "MySet: The add() method does not correctly add to the set.");
	}

}
