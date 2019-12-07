package flowgraph;

import java.util.*;

/**
* A vertex in the network graph.
*/
public class NetworkVertex {
	private String name;

	Hashtable<String, NetworkEdge> edges;

	// used in pre-flow push
	private double excess;
	private int height;
	
	private boolean visited;
	
	/**
	* Creates a flow vertex.
	* @param name Name of the vertex.
	*/
	public NetworkVertex(String name) {
		this.name = name;
		this.edges = new Hashtable<>();
	}

	/**
	* Add given edge to the vertex. Origin of the given edge must be this vertex.
	* @param edge Edge to add to this vertex.
	* @throws Exception If origin of given edge is not this vertex.
	*/
	public void addEdge(NetworkEdge edge) throws Exception {
		if (!edge.getFirstEndpoint().getName().equals(this.name)) {
			// Adding edge to this vertex but in the edge origin is specified as some other vertex
			throw new Exception("Adding edge " + edge.getName() + " with origin vertex " + edge.getFirstEndpoint().getName() + " on " + this.name);
		}
		
		this.edges.put(edge.getName(), edge);
	}
	
	/**
	* Remove given edge from this vertex.
	* @param edge Edge to remove.
	*/
	public void removeEdge(NetworkEdge edge) {
		this.edges.remove(edge.getName());
	}

	/**
	* Get name of this vertex.
	* @return Name of this vertex.
	*/
	public String getName() {
		return this.name;
	}

	/**
	* @return whether the vertex has been set as visited
	*/
	public boolean isVisited() {
		return this.visited;
	}
	
	/**
	* set the vertex as visited.
	*/
	public void setVisited() {
		this.visited = true;
	}

	/**
	 * set the vertex as not visited.
	*/
	public void setNotVisited() {
		this.visited = false;
	}

	/**
	* Get excess of this vertex.
	* @return Excess of this vertex.
	*/
	public double getExcess() {
		return this.excess;
	}
	
	/**
	* Increase excess on this vertex by given amount.
	* @param increment Amount by which to increase excess.
	*/
	public void increaseExcess(double increment) {
		this.excess += increment;
	}
	
	
	/**
	* Get height label of this vertex.
	* @return Height label of this vertex.
	*/
	public int getHeight() {
		return this.height;
	}
	
	/**
	* Set height label of this vertex.
	* @param height Height of the vertex.
	*/
	public void setHeight(int height) {
		this.height = height;
	}
	
	/**
	* Get edge outgoing from this vertex such that other end point of that edge
	* has height less than this vertex.
	* @return Edge if their is neghboring vertex with height less than this vertex, otherwise null.
	*/
	public NetworkEdge getLowerEdge() {
		for (NetworkEdge edge : this.edges.values()) {
			if (edge.getResidualCapacity() > 0 && edge.getSecondEndpoint().height < this.height) {
				return edge;
			}
		}
		return null;
	}
	
	/**
	* @return The sum of outgoing capacity of the vertex.
	*/
	public double getOutgoingCapacity() {
		double capacity = 0;
		for (NetworkEdge edge : this.edges.values()) {
			if (!edge.isBackward()) {
				capacity += edge.getCapacity();
			}
		}
		
		return capacity;
	}
	
	/**
	* @return Array of outgoing edges from this vertex.
	*/
	public NetworkEdge[] getEdges() {
		NetworkEdge[] edgeArray = new NetworkEdge[this.edges.size()];
		this.edges.values().toArray(edgeArray);
		return edgeArray;
	}
}
