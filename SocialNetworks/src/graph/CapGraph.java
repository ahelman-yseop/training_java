/**
 * Implementation of a directed graph.
 * 
 * Each vertex is represented by an integer, and edges are stored as adjacency lists.
 * The class supports operations such as adding vertices and edges,
 * extracting an ego network around a given vertex, and computing strongly connected components (SCCs)
 * using Kosaraju’s algorithm.
 */
package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

/**
 * @author Agathe Helman
 * 
 * For the warm up assignment, you must implement your Graph in a class
 * named CapGraph.  Here is the stub file.
 *
 */
public class CapGraph implements Graph {
	
	/** 
	 * Internal graph representation.
	 * key: vertex ID, value: set of vertices reachable by outgoing edges.
	 */
	private HashMap<Integer, HashSet<Integer>> graph;

	
    /** 
     * Constructs an empty directed graph.
     */
    public CapGraph() {
        this.graph = new HashMap<>();
    }
    

	/* (non-Javadoc)
	 * @see graph.Graph#addVertex(int)
	 */
	@Override
	public void addVertex(int num) {
		// Week 1.
        graph.putIfAbsent(num, new HashSet<>());
	}

	
	/* (non-Javadoc)
	 * @see graph.Graph#addEdge(int, int)
	 */
	@Override
	public void addEdge(int from, int to) {
		// Week 1
		// Ensure both vertices exist
        addVertex(from);
        addVertex(to);

        // Add directed edge
        graph.get(from).add(to);
	}

	
	/* (non-Javadoc)
	 * @see graph.Graph#getEgonet(int)
	 */
	@Override
	public Graph getEgonet(int center) {
		// Week 1 
		CapGraph egonet = new CapGraph();

        if (!graph.containsKey(center)) {
            return egonet; // empty graph
        }

        // collect all relevant nodes
        HashSet<Integer> nodes = new HashSet<>();
        nodes.add(center);
        nodes.addAll(graph.get(center));

        // add all vertices
        for (int v : nodes) {
            egonet.addVertex(v);
        }

        // add all edges among vertices
        for (int v : nodes) {
            for (int neighbor : graph.get(v)) {
                if (nodes.contains(neighbor)) {
                    egonet.addEdge(v, neighbor);
                }
            }
        }

        return egonet;
	}

	
    /**
     * Depth-first search helper that records finishing times of vertices.
     * Used in the first phase of Kosaraju's algorithm.
     * 
     * @param v 		the current vertex
     * @param visited 	the set of already visited vertices
     * @param finished 	the stack storing vertices by finishing order
     */
    private void dfsFinish(int v, HashSet<Integer> visited, Stack<Integer> finished) {
        visited.add(v);
        for (int neighbor : graph.get(v)) {
            if (!visited.contains(neighbor)) {
                dfsFinish(neighbor, visited, finished);
            }
        }
        finished.push(v);
    }
    
    
    /**
     * Generates the transpose (reversed) version of this graph.
     * 
     * @return a new CapGraph with all edges reversed
     */
    private CapGraph getTranspose() {
        CapGraph transposed = new CapGraph();
        for (int v : graph.keySet()) {
            transposed.addVertex(v);
            for (int neighbor : graph.get(v)) {
                transposed.addVertex(neighbor);
                transposed.addEdge(neighbor, v);
            }
        }
        return transposed;
    }
	
    
    /**
     * Depth-first search helper that collects all vertices reachable from a starting vertex.
     * Used in the second phase of Kosaraju's algorithm.
     * 
     * @param v 		the current vertex
     * @param visited 	the set of already visited vertices
     * @param component the set collecting vertices in the current SCC
     */
    private void dfsCollect(int v, HashSet<Integer> visited, HashSet<Integer> component) {
        visited.add(v);
        component.add(v);
        for (int neighbor : graph.get(v)) {
            if (!visited.contains(neighbor)) {
                dfsCollect(neighbor, visited, component);
            }
        }
    }
    
    
	/* (non-Javadoc)
	 * @see graph.Graph#getSCCs()
	 */
	@Override
	public List<Graph> getSCCs() {
		// Week 1 
		List<Graph> result = new ArrayList<>();
        Stack<Integer> finished = new Stack<>();
        HashSet<Integer> visited = new HashSet<>();

        // First DFS pass. Record finish times
        for (int v : graph.keySet()) {
            if (!visited.contains(v)) {
                dfsFinish(v, visited, finished);
            }
        }

        // Reverse the graph
        CapGraph reversed = getTranspose();

        // Second DFS pass in reverse finishing order
        visited.clear();
        while (!finished.isEmpty()) {
            int v = finished.pop();
            if (!visited.contains(v)) {
                HashSet<Integer> component = new HashSet<>();
                reversed.dfsCollect(v, visited, component);

                // Build subgraph for this SCC
                CapGraph sccGraph = new CapGraph();
                for (int node : component) {
                    sccGraph.addVertex(node);
                }
                for (int node : component) {
                    for (int neighbor : reversed.graph.get(node)) {
                        if (component.contains(neighbor)) {
                            sccGraph.addEdge(node, neighbor);
                        }
                    }
                }
                result.add(sccGraph);
            }
        }
        return result;
	}

	
	/* (non-Javadoc)
	 * @see graph.Graph#exportGraph()
	 */
	@Override
	public HashMap<Integer, HashSet<Integer>> exportGraph() {
		// Week 1
		// Return a deep copy for safety
        HashMap<Integer, HashSet<Integer>> copy = new HashMap<>();
        for (Integer v : graph.keySet()) {
            copy.put(v, new HashSet<>(graph.get(v)));
        }
        return copy;
	}

	
	/**
	 * Helper method to check contents
	 * Prints the graph’s adjacency list to standard output.
	 * Useful for debugging or verifying graph structure.
	 */
    public void printGraph() {
        for (Integer v : graph.keySet()) {
            System.out.println(v + " -> " + graph.get(v));
        }
    }
}
