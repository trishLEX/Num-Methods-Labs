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
}
