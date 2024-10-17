package ru.nsu.martynov;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

class IncidenceTest {

    /**
     * Tests the vertex count functionality of the Incidence class.
     * Initially, the vertex count should be 0.
     * After adding vertices, the count should increase accordingly.
     */
    @Test
    void getVertCountTest() {
        Incidence inc = new Incidence();
        assertEquals(0, inc.getVertCount(), "Initial vertex count should be 0.");
        inc.addVert();
        assertEquals(1, inc.getVertCount(), "Vertex count should be 1 after adding one vertex.");
        inc.addVert();
        assertEquals(2, inc.getVertCount(), "Vertex count should be 2 after adding two vertices.");
        inc.addVert();
        inc.addVert();
        assertEquals(4, inc.getVertCount(), "Vertex count should be 4 after adding four vertices.");
    }

    /**
     * Tests the edge count functionality of the Incidence class.
     * Initially, the edge count should be 0.
     * After adding edges, the count should increase accordingly.
     */
    @Test
    void getEdgeCountTest() {
        Incidence inc = new Incidence();
        assertEquals(0, inc.getEdgeCount(), "Initial edge count should be 0.");
        inc.addVert();
        inc.addEdge(0, 0);
        assertEquals(1, inc.getEdgeCount(), "Edge count should be 1 after adding one edge.");
        inc.addVert();
        inc.addEdge(0, 1);
        assertEquals(2, inc.getEdgeCount(), "Edge count should be 2 after adding two edges.");
    }

    /**
     * Tests the correctness of the matrix representation after adding vertices and edges.
     */
    @Test
    void matrixTest() {
        Incidence inc = new Incidence();
        inc.addVert();
        inc.addEdge(0, 0);
        int[][] out1 = inc.matrix();
        assertEquals(2, out1[0][0], "Matrix value at (0,0) should be 2 after adding edge (0,0).");

        inc.addVert();
        inc.addEdge(0, 1);
        inc.addEdge(1, 1);
        int[][] out2 = inc.matrix();
        assertEquals(2, out2[0][0], "Matrix value at (0,0) should remain 2.");
        assertEquals(1, out2[0][1], "Matrix value at (0,1) should be 1 after adding edge (0,1).");
        assertEquals(0, out2[0][2], "Matrix value at (0,2) should be 0.");
        assertEquals(0, out2[1][0], "Matrix value at (1,0) should be 0.");
        assertEquals(-1, out2[1][1], "Matrix value at (1,1) should be -1 after adding edge (1,1).");
        assertEquals(2, out2[1][2], "Matrix value at (1,2) should be 2.");
    }

    /**
     * Tests the addVert() method to ensure that the matrix is updated correctly when vertices are added.
     */
    @Test
    void addVertTest() {
        Incidence inc = new Incidence();
        int[][] out0 = inc.matrix();
        assertEquals(0, out0.length, "Matrix should initially be empty.");

        inc.addVert();
        int[][] out1 = inc.matrix();
        assertEquals(1, out1.length, "Matrix should have 1 row after adding one vertex.");
        assertEquals(0, out1[0].length, "Matrix should have 0 columns after adding one vertex with no edges.");

        inc.addVert();
        int[][] out2 = inc.matrix();
        assertEquals(2, out2.length, "Matrix should have 2 rows after adding two vertices.");
        assertEquals(0, out2[0].length, "Matrix should still have 0 columns.");
        assertEquals(0, out2[1].length, "Matrix should still have 0 columns in the second row.");
    }

    /**
     * Tests the remVert() method to ensure that vertices and their associated edges are removed correctly.
     */
    @Test
    void remVertTest() {
        Incidence inc = new Incidence();
        int cnt = 5;
        for (int i = 0; i < cnt; i++) {
            inc.addVert();
        }

        inc.addEdge(0, 1);
        inc.addEdge(2, 3);

        assertEquals(cnt, inc.getVertCount(), "Vertex count should be 5 after adding 5 vertices.");

        inc.remVert(0); // удаляем вершину 0
        assertEquals(4, inc.getVertCount(), "Vertex count should be 4 after removing vertex 0.");

        boolean flag = false;
        try {
            inc.remVert(-1); // попытка удалить несуществующую вершину
        } catch (Exception e) {
            flag = true;
        }
        assertTrue(flag, "Removing a vertex with negative index should throw an exception.");

        inc.remVert(3);
        assertEquals(3, inc.getVertCount(), "Vertex count should be 3 after removing vertex 3.");
    }

