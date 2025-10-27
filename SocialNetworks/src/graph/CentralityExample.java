package graph;

import java.util.Map;

import util.GraphLoader;

public class CentralityExample {
	public static void main(String[] args) {
        CapGraph g = new CapGraph();
        System.out.println("Loading graph...");
        GraphLoader.loadGraph(g, "data/facebook_ucsd.txt");
        System.out.println("Graph loaded!");

        Map<Integer, Integer> centrality = DegreeCentrality.compute(g);
        DegreeCentrality.printTop(centrality, 10);
    }
}
