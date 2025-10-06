/**
 * @author A. Helman
 * 
 * A class which represents a directed edge in the graph, corresponding to a road segment.
 *
 */
package roadgraph;

import geography.GeographicPoint;

public class MapEdge {
    private GeographicPoint start;
    private GeographicPoint end;
    private double length;


    /**
     * Create a new directed edge between two intersections.
     * @param start 	Starting intersection
     * @param end 		Ending intersection
     * @param length 	Length of the road
     */
    public MapEdge(GeographicPoint start, GeographicPoint end, String roadName, String roadType, 
    																				double length) {
        this.start = start;
        this.end = end;
        this.length = length;
    }

    
    /** @return the end point of the edge */
    public GeographicPoint getEnd() { 
    	return end; 
    }
    
    
    /** @return the length of the edge */
    public double getLength() { 
    	return length; 
    }
}
