package maxflow;

import simplegraph.SimpleGraph;
import simplegraph.GraphInput;

public class Test {
    public static void main(String[] args) throws Exception {
        String graphpath = "src/generation/Bipartite/g1.txt"; // can be modified to any other graph you want to test.
        SimpleGraph graph1 = new SimpleGraph();
        GraphInput.LoadSimpleGraph(graph1, graphpath);
        FordFulkerson FF = new FordFulkerson(graph1);
        double result1 = FF.findMaxFlow(graph1);


        SimpleGraph graph2 = new SimpleGraph();
        GraphInput.LoadSimpleGraph(graph2, graphpath);
        ScalingFordFulkerson SFF = new ScalingFordFulkerson(graph2);
        double result2 = SFF.findMaxFlow();


        SimpleGraph graph3 = new SimpleGraph();
        GraphInput.LoadSimpleGraph(graph3, graphpath);
        PreflowPush PFP = new PreflowPush(graph3);
        double result3 = PFP.findMaxFlow(graph3);

        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);
    }
}
