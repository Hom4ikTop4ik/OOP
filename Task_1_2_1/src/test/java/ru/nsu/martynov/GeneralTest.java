package ru.nsu.martynov;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class GeneralTest {

    @Test
    void cmpTest() {
        ListGraph listGraph = new ListGraph();
        Adjacency adj = new Adjacency();
        Incidence inc = new Incidence();

        int num = 5;
        for (int i = 0; i < num; i++) {
            adj.addVert();
            inc.addVert();
        }

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

                assertTrue(genAdj.genEquals(genInc));
                assertTrue(genInc.genEquals(genList));
                assertTrue(genList.genEquals(genAdj));
            }
        }

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

}