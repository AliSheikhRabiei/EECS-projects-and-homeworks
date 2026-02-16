import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

public class GraphsNearbyTest {

    @Test
    public void emptyAndEdgeCases() {
        Graph g = new Graph("Edges.csv");

        // hops <= 0 => empty
        assertTrue(Graphs.nearby(g, "Toronto", 0).isEmpty());

        // unknown origin => empty
        assertTrue(Graphs.nearby(g, "NO_CITY", 2).isEmpty());
    }

    @Test
    public void distancesAreWithinHops_andWellFormed() {
        Graph g = new Graph("Edges.csv");
        int hops = 3;
        Set<String> out = Graphs.nearby(g, "Toronto", hops);

        // No duplicates because Set; also should not contain origin
        for (String s : out) {
            // format: "Name, d"
            int comma = s.lastIndexOf(',');
            assertTrue("Bad format: " + s, comma > 0 && comma < s.length()-1);

            String dStr = s.substring(comma + 1).trim();
            int d = Integer.parseInt(dStr);
            assertTrue("distance out of range: " + s, d >= 1 && d <= hops);
        }
    }

    @Test
    public void sampleFromHandout_ifYourCSVMatches() {
        Graph g = new Graph("Edges.csv");
        Set<String> out = Graphs.nearby(g, "Oshawa", 2);

        // These should appear if the provided CSV matches the assignment map
        assertTrue(out.contains("Port Perry, 1"));
        assertTrue(out.contains("Ajax, 1"));
        assertTrue(out.contains("Bowmanville, 1"));
        assertTrue(out.contains("Uxbridge, 2"));
        assertTrue(out.contains("Scarborough, 2"));
    }
}
