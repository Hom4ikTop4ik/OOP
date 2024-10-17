package ru.nsu.martynov;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class ListGraph implements Graph {
    // Множество рёбер: каждый элемент представляет собой тройку (from, to, count)
    private Set<Edge> edges;

    public ListGraph() {
        this.edges = new HashSet<>();
    }

    public Set<Edge> list() {
        return edges;
    }

    public void addVert() {
        // nothing
    }

    // Удаление вершины по индексу
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

    // Добавление ребра между вершинами (from, to) с возможностью кратных рёбер
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

    // Удаление ребра между вершинами (from, to)
    public int remEdge(int from, int to) {
        for (Edge edge : this.edges) {
            if (edge.from == from && edge.to == to) {
                if (edge.count > 1) {
                    edge.count--;
                    return 1;
                } else if (edge.count == 1) {
                    edges.remove(edge);
                    return 1;
                } else {
                    return 0;
                }
            }
        }
        return 0;
    }

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

    // Возвращает массив вершин, смежных к текущей
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

    // Вывод всех рёбер
    private void printEdges() {
        for (Edge edge : this.edges) {
            System.out.println(edge);
        }
    }

    public void printGraph() {
        printEdges();
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
            System.out.println("Error reading file " + fileName);
        }
    }
}