package ru.nsu.martynov;

public interface Graph {

    void addVert();
    void remVert(int index);

    void addEdge(int from, int to);
    int remEdge(int from, int to);

    int[] getNeighbours(int index);

    void printGraph();

    void readFile(String fileName);
}
