package ru.nsu.martynov;

import java.util.HashSet;
import java.util.Set;

public class General {
    private Set<Edge> edges;  // Множество рёбер в формате (from, to, count)

    public General() {
        this.edges = new HashSet<>();
    }

    // Перевод матрицы смежности в общий вид
    public void fromAdjacencyMatrix(int[][] adjacencyMatrix) {
        for (int from = 0; from < adjacencyMatrix.length; from++) {
            for (int to = 0; to < adjacencyMatrix[from].length; to++) {
                if (adjacencyMatrix[from][to] > 0) {
                    edges.add(new Edge(from, to, adjacencyMatrix[from][to]));
                }
            }
        }
    }

    // Перевод матрицы инцидентности в общий вид
    public void fromIncidenceMatrix(int[][] incidenceMatrix) {
        int vertices = incidenceMatrix.length;
        if (vertices <= 0) {
            return;
        }
        int edgesCount = incidenceMatrix[0].length;

        // По рёбрам
        for (int e = 0; e < edgesCount; e++) {
            int from = -1, to = -1;
            // Поиск вершин from и to
            for (int v = 0; v < vertices; v++) {
                if (incidenceMatrix[v][e] > 0) {
                    from = v;
                } else if (incidenceMatrix[v][e] < 0) {
                    to = v;
                }
            }
            if (from >= 0) {
                int count = incidenceMatrix[from][e];
                // Если ребро в себя
                if (to == -1) {
                    to = from;
                    count /= 2;
                }
                edges.add(new Edge(from, to, count));
            } else {
                throw new IllegalArgumentException
                        ("Invalid graph: edge from '" + from + "' to '" + to + "'");
            }
        }
    }

    public void fromList(Set<Edge> list) {
        this.edges = list;
    }

    // Метод для сравнения общего вида графов
    public boolean genEquals(General other) {
        // закомментированное не работает:
//        for (Edge edge : this.edges) {
//            if (!other.edges.contains(edge)) {
//                return false;
//            }
//        }
//        for (Edge edgeOther : other.edges) {
//            if (!this.edges.contains(edgeOther)) {
//                return false;
//            }
//        }

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
