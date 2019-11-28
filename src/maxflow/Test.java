package maxflow;

import simplegraph.SimpleGraph;

public class Test {
    public static void main(String[] args) {
        SimpleGraph graph = new SimpleGraph();
        int result = FF.maxflow(graph);
        System.out.println(result);
    }
}
