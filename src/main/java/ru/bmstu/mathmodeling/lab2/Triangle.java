package ru.bmstu.mathmodeling.lab2;

public class Triangle {
    private static final int SIZE = 3;
    private Point[] points;

    public Triangle(Point a, Point b, Point c) {
        points = new Point[SIZE];

        points[0] = a;
        points[1] = b;
        points[2] = c;
    }

    public Point[] getPoints() {
        return points;
    }
}
