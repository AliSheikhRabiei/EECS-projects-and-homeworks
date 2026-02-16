import static org.junit.Assert.*;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import java.util.regex.Pattern;

public class GraphsNearbyHardTest {

    // ---------- helpers ----------

    /** Writes a tiny CSV graph file the way Graph(String) expects. */
    private static void writeCSV(String file, int V, int E, String... edges) throws IOException {
        // edges are "From,To,dist,time"
        try (PrintWriter out = new PrintWriter(new FileWriter(file))) {
            out.println(V);
            out.println(E);
            for (String e : edges) {
                out.println(e);
            }
        }
    }

    /** Utility: assert set contains "Name, d". */
    private static void assertHas(Set<String> s, String name, int d) {
        assertTrue("missing: " + name + ", " + d, s.contains(name + ", " + d));
    }

    // ---------- tests ----------

    @Test
    public void oneEdge_basicReachability() throws Exception {
        String fn = "__g_one.csv";
        // V = 2, E = 1:  A-B
        writeCSV(fn, 2, 1, "A,B,1.0,5.0");
        try {
            Graph g = new Graph(fn);
            assertTrue(Graphs.nearby(g, "A", 0).isEmpty());
            Set<String> s = Graphs.nearby(g, "A", 1);
            assertEquals(1, s.size());
            assertHas(s, "B", 1);

            // Higher hops shouldn’t invent more vertices
            s = Graphs.nearby(g, "A", 3);
            assertEquals(1, s.size());
            assertHas(s, "B", 1);
        } finally {
            new java.io.File(fn).delete();
        }
    }

    @Test
    public void selfLoop_doesNotAddOrigin_noInfiniteLoop() throws Exception {
        String fn = "__g_self.csv";
        // V = 1, E = 1: A-A (self loop)
        writeCSV(fn, 1, 1, "A,A,1.0,1.0");
        try {
            Graph g = new Graph(fn);
            assertTrue("self-loop should produce empty set",
                    Graphs.nearby(g, "A", 2).isEmpty());
        } finally {
            new java.io.File(fn).delete();
        }
    }

    @Test
    public void parallelEdges_noDuplicates() throws Exception {
        String fn = "__g_parallel.csv";
        // V = 2, E = 2: A-B twice
        writeCSV(fn, 2, 2, "A,B,1.0,1.0", "A,B,2.0,2.0");
        try {
            Graph g = new Graph(fn);
            Set<String> s = Graphs.nearby(g, "A", 1);
            assertEquals("only one B expected", 1, s.size());
            assertHas(s, "B", 1);
        } finally {
            new java.io.File(fn).delete();
        }
    }

    @Test
    public void squareGraph_minDistanceWins() throws Exception {
        String fn = "__g_square.csv";
        // A-B-C and A-D-C (two paths to C). All undirected because loader adds both directions.
        // V=4, E=4:
        writeCSV(fn, 4, 4,
                "A,B,1.0,1.0",
                "B,C,1.0,1.0",
                "A,D,1.0,1.0",
                "D,C,1.0,1.0");
        try {
            Graph g = new Graph(fn);
            Set<String> s = Graphs.nearby(g, "A", 2);
            // 1-hop: B, D; 2-hop: C
            assertEquals(3, s.size());
            assertHas(s, "B", 1);
            assertHas(s, "D", 1);
            assertHas(s, "C", 2); // must be 2, not 3+
        } finally {
            new java.io.File(fn).delete();
        }
    }

    @Test
    public void longChain_largeHopsStopsProperly() throws Exception {
        String fn = "__g_chain.csv";
        // Chain A-B-C-D-E (5 vertices, 4 edges)
        writeCSV(fn, 5, 4,
                "A,B,1.0,1.0",
                "B,C,1.0,1.0",
                "C,D,1.0,1.0",
                "D,E,1.0,1.0");
        try {
            Graph g = new Graph(fn);
            Set<String> s = Graphs.nearby(g, "A", 10);
            // reachable: B(1), C(2), D(3), E(4) -> size 4
            assertEquals(4, s.size());
            assertHas(s, "B", 1);
            assertHas(s, "C", 2);
            assertHas(s, "D", 3);
            assertHas(s, "E", 4);
        } finally {
            new java.io.File(fn).delete();
        }
    }

    @Test
    public void unknownOrigin_andNegativeHops() throws Exception {
        String fn = "__g_small.csv";
        writeCSV(fn, 2, 1, "X,Y,1.0,1.0");
        try {
            Graph g = new Graph(fn);
            assertTrue(Graphs.nearby(g, "NOPE", 2).isEmpty());
            assertTrue(Graphs.nearby(g, "X", -3).isEmpty());
        } finally {
            new java.io.File(fn).delete();
        }
    }

    @Test
    public void outputFormatting_strictCommaSpaceDistance() throws Exception {
        String fn = "__g_fmt.csv";
        writeCSV(fn, 2, 1, "P,Q,7.5,9.0");
        try {
            Graph g = new Graph(fn);
            Set<String> s = Graphs.nearby(g, "P", 1);
            assertEquals(1, s.size());
            String only = s.iterator().next();
            // Must be: "City, d" (exactly one comma, one space, integer distance)
            assertTrue("bad format: " + only,
                    Pattern.matches(".+?,\\s+[0-9]+", only));
            assertEquals("Q, 1", only);
        } finally {
            new java.io.File(fn).delete();
        }
    }
}
