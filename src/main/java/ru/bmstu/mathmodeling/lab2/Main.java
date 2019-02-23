package ru.bmstu.mathmodeling.lab2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Main {
    private static final int K = 10;
    private static final int N = 3;

    public static void main(String[] args) {
        int[] coords = new Random().ints(N * 2, 0, 1 << K).toArray();
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < coords.length; i += 2) {
            points.add(new Point(coords[i], coords[i + 1]));
        }

        System.out.println(points);
    }

    public static void triangulate(List<Point> points) {
        points.sort(Comparator.comparingLong(Point::getZCode));
        List<Triangle> triangles = new ArrayList<>();

        triangles.add(new Triangle(points.get(0), points.get(1), points.get(2)));

        if (points.size() > 3) {
            for (int i = 3; i < points.size(); i++) {

            }
        }
    }
}
