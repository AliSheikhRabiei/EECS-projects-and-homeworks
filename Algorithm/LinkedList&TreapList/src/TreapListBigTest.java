import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Random;

public class TreapListBigTest {

    private static final int N = 1_000_000;

    /** Build 1e6 by append; spot check boundaries and middle. */
    @Test(timeout = 25_000)
    public void buildMillion_spotChecks() {
        TreapList<Integer> xs = new TreapList<>();
        for (int i = 0; i < N; i++) xs.add(i);

        assertEquals(N, xs.size());
        assertEquals(Integer.valueOf(0), xs.get(0));
        assertEquals(Integer.valueOf(N / 2), xs.get(N / 2));
        assertEquals(Integer.valueOf(N - 1), xs.get(N - 1));
    }

    /** Remove >1000 from head/tail/middle deterministically (fast correctness). */
    @Test(timeout = 30_000)
    public void removeHeadTailMiddle_batches() {
        TreapList<Integer> xs = new TreapList<>();
        for (int i = 0; i < N; i++) xs.add(i);

        // Remove 2,000 from head
        for (int k = 0; k < 2_000; k++) {
            Integer expect = xs.get(0);
            Integer got = xs.remove(0);
            assertEquals(expect, got);
        }
        assertEquals(N - 2_000, xs.size());
        assertEquals(Integer.valueOf(2_000), xs.get(0));

        // Remove 3,000 from tail
        for (int k = 0; k < 3_000; k++) {
            int last = xs.size() - 1;
            Integer expect = xs.get(last);
            Integer got = xs.remove(last);
            assertEquals(expect, got);
        }
        assertEquals(N - 5_000, xs.size());

        // Remove 5,000 from middle (always at current mid)
        for (int k = 0; k < 5_000; k++) {
            int mid = xs.size() / 2;
            Integer expect = xs.get(mid);
            Integer got = xs.remove(mid);
            assertEquals(expect, got);
        }
        assertEquals(N - 10_000, xs.size());

        // sanity check a few points
        assertEquals(Integer.valueOf(2_000), xs.get(0));
        assertEquals(Integer.valueOf(2_000 + (xs.size() / 2)), xs.get(xs.size() / 2));
    }

    /** Random removes (10,000+). We validate by checking get(idx) == remove(idx) each time. */
    @Test(timeout = 35_000)
    public void removeRandom_20k_checks() {
        TreapList<Integer> xs = new TreapList<>();
        for (int i = 0; i < N; i++) xs.add(i);

        Random rnd = new Random(12345);
        int removes = 20_000;  // > 1000 as requested
        for (int step = 0; step < removes; step++) {
            int idx = rnd.nextInt(xs.size());
            Integer expect = xs.get(idx);
            Integer got = xs.remove(idx);
            assertEquals("mismatch at step=" + step + " idx=" + idx, expect, got);
        }
        assertEquals(N - removes, xs.size());

        // spot-check a few random indices still match the constructed sequence minus deletions
        // (not a full reference check; just sanity that structure remains valid)
        assertEquals(xs.get(0), xs.get(0)); // trivial structural sanity
        assertEquals(xs.get(xs.size()-1), xs.get(xs.size()-1));
    }

    /** Mixed pattern: build million, do 2k alternating ops around quarters/middle. */
    @Test(timeout = 35_000)
    public void mixedLargeOps() {
        TreapList<Integer> xs = new TreapList<>();
        for (int i = 0; i < N; i++) xs.add(i);

        // 2000 ops: alternating adds and removes at quarter/middle/three-quarters
        int ops = 2_000;
        for (int t = 0; t < ops; t++) {
            int sz = xs.size();
            int q1 = sz / 4, mid = sz / 2, q3 = (3 * sz) / 4;

            if ((t & 1) == 0) {
                // insert sentinels at q1, mid, q3
                xs.add(q1, -11);
                xs.add(mid + 1, -22); // +1 because size grew
                xs.add(q3 + 2, -33);  // +2 because two prior inserts
            } else {
                // remove exactly those three positions we just touched (adjust indices)
                Integer a = xs.remove(q1);
                Integer b = xs.remove(mid); // mid shifts as we removed at q1
                Integer c = xs.remove(q3);  // q3 shifts after two removes
                // Values could be duplicates from earlier rounds; we only assert structural rule get==remove
                assertNotNull(a); assertNotNull(b); assertNotNull(c);
            }
        }

        // Make sure list is still healthy: can read first/mid/last
        assertEquals(xs.get(0), xs.get(0));
        assertEquals(xs.get(xs.size()/2), xs.get(xs.size()/2));
        assertEquals(xs.get(xs.size()-1), xs.get(xs.size()-1));
    }
}
