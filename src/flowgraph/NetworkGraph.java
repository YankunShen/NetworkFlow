package flowgraph;

import java.util.*;
import simplegraph.*;

/**
* Represents a network graph.
*/
public class NetworkGraph {
	private Hashtable<String, NetworkVertex> vertices;
	private Hashtable<String, NetworkEdge> edges;
	
	/**
	* Creates a flow graph from the simple graph.
	* @param graph Simple graph from which to construct flow graph
	* @throws Exception If graph generation fails.
	*/
	public NetworkGraph(SimpleGraph graph) throws Exception {
		this.vertices = new Hashtable<>();
		this.edges = new Hashtable<>();

		Iterator vertexIterator = graph.vertices();
		while (vertexIterator.hasNext()) {
			Vertex vertex = (Vertex)vertexIterator.next();
			NetworkVertex flowVertex = new NetworkVertex((String)vertex.getName());
			this.addVertex(flowVertex);
		}
		
		Iterator edgeIterator = graph.edges();
		while (edgeIterator.hasNext()) {
			Edge edge = (Edge)edgeIterator.next();
			
			NetworkVertex v1 = this.vertices.get(edge.getFirstEndpoint().getName());
			NetworkVertex dest = this.vertices.get(edge.getSecondEndpoint().getName());
			double capacity = (double)edge.getData();

			this.addEdge(v1, dest, capacity);
		}
	}

	/**
	* Get vertices in the graph.
	* @return Collection of vertices in the graph.
	*/
	public Collection<NetworkVertex> getVertices() {
		return this.vertices.values();
	}
	
	/**
	* Add vertex in the graph.
	* @param vertex Vertex to add in the graph.
	*/
	private void addVertex(NetworkVertex vertex) {
		this.vertices.put(vertex.getName(), vertex);
	}
	
	/**
	* Add edge in the graph.
	* @param v1 v1 vertex of edge to add.
	* @param v2 v2 vertex of edge to add.
	* @param capacity Capacity of the edge to add.
	* @throws Exception If addition of edge fails
	*/
	private void addEdge(NetworkVertex v1, NetworkVertex v2, double capacity) throws Exception {
		NetworkEdge edge = new NetworkEdge(v1, v2, capacity);
		v1.addEdge(edge);
		this.edges.put(edge.getName(), edge);
	}
	
	/**
	* Get source vertex in the graph.
	* @return Source vertex.
	*/
	public NetworkVertex getSource() {
		return this.getVertex("s");
	}
	
	/**
	* Get vertex of given name in the graph.
	* @param name Name of the vertex to return.
	* @return Vertex with given name.
	*/
	private NetworkVertex getVertex(String name) {
		return this.vertices.get(name);
	}
	
	/**
	* set all the vertices in the graph not visited
	*/
	public void setNotVisited() {
		for (NetworkVertex vertex : this.vertices.values()) {
			vertex.setNotVisited();
		}
	}
	
	/**
	* Get the number of vertices in the graph.
	* @return Number of vertices in the graph.
	*/
	public int getVerticesNum() {
		return this.vertices.size();
	}


	/**
	 * Get the flow of the graph
	 * @return The flow of the graph
	 */
	public double getGraphFlow() {
		NetworkVertex source = this.getSource();
		double flow = 0;
		for (NetworkEdge edge : source.edges.values()) {
			if (!edge.isBackward()) {
				flow += edge.getFlow();
			}
		}

		return flow;
	}
}
