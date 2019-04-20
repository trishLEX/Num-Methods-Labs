package ru.bmstu.mathmodeling.lab4;

import java.util.Objects;

public class Point {
    private double x;
    private double y;
    private int year;

    public Point(double x, double y, int year) {
        this.x = x;
        this.y = y;
        this.year = year;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Double.compare(point.x, x) == 0 && Double.compare(point.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(x=" + x + ", y=" + y + ", year=" + year + ")";
    }
}
