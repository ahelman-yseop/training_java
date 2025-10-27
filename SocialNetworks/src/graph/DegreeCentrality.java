package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class DegreeCentrality {
	
    /**
     * Computes the degree centrality for each vertex in the given graph.
     * For an undirected graph (as in the Facebook dataset), we count both incoming and outgoing edges.
     * 
     *
     * @param g the {@link CapGraph} 	instance representing the network
     * @return a map associating each vertex ID with its degree centrality (number of connections)
     */
    public static Map<Integer, Integer> compute(CapGraph g) {
        Map<Integer, HashSet<Integer>> graphData = g.exportGraph();
        Map<Integer, Integer> centrality = new HashMap<>();

        // Initialize all degrees to 0
        for (int v : graphData.keySet()) {
            centrality.put(v, 0);
        }

        // Count degrees (since edges are stored only as outgoing)
        for (Map.Entry<Integer, HashSet<Integer>> entry : graphData.entrySet()) {
            int from = entry.getKey();
            for (int to : entry.getValue()) {
                centrality.put(from, centrality.get(from) + 1);
                centrality.put(to, centrality.get(to) + 1); // make undirected
            }
        }

        return centrality;
    }


    /**
     * Prints the top N vertices with the highest degree centrality values.
     *
     * @param centrality a map containing vertex IDs as keys and their degree centrality
     *                   values (number of connections) as values
     * @param topN       the number of top vertices to display (e.g., 10 for the top 10 most connected)
     */
    public static void printTop(Map<Integer, Integer> centrality, int topN) {
        List<Map.Entry<Integer, Integer>> sorted =
                new ArrayList<Map.Entry<Integer, Integer>>(centrality.entrySet());

        Collections.sort(sorted, new Comparator<Map.Entry<Integer, Integer>>() {
            public int compare(Map.Entry<Integer, Integer> e1, Map.Entry<Integer, Integer> e2) {
                return e2.getValue() - e1.getValue(); // descending
            }
        });

        System.out.println("Top " + topN + " most connected friends:");
        for (int i = 0; i < Math.min(topN, sorted.size()); i++) {
            Map.Entry<Integer, Integer> e = sorted.get(i);
            System.out.printf("Friend %d → %d connections%n", e.getKey(), e.getValue());
        }
    }
}
