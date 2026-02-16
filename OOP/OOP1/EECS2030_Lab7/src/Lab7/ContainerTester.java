package Lab7;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



class ContainerTester {

	@Test
	void testQueue1() {
		Container container = new Queue("input.txt"); 
		int expected = 10; 
		int actual = container.getSize();
		assertEquals(expected, actual, "The queue is not populated properly or getSize() method is not correct");
	}

	@Test
	void testQueue2() {
		Container container = new Queue("in.txt"); 
		int expected = 0; 
		int actual = container.getSize();
		assertEquals(expected, actual, "The queue is not populated properly or getSize() method is not correct");
	}
	@Test
	void testQueueAdd1() {
		Container container = new Queue("in.txt"); 
		container.add("Jack");
		int expected = 1; 
		int actual = container.getSize();
		assertEquals(expected, actual, "The queue is not populated properly or getSize() method is not correct");
	}
	@Test
	void testQueueTop() {
		Queue container = new Queue("in.txt"); 
		container.add("Jack");
		String obj = (String) container.top(); 
		boolean actual = obj.compareTo("Jack") == 0;
		assertEquals( true, actual, "The queue's top() or add() is not correct");
	}
	@Test
	void testQueueRemove() {
		Container container = new Queue("input.txt"); 
		container.add("Jack");
		String obj = (String) container.remove(); 
		boolean actual = obj.compareTo("Q1") == 0 && container.getSize() == 10;
		assertEquals( true, actual, "The queue's remove(), add() or getSize() is not correct");
	}
	@Test
	void testStack1() {
		Container container = new Stack("input.txt"); 
		int expected = 9; 
		int actual = container.getSize();
		assertEquals(expected, actual, "The stack is not populated properly or getSize() method is not correct");
	}
	@Test
	void testStack2() {
		Container container = new Stack("in.txt"); 
		int expected = 7; 
		int actual = container.getSize();
		assertEquals(expected, actual, "The stack is not populated properly or getSize() method is not correct");
	}
	@Test
	void testStackAdd1() {
		Container container = new Stack("in.txt"); 
		container.add("Jack");
		int expected = 8; 
		int actual = container.getSize();
		assertEquals(expected, actual, "The stack is not populated properly or getSize() method is not correct");
	}
	@Test
	void testStackTop() {
		Stack container = new Stack("in.txt"); 
		container.add("Jack");
		String obj = (String) container.top(); 
		boolean actual = obj.compareTo("Jack") == 0;
		assertEquals( true, actual, "The stack's top() or add() is not correct");
	}
	@Test
	void testStackRemove() {
		Container container = new Stack("input.txt"); 
		container.add("Jack");
		String obj = (String) container.remove(); 
		boolean actual = obj.compareTo("Jack") == 0 && container.getSize() == 9;
		assertEquals( true, actual, "The stack's remove(), add() or getSize() is not correct");
	}

}
