package ru.nsu.martynov;

/**
 * The Graph interface defines a set of methods for working with various graph representations.
 * Implementations of this interface should handle adding/removing vertices and edges, retrieving
 * neighbors, printing the graph structure, and reading graph data from a file.
 */
public interface Graph {
    /**
     * Adds a new vertex to the graph.
     */
    void addVert();

    /**
     * Removes the vertex with the given index from the graph.
     *
     * @param index the index of the vertex to be removed
     * @throws IllegalArgumentException if the index is out of bounds
     */
    void remVert(int index);

    /**
     * Adds an edge between two vertices in the graph. Supports multiple edges between the same vertices.
     *
     * @param from the source vertex index
     * @param to   the destination vertex index
     * @throws IllegalArgumentException if the indices are out of bounds
     */
    void addEdge(int from, int to);

    /**
     * Removes an edge between two vertices in the graph.
     *
     * @param from the source vertex index
     * @param to   the destination vertex index
     * @return the number of edges removed, typically 1 or 0 if no edge exists
     * @throws IllegalArgumentException if the indices are out of bounds
     */
    int remEdge(int from, int to);

    /**
     * Returns the neighbors of a vertex, i.e., the vertices connected to it by edges.
     *
     * @param index the index of the vertex
     * @return an array of indices of neighboring vertices
     * @throws IllegalArgumentException if the index is out of bounds
     */
    int[] getNeighbours(int index);

    /**
     * Prints the structure of the graph, typically by listing vertices and their connections.
     */
    void printGraph();

    /**
     * Reads the graph structure from a file. The file is expected to define the vertices and edges.
     *
     * @param fileName the name of the file containing the graph data
     * @throws IllegalArgumentException if the file format is incorrect
     */
    void readFile(String fileName);
}
