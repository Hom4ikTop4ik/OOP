package ru.nsu.martynov;

import java.util.HashSet;
import java.util.Set;

/**
 * The General class represents a generalized view of a graph where edges are stored as
 * (from, to, count) tuples. It provides methods for converting different matrix representations
 * (adjacency matrix, incidence matrix) into this format and for comparing graphs.
 */
public class General {
    private Set<Edge> edges;  // Set of edges in the format (from, to, count)

    /**
     * Constructs an empty General graph.
     */
    public General() {
        this.edges = new HashSet<>();
    }

    /**
     * Converts an adjacency matrix into the general graph format.
     *
     * @param adjacencyMatrix the adjacency matrix to convert
     * @throws IllegalArgumentException if the adjacency matrix is invalid
     */
    public void fromAdjacencyMatrix(int[][] adjacencyMatrix) {
        for (int from = 0; from < adjacencyMatrix.length; from++) {
            for (int to = 0; to < adjacencyMatrix[from].length; to++) {
                if (adjacencyMatrix[from][to] > 0) {
                    edges.add(new Edge(from, to, adjacencyMatrix[from][to]));
                }
            }
        }
    }

    /**
     * Converts an incidence matrix into the general graph format.
     *
     * @param incidenceMatrix the incidence matrix to convert
     * @throws IllegalArgumentException if the incidence matrix is invalid
     */
    public void fromIncidenceMatrix(int[][] incidenceMatrix) {
        int vertices = incidenceMatrix.length;
        if (vertices <= 0) {
            return;
        }
        int edgesCount = incidenceMatrix[0].length;

        for (int e = 0; e < edgesCount; e++) {
            int from = -1;
            int to = -1;
            for (int v = 0; v < vertices; v++) {
                if (incidenceMatrix[v][e] > 0) {
                    from = v;
                } else if (incidenceMatrix[v][e] < 0) {
                    to = v;
                }
            }
            if (from >= 0) {
                int count = incidenceMatrix[from][e];
                if (to == -1) {
                    to = from;
                    count /= 2;
                }
                edges.add(new Edge(from, to, count));
            } else {
                throw new IllegalArgumentException(
                        "Invalid graph: edge from '" + from + "' to '" + to + "'");
            }
        }
    }

    /**
     * Converts a set of edges into the general graph format.
     *
     * @param list the set of edges to import into the general graph
     */
    public void fromList(Set<Edge> list) {
        this.edges = list;
    }

    /**
     * Compares the current general graph with another general graph for equality.
     *
     * @param other the other General graph to compare with
     * @return true if the two graphs are equal, false otherwise
     */
    public boolean genEquals(General other) {
        for (Edge edge : this.edges) {
            boolean found = false;
            for (Edge otherEdge : other.edges) {
                if (edge.equals(otherEdge)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        for (Edge edgeOther : other.edges) {
            boolean found = false;
            for (Edge edge : this.edges) {
                if (edgeOther.equals(edge)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }

        return true;
    }
}
