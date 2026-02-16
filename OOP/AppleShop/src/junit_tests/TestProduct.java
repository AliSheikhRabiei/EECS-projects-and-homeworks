package junit_tests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Product;

public class TestProduct {

    @Test
    public void test_product_1() {
        Product p = new Product();

        assertNull(p.getModel());

        assertTrue(p.getFinish() == null);
        assertFalse(p.getFinish() != null); 

        assertTrue(p.getStorage() == 0);
        assertEquals(0, p.getStorage());
    
        assertFalse(p.hasCellularConnectivity());
        assertFalse(p.hasCellularConnectivity() == true);
        assertTrue(p.hasCellularConnectivity() == false);
        assertTrue(p.hasCellularConnectivity() != true);
    	assertTrue(!(p.hasCellularConnectivity() == true));
    	assertTrue(!p.hasCellularConnectivity());
    	
    	assertEquals(0.0, p.getOriginalPrice(), 0.1);
    	assertEquals(0.0, p.getDiscountValue(), 0.1);
    	assertEquals(0.0, p.getPrice(), 0.1);
    	
    	assertEquals("null null 0GB (CellularConnectivity: false): $(0.00 - 0.00)", p.toString());
    	
    }
    
    @Test
    public void test_product_2() {
    	Product p = new Product("iPad Pro 12.9", 1709.00);
    	
    	//copied and edited 1
    	assertNotNull(p.getModel());
    	assertEquals("iPad Pro 12.9", p.getModel());
    	assertTrue(p.getModel().equals("iPad Pro 12.9"));
    	//this one was wrong i dont want to write in case i make mistake in future

        assertTrue(p.getFinish() == null);
        assertFalse(p.getFinish() != null); 

        assertTrue(p.getStorage() == 0);
        assertEquals(0, p.getStorage());
    
        assertFalse(p.hasCellularConnectivity());
        assertFalse(p.hasCellularConnectivity() == true);
        assertTrue(p.hasCellularConnectivity() == false);
        assertTrue(p.hasCellularConnectivity() != true);
    	assertTrue(!(p.hasCellularConnectivity() == true));
    	assertTrue(!p.hasCellularConnectivity());
    	
    	assertEquals(1709.00, p.getOriginalPrice(), 0.1);
    	assertEquals(0.0, p.getDiscountValue(), 0.1);
    	assertEquals(1709.00, p.getPrice(), 0.1);
    	
    	assertEquals("iPad Pro 12.9 null 0GB (CellularConnectivity: false): $(1709.00 - 0.00)", p.toString());
    	
    }
    
    //copied and edited 2 
    @Test
    public void test_product_3() {
    	Product p = new Product(new String("iPad Pro 12.9"), 1709.00);
    	
    	//copy and edit
    	assertNotNull(p.getModel());
    	assertEquals("iPad Pro 12.9", p.getModel());
    	assertTrue(p.getModel().equals("iPad Pro 12.9"));
    	//this one was wrong i dont want to write in case i make mistake in future

    	p.setFinish("Space Grey");
        assertFalse(p.getFinish() == null);
        assertTrue(p.getFinish() != null); 
    	assertEquals("Space Grey", p.getFinish());
    	assertTrue(p.getFinish().equals("Space Grey"));
    	
    	p.setStorage(1000);
        assertTrue(p.getStorage() == 1000);
        assertEquals(1000, p.getStorage());
        
        p.setHasCellularConnectivity(true);
        //i did while video was paused!
        assertFalse(!p.hasCellularConnectivity());
        assertFalse(!(p.hasCellularConnectivity() == true));
        assertTrue(!(p.hasCellularConnectivity() == false));
        assertTrue(p.hasCellularConnectivity() == true);
    	assertTrue(!(p.hasCellularConnectivity() != true));
    	assertTrue(p.hasCellularConnectivity());
    	
    	assertEquals(1709.00, p.getOriginalPrice(), 0.1);
    	p.setDiscountValue(220);
    	assertEquals(220.00, p.getDiscountValue(), 0.1);
    	assertEquals(1489.00, p.getPrice(), 0.1);
    	
    	assertEquals("iPad Pro 12.9 Space Grey 1000GB (CellularConnectivity: true): $(1709.00 - 220.00)", p.toString());
    	
    }
    
    
}
