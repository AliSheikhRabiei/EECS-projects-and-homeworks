public class A4demo2{
    public static void main(String[] args) {
        // pick a file you actually have in the project root
        Graph g = new Graph("Edges.csv");

        String origin = "Oshawa";
        int hops = 2;

        System.out.println("Origin: " + origin + ", hops=" + hops);
        for (String s : Graphs.nearby(g, origin, hops)) {
            System.out.println("  " + s);
        }

        // OPTIONAL: a couple of simple checks you can eyeball
        // Example from the handout (these should appear if your CSV matches):
        // "Port Perry, 1", "Ajax, 1", "Bowmanville, 1", "Uxbridge, 2", "Scarborough, 2"
    }
}
