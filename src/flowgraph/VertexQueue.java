package flowgraph;

import java.util.HashSet;
import java.util.LinkedList;

public class VertexQueue {
    private LinkedList<NetworkVertex> verticesList;
    private HashSet<String> verticesSet;

    public VertexQueue()
    {
        this.verticesList = new LinkedList<NetworkVertex>();
        this.verticesSet = new HashSet<String>();
    }

    /**
     * @param vertex the vertex you want to add to the queue
     */
    public void add(NetworkVertex vertex) {
        if (vertex.getName().equals("s") || (vertex.getName().equals("t"))) {
            return;
        }
        if (verticesSet.add(vertex.getName())) {
            verticesList.addLast(vertex);
        }
    }

    /**
     * @return a vertex popped from the queue
     */
    public NetworkVertex pop() {
        NetworkVertex vertex = verticesList.remove();
        verticesSet.remove(vertex.getName());
        return vertex;
    }

    public Boolean isEmpty() {
        return verticesList.isEmpty();
    }
}