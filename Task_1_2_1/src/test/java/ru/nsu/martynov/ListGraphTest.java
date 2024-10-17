package ru.nsu.martynov;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Set;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the ListGraph class, covering basic graph operations
 * such as adding/removing vertices and edges, getting neighbors, and
 * reading from a file.
 */
class ListGraphTest {

    /**
     * Tests that the initial list of edges in a new ListGraph is empty.
     */
    @Test
    void listTest() {
        ListGraph listGraph = new ListGraph();
        assertTrue(listGraph.list().isEmpty(),
                "Initial list of edges should be empty.");
    }

    /**
     * Tests adding a vertex. Currently, this operation doesn't change
     * the internal vertex count in this implementation.
     */
    @Test
    void addVertTest() {
        ListGraph listGraph = new ListGraph();
        listGraph.addVert(); // Currently has no effect on vertex count
        assertEquals(0, listGraph.getVertCount(),
                "Vert count should still be 0.");
    }

    /**
     * Tests removing a vertex, which should remove edges connected to it.
     */
    @Test
    void remVertTest() {
        ListGraph listGraph = new ListGraph();
        listGraph.addEdge(0, 1);
        listGraph.addEdge(1, 2);
        assertEquals(3, listGraph.getVertCount(),
                "Vert count should be 3.");

        listGraph.remVert(1); // Remove vertex 1
        assertEquals(0, listGraph.getVertCount(),
                "Vert count should be 0 after removing vertex 1.");

        Set<Edge> edges = listGraph.list();
        for (Edge edge : edges) {
            assertTrue(edge.from != 1 && edge.to != 1,
                    "Edges connected to vertex 1 should be removed.");
        }
    }

    /**
     * Tests adding an edge to the graph.
     * - Ensures that the edge is added correctly.
     * - Adds the same edge twice to increase its weight (count).
     */
    @Test
    void addEdgeTest() {
        ListGraph listGraph = new ListGraph();
        listGraph.addEdge(0, 1);
        Set<Edge> edges = listGraph.list();
        assertEquals(1, edges.size(),
                "There should be one edge after adding.");

        Edge edge = edges.iterator().next();
        assertEquals(0, edge.from);
        assertEquals(1, edge.to);
        assertEquals(1, edge.count, "Initial edge count should be 1.");

        listGraph.addEdge(0, 1);
        assertEquals(1, edges.size(),
                "Edge count should still be 1 (with increased weight).");
        edge = edges.iterator().next();
        assertEquals(2, edge.count, "Edge count should increase to 2.");
    }

    /**
     * Tests removing an edge from the graph.
     * - Checks return values when removing existing and non-existing edges.
     */
    @Test
    void remEdgeTest() {
        ListGraph listGraph = new ListGraph();
        listGraph.addEdge(0, 0);
        listGraph.addEdge(0, 1);
        listGraph.addEdge(0, 1);
        assertEquals(1, listGraph.remEdge(0, 1),
                "Removing an existing edge should return 1.");
        assertEquals(1, listGraph.remEdge(0, 1),
                "Removing an existing edge should return 1.");
        assertEquals(0, listGraph.remEdge(0, 1),
                "Removing a non-existing edge should return 0.");
        assertEquals(1, listGraph.remEdge(0, 0),
                "Removing an existing edge should return 1.");
        assertTrue(listGraph.list().isEmpty(),
                "List of edges should be empty after removal.");
    }

    /**
     * Tests getting the vertex count based on added edges.
     */
    @Test
    void getVertCountTest() {
        ListGraph listGraph = new ListGraph();
        assertEquals(0, listGraph.getVertCount(),
                "Initial vertex count should be 0.");

        listGraph.addEdge(0, 1);
        assertEquals(2, listGraph.getVertCount(),
                "Vertex count should be 2 after adding edge (0, 1).");

        listGraph.addEdge(1, 2);
        assertEquals(3, listGraph.getVertCount(),
                "Vertex count should be 3 after adding edge (1, 2).");
    }

    /**
     * Tests retrieving the neighbors of a vertex.
     */
    @Test
    void getNeighboursTest() {
        ListGraph listGraph = new ListGraph();
        listGraph.addEdge(0, 1);
        listGraph.addEdge(0, 2);
        listGraph.addEdge(1, 2);

        int[] neighbours0 = listGraph.getNeighbours(0);
        assertArrayEquals(new int[]{1, 2}, neighbours0,
                "Neighbours of vertex 0 should be 1 and 2.");

        int[] neighbours1 = listGraph.getNeighbours(1);
        assertArrayEquals(new int[]{0, 2}, neighbours1,
                "Neighbours of vertex 1 should be 0 and 2.");

        int[] neighbours2 = listGraph.getNeighbours(2);
        assertArrayEquals(new int[]{0, 1}, neighbours2,
                "Neighbours of vertex 2 should be 0 and 1.");
    }

    /**
     * Tests the printGraph method by capturing and comparing the console output.
     */
    @Test
    void printGraphTest() {
        ListGraph listGraph = new ListGraph();
        int num = 5;

        // Add 10 edges from vertex 3 to vertex 7
        for (int aboba = 0; aboba < 2; aboba++) {
            for (int i = 0; i < num; i++) {
                listGraph.addEdge(3, 7);
            }
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(outputStream));

        listGraph.printGraph();
        String expectedOutput = "Edge{from=3, to=7, count=10}" + System.lineSeparator();
        System.setOut(oldOut);

        assertEquals(expectedOutput, outputStream.toString());
    }

    /**
     * Tests reading a graph from a file and verifying its contents.
     */
    @Test
    void readFileTest() {
        String fileName = "readFileList.txt";
        ListGraph listGraph = new ListGraph();
        listGraph.readFile(fileName);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(outputStream));

        listGraph.printGraph();
        String expectedOutput = "Edge{from=3, to=1, count=9}" + System.lineSeparator();
        System.setOut(oldOut);

        assertEquals(expectedOutput, outputStream.toString());
    }

    /**
     * Tests reading a graph from not existing file.
     */
    @Test
    void readFileTestNotExist() {
        String fileName = "readFileListNotExist.txt";
        ListGraph listGraph = new ListGraph();


        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(outputStream));

        listGraph.readFile(fileName);
        String output = "Error reading file " + fileName;
        assertEquals(output, outputStream.toString());
    }

    /**
     * Tests reading a graph from small file (not enough numbers).
     */
    @Test
    void readFileTestNotEnough() {
        String fileName = "readFileListNotEnough.txt";
        ListGraph listGraph = new ListGraph();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            listGraph.readFile(fileName);
        } catch (IllegalArgumentException e) {
            System.out.print(e.getMessage());
        }
        String output = "File is bad — not enough numbers in table";
        assertEquals(output, outputStream.toString());
    }

    /**
     * Tests reading a graph from small file (there is not size of table).
     */
    @Test
    void readFileTestNoSize() {
        String fileName = "readFileListNoSize.txt";
        ListGraph listGraph = new ListGraph();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            listGraph.readFile(fileName);
        } catch (IllegalArgumentException e) {
            System.out.print(e.getMessage());
        }
        String output = "File is bad — there isn't table size (count of vertices)";
        assertEquals(output, outputStream.toString());
    }
}
