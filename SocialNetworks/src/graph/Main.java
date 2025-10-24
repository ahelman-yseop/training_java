package graph;

import java.util.List;

public class Main {
	public static void main(String[] args) {
        System.out.println("Test 1");

        CapGraph g = new CapGraph();
        g.addVertex(1);
        g.addVertex(2);
        g.addVertex(3);
        g.addEdge(1, 2);
        g.addEdge(2, 3);
        g.addEdge(3, 1);

        System.out.println("\nGraph structure:");
        g.printGraph();

        System.out.println("\nExported graph:");
        System.out.println(g.exportGraph());
        
        System.out.println("\nTest 2");

        CapGraph h = new CapGraph();
        h.addEdge(1, 2);
        h.addEdge(2, 3);
        h.addEdge(3, 1);
        h.addEdge(3, 4);
        h.addEdge(4, 5);
        h.addEdge(5, 4);

        System.out.println("\nOriginal graph:");
        h.printGraph();

        System.out.println("\nEgonet of 3:");
        CapGraph ego = (CapGraph) h.getEgonet(3);
        ego.printGraph();

        System.out.println("\nSCCs:");
        List<Graph> sccs = h.getSCCs();
        for (int i = 0; i < sccs.size(); i++) {
            System.out.println("SCC " + (i + 1) + ":");
            ((CapGraph) sccs.get(i)).printGraph();
        }
    }
    
}
