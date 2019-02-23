package ru.bmstu.mathmodeling.lab2;

import ru.bmstu.common.TriangleDrawer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Main {
    private static final int K = 9;
    private static final int N = 50;

    public static void main(String[] args) {
        int[] coords = new Random().ints(N * 2, 0, 1 << K).toArray();
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < coords.length; i += 2) {
            points.add(new Point(coords[i], coords[i + 1]));
        }
//        points.add(new Point(505, 344));
//        points.add(new Point(157, 480));
//        points.add(new Point(50, 345));
//        points.add(new Point(38, 155));

        points.sort(Comparator.comparingLong(Point::getZCode));

        System.out.println(points);

        Triangulation triangulation = new Triangulation();
        triangulation.triangulate(points);

        System.out.println(triangulation.getCircles());

        TriangleDrawer drawer = new TriangleDrawer(triangulation.getTriangles(), triangulation.getCircles());
        drawer.draw();
    }
}