    /**
     * Tests adding edges between vertices and updating the matrix representation accordingly.
     */
    @Test
    void addEdgeTest() {
        matrixTest(); // already tested within matrixTest()
    }

    /**
     * Tests removing edges between vertices and updating the matrix representation accordingly.
     */
    @Test
    void remEdgeTest() {
        Incidence inc = new Incidence();
        inc.addVert();
        inc.addEdge(0, 0);
        inc.addVert();
        inc.addEdge(0, 1);
        inc.addEdge(1, 1);

        assertEquals(1, inc.remEdge(0, 0), "Removing edge (0,0) should return 1.");
        int[][] out = inc.matrix();
        assertEquals(0, out[0][0], "Matrix value at (0,0) should be 0 after removing edge (0,0).");
        assertEquals(1, out[0][1], "Matrix value at (0,1) should remain 1.");

        assertEquals(0, inc.remEdge(0, 0), "Removing non-existing edge (0,0) should return 0.");
    }

    /**
     * Tests retrieving the neighbors of a given vertex.
     */
    @Test
    void getNeighboursTest() {
        Incidence inc = new Incidence();
        inc.addVert();
        inc.addVert();
        inc.addVert();
        inc.addEdge(0, 0);
        inc.addEdge(1, 0);
        inc.addEdge(0, 2);
        inc.addEdge(2, 1);

        int[] neighbours = inc.getNeighbours(0); // соседи для вершины 0
        assertArrayEquals(new int[]{0, 1, 2}, neighbours, "Neighbours of vertex 0 should be {0, 1, 2}.");
    }

    /**
     * Tests the printGraph() method by verifying the printed matrix representation of the graph.
     */
    @Test
    void printGraphTest() {
        Incidence inc = new Incidence();
        for (int i = 0; i < 5; i++) {
            inc.addVert();
        }
        for (int aboba = 0; aboba < 3; aboba++) {
            for (int i = 0; i < inc.getVertCount(); i++) {
                inc.addEdge(i, inc.getVertCount() - 1 - i);
            }
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(outputStream));

        inc.printGraph();

        String output = " 3  0  0  0 -3 " + System.lineSeparator()
                + " 0  3  0 -3  0 " + System.lineSeparator()
                + " 0  0  6  0  0 " + System.lineSeparator()
                + " 0 -3  0  3  0 " + System.lineSeparator()
                + "-3  0  0  0  3 " + System.lineSeparator();
        System.setOut(oldOut);
        assertEquals(output, outputStream.toString(), "Printed graph should match the expected output.");
    }

    /**
     * Tests reading a graph from a file and ensuring the matrix representation matches expectations.
     */
    @Test
    void readFileTest() {
        String fileName = "readFileInc.txt";
        Incidence inc = new Incidence();
        inc.readFile(fileName);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(outputStream));

        inc.printGraph();
        String output = " 1  0  0  0 -1 " + System.lineSeparator()
                + " 0  1  0 -1  0 " + System.lineSeparator()
                + " 0  0  2  0  0 " + System.lineSeparator()
                + " 0 -1  0  1  0 " + System.lineSeparator()
                + "-1  0  0  0  1 " + System.lineSeparator();
        System.setOut(oldOut);
        assertEquals(output, outputStream.toString(), "Matrix read from file should match the expected output.");
    }

    /**
     * Tests handling of invalid files with errors such as missing data or incorrect format.
     */
    @Test
    void badFileTest() {
        String fileName = "badFile.txt";
        Incidence inc = new Incidence();
        boolean flag = false;
        try {
            inc.readFile(fileName);
        } catch (Exception e) {
            flag = true;
        }
        assertTrue(flag, "Reading from an invalid file should throw an exception.");
    }
}
