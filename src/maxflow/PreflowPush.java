package maxflow;

import flowgraph.*;
import simplegraph.SimpleGraph;

import java.util.HashSet;
import java.util.LinkedList;

public class PreflowPush {

    private VertexQueue exceedQueue;

    public PreflowPush()
    {
        this.exceedQueue =  new VertexQueue();
    }

    public double findMaxFlow(SimpleGraph simpleGraph) throws Exception {
        NetworkGraph graph = new NetworkGraph(simpleGraph);
        NetworkVertex source = graph.getSource();

        // Start with initial labeling and preflow
        source.setHeight(graph.numberOfVertices());
        for (NetworkEdge edge : source.getEdges()) {
            edge.increaseFlow(edge.getResidualCapacity());
            exceedQueue.add(edge.getDest());
        }

        // While there is a node with positive excess
        while (!exceedQueue.isEmpty()) {
            NetworkVertex vertex = exceedQueue.pop();
            NetworkEdge edge = vertex.getLessHeightNeighborEdge();
            if (edge == null) {
                // No neighbor with less height, relabel
                vertex.incrementHeight();

                // Add vertex back
                exceedQueue.add(vertex);
            } else {
                double increment = Math.min(edge.getResidualCapacity(), vertex.getExcess());
                edge.increaseFlow(increment);

                // Add origin if there is still excess
                if (edge.getOrigin().getExcess() > 0) {
                    exceedQueue.add(edge.getOrigin());
                }

                // Add dest if excess > 0
                if (edge.getDest().getExcess() > 0) {
                    exceedQueue.add(edge.getDest());
                }
            }
        }

        return source.getOutgoingFlow();
    }
}

