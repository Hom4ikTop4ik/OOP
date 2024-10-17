package ru.nsu.martynov;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ListGraphTest {
    @Test
    void listTest() {
        ListGraph listGraph = new ListGraph();
        assertTrue(listGraph.list().isEmpty(),
                "Initial list of edges should be empty.");
    }

    @Test
    void addVertTest() {
        ListGraph listGraph = new ListGraph();
        listGraph.addVert(); // В текущей реализации вершина не сохраняется, тест добавлен для формы
        assertEquals(0, listGraph.getVertCount(),
                "Vert count should still be 0.");
    }

    @Test
    void remVertTest() {
        ListGraph listGraph = new ListGraph();
        listGraph.addEdge(0, 1);
        listGraph.addEdge(1, 2);
        // 0 1 0
        // 0 0 1
        // 0 0 0
        assertEquals(3, listGraph.getVertCount(),
                "Vert count should be 3.");
        listGraph.remVert(1);
        // 0 x 0
        // x x x
        // 0 x 0
        assertEquals(0, listGraph.getVertCount(),
                "Vert count should be 0 after removing vertex 1.");
        Set<Edge> edges = listGraph.list();
        for (Edge edge : edges) {
            assertTrue(edge.from != 1 && edge.to != 1,
                    "Edges connected to vertex 1 should be removed.");
        }
    }

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
        assertEquals(1, edge.count,
                "Initial edge count should be 1.");

        listGraph.addEdge(0, 1);
        assertEquals(1, edges.size(),
                "Edge count should still be 1 (with increased weight).");
        edge = edges.iterator().next();
        assertEquals(2, edge.count,
                "Edge count should increase to 2.");
    }

    @Test
    void remEdgeTest() {
        ListGraph listGraph = new ListGraph();
        listGraph.addEdge(0, 1);
        assertEquals(1, listGraph.remEdge(0, 1),
                "Removing an existing edge should return 1.");
        assertEquals(0, listGraph.remEdge(0, 1),
                "Removing a non-existing edge should return 0.");
        assertTrue(listGraph.list().isEmpty(),
                "List of edges should be empty after removal.");
    }

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

    @Test
    void printGraphTest() {
        ListGraph listGraph = new ListGraph();

        int num = 5;

        for (int aboba = 0; aboba < 2; aboba++) {
            for (int i = 0; i < num; i++) {
                listGraph.addEdge(3, 7);
            }
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(outputStream));

        listGraph.printGraph();

        String output = "Edge{from=3, to=7, count=10}" + System.lineSeparator();
        System.setOut(oldOut);
        assertEquals(output, outputStream.toString());
    }

    @Test
    void readFileTest() {
        String fileName = "readFileList.txt";

        ListGraph listGraph = new ListGraph();
        listGraph.readFile(fileName);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(outputStream));

        listGraph.printGraph();
        String output = "Edge{from=3, to=1, count=9}" + System.lineSeparator();
        System.setOut(oldOut);
        assertEquals(output, outputStream.toString());
    }
}