package maxflow;

import simplegraph.SimpleGraph;
import simplegraph.GraphInput;

public class Test {
    public static void main(String[] args) throws Exception {
        String graphPath = args[0]; // the first argument is the path of the graph text file

        SimpleGraph simpleGraph = new SimpleGraph();
        GraphInput.LoadSimpleGraph(simpleGraph, graphPath);

        System.out.println("Start to execute algorithms.");
        FordFulkerson FF = new FordFulkerson();
        long beginTime1 = System.currentTimeMillis();
        double result1 = FF.findMaxFlow(simpleGraph);
        long endTime1 = System.currentTimeMillis();
        long time1 = endTime1 - beginTime1;
        System.out.println("Ford-Fulkerson" +  " Max flow: " + result1 + " in " + time1 + " ms");

        ScalingFordFulkerson SFF = new ScalingFordFulkerson();
        long beginTime2 = System.currentTimeMillis();
        double result2 = SFF.findMaxFlow(simpleGraph);
        long endTime2 = System.currentTimeMillis();
        long time2 = endTime2 - beginTime2;
        System.out.println("Scalling-Ford-Fulkerson" +  " Max flow: " + result2 + " in " + time2 + " ms");

        PreflowPush PFP = new PreflowPush();
        long beginTime3 = System.currentTimeMillis();
        double result3 = PFP.findMaxFlow(simpleGraph);
        long endTime3 = System.currentTimeMillis();
        long time3 = endTime3 - beginTime3;
        System.out.println("Pre Flow Push" +  " Max flow: " + result3 + " in " + time3 + " ms");
    }
}
