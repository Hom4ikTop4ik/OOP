package ru.nsu.martynov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

class AdjacencyTest {
    @Test
    void getVertCountTest() {
        Adjacency adj = new Adjacency();
        assertEquals(0, adj.getVertCount(),
                "Initial vertex count should be 0.");
        adj.addVert();
        assertEquals(1, adj.getVertCount(),
                "Vertex count should be 1 after adding one vertex.");
        adj.addVert();
        assertEquals(2, adj.getVertCount(),
                "Vertex count should be 2 after adding two vertices.");
        adj.addVert();
        adj.addVert();
        assertEquals(4, adj.getVertCount(),
                "Vertex count should be 4 after adding four vertices.");
    }

    @Test
    void matrixTest() {
        Adjacency adj = new Adjacency();

        adj.addVert();
        adj.addEdge(0, 0);
        int[][] out1 = adj.matrix();
        // 1
        assertEquals(1, out1[0][0],
                "Matrix value at (0,0) should be 1 after adding edge (0,0).");

        adj.addVert();
        adj.addEdge(0, 1);
        adj.addEdge(0, 1);
        adj.addEdge(1, 1);
        adj.addEdge(1, 1);
        adj.addEdge(1, 1);
        int[][] out2 = adj.matrix();
        // 1 2
        // 0 3
        assertEquals(1, out2[0][0],
                "Matrix value at (0,0) should remain 1.");
        assertEquals(2, out2[0][1],
                "Matrix value at (0,1) should be 2 after adding two edges.");
        assertEquals(0, out2[1][0],
                "Matrix value at (1,0) should be 0.");
        assertEquals(3, out2[1][1],
                "Matrix value at (1,1) should be 3 after adding three edges.");
    }

    @Test
    void addVertTest() {
        Adjacency adj = new Adjacency();
        int[][] out0 = adj.matrix();
        assertEquals(0, out0.length,
                "Matrix should initially be empty.");

        adj.addVert();
        int[][] out1 = adj.matrix();
        assertEquals(1, out1.length,
                "Matrix should have 1 row after adding one vertex.");
        assertEquals(1, out1[0].length,
                "Matrix should have 1 column after adding one vertex.");

        adj.addVert();
        int[][] out2 = adj.matrix();
        assertEquals(2, out2.length,
                "Matrix should have 2 rows after adding two vertices.");
        assertEquals(2, out2[0].length,
                "Matrix should have 2 columns after adding two vertices.");
        assertEquals(2, out2[1].length,
                "Matrix should have 2 columns in the second row.");
    }

    @Test
    void remVertTest() {
        Adjacency adj = new Adjacency();
        int cnt = 5;
        for (int i = 0; i < cnt; i++) {
            adj.addVert();
        }

        int[][] out = adj.matrix(); // left indexes [0..4]
        assertEquals(cnt, out.length,
                "Matrix should have 5 rows after adding 5 vertices.");
        assertEquals(cnt, out[0].length,
                "Matrix should have 5 columns after adding 5 vertices.");

        adj.remVert(0); // left [0..3]
        out = adj.matrix();
        assertEquals(--cnt, out.length,
                "Matrix should have 4 rows after removing vertex 0.");
        assertEquals(cnt, out[0].length,
                "Matrix should have 4 columns after removing vertex 0.");

        adj.remVert(3); // left [0..2]
        out = adj.matrix();
        assertEquals(--cnt, out.length,
                "Matrix should have 3 rows after removing vertex 3.");
        assertEquals(cnt, out[0].length,
                "Matrix should have 3 columns after removing vertex 3.");

        adj.remVert(1); // left [0..1]
        out = adj.matrix();
        assertEquals(--cnt, out.length,
                "Matrix should have 2 rows after removing vertex 1.");
        assertEquals(cnt, out[0].length,
                "Matrix should have 2 columns after removing vertex 1.");

        // Try removing not existing vert
        boolean flag = false;
        try {
            adj.remVert(-1);
        } catch (Exception e) {
            flag = true;
        }
        assertTrue(flag,
                "Removing a vertex with negative index should throw an exception.");

        flag = false;
        try {
            adj.remVert(9);
        } catch (Exception e) {
            flag = true;
        }
        assertTrue(flag,
                "Removing a non-existing vertex should throw an exception.");

        adj.remVert(1); // left [0]
        out = adj.matrix();
        assertEquals(--cnt, out.length,
                "Matrix should have 1 row after removing vertex 1.");
        assertEquals(cnt, out[0].length,
                "Matrix should have 1 column after removing vertex 1.");

        adj.remVert(0);
        out = adj.matrix();
        assertEquals(--cnt, out.length,
                "Matrix should be empty after removing the last vertex.");
    }

