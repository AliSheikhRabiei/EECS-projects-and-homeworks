import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.Test;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TreapListTester3 {

    // --- Constructor / interface / field policy like the old tester ---

    @Test
    public void A_constructorInterfacePolicy_10_pts() {
        // no public fields
        Field[] fields = TreapList.class.getDeclaredFields();
        for (Field f : fields) {
            assertTrue("List class contains a public field: " + f.getName(),
                    !Modifier.isPublic(f.getModifiers()));
        }
        // exactly one public constructor
        assertTrue("Number of constructors != 1",
                TreapList.class.getDeclaredConstructors().length == 1);

        // implements exactly one interface: java.util.List
        assertTrue("List interface not implemented or other interfaces present",
                TreapList.class.getInterfaces().length == 1
                        && TreapList.class.getInterfaces()[0].getName().equals("java.util.List"));
    }

    // --- add() bounds + simple appends ---

    @Test
    public void B_addBoundsAndAppend_5_pts() {
        TreapList<String> xs = new TreapList<>();
        try {
            xs.add(1, "x");
            fail("Exception was to be thrown for add(1,...) on empty list");
        } catch (IndexOutOfBoundsException ok) {}

        assertTrue(xs.add("0"));
        assertEquals("0", xs.get(0));
    }

    // tiny indexed sequence; same pattern as prof’s “[1, 2, 0]” test
    @Test
    public void C_indexedAddOrder_10_pts() {
        TreapList<String> xs = new TreapList<>();
        xs.add(0, "0");
        xs.add(0, "1");
        xs.add(1, "2");
        assertTrue(xs.toString().equals("[1, 2, 0]")
                || (xs.get(0).equals("1") && xs.get(1).equals("2") && xs.get(2).equals("0")));
    }

    // --- remove() bounds + basic behavior ---

    @Test
    public void D_removeBounds_5_pts() {
        TreapList<String> xs = new TreapList<>();
        try {
            xs.remove(0);
            fail("Exception was to be thrown for remove(0) on empty list");
        } catch (IndexOutOfBoundsException ok) {}
    }

    @Test
    public void E_removeMiddleKeepsOrder_10_pts() {
        TreapList<String> xs = new TreapList<>();
        xs.add("1"); xs.add("2"); xs.add("3");
        assertEquals(3, xs.size());
        assertEquals("2", xs.remove(1));
        assertEquals(2, xs.size());
        assertTrue(xs.toString().equals("[1, 3]")
                || (xs.get(0).equals("1") && xs.get(1).equals("3")));
    }

    // --- get() bounds + basics ---

    @Test
    public void F_getBounds_5_pts() {
        TreapList<String> xs = new TreapList<>();
        try {
            xs.get(0);
            fail("Exception was to be thrown for get(0) on empty list");
        } catch (IndexOutOfBoundsException ok) {}
    }

    @Test
    public void G_getBasic_5_pts() {
        TreapList<String> xs = new TreapList<>();
        xs.add("0"); xs.add("1");
        assertEquals("0", xs.get(0));
        assertEquals("1", xs.get(1));
    }

    // --- clear / size / toString ---

    @Test
    public void H_clear_5_pts() {
        TreapList<String> xs = new TreapList<>();
        assertEquals(0, xs.size());
        xs.add("1"); xs.add("2"); xs.add("3");
        assertEquals(3, xs.size());
        xs.clear();
        assertEquals(0, xs.size());
        try { xs.get(0); fail("get(0) after clear should throw"); }
        catch (IndexOutOfBoundsException ok) {}
    }

    @Test
    public void I_sizeCounts_5_pts() {
        TreapList<String> xs = new TreapList<>();
        assertEquals(0, xs.size());
        xs.add("1");
        assertEquals(1, xs.size());
        xs.add("9");
        assertEquals(2, xs.size());
    }

    @Test
    public void J_toStringEmpty_5_pts() {
        TreapList<String> xs = new TreapList<>();
        assertEquals("[]", xs.toString());
    }

    @Test
    public void K_toStringFilled_10_pts() {
        TreapList<String> xs = new TreapList<>();
        xs.add("1"); xs.add("2"); xs.add("3");
        assertEquals("[1, 2, 3]", xs.toString());
    }

    // --- iterator semantics (grader often relies on this indirectly) ---

    @Test
    public void L_iteratorSemantics_10_pts() {
        TreapList<Integer> xs = new TreapList<>();
        for (int i = 0; i < 7; i++) xs.add(i);
        int expect = 0;
        java.util.Iterator<Integer> it = xs.iterator();
        while (it.hasNext()) {
            assertEquals(Integer.valueOf(expect++), it.next());
        }
        assertEquals(7, expect);

        // next() on exhaustion must throw NoSuchElementException
        try { it.next(); fail("next() when exhausted must throw"); }
        catch (java.util.NoSuchElementException ok) {}

        // remove() unsupported
        it = xs.iterator();
        it.next();
        try { it.remove(); fail("iterator.remove() must be unsupported"); }
        catch (UnsupportedOperationException ok) {}
    }

    // --- big randomized equivalence vs ArrayList (like prof’s) ---

    @Test(timeout = 800)
    public void M_randomAddRemove_25_pts() {
        Random rnd = new Random(42);
        List<Integer> ref = new ArrayList<>();
        List<Integer> treap = new TreapList<>();

        for (int i = 0; i < 30_000; i++) {
            if (rnd.nextDouble() < 0.5) {
                int pos = rnd.nextInt(ref.size() + 1);
                ref.add(pos, i);
                treap.add(pos, i);
            } else if (!ref.isEmpty()) {
                int pos = rnd.nextInt(ref.size());
                Integer x = ref.remove(pos);
                Integer y = treap.remove(pos);
                assertEquals("remove value mismatch at step " + i, x, y);
            }

            // spot-check a random index if non-empty
            if (!ref.isEmpty()) {
                int j = rnd.nextInt(ref.size());
                assertEquals("get mismatch at step " + i + ", idx " + j, ref.get(j), treap.get(j));
            }
        }

        // final deep check
        assertEquals("final size mismatch", ref.size(), treap.size());
        assertEquals("final toString mismatch", ref.toString(), treap.toString());
    }

    // --- very lenient performance sanity (not a benchmark) ---

    @Test(timeout = 5_000)
    public void N_logPerformanceSanity_FAILifPathological_15_pts() {
        final int SIZE = 65_536;
        final int CONST_FACTOR = 200; // lenient: treap add-mid should be within 200x ArrayList append

        List<Integer> al = new ArrayList<>();
        List<Integer> tl = new TreapList<>();
        List<Integer> ll = new LinkedList<>();
        long t0, t1;

        // ArrayList append baseline (amortized O(1))
        t0 = System.nanoTime();
        for (int i = 0; i < SIZE; i++) al.add(i);
        t1 = System.nanoTime();
        long baseAL = t1 - t0;

        // Treap add front/mid/end (O(log n) each)
        t0 = System.nanoTime();
        for (int i = 0; i < SIZE; i++) tl.add(0, i);
        t1 = System.nanoTime();
        long treapFront = t1 - t0;

        tl.clear();
        t0 = System.nanoTime();
        for (int i = 0; i < SIZE; i++) tl.add(i / 2, i);
        t1 = System.nanoTime();
        long treapMid = t1 - t0;

        tl.clear();
        t0 = System.nanoTime();
        for (int i = 0; i < SIZE; i++) tl.add(i, i);
        t1 = System.nanoTime();
        long treapEnd = t1 - t0;

        // LinkedList middle (O(n) per op) should be far worse than treap mid for this SIZE.
        ll.clear();
        t0 = System.nanoTime();
        for (int i = 0; i < SIZE; i++) ll.add(ll.size() / 2, i);
        t1 = System.nanoTime();
        long linkedMid = t1 - t0;

        // print for manual inspection (kept minimal)
        System.out.println("AL append (ms): " + baseAL/1e6
                + " | Treap front/mid/end (ms): " + treapFront/1e6 + " / " + treapMid/1e6 + " / " + treapEnd/1e6
                + " | Linked mid (ms): " + linkedMid/1e6);

        // sanity assertions (lenient)
        assertTrue("Treap mid too slow vs AL baseline",
                treapMid < baseAL * CONST_FACTOR);
        assertTrue("Treap end too slow vs AL baseline",
                treapEnd < baseAL * CONST_FACTOR);
        assertTrue("Treap front too slow vs AL baseline",
                treapFront < baseAL * CONST_FACTOR);

        // and treap middle should be (much) faster than LinkedList middle for this size
        assertTrue("Treap middle should beat LinkedList middle",
                treapMid * 2 < linkedMid); // factor 2 slack
    }
}
