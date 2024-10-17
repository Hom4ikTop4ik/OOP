package ru.nsu.martynov;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Adjacency implements Graph {
    private int[][] matrix;

    public Adjacency() {
        this.matrix = new int[0][0];
    }

    public int getVertCount() {
        return this.matrix.length;
    }

    public int[][] matrix() {
        return this.matrix;
    }

    private void printMatrix() {
        for (int[] integers : matrix) {
            for (Integer integer : integers) {
                System.out.print(integer + " ");
            }
            System.out.println();
        }
    }

    // Добавление новой вершины
    public void addVert() {
        int[][] newMatrix = new int[this.matrix.length + 1][this.matrix.length + 1];

        // Копируем старую матрицу в новую
        for (int i = 0; i < this.matrix.length; i++) {
            System.arraycopy(matrix[i], 0, newMatrix[i], 0, this.matrix.length);
        }

        // Заменяем старую матрицу на новую
        matrix = newMatrix;
    }

    // Удаление вершины по индексу
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

    @Override
    public void addEdge(int from, int to) {
        Helper.checkIndexes(from, to, this.matrix);

        this.matrix[from][to]++;
    }

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

    @Override
    public void printGraph() {
        printMatrix();
    }

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
            System.out.println("Error reading file " + fileName);
        }
    }
}
