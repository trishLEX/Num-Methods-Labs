package ru.bmstu.mathmodeling.lab2;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

public class Edge {
    private Point first;
    private Point second;
    private List<Triangle> triangles;

    public Edge(Point first, Point second) {
        this.first = first;
        this.second = second;

        this.triangles = new ArrayList<>(2);
    }

    public Point getFirst() {
        return first;
    }

    public Point getSecond() {
        return second;
    }

    public Set<Point> getPoints() {
        return ImmutableSet.of(first, second);
    }

    public void addTriangle(Triangle triangle) {
        if (triangles.size() == 2) {
            throw new IllegalStateException("Edge can be common only for max 2 triangles");
        }

        triangles.add(triangle);
    }

    public List<Triangle> getTriangles() {
        return triangles;
    }

    public boolean contains(Point point) {
        return first.equals(point) || second.equals(point);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        Edge other = (Edge) obj;

        return this.getPoints().equals(other.getPoints());
    }

    @Override
    public int hashCode() {
        return getPoints().hashCode();
    }

    @Override
    public String toString() {
        return "Edge{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }
}
