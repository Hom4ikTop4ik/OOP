package ru.nsu.martynov;

// Класс для представления ребра
public class Edge {
    int from;
    int to;
    int count;

    public Edge(int from, int to, int count) {
        this.from = from;
        this.to = to;
        this.count = count;
    }

    @Override
    public String toString() {
        return "Edge{"
                + "from=" + from
                + ", to=" + to
                + ", count=" + count
                + '}';
    }

//    @Override
//    public int hashCode() {
//        return this.from * Helper.FIRST_PRIME
//                + this.to * Helper.SECOND_PRIME
//                + this.count * Helper.THIRD_PRIME;
//    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Edge edge = (Edge) obj;
        return from == edge.from && to == edge.to && count == edge.count;
    }
}