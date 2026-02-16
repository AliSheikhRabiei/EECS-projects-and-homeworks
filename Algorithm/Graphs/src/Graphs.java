import java.util.HashSet;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.ArrayDeque;
import java.util.Deque;

public class Graphs {
	/**
	 * Description
	 * @param graph input graph
	 * @param origin starting vertex
	 * @param numberOfEdges
	 * @return Set of vertices within the specified numberOfEdges
	 * Returns the set of cities reachable from origin by at most edges. 
     * Each element is formatted as
     * "CityName, d" where d is the hop distance (1..numberOfEdges).
     * Edge weights/times are ignored, we treat the graph as unweighted.
	 */
	public static Set<String> nearby(Graph graph, String origin, int numberOfEdges) {
		//DUMMY CODE (STUB) TODO
		
        LinkedHashSet<String> result = new LinkedHashSet<>();
        if (graph == null || origin == null || origin.isEmpty() || numberOfEdges <= 0) {
            
//		Set <String>result = new HashSet<>();
//		result.add("Etobicoke, 1");
//		result.add("Mississauga, 5");
		return result;
	}
	
	//private methods go here if needed
	

        // If origin is not in the graph, return empty
        Iterable<Edge> originAdj = graph.adj(new Vertex(origin));
        if (originAdj == null) return result;

        // BFS by layers, hop count
        HashSet<String> visited = new HashSet<>();
        Deque<Vertex> qV = new ArrayDeque<>();
        Deque<Integer> qD = new ArrayDeque<>();

        visited.add(origin);
        qV.addLast(new Vertex(origin));
        qD.addLast(0);

        while (!qV.isEmpty()) {
            Vertex u = qV.removeFirst();
            int d = qD.removeFirst();

            //so it doesnt expand beyond the allowed depth
            if (d == numberOfEdges) continue;

            Iterable<Edge> adjIt = graph.adj(u);
            if (adjIt == null) continue;

            for (Edge e : adjIt) {
                Vertex v = e.other(u);          // neighbor
                String name = v.name;
                if (visited.contains(name)) continue;

                int nd = d + 1;                 // neighbor distance
                visited.add(name);

                // record neighbor if within 1 and numberOfEdges
                if (nd <= numberOfEdges) {
                    result.add(name + ", " + nd);
                }

                // enqueue only if we still can expand further
                if (nd < numberOfEdges) {
                    qV.addLast(v);
                    qD.addLast(nd);
                }
            }
        }
        return result;
    }


}
