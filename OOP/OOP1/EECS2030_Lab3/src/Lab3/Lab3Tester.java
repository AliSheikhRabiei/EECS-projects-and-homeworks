package Lab3;

import static org.junit.Assert.*;


import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;



@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Lab3Tester {

	
	// sum
	
	@Test
	public void testSum0() {
		int start = 0;
		int end = 0; 
		int  sum= 0;
		assertEquals("Failed at sum (" + start + ", " + end + ")", sum, Lab3.sum(start, end));
			
	}
	@Test
	public void testSum1() {
		int start = 0;
		int end = 5; 
		int  sum= 0;
		for (int i = start ; i <= end ; i++) 
			sum  += i;
		
		assertEquals("Failed at sum (" + start + ", " + end + ")", sum, Lab3.sum(start, end));
			
	}

	@Test
	public void testMakeString0() {
		char init = ' '; 
		int n = 1;
		String result = ""; 
		assertEquals("Failed at makeString(" + init + ", " + n + ")", result, Lab3.makeString(init, n));	
	}
	@Test
	public void testMakeString1() {
		char init = '*'; 
		int n = 5;
		String result = ""; 
		for (int i = 0 ; i < n ; i++)
			result += init;
		assertEquals("Failed at makeString(" + init + ", " + n + ")", result, Lab3.makeString(init, n));	
	}

	
	@Test
	public void testInterlace1() {

		String str = "";
		assertEquals("Failed: interlace(\"*\",\"-\",0)", str, Lab3.interlace("*","-",0));
	}
	@Test
	public void testInterlace2() {

		String str = "*";
		assertEquals("Failed: interlace(\"*\",\"-\",1)", str, Lab3.interlace("*","-",1));
	}

	
	@Test
	public void testGetSubstring0() {

		String str1 = "This is a {} practice";
		String result = "";
		char open = '{';
		char close = '}';
		assertEquals("Failed: getSubstring(\"This is a {} practice\", '{', '}')", result, Lab3.getSubstring("This is a {} practice", '{', '}'));
	}
	@Test
	public void testGetSubstring1() {

		String str1 = "x + y + z - ( y * z) / 3 * n ";
		String result = " y * z";
		char open = '(';
		char close = ')';
		assertEquals("Failed: getSubstring(\"x + y + z - ( y * z) / 3 * n \", \"(\", \")\")", result, Lab3.getSubstring("x + y + z - ( y * z) / 3 * n ", '(', ')'));
	}

	@Test
	public void testDecimalToBinary0() {
		int decimal = 0; 
		String binary = "0";
		assertEquals("Failed: decimalToBinary(0)", binary, Lab3.decimalToBinary(0));
	}
	@Test
	public void testDecimalToBinary1() {
		int decimal = 0; 
		String binary = "0";
		assertEquals("Failed: decimalToBinary(0)", binary, Lab3.decimalToBinary(0));
	}


}