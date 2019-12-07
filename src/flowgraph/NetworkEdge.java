package flowgraph;

/**
 * Represents an edge with flow in the flow graph. Edge could be forward or backward edge.
 */
public class NetworkEdge {
    private NetworkVertex v1;
    private NetworkVertex v2;
    private double capacity;
    private double flow;

    // forward edge in case this is a backward edge in residual graph
    private NetworkEdge forwardEdge;

    // backward edge in case this edge is forward edge and flow > 0
    private NetworkEdge backwardEdge;

    /**
     * Constructor of network edge.
     *
     * @param v1       v1 vertex of this edge
     * @param v2       v2 vertex of this edge
     * @param capacity Capacity of this edge
     */
    public NetworkEdge(NetworkVertex v1, NetworkVertex v2, double capacity) {
        this.v1 = v1;
        this.v2 = v2;
        this.capacity = capacity;
    }

    /**
     * Increase flow on the edge by given amount. Also takes care of adding/removing/updating
     * corresponding backward edge if this is forward edge. If this is backward edge, it also
     * updates the flow on corresponding forward edge, and removes this edge if necessary.
     *
     * @param increment Amount by which to increment the flow on this edge
     * @throws Exception If given increment violates capacity constraints
     */
    public void increaseFlow(double increment) throws Exception {
        if (this.isBackward()) {
            if (increment > capacity) {
                throw new Exception("Increment of " + increment + " on backward edge of capacity " + this.capacity);
            }

            // Reduce the capacity of this edge, and reduce the flow in real forward edge
            this.capacity = this.capacity - increment;
            this.forwardEdge.flow -= increment;

            // Adjust excess in v1 of real edge, decreasing outgoing flow from v1, so excess increases in v1
            this.forwardEdge.v1.increaseExcess(increment);

            // Adjust excess in dest of real edge, decreasing incoming flow into dest, so excess decreases in dest
            this.forwardEdge.v2.increaseExcess(-increment);

            if (this.capacity == 0) {
                // no back edge for zero flow, remove edge from forward edge and vertex
                this.forwardEdge.backwardEdge = null;
                this.v1.removeEdge(this);
            }
        } else {
            if (this.flow + increment > this.capacity) {
                throw new Exception("Increment of " + increment + " on forward edge of capacity " + this.capacity + " and flow " + this.flow);
            }

            this.flow += increment;

            if (this.backwardEdge == null) {
                // add backward edge if not already there
                this.backwardEdge = new NetworkEdge(this.v2, this.v1, this.flow);
                this.backwardEdge.forwardEdge = this;
                this.v2.addEdge(this.backwardEdge);
            } else {
                // backward edge already there, adjust it's capacity
                this.backwardEdge.setCapacity(this.flow);
            }

            // Adjust excess in v1, increasing outgoing flow from v1, so excess decreases in v1
            this.v1.increaseExcess(-increment);

            // Adjust excess in dest, increasing incoming flow into dest, so excess increases in dest
            this.v2.increaseExcess(increment);
        }
    }

    /**
     * @return Name of the edge.
     */
    public String getName() {
        String name = this.v1.getName() + "_" + this.v2.getName();
        if (this.isBackward()) {
            name += "_backward";
        }
        return name;
    }

    /**
     * Get first endpoint vertex of this edge.
     *
     * @return first endpoint vertex.
     */
    public NetworkVertex getFirstEndpoint() {
        return this.v1;
    }

    /**
     * @return second endpoint vertex.
     */
    public NetworkVertex getSecondEndpoint() {
        return this.v2;
    }

    /**
     * Get amount of flow on this edge. Flow on backward edges is always zero.
     *
     * @return Flow on this edge.
     */
    public double getFlow() {
        return this.flow;
    }


    /**
     * Set capacity of this edge.
     *
     * @param capacity Updated capacity of the edge.
     */
    void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    /**
     * Get capacity of this edge. For backward edges, capacity = flow on corresponding forward edge.
     *
     * @return Capacity of edge.
     */
    double getCapacity() {
        return this.capacity;
    }

    /**
     * Get residual capacity of this edge.
     * For forward edge, residual capacity = capacity - flow.
     * For backward edge, residual capacity = capacity = flow in corresponding forward edge
     *
     * @return Residual capacity of edge.
     */
    public double getResidualCapacity() {
		// backward edge: residual capacity = capacity = flow in forward edge
		// forward edge: residual capacity = capacity - flow
        if (this.isBackward()) {
            return this.capacity;
        } else {
            return this.capacity - this.flow;
        }
    }

    /**
     * Whether the edge is backward edge or forward edge.
     *
     * @return True if edge is backward edge and false if edge is forward edge.
     */
    boolean isBackward() {
        return this.forwardEdge != null;
    }
}
