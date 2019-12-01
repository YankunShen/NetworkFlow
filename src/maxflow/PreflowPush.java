package maxflow;

import flowgraph.*;
import simplegraph.SimpleGraph;

import java.util.HashSet;
import java.util.LinkedList;

public class PreflowPush {

    private SimpleGraph graph;

    public PreflowPush(SimpleGraph graph)
    {
        this.graph = new SimpleGraph();
    }

    public double findMaxFlow(SimpleGraph simpleGraph) throws Exception {
        NetworkGraph graph = new NetworkGraph(simpleGraph);
        NetworkVertex source = graph.getSource();
        NetworkVertex sink = graph.getSink();

        LinkedList<NetworkVertex> positiveExcessVertices = new LinkedList<NetworkVertex>();
        HashSet<String> verticesInQueue = new HashSet<String>();

        // Initial conditions
        source.setHeight(graph.numberOfVertices());
        for (NetworkEdge edge : source.getEdges()) {
            edge.increaseFlow(edge.getResidualCapacity());
            this.addVertexIfNotPresent(positiveExcessVertices, verticesInQueue, edge.getDest());
        }

        while (!positiveExcessVertices.isEmpty()) {
            NetworkVertex vertex = this.remove(positiveExcessVertices, verticesInQueue);
            NetworkEdge edge = vertex.getLessHeightNeighborEdge();
            if (edge == null) {
                // No neighbor with less height, relabel
                vertex.incrementHeight();

                // Add vertex back
                this.addVertexIfNotPresent(positiveExcessVertices, verticesInQueue, vertex);
            } else {
                double increment = Math.min(edge.getResidualCapacity(), vertex.getExcess());
                edge.increaseFlow(increment);

                // Add origin if there is still excess
                if (edge.getOrigin().getExcess() > 0) {
                    this.addVertexIfNotPresent(positiveExcessVertices, verticesInQueue, edge.getOrigin());
                }

                // Add dest if excess > 0
                if (edge.getDest().getExcess() > 0) {
                    this.addVertexIfNotPresent(positiveExcessVertices, verticesInQueue, edge.getDest());
                }
            }
        }

        return source.getOutgoingFlow();
    }

    /**
     * Add vertex to the list containing vertices with positive excess if vertex is not already present.
     * @param positiveExcessVertices List of vertices with positive excess
     * @param verticesInQueue Names of vertices present in list containing vertices with positive excess.
     * @param vertex Vertox to add in the list.
     */
    private void addVertexIfNotPresent(
            LinkedList<NetworkVertex> positiveExcessVertices,
            HashSet<String> verticesInQueue,
            NetworkVertex vertex) {

        if (vertex.isSourceOrSink()) {
            return;
        }

        if (verticesInQueue.add(vertex.getName())) {
            positiveExcessVertices.addLast(vertex);
        }
    }

    private NetworkVertex remove(LinkedList<NetworkVertex> positiveExcessVertices, HashSet<String> verticesInQueue) {
        NetworkVertex vertex = positiveExcessVertices.remove();
        verticesInQueue.remove(vertex.getName());
        return vertex;
    }
}