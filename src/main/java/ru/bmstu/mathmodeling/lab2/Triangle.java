package ru.bmstu.mathmodeling.lab2;

import java.util.Arrays;
import java.util.List;

public class Triangle {
    private static final int SIZE = 3;
    private Point[] points;
    private Circle circumCircle;

    public Triangle(Point a, Point b, Point c) {
        points = new Point[SIZE];

        points[0] = a;
        points[1] = b;
        points[2] = c;
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
        return Arrays.hashCode(points);
    }

    public List<Point> getClosestEdge(Point point) {
        List<Point> closest = Arrays.asList(points[0], points[1]);
        double min = Utils.getDistanceToSegment(point, points[0], points[1]);

        double current = Utils.getDistanceToSegment(point, points[0], points[2]);
        if (current < min) {
            min = current;
            closest = Arrays.asList(points[0], points[2]);
        }

        current = Utils.getDistanceToSegment(point, points[1], points[2]);
        if (current < min) {
            closest = Arrays.asList(points[1], points[2]);
        }

        return closest;
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
