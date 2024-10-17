package ru.nsu.martynov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class EdgeTest {

    @Test
    void testToString() {
        Edge edge = new Edge(1, 1, 1);
        String output = "Edge{from=1, to=1, count=1}";
        assertEquals(output, edge.toString());
    }

    @Test
    void testEquals() {
        Edge edge = new Edge(1, 1, 1);
        assertEquals(edge, edge);
        Edge edge1 = new Edge(1, 1, 1);
        assertEquals(edge, edge1);
        Edge edge2 = new Edge(1, 1, 2);
        assertNotEquals(edge, edge2);
        Edge edge3 = new Edge(1, 2, 1);
        assertNotEquals(edge, edge3);
        Edge edge4 = new Edge(1, 2, 2);
        assertNotEquals(edge, edge4);
        Edge edge5 = new Edge(2, 1, 1);
        assertNotEquals(edge, edge5);
        Edge edge6 = new Edge(2, 1, 2);
        assertNotEquals(edge, edge6);
        Edge edge7 = new Edge(2, 2, 1);
        assertNotEquals(edge, edge7);
        Edge edge8 = new Edge(2, 2, 2);
        assertNotEquals(edge, edge8);

        assertNotEquals(edge, 5);
        assertNotEquals(edge, null);

    }
}