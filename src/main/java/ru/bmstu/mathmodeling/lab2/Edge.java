package ru.bmstu.mathmodeling.lab2;

public class Edge {
    private Point first;
    private Point second;

    public Edge(Point first, Point second) {
        this.first = first;
        this.second = second;
    }

    public Point getFirst() {
        return first;
    }

    public Point getSecond() {
        return second;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }
}
