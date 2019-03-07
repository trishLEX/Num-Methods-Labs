package ru.bmstu.mathmodeling.lab2;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

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
                } else if (Double.compare(distance, min) == 0) {
                    if (closest.isPointInside(point)) {
                        return closest;
                    } else if (triangle.isPointInside(point)) {
                        return triangle;
                    } else {
                        min = distance;
                        closest = triangle;
                    }
                }
            }
        }

        return closest;
    }

    public static double getDistanceToTriangle(Point point, Triangle triangle) {
        List<Point> points = Lists.newArrayList(triangle.getPoints());
        double min = getDistanceToSegment(point, points.get(0), points.get(1));
        min = Math.min(min, getDistanceToSegment(point, points.get(0), points.get(2)));
        min = Math.min(min, getDistanceToSegment(point, points.get(1), points.get(2)));

        return min;
    }

    public static double getDistanceToSegment(Point point, Point start, Point end) {
        double k = ((end.getY() - start.getY()) * (point.getX() - start.getX()) - (end.getX() - start.getX()) * (point.getY() - start.getY())) /
                (Math.pow(end.getY() - start.getY(), 2) + Math.pow(end.getX() - start.getX(), 2));

        double x = point.getX() - k * (end.getY() - start.getY());
        double y = point.getY() + k * (end.getX() - start.getX());

        if (x > Point.minX(start, end).getX() && x < Point.maxX(start, end).getX()
                && y > Point.minY(start, end).getY() && y < Point.maxY(start, end).getY())
        {
            return getDistance(point.getX(), point.getY(), x, y);
        } else {
            return Double.min(point.getDistance(start), point.getDistance(end));
        }
    }

    public static double getDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public static double getDistance(Point a, Point b) {
        return getDistance(a.getX(), a.getY(), b.getX(), b.getY());
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

    public static Set<Point> getCommonEdge(Triangle triangle1, Triangle triangle2) {
        Sets.SetView<Point> commonEdge = Sets.intersection(
                Sets.newHashSet(triangle1.getPoints()),
                Sets.newHashSet(triangle2.getPoints())
        );

        if (commonEdge.size() != 2) {
            throw new IllegalStateException("Common edge is only one");
        }

        return commonEdge;
    }

    public static boolean doIntersect(Point p1, Point q1, Point p2, Point q2) {
        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        if (o1 != o2 && o3 != o4) {
            return true;
        }

        if (o1 == 0 && onSegment(p1, p2, q1)) {
            return true;
        }

        if (o2 == 0 && onSegment(p1, q2, q1)) {
            return true;
        }

        if (o3 == 0 && onSegment(p2, p1, q2)) {
            return true;
        }

        if (o4 == 0 && onSegment(p2, q1, q2)) {
            return true;
        }

        return false;
    }

    public static boolean onSegment(Point p, Point q, Point r)
    {
        return q.getX() <= Math.max(p.getX(), r.getX()) && q.getX() >= Math.min(p.getX(), r.getX()) &&
                q.getY() <= Math.max(p.getY(), r.getY()) && q.getY() >= Math.min(p.getY(), r.getY());

    }

    private static int orientation(Point p, Point q, Point r)
    {
        double val = (q.getY() - p.getY()) * (r.getX() - q.getX()) -
                (q.getX() - p.getX()) * (r.getY() - q.getY());

        if (val == 0) return 0;  // colinear

        return (val > 0)? 1 : 2; // clock or counterclock wise
    }
}
