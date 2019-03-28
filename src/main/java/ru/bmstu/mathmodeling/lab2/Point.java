package ru.bmstu.mathmodeling.lab2;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static ru.bmstu.mathmodeling.lab2.Main.ENVIRONMENT;

public class Point {
    private double x;
    private double y;
    private double z;
    private long zCode;
    private Set<Triangle> triangles;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;

        byte[] bytes = new byte[Long.SIZE];
        for (int i = 0; i < Integer.SIZE; i++) {
            bytes[i * 2] = getBit(x, i);
            bytes[i * 2 + 1] = getBit(y, i);
        }

        zCode = 0;
        for (int i = 0; i < bytes.length; i++) {
            zCode += (1 << i) * (bytes[i] * 0xff);
        }

        triangles = new HashSet<>();
    }

    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;

        triangles = new HashSet<>();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public long getZCode() {
        return zCode;
    }

    private byte getBit(int num, int position) {
        return (byte) ((num >> position) & 1);
    }

    public double getDistance(Point other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    public void addTriangle(Triangle triangle) {
        triangles.add(triangle);
    }

    public Set<Triangle> getTriangles() {
        return triangles;
    }

    public void removeTriangle(Triangle triangle) {
        triangles.remove(triangle);
    }

    public Point withZ(int z) {
        this.z = z;
        return this;
    }

    public double getZ() {
        return z;
    }

    @Override
    public String toString() {
        if (ENVIRONMENT == Environment.DEVELOPMENT) {
            return String.format("(%f; %f)\n", x, y);
        } else {
            return String.format("points.add(new Point(%f, %f));\n", x, y);
//            return "Point{" +
//                    "x=" + x +
//                    ", y=" + y +
//                    ", zCode=" + zCode +
//                    '}';
        }
    }

    public static Point maxX(Point point, Point ... points) {
        Point max = point;
        for (Point p : points) {
            if (p.x > max.x) {
                max = p;
            }
        }

        return max;
    }

    public static Point minX(Point point, Point ... points) {
        Point min = point;
        for (Point p : points) {
            if (p.x < min.x) {
                min = p;
            }
        }

        return min;
    }

    public static Point maxY(Point point, Point ... points) {
        Point max = point;
        for (Point p : points) {
            if (p.y > max.y) {
                max = p;
            }
        }

        return max;
    }

    public static Point minY(Point point, Point ... points) {
        Point min = point;
        for (Point p : points) {
            if (p.y < min.y) {
                min = p;
            }
        }

        return min;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Point point = (Point) o;
        return this.x == point.x && this.y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
