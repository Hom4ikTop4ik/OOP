package ru.nsu.martynov;

import java.util.Arrays;

public class Main {
    static void adjTest() {
        Adjacency adj = new Adjacency();

        for (int i = 1; i <= 5; i++) {
            adj.addVert();
            adj.printGraph();
        }

        adj.printGraph();
        for (int i = 0; i < adj.size(); i++) {
            for (int j = 0; j < adj.size(); j++) {
                adj.addEdge(i, j);
            }
            System.out.println(Arrays.toString(adj.getNeighbours(i)));
        }
        adj.printGraph();
        for (int i = 0; i < adj.size(); i++) {
            System.out.println(Arrays.toString(adj.getNeighbours(i)));
        }

        for (int i = 4; i >= 0; i--) {
            adj.remVert(i);
            adj.printGraph();
        }
    }

    static void incTest() {
        Incidence adj = new Incidence();

        for (int i = 0; i < 5; i++) {
            adj.addVert();
            adj.printGraph();
        }

        adj.printGraph();
        for (int i = 0; i < adj.getVertCount(); i++) {
            adj.addEdge(i, adj.getVertCount() - 1 - i);
            System.out.println(Arrays.toString(adj.getNeighbours(i)));
        }
        adj.printGraph();
        for (int i = 0; i < adj.getVertCount(); i++) {
            System.out.println(Arrays.toString(adj.getNeighbours(i)));
        }

        adj.printGraph();
        for (int i = 4; i >= 0; i--) {
            adj.remVert(i);
            adj.printGraph();
        }
    }

    public static void main(String[] args) {
        incTest();
    }
}