package ru.bmstu.mathmodeling.lab2;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@ParametersAreNonnullByDefault
public class Utils {
    private Utils() {
        //utility class
    }

    public static Triangle getClosestTriangle(Point point, List<Triangle> triangles) {
        Triangle closest = triangles.get(0);
        double min = getDistanceToTriangle(point, closest);

        if (triangles.size() > 1) {
            for (int i = 1; i < triangles.size(); i++) {
                Triangle triangle = triangles.get(i);
                double distance = getDistanceToTriangle(point, triangle);
                if (distance < min) {
                    min = distance;
                    closest = triangle;
                }
            }
        }

        return closest;
    }

    public static double getDistanceToTriangle(Point point, Triangle triangle) {
        Point[] points = triangle.getPoints();
        double min = getDistanceToSegment(point, points[0], points[1]);
        min = Math.min(min, getDistanceToSegment(point, points[0], points[2]));
        min = Math.min(min, getDistanceToSegment(point, points[1], points[2]));

        return min;
    }

    public static double getDistanceToSegment(Point point, Point start, Point end) {
        if (point.getX() > Point.minX(start, end).getX() && point.getX() < Point.maxX(start, end).getX()
                || point.getY() > Point.minY(start, end).getY() && point.getY() < Point.minY(start, end).getY())
        {
            return Math.abs(
                    (end.getY() - start.getY()) * point.getX()
                            - (end.getX() - start.getX()) * point.getY()
                            + end.getX() * start.getY()
                            - end.getY() * start.getX()
            ) / Math.sqrt(Math.pow(end.getY() - start.getY(), 2) + Math.pow(end.getX() - start.getX(), 2));
        } else {
            return Double.min(point.getDistance(start), point.getDistance(end));
        }
    }

    public static double getDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public static double[] getCircumcenter(Point a, Point b, Point c) {
        double d = 2 * (a.getX() * (b.getY() - c.getY())
                + b.getX() * (c.getY() - a.getY())
                + c.getX() * (a.getY() - b.getY()));

        double centerX = ((Math.pow(a.getX(), 2) + Math.pow(a.getY(), 2)) * (b.getY() - c.getY())
                + (Math.pow(b.getX(), 2) + Math.pow(b.getY(), 2)) * (c.getY() - a.getY())
                + (Math.pow(c.getX(), 2) + Math.pow(c.getY(), 2)) * (a.getY() - b.getY())) / d;

        double centerY = ((Math.pow(a.getX(), 2) + Math.pow(a.getY(), 2)) * (c.getX() - b.getX())
                + (Math.pow(b.getX(), 2) + Math.pow(b.getY(), 2)) * (a.getX() - c.getX())
                + (Math.pow(c.getX(), 2) + Math.pow(c.getY(), 2)) * (b.getX() - a.getX())) / d;

        return new double[]{centerX, centerY};
    }

    public static double[] getCircumcenter(Triangle triangle) {
        return getCircumcenter(triangle.getFirst(), triangle.getSecond(), triangle.getThird());
    }
}
