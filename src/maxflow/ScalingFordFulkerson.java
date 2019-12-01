package maxflow;

import flowgraph.*;
import simplegraph.SimpleGraph;

import java.util.LinkedList;

public class ScalingFordFulkerson {
    private SimpleGraph simpleGraph;
    private double minResidualCapacity;
    protected static double INF = Double.MAX_VALUE/2;

    public ScalingFordFulkerson(SimpleGraph graph)
    {
        this.simpleGraph = graph;
    }

    public double findMaxFlow() throws Exception {
        NetworkGraph graph = new NetworkGraph(simpleGraph);
        NetworkVertex source = graph.getSource();
        //Outgoing Capacity from Source
        double sourceOutgoingCapacity = source.getOutgoingCapacity();
        // Get starting min residual capacity
        minResidualCapacity = 1;
        while (minResidualCapacity * 2 < sourceOutgoingCapacity) {
            minResidualCapacity *= 2;
        }
        for(double f = 0; minResidualCapacity > 0; minResidualCapacity/=2){
            do{
                graph.resetVisited();
                f = dfs(graph, graph.getSource(), INF);
            }while(f != 0);
        }

        return source.getOutgoingFlow();
    }

    /**
     * This Linked List is used to find the s-t path to the sink with the Minimum Residual capacity
     * @param graph graph graph in which we find the max flow
     * @param origin origin starting nodes of the s-t flow
     * @param flow: the current augmenting path's flow
     * @return FlowEdge: s-t path with the max Flow, minimum residual capacity.
     */

    //We take the graph, source node and the minimum residual capacity value as the input for this method.
    private double dfs(
            NetworkGraph graph,
            NetworkVertex origin,
            double flow) throws Exception {

        if(origin.getName().equals("t"))
            return flow;

        // Mark origin as visited
        origin.markVisited();

        for (NetworkEdge edge : origin.getEdges()) {
            if (edge.getResidualCapacity() >= minResidualCapacity && !edge.getDest().isVisited()) {
                double bottleneck = dfs(graph, edge.getDest(), Math.min(flow, edge.getResidualCapacity()));
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
