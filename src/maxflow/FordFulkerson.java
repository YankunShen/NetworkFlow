package maxflow;

import simplegraph.SimpleGraph;
import flowgraph.*;

import java.util.LinkedList;

public class FordFulkerson {
    protected static double INF = Double.MAX_VALUE/2;

    public double findMaxFlow(SimpleGraph simpleGraph) throws Exception {
        NetworkGraph graph = new NetworkGraph(simpleGraph);
        NetworkVertex source = graph.getSource();
        double f = dfs(graph, graph.getSource(), INF);
        for(; f!=0; ){
            graph.resetVisited();
            f = dfs(graph, graph.getSource(), INF);
        }
        return source.getOutgoingFlow();
    }

    /**
     * Traversing the vertices
     * @param graph This is a residual graph
     * @param origin This is source
     * @return LinkedList Returns the s-t path
     */
    private double dfs(NetworkGraph graph, NetworkVertex origin, double flow) throws Exception {
        // Mark origin as visited

        if(origin.getName().equals("t"))
            return flow;

        origin.markVisited();

        for (NetworkEdge edge : origin.getEdges()) {
            if (edge.getResidualCapacity() > 0 && !edge.getDest().isVisited()) {
                double bottleneck = this.dfs(graph, edge.getDest(), Math.min(flow, edge.getResidualCapacity()));
                if(bottleneck > 0){
                   edge.increaseFlow(bottleneck);
                   return bottleneck;
                }

            }
        }

        // No path found
        return 0;
    }
}
