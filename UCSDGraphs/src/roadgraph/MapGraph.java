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
import java.util.PriorityQueue;
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
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName, String roadType,
						double length) 
								throws IllegalArgumentException {
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
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal,
										Consumer<GeographicPoint> nodeSearched) {
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
									Consumer<GeographicPoint> nodeSearched, 
									Queue<GeographicPoint> toExplore, Set<GeographicPoint> visited, 
									Map<GeographicPoint, GeographicPoint> parent) {
		
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
	 * @param start 		The starting location
	 * @param goal 			The goal location
	 * @param nodeSearched 	A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal, 
											Consumer<GeographicPoint> nodeSearched) {
		// WEEK 4

		// Validate input
	    if (!isValidPoints(start, goal)) {
	        return null;
	    }

	    // Data structure setup
	    PriorityQueue<MapNode> pq = new PriorityQueue<>((a, b) -> Double.compare(a.getDistance(), b.getDistance()));
	    Set<GeographicPoint> visited = new HashSet<>();
	    Map<GeographicPoint, GeographicPoint> parent = new HashMap<>();
	    
	    // Initialize distances
	    resetAllDistances();
	    // Start node setup
	    setupStartNodeDijkstra(start, pq);

	    // Run Dijkstra loop until goal found or queue empty
	    int count = runDijkstraLoop(goal, nodeSearched, pq, visited, parent);
	    
	    // no path found
	    if (count == -1) {
	        System.out.println("No path found");
	        return null;
	    }
	    
	    // reconstruct the shortest path
	    System.out.println("Dijkstra visited nodes: " + count);
	    return reconstructPath(start, goal, parent);
	}

	
	/** Reset all nodes’ distances to infinity */
	private void resetAllDistances() {
	    for (MapNode node : vertices.values()) {
	        node.setDistance(Double.POSITIVE_INFINITY);
	    }
	}
	
	
	/** 
	 * Prepare the start node for Dijkstra's algorithm.
	 * 
	 * @param start The geographic point representing the starting node
	 * @param pq    The priority queue that orders nodes by their current distance
	 */
	private void setupStartNodeDijkstra(GeographicPoint start, PriorityQueue<MapNode> pq) {
		// We know the distance from start to itself is 0, so we add it to the queue first.
	    MapNode startNode = vertices.get(start);
	    startNode.setDistance(0.0);
	    pq.add(startNode);
	}
	
	
	/**
	 * Runs the main Dijkstra loop.
	 *
	 * Extract the node with the smallest current distance from the priority queue, 
	 * explores its neighbors, and updates their distances if a shorter path is found.
	 *
	 * Stop when the goal node is reached or when the queue becomes empty.
	 *
	 * @param goal         The destination point we want to reach
	 * @param nodeSearched A callback used for visualization or debugging
	 * @param pq           The priority queue of nodes ordered by current distance
	 * @param visited      A set of nodes already processed
	 * @param parent       A map linking each node to its predecessor in the shortest path
	 * @return 	the number of visited nodes if the goal is found, 
	 * 			or -1 if no path exists
	 */
	private int runDijkstraLoop(GeographicPoint goal, Consumer<GeographicPoint> nodeSearched,
	                            PriorityQueue<MapNode> pq, Set<GeographicPoint> visited, 
	                            Map<GeographicPoint, GeographicPoint> parent) {
	    int count = 0;

	    while (!pq.isEmpty()) {
	    	// Take the node with the smallest known distance
	        MapNode current = pq.poll();
	        count++;
	        
	        // Visualization hook
	        nodeSearched.accept(current.getLocation());

	        // skip nodes already processed
	        if (visited.contains(current.getLocation())) {
	        	continue;
	        }
	        
	        // Mark the node as visited
	        visited.add(current.getLocation());

	        // goal check
	        if (current.getLocation().equals(goal)) {
	            return count; // goal found
	        }

	        // explore neighbors
	        exploreDijkstraNeighbors(current, visited, parent, pq);
	    }
	    
	    // no path found
	    return -1;
	}
	
	
	/**
	 * Explore all outgoing edges of the current node in Dijkstra's algorithm.
	 *
	 * @param current the node whose neighbors are being explored
	 * @param visited set of nodes that have already been fully processed
	 * @param parent  map storing the best predecessor for each node (used for path reconstruction)
	 * @param pq      priority queue ordering nodes by their current shortest known distance
	 */	private void exploreDijkstraNeighbors(MapNode current, Set<GeographicPoint> visited,
	                              Map<GeographicPoint, GeographicPoint> parent, 
	                              PriorityQueue<MapNode> pq) {

	    for (MapEdge edge : current.getEdges()) {
	        MapNode neighbor = vertices.get(edge.getEnd());

	        // skip visited
	        if (visited.contains(neighbor.getLocation())) {
	        	continue;
	        }

	        // Compute the total distance from start → current → neighbor
	        double newDist = current.getDistance() + edge.getLength();

	        // check if there is a smaller distance, then
            // update neighbor distance and add to queue for future exploration
	        if (newDist < neighbor.getDistance()) {
	            neighbor.setDistance(newDist);
	            parent.put(neighbor.getLocation(), current.getLocation());
	            pq.add(neighbor);
	        }
	    }
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
	 * @param start 		The starting location
	 * @param goal 			The goal location
	 * @param nodeSearched 	A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal, 
												Consumer<GeographicPoint> nodeSearched) {
		// WEEK 4
		
	    // Validate input
	    if (!isValidPoints(start, goal)) {
	        return null;
	    }

	    // Data structure setup
	    PriorityQueue<MapNode> pq = new PriorityQueue<>((a, b) -> Double.compare(a.getDistance(), b.getDistance()));
	    Set<GeographicPoint> visited = new HashSet<>();
	    Map<GeographicPoint, GeographicPoint> parent = new HashMap<>();
	    
	    setupAStar(start, pq);

	    int count = runAStarLoop(goal, nodeSearched, pq, visited, parent);

	    if (count == -1) {
	    	System.out.println("No path found using A*");
	    	return null;
	    }
	    
	    System.out.println("A* visited nodes: " + count);
	    return reconstructPath(start, goal, parent);
	}

	
	/**
	 * Initializes all nodes and prepares the priority queue for the A* search.
	 *
	 * In A*:
	 *   - actualDistance (g) = true cost from start to the node
	 *   - distance (f) = g + h (used for ordering in the priority queue)
	 *
	 * @param start 	The geographic location where the search begins.
	 * @param pq 		The priority queue that orders nodes by their estimated total cost f = g + h.
	 */	
	private void setupAStar(GeographicPoint start, PriorityQueue<MapNode> pq) {
		// reset all nodes
	    for (MapNode node : vertices.values()) {
	        node.setDistance(Double.POSITIVE_INFINITY);       // f = g + h
	        node.setActualDistance(Double.POSITIVE_INFINITY);  // g (true distance from start)
	    }

	    // init start node
	    MapNode startNode = vertices.get(start);
	    startNode.setActualDistance(0.0);  // g(start) = 0 (no cost to reach itself)
	    startNode.setDistance(0.0);        // f(start) = g + h = 0 (h = 0 at the start)
	    pq.add(startNode);
	}
	
	
	/**
	 * Executes the main loop of the A* algorithm.
	 *
	 * A* repeatedly expands the node with the smallest estimated total cost f(n),
	 * where:
	 *   - g(n): actual distance from the start node to n
	 *   - h(n): heuristic estimate from n to the goal
	 *   - f(n) = g(n) + h(n)
	 *
	 * For each node, explore all neighbors and update the distance if a shorter path is found. 
	 * Stop when the goal node is found or when there are no more nodes to explore.
	 *
	 * @param goal 			The goal location.
	 * @param nodeSearched 	A callback to visualize or track which nodes were visited.
	 * @param pq 			The priority queue of nodes to explore (ordered by f = g + h).
	 * @param visited 		The set of nodes already fully explored.
	 * @param parent 		A mapping from each node to its predecessor in the shortest path.
	 * @return 	The number of nodes visited, 
	 * 			or -1 if no path to the goal was found.
	 */	
	private int runAStarLoop(GeographicPoint goal, Consumer<GeographicPoint> nodeSearched,
	                         PriorityQueue<MapNode> pq, Set<GeographicPoint> visited,
	                         Map<GeographicPoint, GeographicPoint> parent) {
	    int count = 0;

	    while (!pq.isEmpty()) {
	    	// Take the node with the smallest f(n) = g + h
	        MapNode current = pq.poll();
	        count++;

	        // visualization hook
	        nodeSearched.accept(current.getLocation());

	        // skip visited nodes
	        if (visited.contains(current.getLocation())) {
	        	continue;
	        }
	        visited.add(current.getLocation());

	        // goal check
	        if (current.getLocation().equals(goal)) {
	            return count;
	        }

	        // explore neighbors
	        exploreAStarNeighbors(current, goal, visited, parent, pq);
	    }

	    // no path found
	    return -1;
	}
	
	
	/**
	 * Explores and updates all neighboring nodes of the current node in the A* search.
	 *
	 * @param current 	The node currently being expanded.
	 * @param goal 		The goal location, used to compute the heuristic.
	 * @param visited 	Set of already finalized nodes.
	 * @param parent 	Map tracking each node’s predecessor for path reconstruction.
	 * @param pq 		Priority queue ordering nodes by f = g + h.
	 */
	private void exploreAStarNeighbors(MapNode current, GeographicPoint goal, 
										Set<GeographicPoint> visited, 
										Map<GeographicPoint, GeographicPoint> parent, 
										PriorityQueue<MapNode> pq) {
		
	    for (MapEdge edge : current.getEdges()) {
	        MapNode neighbor = vertices.get(edge.getEnd());

	        // skip visited
	        if (visited.contains(neighbor.getLocation())) {
	        	continue;
	        }

	        // Compute tentative g (distance from start via current)
	        double tentativeG = current.getActualDistance() + edge.getLength();

	        // check if there is a better path
	        if (tentativeG < neighbor.getActualDistance()) {
	            neighbor.setActualDistance(tentativeG);
	            double h = neighbor.getLocation().distance(goal);
	            neighbor.setDistance(tentativeG + h);  // f = g + h

	            parent.put(neighbor.getLocation(), current.getLocation());
	            pq.add(neighbor);
	        }
	    }
	}
	
	
	public static void main(String[] args) {
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
