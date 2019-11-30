package maxflow;

import simplegraph.SimpleGraph;
import simplegraph.GraphInput;

public class Test {
    public static void main(String[] args) {
        String graphpath = "src/generation/Bipartite/g1.txt"; // can be modified to any other graph you want to test.
        SimpleGraph graph = new SimpleGraph();
        GraphInput.LoadSimpleGraph(graph, graphpath);
        int result1 = FordFulkerson.maxflow(graph);
        int result2 = ScalingFordFulkerson.maxflow(graph);
        int result3 = PreﬂowPush.maxflow(graph);
        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);
    }
}
