package ru.bmstu.mathmodeling.lab2;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.bmstu.common.Drawer.RED;

public class Circle {
    private double[] center;
    private double radius;
    private double[] color;
    private Triangle triangle;

    public Circle(double[] center, double radius) {
        this.center = center;
        this.radius = radius;
        this.color = RED;
    }

    public Circle(double[] center, double radius, Triangle triangle) {
        this(center, radius);
        this.triangle = triangle;
    }

    public Circle withColor(double[] color) {
        this.color = color;
        return this;
    }

    public double[] getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    public double[] getColor() {
        return color;
    }

    public boolean doNotContainPoints(List<Triangle> triangles) {
        List<Point> points = triangles.stream()
                .map(tr -> Arrays.asList(tr.getPoints()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        for (Point p : points) {
            if (triangle.contains(p)) {
                continue;
            }
            if (Utils.getDistance(p.getX(), p.getY(), center[0], center[1]) < radius) {
                return false;
            }
        }

        return true;
    }

    public boolean containsSomePointOfTriangle(Triangle triangle) {
        return containsPoint(triangle.getFirst()) || containsPoint(triangle.getSecond()) || containsPoint(triangle.getThird());
    }

    public boolean containsPoint(Point p) {
        if (triangle.contains(p)) {
            return false;
        }

        return radius > distanceFromCenter(p);
    }

    private double distanceFromCenter(Point p) {
        return Utils.getDistance(center[0], center[1], p.getX(), p.getY());
    }

    public static Circle getCircumcircle(Triangle triangle) {
        double[] center = Utils.getCircumcenter(triangle);
        double radius = Utils.getDistance(center[0], center[1], triangle.getFirst().getX(), triangle.getFirst().getY());

        return new Circle(center, radius, triangle);
    }

    public void setColor(double[] color) {
        this.color = color;
    }

    public List<Point> filterPointsInside(List<Point> points) {
        return points
                .stream()
                .filter(p -> radius > Utils.getDistance(center[0], center[1], p.getX(), p.getY()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        Circle other = (Circle) obj;
        return Arrays.equals(this.center, other.center) && this.radius == other.radius;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(radius);
        result = 31 * result + Arrays.hashCode(center);
        return result;
    }

    @Override
    public String toString() {
        return "Circle{" +
                "center=" + Arrays.toString(center) +
                ", radius=" + radius +
                ", triangle=" + triangle +
                '}';
    }
}
