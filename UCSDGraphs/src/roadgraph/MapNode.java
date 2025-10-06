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

    
    /**
     * Creates a new node for a given intersection
     * @param location 	The geographic position of the node
     */
    public MapNode(GeographicPoint location) {
        this.location = location;
        this.edges = new ArrayList<>();
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
}
