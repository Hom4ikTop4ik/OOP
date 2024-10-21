package ru.nsu.martynov;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * The Adjacency class represents a graph using an adjacency matrix.
 * It provides methods for adding/removing vertices and edges, retrieving
 * neighbors, printing the graph, and reading the graph from a file.
 */
public class Adjacency implements Graph {
    private int[][] matrix;

    /**
     * Constructs an empty adjacency matrix.
     */
    public Adjacency() {
        this.matrix = new int[0][0];
    }

    /**
     * Returns the number of vertices in the graph.
     *
     * @return the number of vertices
     */
    public int getVertCount() {
        return this.matrix.length;
    }

    /**
     * Returns the adjacency matrix.
     *
     * @return the adjacency matrix
     */
    public int[][] matrix() {
        return this.matrix;
    }

    /**
     * Prints the adjacency matrix to the console.
     */
    private void printMatrix() {
        for (int[] integers : matrix) {
            for (Integer integer : integers) {
                System.out.print(integer + " ");
            }
            System.out.println();
        }
    }

    /**
     * Adds a new vertex to the graph.
     * Expands the adjacency matrix by one row and one column.
     */
    public void addVert() {
        int[][] newMatrix = new int[this.matrix.length + 1][this.matrix.length + 1];

        // Копируем старую матрицу в новую
        for (int i = 0; i < this.matrix.length; i++) {
            System.arraycopy(matrix[i], 0, newMatrix[i], 0, this.matrix.length);
        }

        // Заменяем старую матрицу на новую
        matrix = newMatrix;
    }

    /**
     * Removes a vertex by its index and adjusts the adjacency matrix accordingly.
     *
     * @param index the index of the vertex to remove
     */
    public void remVert(int index) {
        Helper.checkIndex(index, this.matrix);

        int[][] newMatrix = new int[this.matrix.length - 1][this.matrix.length - 1];

        // Копируем данные, пропуская удаляемую вершину
        for (int i = 0, ii = 0; i < this.matrix.length; i++) {
            if (i == index) {
                continue;
            }
            for (int j = 0, jj = 0; j < this.matrix.length; j++) {
                if (j == index) {
                    continue;
                }
                newMatrix[ii][jj++] = matrix[i][j];
            }
            ii++;
        }

        // Заменяем старую матрицу на новую
        matrix = newMatrix;
    }

    /**
     * Adds an edge between two vertices in the graph.
     *
     * @param from the starting vertex
     * @param to the ending vertex
     */
    @Override
    public void addEdge(int from, int to) {
        Helper.checkIndexes(from, to, this.matrix);

        this.matrix[from][to]++;
    }

    /**
     * Removes an edge between two vertices.
     *
     * @param from the starting vertex
     * @param to the ending vertex
     * @return 1 if an edge was removed, 0 if there was no edge
     */
    @Override
    public int remEdge(int from, int to) {
        Helper.checkIndexes(from, to, this.matrix);

        if (this.matrix[from][to] <= 0) {
            this.matrix[from][to] = 0;
            return 0;
        }
        this.matrix[from][to]--;
        return 1;
    }

    /**
     * Returns an array of neighbors for a given vertex.
     *
     * @param index the vertex index
     * @return an array of neighboring vertices
     */
    @Override
    public int[] getNeighbours(int index) {
        int cnt = 0;
        int[] neighbours = new int[this.matrix.length];
        for (int i = 0; i < this.matrix.length; i++) {
            if (this.matrix[i][index] > 0 || this.matrix[index][i] > 0) {
                neighbours[cnt++] = i;
            }
        }
        neighbours = Arrays.copyOf(neighbours, cnt);
        return neighbours;
    }

    /**
     * Prints the graph (adjacency matrix) to the console.
     */
    @Override
    public void printGraph() {
        printMatrix();
    }

    /**
     * Reads a graph from a file and fills the adjacency matrix.
     *
     * @param fileName the name of the file containing the graph data
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
            int[][] newMatrix = new int[vertCount][vertCount];

            for (int i = 0; i < vertCount; i++) {
                for (int j = 0; j < vertCount; j++) {
                    if (!scanner.hasNextInt()) {
                        throw new IllegalArgumentException(
                                "File is bad — not enough numbers in table");
                    }
                    int tmp = scanner.nextInt();
                    newMatrix[i][j] = tmp;
                }
            }
            matrix = newMatrix;
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.print("Error reading file " + fileName);
        }
    }
}
