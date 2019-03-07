package ru.bmstu.mathmodeling.lab2;

import ru.bmstu.common.TriangleDrawer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Main {
    private static final int N = 15;

    public static final int WINDOW_SIZE = 720;
    public static final Environment ENVIRONMENT = Environment.DEVELOPMENT;

    public static void main(String[] args) {
        int fieldsCount = 1;
        for (int i = 1; fieldsCount < N; i++) {
            fieldsCount = (int) Math.pow(i, 2);
        }
        boolean[] isFilledField = new boolean[fieldsCount];
        for (int i = 0; i < fieldsCount; i++) {
            isFilledField[i] = false;
        }

        int count = 0;
        Random random = new Random();
        List<Point> points = new ArrayList<>();
        while (count < N) {
            int i = random.nextInt(fieldsCount);
            if (!isFilledField[i]) {
                isFilledField[i] = true;

                int size = (int) Math.sqrt(fieldsCount);
                int xBound = i % size;
                int yBound = i / size;

                int xLowBound = WINDOW_SIZE / size * xBound;
                int xHighBound = WINDOW_SIZE / size * (xBound + 1);

                int yLowBound = WINDOW_SIZE / size * yBound;
                int yHighBound = WINDOW_SIZE / size * (yBound + 1);

                points.add(new Point(xLowBound + random.nextInt(xHighBound - xLowBound), yLowBound + random.nextInt(yHighBound - yLowBound)));
                count++;
            }
        }

        showTriangulation(points);
    }

    public static void showTriangulation(List<Point> points) {
        Triangulation triangulation = new Triangulation(points);

        TriangleDrawer drawer = new TriangleDrawer(WINDOW_SIZE, WINDOW_SIZE, triangulation, points);
        drawer.draw();

        //triangulation.triangulate();

        System.out.println(triangulation.getCircles());
    }
}
