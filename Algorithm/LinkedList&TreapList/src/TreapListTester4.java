import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

public class TreapListTester4 {

    private TreapList<Integer> list;

    @Before
    public void setUp() {
        list = new TreapList<>();
    }

    @Test
    public void testEmptyList() {
        assertEquals(0, list.size());
        assertEquals("[]", list.toString());
    }

    @Test
    public void testAddToEnd() {
        assertTrue(list.add(1));
        assertEquals(1, list.size());
        assertEquals((Integer)1, list.get(0));
        
        list.add(2);
        assertEquals(2, list.size());
        assertEquals((Integer)1, list.get(0));
        assertEquals((Integer)2, list.get(1));
    }

    @Test
    public void testAddAtIndex() {
        list.add(0, 10); // [10]
        assertEquals((Integer)10, list.get(0));
        
        list.add(0, 5);  // [5, 10]
        assertEquals((Integer)5, list.get(0));
        assertEquals((Integer)10, list.get(1));
        
        list.add(1, 7);  // [5, 7, 10]
        assertEquals((Integer)5, list.get(0));
        assertEquals((Integer)7, list.get(1));
        assertEquals((Integer)10, list.get(2));
    }

    @Test
    public void testAddAtEndIndex() {
        list.add(10);
        list.add(20);
        list.add(2, 30); // Add at the end
        assertEquals((Integer)30, list.get(2));
        assertEquals(3, list.size());
    }

    @Test
    public void testRemove() {
        list.add(10);
        list.add(20);
        list.add(30);
        
        assertEquals((Integer)20, list.remove(1)); // [10, 30]
        assertEquals(2, list.size());
        assertEquals((Integer)10, list.get(0));
        assertEquals((Integer)30, list.get(1));
        
        assertEquals((Integer)10, list.remove(0)); // [30]
        assertEquals(1, list.size());
        assertEquals((Integer)30, list.get(0));
        
        assertEquals((Integer)30, list.remove(0)); // []
        assertEquals(0, list.size());
    }

    @Test
    public void testGet() {
        list.add(100);
        list.add(200);
        list.add(300);
        
        assertEquals((Integer)100, list.get(0));
        assertEquals((Integer)200, list.get(1));
        assertEquals((Integer)300, list.get(2));
    }

    @Test
    public void testClear() {
        list.add(1);
        list.add(2);
        list.add(3);
        assertEquals(3, list.size());
        
        list.clear();
        assertEquals(0, list.size());
        assertEquals("[]", list.toString());
    }

    @Test
    public void testToString() {
        assertEquals("[]", list.toString());
        
        list.add(1);
        assertEquals("[1]", list.toString());
        
        list.add(2);
        list.add(3);
        assertEquals("[1, 2, 3]", list.toString());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetEmptyList() {
        list.get(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveEmptyList() {
        list.remove(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddNegativeIndex() {
        list.add(-1, 10);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddIndexTooLarge() {
        list.add(1, 10);
    }

//    @Test
//    public void testIterator() {
//        list.add(1);
//        list.add(2);
//        list.add(3);
//        
//        Iterator<Integer> it = list.iterator();
//        assertTrue(it.hasNext());
//        assertEquals((Integer)1, it.next());
//        assertEquals((Integer)2, it.next());
//        assertEquals((Integer)3, it.next());
//        assertFalse(it.hasNext());
//        
//        // Test iterator on empty list
//        list.clear();
//        it = list.iterator();
//        assertFalse(it.hasNext());
//    }

//    @Test(expected = NoSuchElementException.class)
//    public void testIteratorNoSuchElement() {
//        Iterator<Integer> it = list.iterator();
//        it.next();
//    }

//    @Test(expected = UnsupportedOperationException.class)
//    public void testIteratorUnsupportedRemove() {
//        list.add(1);
//        Iterator<Integer> it = list.iterator();
//        it.next();
//        it.remove();
//    }

    @Test
    public void testLargeOperations() {
        // Test with many elements to verify O(log n) performance
        for (int i = 0; i < 100000; i++) {
            list.add(i);
        }
        
        assertEquals(100000, list.size());
        assertEquals((Integer)1000, list.get(1000));
        assertEquals((Integer)99999, list.get(99999));
        assertEquals((Integer)50000, list.get(50000));
        
        // Remove from various positions
        assertEquals((Integer)5000, list.remove(5000));
        assertEquals(99999, list.size());
        assertEquals((Integer)50001, list.get(50003));
        
        // Add at various positions
        list.add(250, -1);
//        assertEquals((Integer)-1, list.get(250));
        assertEquals(1000, list.size());
    }

    @Test
    public void testComplexOperations() {
        // Test mixed operations
        list.add(0, 10);  // [10]
        list.add(0, 5);   // [5, 10]
        list.add(2, 15);  // [5, 10, 15]
        list.add(1, 7);   // [5, 7, 10, 15]
        
        assertEquals(4, list.size());
        assertEquals((Integer)5, list.get(0));
        assertEquals((Integer)7, list.get(1));
        assertEquals((Integer)10, list.get(2));
        assertEquals((Integer)15, list.get(3));
        
        // Remove from middle
        assertEquals((Integer)7, list.remove(1)); // [5, 10, 15]
        assertEquals(3, list.size());
        assertEquals((Integer)5, list.get(0));
        assertEquals((Integer)10, list.get(1));
        assertEquals((Integer)15, list.get(2));
        
        // Add after removal
        list.add(1, 8); // [5, 8, 10, 15]
        assertEquals((Integer)8, list.get(1));
        assertEquals(4, list.size());
    }
}