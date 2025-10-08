/**
 * @author A. Helman
 * 
 * A class which represents a node in the graph, corresponding to a geographic intersection.
 * Each node keeps a list of outgoing edges. 
 *
 */
package roadgraph;

import java.util.ArrayList;
import java.util.List;

import geography.GeographicPoint;

public class MapNode {
	
    private GeographicPoint location;
    private List<MapEdge> edges;
    private double distance;    
    private double actualDistance;
    
    
    /**
     * Creates a new node for a given intersection
     * @param location 	The geographic position of the node
     */
    public MapNode(GeographicPoint location) {
        this.location = location;
        this.edges = new ArrayList<>();
        distance = Double.POSITIVE_INFINITY;
    }

    
    /** @return	The geographic location of the node */
    public GeographicPoint getLocation() {
        return location;
    }

    
    /** @return	The list of outgoing edges */
    public List<MapEdge> getEdges() {
        return edges;
    }

    
    /** Adds an outgoing edge from the node */
    public void addEdge(MapEdge edge) {
        edges.add(edge);
    }

    
    /** @return The total estimated cost (f) to reach the goal through this node. */
	public double getDistance() {
		return distance;
	}

	
	/**
	 * Sets the total estimated cost (f) for this node.
	 *
	 * In Dijkstra’s algorithm, f = g (no heuristic).
	 * In A*, f = g + h.
	 *
	 * @param positiveInfinity The total estimated cost to set (often initialized to +∞).
	 */
	public void setDistance(double positiveInfinity) {
		this.distance = positiveInfinity;		
	}


	/** @return The actual (known) shortest distance from the start to this node.*/
	public double getActualDistance() {
		return actualDistance;
	}

	
	/**
	 * Sets the actual distance (g) from the start node to this node.
	 *
	 * @param newG The new actual distance from the start node.
	 */
	public void setActualDistance(double newG) {
		actualDistance = newG;
	}
}
