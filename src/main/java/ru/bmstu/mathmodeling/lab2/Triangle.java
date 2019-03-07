package ru.bmstu.mathmodeling.lab2;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

public class Triangle {
    private Point[] points;
    private Circle circumCircle;

    public Triangle(Point a, Point b, Point c) {
        points = new Point[]{a, b, c};

        if (a.equals(b) || a.equals(c) || b.equals(c)) {
            throw new IllegalArgumentException("Wrong points");
        }

//        a.addTriangle(this);
//        b.addTriangle(this);
//        c.addTriangle(this);
    }

    public Point getFirst() {
        return points[0];
    }

    public Point getSecond() {
        return points[1];
    }

    public Point getThird() {
        return points[2];
    }

    public Point[] getPoints() {
        return points;
    }

    public Set<Triangle> getNeighbours() {
        Set<Triangle> adjacentTrs1 = points[0].getTriangles();
        Set<Triangle> adjacentTrs2 = points[1].getTriangles();
        Set<Triangle> adjacentTrs3 = points[2].getTriangles();

        Sets.SetView<Triangle> neighbours1 = Sets.intersection(adjacentTrs1, adjacentTrs2);
        Sets.SetView<Triangle> neighbours2 = Sets.intersection(adjacentTrs1, adjacentTrs3);
        Sets.SetView<Triangle> neighbours3 = Sets.intersection(adjacentTrs2, adjacentTrs3);

        Sets.SetView<Triangle> union = Sets.union(neighbours1, neighbours2);

        Set<Triangle> result = Sets.newHashSet(Sets.union(union, neighbours3));
        result.remove(this);

        return result;
    }

    //TODO можно добавить еще случай "на стороне треугольника" (одно из чисел будет 0)
    public boolean isPointInside(Point point) {
        double a = (points[0].getX() - point.getX()) * (points[1].getY() - points[0].getY())
                - (points[1].getX() - points[0].getX()) * (points[0].getY() - point.getY());

        double b = (points[1].getX() - point.getX()) * (points[2].getY() - points[1].getY())
                - (points[2].getX() - points[1].getX()) * (points[1].getY() - point.getY());

        double c = (points[2].getX() - point.getX()) * (points[0].getY() - points[2].getY())
                - (points[0].getX() - points[2].getX()) * (points[2].getY() - point.getY());

        if (a >= 0 && b >= 0 && c >= 0 || a < 0 && b < 0 && c < 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean contains(Point point) {
        return points[0].equals(point) || points[1].equals(point) || points[2].equals(point);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        Triangle other = (Triangle) obj;
        return other.contains(points[0]) && other.contains(points[1]) && other.contains(points[2]);
    }

    @Override
    public int hashCode() {
        return points[0].hashCode() + points[1].hashCode() + points[2].hashCode();
    }

    public Set<Point> getClosestEdge(Point point) {
        Set<Point> closest = ImmutableSet.of(points[0], points[1]);
        double min = Utils.getDistanceToSegment(point, points[0], points[1]);

        double current = Utils.getDistanceToSegment(point, points[0], points[2]);
        if (current < min) {
            min = current;
            closest = ImmutableSet.of(points[0], points[2]);
        }

        current = Utils.getDistanceToSegment(point, points[1], points[2]);
        if (current < min) {
            closest = ImmutableSet.of(points[1], points[2]);
        }

        return closest;
    }

    public Point getLastPoint(Collection<Point> edge) {
        if (edge.size() != 2) {
            throw new IllegalArgumentException("Edge must contain 2 points");
        }

        if (!edge.contains(points[0])) {
            return points[0];
        } else if (!edge.contains(points[1])) {
            return points[1];
        } else {
            return points[2];
        }
    }

    public void setCircumCircle(Circle circumCircle) {
        this.circumCircle = circumCircle;
    }

    public Circle getCircumCircle() {
        return circumCircle;
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "points=" + Arrays.toString(points) +
                '}';
    }
}
