package junit_tests;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

import model.RecursiveMethods;

public class TowerOfHanoiTests {
	private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

	@Before
	public void setUp() {
	    System.setOut(new PrintStream(outputStreamCaptor));
	}
	
	@Test
	public void test0() { 
		String[] disks = {};
		RecursiveMethods.towerOfHanoi(disks);
		String[] steps = {	
			
		};
		
		assertEquals(expected(steps), outputStreamCaptor.toString());
	}

	@Test
	public void test1() { 
		String[] disks = {"A"};
		RecursiveMethods.towerOfHanoi(disks);
		String[] steps = {	
			"move disk A from peg 1 to peg 3"
		};
		
		assertEquals(expected(steps), outputStreamCaptor.toString());
	}
	
	@Test
	public void test2() { 
		String[] disks = {"A", "B"};
		RecursiveMethods.towerOfHanoi(disks);
		String[] steps = {	
			"move disk A from peg 1 to peg 2",
			
			"move disk B from peg 1 to peg 3",
			
			"move disk A from peg 2 to peg 3"
		};
		
		assertEquals(expected(steps), outputStreamCaptor.toString());
	}
	
	@Test
	public void test3() { 
		String[] disks = {"A", "B", "C"};
		RecursiveMethods.towerOfHanoi(disks);
		String[] steps = {	
			"move disk A from peg 1 to peg 3",
			"move disk B from peg 1 to peg 2",
			"move disk A from peg 3 to peg 2",
			
			"move disk C from peg 1 to peg 3",
			
			"move disk A from peg 2 to peg 1",
			"move disk B from peg 2 to peg 3",
			"move disk A from peg 1 to peg 3"
		};
		
		assertEquals(expected(steps), outputStreamCaptor.toString());
	}
	
	@Test
	public void test4() { 
		String[] disks = {"A", "B", "C", "D"};
		RecursiveMethods.towerOfHanoi(disks);
		String[] steps = {	
			"move disk A from peg 1 to peg 2",
			"move disk B from peg 1 to peg 3",
			"move disk A from peg 2 to peg 3",
			/* --- */
			"move disk C from peg 1 to peg 2",
			/* --- */
			"move disk A from peg 3 to peg 1",
			"move disk B from peg 3 to peg 2",
			"move disk A from peg 1 to peg 2",
			//--------------------------------
			"move disk D from peg 1 to peg 3",
			//--------------------------------
			"move disk A from peg 2 to peg 3",
			"move disk B from peg 2 to peg 1",
			"move disk A from peg 3 to peg 1",
			/* --- */
			"move disk C from peg 2 to peg 3",
			/* --- */
			"move disk A from peg 1 to peg 2",
			"move disk B from peg 1 to peg 3",
			"move disk A from peg 2 to peg 3"
		};
		
		assertEquals(expected(steps), outputStreamCaptor.toString());
	}
	
	private String expected(String[] strings) {
		String expected = "";
		for(String s : strings) {
			expected += s + "\n";
		}
		return expected;
	}

}
