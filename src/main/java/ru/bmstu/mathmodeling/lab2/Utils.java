package ru.bmstu.mathmodeling.lab2;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;
import java.util.Set;

@ParametersAreNonnullByDefault
public class Utils {
    private Utils() {
        //utility class
    }

    public static Triangle getClosestTriangle(Point point, Collection<Triangle> triangles) {
        Triangle closest = Iterables.get(triangles, 0);
        double min = getDistanceToTriangle(point, closest, triangles);

        if (triangles.size() > 1) {
            Collection<Triangle> otherTriangles = Sets.newHashSet(triangles);
            otherTriangles.remove(closest);
            for (Triangle triangle : otherTriangles) {
                double distance = getDistanceToTriangle(point, triangle, triangles);
                if (distance < min) {
                    min = distance;
                    closest = triangle;
                } else if (Double.compare(distance, min) == 0) {
                    PointPlace closestPlace = closest.isPointInside(point);
                    PointPlace trianglePlace = triangle.isPointInside(point);
                    if (closestPlace == PointPlace.INSIDE || closestPlace == PointPlace.ON_EDGE) {
                        return closest;
                    } else if (trianglePlace == PointPlace.INSIDE || trianglePlace == PointPlace.ON_EDGE) {
                        return triangle;
                    } else {
                        if (getDistanceToCenterOfMasses(point, triangle) < getDistanceToCenterOfMasses(point, closest)) {
                            min = distance;
                            closest = triangle;
                        }
                    }
                }
            }
        }

        return closest;
    }

    public static double getDistanceToTriangle(Point point, Triangle triangle, Collection<Triangle> triangles) {
        Point[] points = triangle.getPoints();
        double min = Double.MAX_VALUE;

        double current = getDistanceToSegment(point, points[0], points[1]);
        if (current < min && !doIntersect(point, points[0], triangles) && !doIntersect(point, points[1], triangles)) {
            min = current;
        }

        current = getDistanceToSegment(point, points[0], points[2]);
        if (current < min && !doIntersect(point, points[0], triangles) && !doIntersect(point, points[2], triangles)) {
            min = current;
        }

        current = getDistanceToSegment(point, points[1], points[2]);
        if (current < min && !doIntersect(point, points[1], triangles) && !doIntersect(point, points[2], triangles)) {
            min = current;
        }

        return min;
    }

    public static boolean doIntersect(Point a, Point b, Collection<Triangle> triangles) {
        for (Triangle triangle : triangles) {
            Point[] points = triangle.getPoints();
            if (!a.equals(points[0])
                    && !a.equals(points[1])
                    && !b.equals(points[0])
                    && !b.equals(points[1])
                    && doIntersect(a, b, points[0], points[1]))
            {
                return true;
            }

            if (!a.equals(points[0])
                    && !a.equals(points[2])
                    && !b.equals(points[0])
                    && !b.equals(points[2])
                    && doIntersect(a, b, points[0], points[2])) {
                return true;
            }

            if (!a.equals(points[2])
                    && !a.equals(points[1])
                    && !b.equals(points[2])
                    && !b.equals(points[1])
                    && doIntersect(a, b, points[1], points[2])) {
                return true;
            }
        }

        return false;
    }

    public static double getDistanceToCenterOfMasses(Point point, Triangle triangle) {
        Point[] points = triangle.getPoints();
        double x = 0;
        double y = 0;
        for (Point point1 : points) {
            x += point1.getX() / 3;
            y += point1.getY() / 3;
        }

        return getDistance(point.getX(), point.getY(), x, y);
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

    public static double getDistanceToSegment(Point point, Set<Point> segment) {
        if (segment.size() != 2) {
            throw new IllegalArgumentException("Segment must contain 2 points");
        }

        return getDistanceToSegment(point, Iterables.get(segment, 0), Iterables.get(segment, 1));
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

        if (val == 0) return 0;

        return (val > 0)? 1 : 2;
    }
}
