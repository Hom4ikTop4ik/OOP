package ru.nsu.martynov;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class GeneralTest {

    /**
     * Tests the consistency of graph representations by comparing
     *   adjacency, incidence, and list representations.
     * This test checks that after adding edges to all three graph types,
     *   their generated representations are equal.
     * It also verifies that the representations remain consistent when vertices are removed.
     */
    @Test
    void cmpTest() {
        ListGraph listGraph = new ListGraph();
        Adjacency adj = new Adjacency();
        Incidence inc = new Incidence();

        int num = 5;
        // Add vertices to the adjacency and incidence graphs
        for (int i = 0; i < num; i++) {
            adj.addVert();
            inc.addVert();
        }

        // Add edges and compare graph representations
        for (int aboba = 0; aboba < 2; aboba++) {
            for (int i = 0; i < num; i++) {
                listGraph.addEdge(i, num - 1 - i);
                adj.addEdge(i, num - 1 - i);
                inc.addEdge(i, num - 1 - i);

                General genAdj = new General();
                genAdj.fromAdjacencyMatrix(adj.matrix());
                General genInc = new General();
                genInc.fromIncidenceMatrix(inc.matrix());
                General genList = new General();
                genList.fromList(listGraph.list());

                // Assert that all graph representations are equivalent
                assertTrue(genAdj.genEquals(genInc));
                assertTrue(genInc.genEquals(genList));
                assertTrue(genList.genEquals(genAdj));
            }
        }

        // Remove vertices and compare graph representations again
        for (int i = 4; i >= 0; i--) {
            listGraph.remVert(i);
            adj.remVert(i);
            inc.remVert(i);

            General genAdj = new General();
            genAdj.fromAdjacencyMatrix(adj.matrix());
            General genInc = new General();
            genInc.fromIncidenceMatrix(inc.matrix());
            General genList = new General();
            genList.fromList(listGraph.list());

            assertTrue(genAdj.genEquals(genInc));
            assertTrue(genInc.genEquals(genList));
            assertTrue(genList.genEquals(genAdj));
        }
    }

    @Test
    void test2() {
        ListGraph listGraph = new ListGraph();
        Adjacency adj = new Adjacency();
        Incidence inc = new Incidence();

        int num = 5;
        // Add vertices to the adjacency and incidence graphs
        for (int i = 0; i < num; i++) {
            adj.addVert();
            inc.addVert();
        }

        adj.addEdge(0, 0);
        inc.addEdge(1, 3);
        listGraph.addEdge(0, 1);
        listGraph.addEdge(0, 0);

        General genAdj = new General();
        genAdj.fromAdjacencyMatrix(adj.matrix());
        General genInc = new General();
        genInc.fromIncidenceMatrix(inc.matrix());
        General genList = new General();
        genList.fromList(listGraph.list());

        assertFalse(genAdj.genEquals(genInc));
        assertFalse(genAdj.genEquals(genList));
        assertFalse(genInc.genEquals(genList));
        assertFalse(genInc.genEquals(genAdj));
        assertFalse(genList.genEquals(genAdj));
        assertFalse(genList.genEquals(genInc));
    }
}
