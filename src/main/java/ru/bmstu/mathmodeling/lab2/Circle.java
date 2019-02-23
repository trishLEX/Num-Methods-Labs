package ru.bmstu.mathmodeling.lab2;

import java.util.Arrays;

public class Circle {
    private double[] center;
    private double radius;

    public Circle(double[] center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public double[] getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "Circle{" +
                "center=" + Arrays.toString(center) +
                ", radius=" + radius +
                '}';
    }
}
