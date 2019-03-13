package ru.bmstu.mathmodeling.lab2;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static ru.bmstu.common.Drawer.*;
import static ru.bmstu.mathmodeling.lab2.Main.MAKE_STEPS;
import static ru.bmstu.mathmodeling.lab2.Utils.*;

@ParametersAreNonnullByDefault
public class Triangulation {
    private Set<Circle> circles;
    private Set<Triangle> triangles;
    private List<Point> points;

    public Triangulation(List<Point> points) {
        this.points = points;
        this.circles = Sets.newConcurrentHashSet();
        this.triangles = Sets.newConcurrentHashSet();
    }

    private synchronized void toWait() {
        if (MAKE_STEPS) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    public void triangulate() {
        System.out.println(points);

        addTriangleWithCircle(points.subList(0, 3));

        if (points.size() > 3) {
            for (int i = 3; i < points.size(); i++) {
                Point point = points.get(i);
                Triangle triangle = Utils.getClosestTriangle(point, triangles);
                switch (triangle.isPointInside(point)) {
                    case INSIDE:
                        toWait();
                        removeTriangle(triangle);

                        Triangle first = addTriangle(point, triangle.getFirst(), triangle.getSecond());
                        Triangle second = addTriangle(point, triangle.getFirst(), triangle.getThird());
                        Triangle third = addTriangle(point, triangle.getSecond(), triangle.getThird());

                        checkInnerTriangles(point, first);
                        checkInnerTriangles(point, second);
                        checkInnerTriangles(point, third);

                        break;
                    case OUTSIDE:
                        Set<Point> closestEdge = triangle.getClosestEdge(point, triangles);

                        Point edgePoint1 = Iterables.get(closestEdge, 0);
                        Point edgePoint2 = Iterables.get(closestEdge, 1);

                        Triangle newTriangle = tryTriangulate(point, edgePoint1, edgePoint2);

                        if (newTriangle == null) {
                            toWait();

                            removeTriangle(triangle);

                            Point mid = triangle.getLastPoint(closestEdge);

                            Triangle triangle1 = addTriangle(point, mid, edgePoint1);
                            Triangle triangle2 = addTriangle(point, mid, edgePoint2);

                            flip(triangle1, triangle2);

                            for (Triangle newTr : Sets.newHashSet(point.getTriangles())) {
                                Triangle neighbour = newTr.getNeighbour(newTr.getOppositeEdge(point));
                                if (neighbour != null) {
                                    makeConvex(point, neighbour, newTr);
                                }
                            }
                        } else {
                            makeConvex(point, triangle, newTriangle);
                        }
                        break;
                    case ON_EDGE:
                        Set<Point> edge = triangle.getClosestEdge(point, triangles);
                        Point opposite = triangle.getLastPoint(edge);

                        removeTriangle(triangle);
                        Triangle triangle1 = addTriangle(point, opposite, Iterables.get(edge, 0));
                        Triangle triangle2 = addTriangle(point, opposite, Iterables.get(edge, 1));

                        break;
                }
            }
        }
    }

    private void checkInnerTriangles(Point point, Triangle triangle) {
        if (triangles.contains(triangle) && !triangle.getCircumCircle().doNotContainPoints(triangles)) {
            Triangle neighbour = triangle.getNeighbour(triangle.getOppositeEdge(point));
            if (neighbour != null) {
                rebuildTriangles(triangle, neighbour);
            }
        }
    }

    private void makeConvex(Point point, Triangle triangle, Triangle newTriangle) {
        Set<Point> outsidePoints = new HashSet<>();
        Set<Point> commonEdge = getCommonEdge(triangle, newTriangle);
        Set<Point> quadrilateral = new HashSet<>(commonEdge);
        quadrilateral.add(point);

        for (Point supportPoint : commonEdge) {
            for (Triangle adjacentTr : Sets.newHashSet(supportPoint.getTriangles())) {
                for (Point potential : adjacentTr.getPoints()) {
                    if (potential.getX() == 286) {
                        System.out.println();
                    }

                    if (!quadrilateral.contains(potential)) {
                        Set<Triangle> intersection = Sets.intersection(supportPoint.getTriangles(), potential.getTriangles());
                        if (intersection.size() == 1) {
                            Point p = point;
                            Point q = potential;
                            if (!Sets.intersection(p.getTriangles(), q.getTriangles()).isEmpty()) {
                                continue;
                            }

                            Point a = Iterables.get(commonEdge, 0);
                            Point b = Iterables.get(commonEdge, 1);

                            Point r;

                            if (p.equals(q) || p.equals(a) || q.equals(a) || p.equals(b) || q.equals(b)) {
                                continue;
                            }

                            double ADistance = getDistanceToSegment(a, p, q);
                            double BDistance = getDistanceToSegment(b, p, q);
                            if (ADistance < BDistance) {
                                r = a;
                            } else {
                                r = b;
                            }

                            if (!doIntersect(p, q, triangles) && !doIntersect(p, q, a, b) && (!outsidePoints.contains(p) || !outsidePoints.contains(q))) {
                                outsidePoints.add(p);
                                outsidePoints.add(q);

                                Triangle tr = new Triangle(p, q, r);
                                if (!triangles.contains(tr)) {
                                    toWait();

                                    tr = addTriangle(p, q, r);
                                    Triangle neighbour = tr.getNeighbour(tr.getOppositeEdge(p));

                                    if (neighbour != null) {
                                        rebuildTriangles(tr, neighbour);
                                        makeConvex(p, neighbour, tr);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void removeTriangle(Triangle triangle) {
        for (Point p : triangle.getPoints()) {
            p.removeTriangle(triangle);
        }

        triangles.remove(triangle);
        circles.removeIf(circle -> Arrays.equals(circle.getCenter(), triangle.getCircumCircle().getCenter()));
    }

    private Triangle addTriangle(Point a, Point b, Point c) {
        Triangle triangle = new Triangle(a, b, c);
        a.addTriangle(triangle);
        b.addTriangle(triangle);
        c.addTriangle(triangle);

        Circle circle = Circle.getCircumcircle(triangle).withColor(GRAY);
        triangle.setCircumCircle(circle);

        triangles.add(triangle);
        circles.add(circle);

        return triangle;
    }

    private void flip(Triangle triangle1, Triangle triangle2) {
        Set<Triangle> neighbours1 = triangle1.getNeighbours();
        neighbours1.remove(triangle2);

        for (Triangle neighbour : neighbours1) {
            if (triangles.contains(neighbour)) {
                rebuildTriangles(triangle1, neighbour);
            }
        }

        Set<Triangle> neighbours2 = triangle2.getNeighbours();
        neighbours2.remove(triangle1);

        for (Triangle neighbour : neighbours2) {
            if (triangles.contains(neighbour)) {
                rebuildTriangles(triangle2, neighbour);
            }
        }
    }

    private void rebuildTriangles(Triangle triangle1, Triangle triangle2) {
        if (triangles.contains(triangle1)
                && triangles.contains(triangle2)
                && (!triangle1.getCircumCircle().doNotContainPoints(triangles) || !triangle2.getCircumCircle().doNotContainPoints(triangles)))
        {
            triangle1.setColor(RED);
            triangle2.setColor(RED);
            toWait();

            Set<Point> commonEdge = Utils.getCommonEdge(triangle1, triangle2);

            Point p1 = triangle1.getLastPoint(commonEdge);
            Point p2 = triangle2.getLastPoint(commonEdge);
            Point commonPoint1 = Iterables.get(commonEdge, 0);
            Point commonPoint2 = Iterables.get(commonEdge, 1);
            if (doIntersect(p1, p2, commonPoint1, commonPoint2)) {
                removeTriangle(triangle1);
                removeTriangle(triangle2);

                Triangle newTriangle1 = addTriangle(p1, p2, commonPoint1);
                Triangle newTriangle2 = addTriangle(p1, p2, commonPoint2);

                newTriangle1.setColor(RED);
                newTriangle2.setColor(RED);

                flip(newTriangle1, newTriangle2);

                newTriangle1.setColor(BLACK);
                newTriangle2.setColor(BLACK);
            }

            triangle1.setColor(BLACK);
            triangle2.setColor(BLACK);
        }
    }

    private void addTriangleWithCircle(List<Point> points) {
        if (points.size() != 3) {
            throw new IllegalArgumentException("Wrong size of points");
        }

        addTriangle(points.get(0), points.get(1), points.get(2));
    }

    @Nullable
    private synchronized Triangle tryTriangulate(Point point, Point a, Point b) {
        Triangle pab = new Triangle(point, a, b);
        Circle pabCircle = Circle.getCircumcircle(pab);
        circles.add(pabCircle);
        if (pabCircle.doNotContainPoints(triangles)) {
            toWait();

            triangles.add(pab);
            pabCircle.setColor(GRAY);
            pab.setCircumCircle(pabCircle);
            point.addTriangle(pab);
            a.addTriangle(pab);
            b.addTriangle(pab);
            return pab;
        }

        return null;
    }

    public Set<Circle> getCircles() {
        return circles;
    }

    public Set<Triangle> getTriangles() {
        return triangles;
    }
}
