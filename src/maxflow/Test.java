package maxflow;

import simplegraph.SimpleGraph;
import simplegraph.GraphInput;

public class Test {
    public static void main(String[] args) throws Exception {
        String graphpath = args[0]; // can be modified to any other graph you want to test.
        SimpleGraph graph1 = new SimpleGraph();
        GraphInput.LoadSimpleGraph(graph1, graphpath);
        FordFulkerson FF = new FordFulkerson();
        long beginTime1 = System.currentTimeMillis();
        double result1 = FF.findMaxFlow(graph1);
        long endTime1 = System.currentTimeMillis();
        long time1 = endTime1 - beginTime1;


        SimpleGraph graph2 = new SimpleGraph();
        GraphInput.LoadSimpleGraph(graph2, graphpath);
        ScalingFordFulkerson SFF = new ScalingFordFulkerson();
        long beginTime2 = System.currentTimeMillis();
        double result2 = SFF.findMaxFlow(graph2);
        long endTime2 = System.currentTimeMillis();
        long time2 = endTime2 - beginTime2;

        SimpleGraph graph3 = new SimpleGraph();
        GraphInput.LoadSimpleGraph(graph3, graphpath);
        PreflowPush PFP = new PreflowPush(graph3);
        long beginTime3 = System.currentTimeMillis();
        double result3 = PFP.findMaxFlow(graph3);
        long endTime3 = System.currentTimeMillis();
        long time3 = endTime3 - beginTime3;

        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);

        System.out.println(time1);
        System.out.println(time2);
        System.out.println(time3);
    }
}
