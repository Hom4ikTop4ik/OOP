package ru.nsu.martynov;

/**
 * The Edge class represents an edge in a graph, storing information about
 * the start vertex (from), end vertex (to), and the count (weight or multiplicity) of the edge.
 */
public class Edge {
    int from;
    int to;
    int count;

    /**
     * Constructs an Edge with specified start vertex, end vertex, and count.
     *
     * @param from the starting vertex of the edge
     * @param to the ending vertex of the edge
     * @param count the count (weight or multiplicity) of the edge
     */
    public Edge(int from, int to, int count) {
        this.from = from;
        this.to = to;
        this.count = count;
    }

    /**
     * Returns a string representation of the edge.
     *
     * @return a string describing the edge with its start, end, and count values
     */
    @Override
    public String toString() {
        return "Edge{"
                + "from=" + from
                + ", to=" + to
                + ", count=" + count
                + '}';
    }

    /**
     * Checks if this edge is equal to another object.
     *
     * @param obj the object to compare with
     * @return true if the specified object is an Edge with the same start vertex,
     *         end vertex, and count, otherwise false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Edge edge = (Edge) obj;
        return from == edge.from && to == edge.to && count == edge.count;
    }
}