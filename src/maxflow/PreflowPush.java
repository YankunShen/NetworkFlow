package maxflow;

import flowgraph.*;
import simplegraph.SimpleGraph;

public class PreflowPush {
    public double findMaxFlow(SimpleGraph simpleGraph) throws Exception {
        NetworkGraph graph = new NetworkGraph(simpleGraph);
        VertexQueue exceedQueue = new VertexQueue();

        // Start with initial labeling and preflow
        NetworkVertex source = graph.getSource();
        source.setHeight(graph.numberOfVertices());
        for (NetworkEdge edge : source.getEdges()) {
            edge.increaseFlow(edge.getResidualCapacity());
            exceedQueue.add(edge.getDest());
        }

        // While there is a node with positive excess
        while (!exceedQueue.isEmpty()) {
            NetworkVertex vertex = exceedQueue.pop();
            NetworkEdge edge = vertex.getLessHeightNeighborEdge();
            if (edge != null) {
                // push
                double flow = Math.min(edge.getResidualCapacity(), vertex.getExcess());
                edge.increaseFlow(flow);

                // check whether origin and destination excess
                if (edge.getOrigin().getExcess() > 0) {
                    exceedQueue.add(edge.getOrigin());
                }
                if (edge.getDest().getExcess() > 0) {
                    exceedQueue.add(edge.getDest());
                }
            } else {
                // relabel
                vertex.setHeight(vertex.getHeight() + 1);
                exceedQueue.add(vertex);
            }
        }
        return source.getOutgoingFlow();
    }
}

