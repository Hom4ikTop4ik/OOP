package ru.nsu.martynov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class EdgeTest {

    /**
     * Tests the string representation of the Edge class.
     * This test checks if the toString method correctly formats the output
     * for an Edge object with specific values.
     */
    @Test
    void testToString() {
        Edge edge = new Edge(1, 1, 1);
        String output = "Edge{from=1, to=1, count=1}";
        assertEquals(output, edge.toString());
    }

    /**
     * Tests the equality comparison of Edge objects.
     * This test checks if the equals method works correctly by comparing
     * different Edge instances with various attributes, including:
     * - A comparison of an Edge with itself (should be equal).
     * - Comparisons of Edge instances with the same and different attributes
     *     (should be equal or not equal as appropriate).
     * - Comparisons with an integer and null (should not be equal).
     */
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

        // Test non-Edge objects
        assertNotEquals(edge, 5);
        assertNotEquals(edge, null);
    }
}
