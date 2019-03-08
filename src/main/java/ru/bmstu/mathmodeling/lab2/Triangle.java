package ru.bmstu.mathmodeling.lab2;

import java.util.*;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

import javax.annotation.Nullable;

import static ru.bmstu.common.Drawer.BLACK;
import static ru.bmstu.mathmodeling.lab2.Utils.*;

public class Triangle {
    private Point[] points;
    private double[] color;
    private Circle circumCircle;

    public Triangle(Point a, Point b, Point c) {
        points = new Point[]{a, b, c};
        color = BLACK;

        if (a.equals(b) || a.equals(c) || b.equals(c)) {
            throw new IllegalArgumentException("Wrong points");
        }
    }

    public Point getFirst() {
        return points[0];
    }

    public Point getSecond() {
        return points[1];
    }

    public Point getThird() {
        return points[2];
    }

    public Point[] getPoints() {
        return points;
    }

    public Set<Triangle> getNeighbours() {
        Set<Triangle> adjacentTrs1 = points[0].getTriangles();
        Set<Triangle> adjacentTrs2 = points[1].getTriangles();
        Set<Triangle> adjacentTrs3 = points[2].getTriangles();

        Sets.SetView<Triangle> neighbours1 = Sets.intersection(adjacentTrs1, adjacentTrs2);
        Sets.SetView<Triangle> neighbours2 = Sets.intersection(adjacentTrs1, adjacentTrs3);
        Sets.SetView<Triangle> neighbours3 = Sets.intersection(adjacentTrs2, adjacentTrs3);

        Sets.SetView<Triangle> union = Sets.union(neighbours1, neighbours2);

        Set<Triangle> result = Sets.newHashSet(Sets.union(union, neighbours3));
        result.remove(this);

        return result;
    }

    public Set<Point> getOppositeEdge(Point p) {
        if (p.equals(points[0])) {
            return Sets.newHashSet(points[1], points[2]);
        } else if (p.equals(points[1])) {
            return Sets.newHashSet(points[0], points[2]);
        } else if (p.equals(points[2])) {
            return Sets.newHashSet(points[0], points[1]);
        } else {
            throw new IllegalArgumentException(p + " is not contained in this triangle");
        }
    }

    @Nullable
    public Triangle getNeighbour(Set<Point> edge) {
        Point a = Iterables.get(edge, 0);
        Point b = Iterables.get(edge, 1);
        if (!contains(a) || !contains(b)) {
            throw new IllegalArgumentException("Wrong edge");
        }

        Set<Triangle> adjacentTrs1 = a.getTriangles();
        Set<Triangle> adjacentTrs2 = b.getTriangles();

        Set<Triangle> neighbours = new HashSet<>(Sets.intersection(adjacentTrs1, adjacentTrs2));
        neighbours.remove(this);
        if (neighbours.size() == 2) {
            System.out.println();
        }

        if (neighbours.isEmpty()) {
            return null;
        } else {
            return Iterables.getOnlyElement(neighbours);
        }
    }

    //TODO можно добавить еще случай "на стороне треугольника" (одно из чисел будет 0)
    public boolean isPointInside(Point point) {
        double a = (points[0].getX() - point.getX()) * (points[1].getY() - points[0].getY())
                - (points[1].getX() - points[0].getX()) * (points[0].getY() - point.getY());

        double b = (points[1].getX() - point.getX()) * (points[2].getY() - points[1].getY())
                - (points[2].getX() - points[1].getX()) * (points[1].getY() - point.getY());

        double c = (points[2].getX() - point.getX()) * (points[0].getY() - points[2].getY())
                - (points[0].getX() - points[2].getX()) * (points[2].getY() - point.getY());

        if (a >= 0 && b >= 0 && c >= 0 || a < 0 && b < 0 && c < 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean contains(Point point) {
        return points[0].equals(point) || points[1].equals(point) || points[2].equals(point);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        Triangle other = (Triangle) obj;
        return other.contains(points[0]) && other.contains(points[1]) && other.contains(points[2]);
    }

    @Override
    public int hashCode() {
        return points[0].hashCode() + points[1].hashCode() + points[2].hashCode();
    }

    public Set<Point> getClosestEdge(Point point, List<Triangle> triangles) {
        Set<Point> closest = ImmutableSet.of();
        double min = Double.MAX_VALUE;

        double current = getDistanceToSegment(point, points[0], points[1]);
        if (!doIntersect(point, points[0], triangles) && !doIntersect(point, points[1], triangles)) {
            if (Double.compare(current, min) == 0) {
                double minSum = 0;
                for (Point p : closest) {
                    minSum += getDistance(point, p);
                }

                double curSum = getDistance(point, points[0]) + getDistance(point, points[1]);
                if (curSum < minSum) {
                    min = current;
                    closest = ImmutableSet.of(points[0], points[1]);
                }

            } else if (current < min) {
                min = current;
                closest = ImmutableSet.of(points[0], points[1]);
            }
        }

        current = getDistanceToSegment(point, points[0], points[2]);
        if (!doIntersect(point, points[0], triangles) && !doIntersect(point, points[2], triangles)) {
            if (Double.compare(current, min) == 0) {
                double minSum = 0;
                for (Point p : closest) {
                    minSum += getDistance(point, p);
                }

                double curSum = getDistance(point, points[0]) + getDistance(point, points[2]);
                if (curSum < minSum) {
                    min = current;
                    closest = ImmutableSet.of(points[0], points[2]);
                }

            } else if (current < min) {
                min = current;
                closest = ImmutableSet.of(points[0], points[2]);
            }
        }

        current = getDistanceToSegment(point, points[1], points[2]);
        if (!doIntersect(point, points[1], triangles) && !doIntersect(point, points[2], triangles)) {
            if (Double.compare(current, min) == 0) {
                double minSum = 0;
                for (Point p : closest) {
                    minSum += getDistance(point, p);
                }

                double curSum = getDistance(point, points[1]) + getDistance(point, points[2]);
                if (curSum < minSum) {
                    closest = ImmutableSet.of(points[1], points[2]);
                }

            } else if (current < min) {
                closest = ImmutableSet.of(points[1], points[2]);
            }
        }

        return closest;
    }

    public Point getLastPoint(Collection<Point> edge) {
        if (edge.size() != 2) {
            throw new IllegalArgumentException("Edge must contain 2 points");
        }

        if (!edge.contains(points[0])) {
            return points[0];
        } else if (!edge.contains(points[1])) {
            return points[1];
        } else {
            return points[2];
        }
    }

    public void setCircumCircle(Circle circumCircle) {
        this.circumCircle = circumCircle;
    }

    public Circle getCircumCircle() {
        return circumCircle;
    }

    public void setColor(double[] color) {
        this.color = color;
    }

    public double[] getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "points=" + Arrays.toString(points) +
                '}';
    }
}
