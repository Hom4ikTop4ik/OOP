package ru.nsu.martynov;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class IncidenceTest {
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

    @Test
    void matrixTest() {
        Incidence inc = new Incidence();

        inc.addVert();
        inc.addEdge(0, 0);
        int[][] out1 = inc.matrix();
        // 2
        assertEquals(2, out1[0][0], "Matrix value at (0,0) should be 2 after adding edge (0,0).");

        inc.addVert();
        inc.addEdge(0, 1);
        inc.addEdge(1, 1);
        int[][] out2 = inc.matrix();
        // 2  1 0
        // 0 -1 2
        assertEquals(2, out2[0][0], "Matrix value at (0,0) should remain 2.");
        assertEquals(1, out2[0][1], "Matrix value at (0,1) should be 1 after adding edge (0,1).");
        assertEquals(0, out2[0][2], "Matrix value at (0,2) should be 0.");
        assertEquals(0, out2[1][0], "Matrix value at (1,0) should be 0.");
        assertEquals(-1, out2[1][1], "Matrix value at (1,1) should be -1 after adding edge (1,1).");
        assertEquals(2, out2[1][2], "Matrix value at (1,2) should be 2.");
    }

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

    @Test
    void remVertTest() {
        Incidence inc = new Incidence();
        int cnt = 5;
        for (int i = 0; i < cnt; i++) {
            inc.addVert();
        }

        inc.addEdge(0, 1);
        inc.addEdge(2, 3);
        int[][] out = inc.matrix(); // 5 вершин, 2 рёбра

        assertEquals(cnt, inc.getVertCount(), "Vertex count should be 5 after adding 5 vertices.");

        inc.remVert(0); // удаляем вершину 0
        out = inc.matrix();
        assertEquals(4, inc.getVertCount(), "Vertex count should be 4 after removing vertex 0.");

        // Try remove not existing vert
        boolean flag = false;
        try {
            inc.remVert(-1);
        } catch (Exception e) {
            flag = true;
        }
        assertTrue(flag, "Removing a vertex with negative index should throw an exception.");

        inc.remVert(3);
        out = inc.matrix();
        assertEquals(3, inc.getVertCount(), "Vertex count should be 3 after removing vertex 3.");
    }

    @Test
    void addEdgeTest() {
        matrixTest();
    }

    @Test
    void remEdgeTest() {
        Incidence inc = new Incidence();

        inc.addVert();
        inc.addEdge(0, 0);

        inc.addVert();
        inc.addEdge(0, 1);
        inc.addEdge(1, 1);
        int[][] out = inc.matrix();

        assertEquals(1, inc.remEdge(0, 0), "Removing edge (0,0) should return 1.");
        out = inc.matrix();
        assertEquals(0, out[0][0], "Matrix value at (0,0) should be 0 after removing edge (0,0).");
        assertEquals(1, out[0][1], "Matrix value at (0,1) should remain 1.");

        assertEquals(0, inc.remEdge(0, 0), "Removing non-existing edge (0,0) should return 0.");
    }

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

    @Test
    void printGraphTest() {
        PrintStream oldOut = System.out;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Incidence inc = new Incidence();

        for (int i = 0; i < 5; i++) {
            inc.addVert();
        }
        for (int aboba = 0; aboba < 3; aboba++) {
            for (int i = 0; i < inc.getVertCount(); i++) {
                inc.addEdge(i, inc.getVertCount() - 1 - i);
            }
        }
        inc.printGraph();

        String output = " 3  0  0  0 -3 " + System.lineSeparator()
                + " 0  3  0 -3  0 " + System.lineSeparator()
                + " 0  0  6  0  0 " + System.lineSeparator()
                + " 0 -3  0  3  0 " + System.lineSeparator()
                + "-3  0  0  0  3 " + System.lineSeparator();
        System.setOut(oldOut);
        assertEquals(output, outputStream.toString(), "Printed graph should match the expected output.");
    }

    @Test
    void readFileTest() {
        String fileName = "readFileInc.txt";
        PrintStream oldOut = System.out;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Incidence inc = new Incidence();
        inc.readFile(fileName);
        inc.printGraph();
        String output = " 1  0  0  0 -1 " + System.lineSeparator()
                + " 0  1  0 -1  0 " + System.lineSeparator()
                + " 0  0  2  0  0 " + System.lineSeparator()
                + " 0 -1  0  1  0 " + System.lineSeparator()
                + "-1  0  0  0  1 " + System.lineSeparator();
        System.setOut(oldOut);
        assertEquals(output, outputStream.toString(), "Graph read from file should match the expected output.");
    }

    @Test
    void readFileTestBad0() {
        String fileName = "readFileIncBadNotExist.txt";
        PrintStream oldOut = System.out;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Incidence inc = new Incidence();
        try {
            inc.readFile(fileName);
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        String output = "Error reading file readFileIncBadNotExist.txt";
        System.setOut(oldOut);
        assertEquals(output, outputStream.toString(), "Bad file. There is should be exception.");
    }

    @Test
    void readFileTestBad1() {
        String fileName = "readFileIncBad1.txt";
        PrintStream oldOut = System.out;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Incidence inc = new Incidence();
        try {
            inc.readFile(fileName);
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        String output = "File is bad — there isn't table size (count of vertices)";
        System.setOut(oldOut);
        assertEquals(output, outputStream.toString(), "Bad file. There is should be exception.");
    }

    @Test
    void readFileTestBad2() {
        String fileName = "readFileIncBad2.txt";
        PrintStream oldOut = System.out;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Incidence inc = new Incidence();
        try {
            inc.readFile(fileName);
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        String output = "File is bad — there isn't table size (count of edges)";
        System.setOut(oldOut);
        assertEquals(output, outputStream.toString(), "Bad file. There is should be exception.");
    }

    @Test
    void readFileTestBad3() {
        String fileName = "readFileIncBad3.txt";
        PrintStream oldOut = System.out;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Incidence inc = new Incidence();
        try {
            inc.readFile(fileName);
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        String output = "File is bad — not enough numbers in table";
        System.setOut(oldOut);
        assertEquals(output, outputStream.toString(), "Bad file. There is should be exception.");
    }

}