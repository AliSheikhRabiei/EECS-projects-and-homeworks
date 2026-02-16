import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class TreapListTester {

    @Test
    public void empty_behaves() {
        TreapList<Integer> xs = new TreapList<>();
        assertEquals(0, xs.size());
        assertEquals("[]", xs.toString());
        try { xs.get(0); fail(); } catch (IndexOutOfBoundsException expected) {}
        try { xs.remove(0); fail(); } catch (IndexOutOfBoundsException expected) {}
        assertFalse(xs.iterator().hasNext());
    }

    @Test
    public void append_addE_and_get() {
        TreapList<Integer> xs = new TreapList<>();
        for (int i = 0; i < 20; i++) assertTrue(xs.add(i));
        assertEquals(20, xs.size());
        for (int i = 0; i < 20; i++) assertEquals(Integer.valueOf(i), xs.get(i));
        assertEquals("[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19]", xs.toString());
    }

    @Test
    public void indexed_inserts_front_middle_end() {
        TreapList<String> xs = new TreapList<>();
        xs.add(0, "0");            // [0]
        xs.add(0, "1");            // [1,0]
        xs.add(1, "2");            // [1,2,0]
        assertEquals("[1, 2, 0]", xs.toString());

        xs.add(xs.size(), "3");    // end
        xs.add(0, "4");            // front
        xs.add(2, "5");            // middle
        assertEquals("[4, 1, 5, 2, 0, 3]", xs.toString());
    }

    @Test
    public void removes_front_middle_end_then_drain() {
        TreapList<Integer> xs = new TreapList<>();
        for (int i = 0; i < 10; i++) xs.add(i); // [0..9]

        assertEquals(Integer.valueOf(0), xs.remove(0));                // front
        assertEquals(Integer.valueOf(9), xs.remove(xs.size()-1));      // end
        assertEquals(Integer.valueOf(5), xs.remove(xs.size()/2));      // middle

        while (xs.size() > 0) xs.remove(xs.size()/2);
        assertEquals(0, xs.size());
        assertEquals("[]", xs.toString());
    }

    @Test
    public void iterator_walks_in_order() {
        TreapList<Integer> xs = new TreapList<>();
        for (int i = 0; i < 7; i++) xs.add(i);
        Iterator<Integer> it = xs.iterator();
        int expect = 0;
        while (it.hasNext()) {
            assertEquals(Integer.valueOf(expect++), it.next());
        }
        assertEquals(7, expect);
        try { it.next(); fail(); } catch (NoSuchElementException expected) {}
    }

    @Test
    public void randomized_against_ArrayList() {
        TreapList<Integer> a = new TreapList<>();
        ArrayList<Integer> b = new ArrayList<>();
        int seed = 1337;
        int state = seed;
        for (int step = 0; step < 50_000; step++) {
            state = 1103515245 * state + 12345;
            int r = state >>> 1;
            int op = r % 3;
            int sz = a.size();

            if (op == 0) { // add(e) append
                int v = r & 1023;
                assertTrue(a.add(v)); b.add(v);
            } else if (op == 1) { // add(i,e)
                int v = (r >> 10) & 1023;
                int idx = (sz == 0) ? 0 : (r % (sz + 1));
                a.add(idx, v); b.add(idx, v);
            } else {
                if (sz > 0) {
                    int idx = r % sz;
                    Integer x = a.remove(idx);
                    Integer y = b.remove(idx);
                    assertEquals(y, x);
                }
            }

            // spot checks
            assertEquals(b.size(), a.size());
            if (a.size() > 0) {
                int i = (r >>> 11) % a.size();
                assertEquals(b.get(i), a.get(i));
            }
        }
        assertEquals(b.toString(), a.toString());
        assertEquals(b, a); // equals() provided
    }

    @Test
    public void bounds_checks() {
        TreapList<Integer> xs = new TreapList<>();
        try { xs.add(-1, 1); fail(); } catch (IndexOutOfBoundsException expected) {}
        xs.add(0, 1); // ok
        try { xs.add(3, 2); fail(); } catch (IndexOutOfBoundsException expected) {}
        try { xs.get(1); fail(); } catch (IndexOutOfBoundsException expected) {}
        try { xs.remove(2); fail(); } catch (IndexOutOfBoundsException expected) {}
    }
}
