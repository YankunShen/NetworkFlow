package maxflow;

import flowgraph.*;
import simplegraph.SimpleGraph;

public class PreflowPush {
    public double findMaxFlow(SimpleGraph simpleGraph) throws Exception {
        NetworkGraph graph = new NetworkGraph(simpleGraph);
        VertexQueue exceedQueue = new VertexQueue();

        // start with initial labeling and preflow
        NetworkVertex source = graph.getSource();
        source.setHeight(graph.getVerticesNum());
        for (NetworkEdge edge : source.getEdges()) {
            edge.increaseFlow(edge.getResidualCapacity());
            exceedQueue.add(edge.getSecondEndpoint());
        }

        // while there is a excess node
        while (!exceedQueue.isEmpty()) {
            NetworkVertex vertex = exceedQueue.pop();
            NetworkEdge edge = vertex.getLowerEdge();
            if (edge != null) {
                // push
                double flow = Math.min(edge.getResidualCapacity(), vertex.getExcess());
                edge.increaseFlow(flow);

                if (edge.getFirstEndpoint().getExcess() > 0) {
                    exceedQueue.add(edge.getFirstEndpoint());
                }
                if (edge.getSecondEndpoint().getExcess() > 0) {
                    exceedQueue.add(edge.getSecondEndpoint());
                }
            } else {
                // relabel
                vertex.setHeight(vertex.getHeight() + 1);
                exceedQueue.add(vertex);
            }
        }
        return graph.getGraphFlow();
    }
}

