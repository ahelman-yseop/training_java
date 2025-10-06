/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;


import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;

import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {
	// WEEK 3: Add member variables
	private Map<GeographicPoint, MapNode> vertices;
	private int numEdges;
	
	
	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph(){
		// WEEK 3
		// Initialize the main data structures
	    vertices = new HashMap<>();
	    numEdges = 0;
	}
	
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices() {
		// WEEK 3
		return vertices.size();
	}
	
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices(){
		// WEEK 3
		return vertices.keySet();
	}
	
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges(){
		// WEEK 3
		return numEdges;
	}

	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location){
		// WEEK 3
		
		// check for invalid inputs
		if (location == null || vertices.containsKey(location)) {
			return false;
		}
		
	    vertices.put(location, new MapNode(location));
	    return true;	
	}

	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
					String roadType, double length) throws IllegalArgumentException {
		// WEEK 3
		
		// check for invalid arguments
		if (from == null || to == null || roadName == null || roadType == null || length < 0)
	        throw new IllegalArgumentException("Invalid edge arguments");
		
		// ensure both intersections already exist in the graph
	    if (!vertices.containsKey(from) || !vertices.containsKey(to))
	        throw new IllegalArgumentException("Both points must be in graph");

	    MapNode startNode = vertices.get(from);
	    startNode.addEdge(new MapEdge(from, to, roadName, roadType, length));
	    numEdges++;
	}
	

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched){
		// WEEK 3
		
		// Sanity checks
	    if (!isValidPoints(start, goal)) {
	    	return null;
	    }

	    // Standard BFS setup
	    Queue<GeographicPoint> toExplore = new LinkedList<>();
	    Set<GeographicPoint> visited = new HashSet<>();
	    Map<GeographicPoint, GeographicPoint> parent = new HashMap<>();

	    // core BFS loop
	    boolean found = runBfsSearch(start,goal, nodeSearched, toExplore, visited, parent);


	    if (!found) {
	    	return null;
	    }

	    // Reconstruct path from start to goal
	    return reconstructPath(start, goal, parent);
	}
	
	
	/**
	 * Validates that the start and goal points are non-null and exist in the graph.
	 *
	 * @param start 	The starting geographic point.
	 * @param goal  	The goal geographic point.
	 * @return true if both points are valid and present in the graph, false otherwise.
	 */
	private boolean isValidPoints(GeographicPoint start, GeographicPoint goal) {
		// null check
	    if (start == null || goal == null) {
	        System.err.println("ERROR: Start or goal point is null");
	        return false;
	    }
	    // existence in the graph
	    if (!vertices.containsKey(start) || !vertices.containsKey(goal)) {
	        System.err.println("ERROR: Start or goal point not found in graph");
	        return false;
	    }   
	    return true;
	}
	
	
	/**
	 * Executes the core logic of the BFS.
	 *
	 * @param start         The starting geographic point.
	 * @param goal          The goal geographic point to reach.
	 * @param nodeSearched  A hook for visualization (called each time a node is visited).
	 * @param toExplore     A queue of nodes to be explored (FIFO).
	 * @param visited       A set of nodes already visited during the search.
	 * @param parent        A map recording each node's predecessor for path reconstruction.
	 * @return true if the goal node was found, false otherwise.
	 */
	private boolean runBfsSearch(GeographicPoint start, GeographicPoint goal, 
			Consumer<GeographicPoint> nodeSearched, Queue<GeographicPoint> toExplore,
			Set<GeographicPoint> visited, Map<GeographicPoint, GeographicPoint> parent) {
		
		// initialization
		toExplore.add(start);
	    visited.add(start);

	    // main BFS loop
	    while (!toExplore.isEmpty()) {
	    	// take next node from the queue
	        GeographicPoint current = toExplore.remove();
	        // Hook for visualization
	        nodeSearched.accept(current);

	        // Goal check
	        if (current.equals(goal)) {
	            return true;
	        }

	        // Explore neighbors
	        for (MapEdge edge : vertices.get(current).getEdges()) {
	        	// retrieve outgoing edge from current node
	            GeographicPoint neighbor = edge.getEnd();
	            // visit unvisited neighbors
	            if (!visited.contains(neighbor)) {
	                visited.add(neighbor);
	                parent.put(neighbor, current);
	                toExplore.add(neighbor);
	            }
	        }
	    }
	    return false;
	}
	
	
	/**
	 * Reconstructs the path from the start point to the goal point
	 * using the parent map built during BFS.
	 *
	 * Each node’s parent indicates the node from which it was first reached.
	 * Starting from the goal, this method backtracks through the parent map
	 * until it reaches the start, building the path in the correct order.
	 *
	 * @param start  The starting geographic point.
	 * @param goal   The goal geographic point.
	 * @param parent A map linking each visited node to its predecessor.
	 * @return A list of geographic points representing the path from start to goal,
	 *         including both endpoints.
	 */
	private List<GeographicPoint> reconstructPath(GeographicPoint start, GeographicPoint goal,
            Map<GeographicPoint, GeographicPoint> parent) {
		// create empty path list
	    List<GeographicPoint> path = new LinkedList<>();
	    // start backtracking from the goal
	    GeographicPoint curr = goal;
	    // backtrack until the start is reached
	    while (!curr.equals(start)) {
	        path.add(0, curr);
	        curr = parent.get(curr);
	    }
	    // add the start point
	    path.add(0, start);

	    return path;
	}

	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 4

		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		
		return null;
	}

	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 4
		
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		
		return null;
	}

	
	
	public static void main(String[] args)
	{
		System.out.print("Making a new map...");
		MapGraph firstMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", firstMap);
		System.out.println("DONE.");
		
		// You can use this method for testing.  
		
		
		/* Here are some test cases you should try before you attempt 
		 * the Week 3 End of Week Quiz, EVEN IF you score 100% on the 
		 * programming assignment.
		 */
		/*
		MapGraph simpleTestMap = new MapGraph();
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);
		
		GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
		GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);
		
		System.out.println("Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5");
		List<GeographicPoint> testroute = simpleTestMap.dijkstra(testStart,testEnd);
		List<GeographicPoint> testroute2 = simpleTestMap.aStarSearch(testStart,testEnd);
		
		
		MapGraph testMap = new MapGraph();
		GraphLoader.loadRoadMap("data/maps/utc.map", testMap);
		
		// A very simple test using real data
		testStart = new GeographicPoint(32.869423, -117.220917);
		testEnd = new GeographicPoint(32.869255, -117.216927);
		System.out.println("Test 2 using utc: Dijkstra should be 13 and AStar should be 5");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		
		
		// A slightly more complex test using real data
		testStart = new GeographicPoint(32.8674388, -117.2190213);
		testEnd = new GeographicPoint(32.8697828, -117.2244506);
		System.out.println("Test 3 using utc: Dijkstra should be 37 and AStar should be 10");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		*/
		
		
		/* Use this code in Week 3 End of Week Quiz */
		/*MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);

		*/
		
	}
	
}
