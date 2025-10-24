package graph;

import java.util.HashSet;

import util.GraphLoader;

public class LoadExample {
	public static void main(String[] args) {
        CapGraph g = new CapGraph();

        System.out.println("Loading graph...");
        GraphLoader.loadGraph(g, "data/facebook_ucsd.txt");  // or twitter_combined.txt

        System.out.println("Graph loaded successfully!");
        System.out.println("Number of vertices: " + g.exportGraph().size());

        // Estimate number of edges
        int edges = 0;
        for (HashSet<Integer> entry : g.exportGraph().values()) {
            edges += entry.size();
        }
        System.out.println("Number of edges: " + edges);

        // Optional: print a few vertices
        System.out.println("\nSample edges:");
        int count = 0;
        for (Integer v : g.exportGraph().keySet()) {
            System.out.println(v + " -> " + g.exportGraph().get(v));
            if (++count > 10) break;
        }
    }
}
