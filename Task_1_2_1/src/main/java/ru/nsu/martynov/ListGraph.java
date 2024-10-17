package ru.nsu.martynov;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * This class represents a graph using a list of edges.
 * Each edge is represented as a triple (from, to, count)
 * This class implements basic graph operations:
 *   adding/removing vertices and edges,
 *   reading from a file,
 *   printing the graph.
 */
public class ListGraph implements Graph {
    /**
     * Set of edges in the graph. Each edge is represented by the {@code Edge} class.
     */
    private Set<Edge> edges;

    /**
     * Constructs an empty graph with no edges.
     */
    public ListGraph() {
        this.edges = new HashSet<>();
    }

    /**
     * Returns the set of edges in the graph.
     *
     * @return the set of edges.
     */
    public Set<Edge> list() {
        return edges;
    }

    /**
     * Adds a new vertex to the graph.
     * This implementation doesn't store explicit vertices,
     * so this method doesn't affect the internal structure.
     */
    public void addVert() {
        // nothing
    }

    /**
     * Removes all edges connected to a given vertex.
     *
     * @param index the index of the vertex to be removed.
     */
    public void remVert(int index) {
        Set<Edge> toDelete = new HashSet<>();

        for (Edge edge : this.edges) {
            if (edge.from == index || edge.to == index) {
                toDelete.add(edge);
            }
        }

        for (Edge edge : toDelete) {
            this.edges.remove(edge);
        }
    }

    /**
     * Adds an edge between two vertices. If the edge already exists, increments its count.
     *
     * @param from the starting vertex of the edge.
     * @param to the ending vertex of the edge.
     */
    public void addEdge(int from, int to) {
        Helper.checkIndexesNeg(from, to);

        // Проверяем, есть ли уже ребро (from -> to)
        for (Edge edge : edges) {
            if (edge.from == from && edge.to == to) {
                edge.count++;  // Увеличиваем количество рёбер
                return;
            }
        }

        // Если ребра нет, добавляем новое
        edges.add(new Edge(from, to, 1));
    }

    /**
     * Removes an edge between two vertices. If the edge exists and has a count greater than 1,
     * decrements the count. Otherwise, removes the edge entirely.
     *
     * @param from the starting vertex of the edge.
     * @param to the ending vertex of the edge.
     * @return 1 if an edge was removed, or 0 if no edge existed.
     */
    public int remEdge(int from, int to) {
        for (Edge edge : this.edges) {
            if (edge.from == from && edge.to == to) {
                if (edge.count > 1) {
                    edge.count--;
                    return 1;
                } else { // 1 edge
                    edges.remove(edge);
                    return 1;
                }
            }
        }
        return 0;
    }

    /**
     * Returns the number of vertices in the graph. The vertex count is inferred
     * from the highest index found in the edges.
     *
     * @return the number of vertices in the graph.
     */
    public int getVertCount() {
        int count = -1;
        for (Edge edge : this.edges) {
            if (edge.from > count) {
                count = edge.from;
            }
            if (edge.to > count) {
                count = edge.to;
            }
        }
        return count + 1; // indexes start from zero
    }

    /**
     * Returns an array of vertices adjacent to the specified vertex.
     *
     * @param index the index of the vertex.
     * @return an array of adjacent vertices.
     */
    public int[] getNeighbours(int index) {
        Set<Integer> neighboursSet = new HashSet<>();

        for (Edge edge : this.edges) {
            if (edge.from == index) {
                neighboursSet.add(edge.to);
            } else if (edge.to == index) {
                neighboursSet.add(edge.from);
            }
        }

        // Преобразование Set в массив
        int[] neighboursArray = new int[neighboursSet.size()];
        int i = 0;
        for (Integer neighbour : neighboursSet) {
            neighboursArray[i++] = neighbour;
        }

        return neighboursArray;
    }

    /**
     * Prints all the edges of the graph.
     */
    private void printEdges() {
        for (Edge edge : this.edges) {
            System.out.println(edge);
        }
    }


    /**
     * Prints the entire graph by printing all its edges.
     */
    public void printGraph() {
        printEdges();
    }

    /**
     * Reads a graph from a file and constructs it based on the provided adjacency matrix.
     * The file should start with the number of vertices, followed by the adjacency matrix.
     * Positive integers in the matrix represent the number of edges between two vertices.
     *
     * @param fileName the name of the file containing the graph data.
     * @throws IllegalArgumentException if the file format is incorrect.
     */
    @Override
    public void readFile(String fileName) {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            int vertCount = 0;
            if (!scanner.hasNextInt()) {
                throw new IllegalArgumentException(
                        "File is bad — there isn't table size (count of vertices)");
            }
            vertCount = scanner.nextInt();

            Set<Edge> newEdges = new HashSet<>();

            for (int i = 0; i < vertCount; i++) {
                for (int j = 0; j < vertCount; j++) {
                    if (!scanner.hasNextInt()) {
                        throw new IllegalArgumentException(
                                "File is bad — not enough numbers in table");
                    }
                    int tmp = scanner.nextInt();
                    if (tmp > 0) {
                        newEdges.add(new Edge(i, j, tmp));
                    }
                }
            }
            edges = newEdges;
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.print("Error reading file " + fileName);
        }
    }
}