    @Test
    void addEdgeTest() {
        matrixTest();
    }

    @Test
    void remEdgeTest() {
        Adjacency adj = new Adjacency();

        adj.addVert();
        adj.addEdge(0, 0);
        // 1

        adj.addVert();
        adj.addEdge(0, 1);
        adj.addEdge(0, 1);
        adj.addEdge(1, 1);
        adj.addEdge(1, 1);
        adj.addEdge(1, 1);
        // 1 2
        // 0 3

        assertEquals(1, adj.remEdge(0, 0),
                "Removing existing edge should return 1.");
        // successful — removed 1 edge
        // 0 2
        // 0 3
        int[][] out = adj.matrix();
        assertEquals(0, out[0][0],
                "Matrix value at (0,0) should be 0 after removing edge (0,0).");
        assertEquals(2, out[0][1],
                "Matrix value at (0,1) should remain 2.");
        assertEquals(0, out[1][0],
                "Matrix value at (1,0) should remain 0.");
        assertEquals(3, out[1][1],
                "Matrix value at (1,1) should remain 3.");

        assertEquals(0, adj.remEdge(0, 0),
                "Removing non-existing edge should return 0.");
        // unsuccessful — removed 0 edges
        // 0 2
        // 0 3
        out = adj.matrix();
        assertEquals(0, out[0][0],
                "Matrix value at (0,0) should remain 0.");
        assertEquals(2, out[0][1],
                "Matrix value at (0,1) should remain 2.");
        assertEquals(0, out[1][0],
                "Matrix value at (1,0) should remain 0.");
        assertEquals(3, out[1][1],
                "Matrix value at (1,1) should remain 3.");

        assertEquals(1, adj.remEdge(1, 1),
                "Removing edge (1,1) should return 1.");
        // successful — removed 1 edge
        // 0 2
        // 0 2
        out = adj.matrix();
        assertEquals(0, out[0][0],
                "Matrix value at (0,0) should remain 0.");
        assertEquals(2, out[0][1],
                "Matrix value at (0,1) should remain 2.");
        assertEquals(0, out[1][0],
                "Matrix value at (1,0) should remain 0.");
        assertEquals(2, out[1][1],
                "Matrix value at (1,1) should be 2 after removing one edge.");
    }

    @Test
    void getNeighboursTest() {
        Adjacency adj = new Adjacency();
        adj.addVert();
        adj.addVert();
        adj.addVert();
        adj.addEdge(0, 0);
        adj.addEdge(0, 0);
        adj.addEdge(1, 0);
        adj.addEdge(0, 2);
        adj.addEdge(2, 1);
        adj.addEdge(2, 2);
        // 2 0 1
        // 1 0 0
        // 0 1 1

        int[] neighbours = adj.getNeighbours(0); // should be equals {0, 1, 2}
        assertEquals(0, neighbours[0],
                "First neighbour of vertex 0 should be 0.");
        assertEquals(1, neighbours[1],
                "Second neighbour of vertex 0 should be 1.");
        assertEquals(2, neighbours[2],
                "Third neighbour of vertex 0 should be 2.");
    }

    @Test
    void printGraphTest() {
        Adjacency adj = new Adjacency();

        for (int i = 0; i < 5; i++) {
            adj.addVert();
        }
        for (int aboba = 0; aboba < 3; aboba++) {
            for (int i = 0; i < adj.getVertCount(); i++) {
                adj.addEdge(i, adj.getVertCount() - 1 - i);
            }
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(outputStream));

        adj.printGraph();

        String output = "0 0 0 0 3 " + System.lineSeparator()
                + "0 0 0 3 0 " + System.lineSeparator()
                + "0 0 3 0 0 " + System.lineSeparator()
                + "0 3 0 0 0 " + System.lineSeparator()
                + "3 0 0 0 0 " + System.lineSeparator();
        System.setOut(oldOut);
        assertEquals(output, outputStream.toString(),
                "Printed graph should match the expected output.");
    }

    @Test
    void readFileTest() {
        String fileName = "readFileAdj.txt";

        Adjacency adj = new Adjacency();
        adj.readFile(fileName);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(outputStream));

        adj.printGraph();
        String output = "1 0 0 9 2 " + System.lineSeparator()
                + "5 7 3 0 8 " + System.lineSeparator()
                + "2 9 6 6 2 " + System.lineSeparator()
                + "0 4 7 9 2 " + System.lineSeparator()
                + "3 0 5 9 2 " + System.lineSeparator();
        System.setOut(oldOut);
        assertEquals(output, outputStream.toString(),
                "Graph read from file should match the expected output.");
    }
}