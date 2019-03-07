package ru.bmstu.mathmodeling.lab2;

import com.google.common.collect.*;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import static ru.bmstu.common.Drawer.GRAY;
import static ru.bmstu.common.Drawer.RED;
import static ru.bmstu.mathmodeling.lab2.Utils.*;

public class Triangulation {
    private List<Circle> circles;
    private List<Triangle> triangles;
    private List<Point> points;

    public Triangulation(List<Point> points) {
        this.points = points;
        this.circles = new CopyOnWriteArrayList<>();
        this.triangles = new CopyOnWriteArrayList<>();
    }

    public synchronized void triangulate() {
        points.sort(Comparator.comparingLong(Point::getZCode));
        System.out.println(points);

        addTriangleWithCircle(points.subList(0, 3));

        if (points.size() > 3) {
            for (int i = 3; i < points.size(); i++) {
                Point point = points.get(i);
                //for (Triangle triangle : Lists.newArrayList(triangles)) {
                Triangle triangle = Utils.getClosestTriangle(point, triangles);
                    if (triangle.isPointInside(point)) {
                        removeTriangle(triangle);

                        addTriangle(point, triangle.getFirst(), triangle.getSecond());
                        addTriangle(point, triangle.getFirst(), triangle.getThird());
                        addTriangle(point, triangle.getSecond(), triangle.getThird());
                    } else {
                        Set<Point> closestEdge = triangle.getClosestEdge(point);

                        Point edgePoint1 = Iterables.get(closestEdge, 0);
                        Point edgePoint2 = Iterables.get(closestEdge, 1);

                        Triangle newTriangle = tryTriangulate(point, edgePoint1, edgePoint2);

                        if (newTriangle == null) {
                            try {
                                wait();
                            } catch (InterruptedException e) {
                                throw new IllegalStateException(e);
                            }

                            removeTriangle(triangle);

                            Point mid = triangle.getLastPoint(closestEdge);

                            Triangle triangle1 = addTriangle(point, mid, edgePoint1);
                            Triangle triangle2 = addTriangle(point, mid, edgePoint2);

                            flip(triangle1, triangle2);

                            triangle = triangle1;
                            newTriangle = triangle2;
                        }

                        makeConvex(point, triangle, newTriangle);
                    }
                //}
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
                    if (!quadrilateral.contains(potential)) {
                        Set<Triangle> intersection = Sets.intersection(supportPoint.getTriangles(), potential.getTriangles());
                        if (intersection.size() == 1) {
                            Point p = point;
                            Point q = potential;

                            Point a = Iterables.get(commonEdge, 0);
                            Point b = Iterables.get(commonEdge, 1);

                            Point r;

                            if (p.equals(q) || p.equals(a) || q.equals(a) || p.equals(b) || q.equals(b)) {
                                continue;
                            }

                            Triangle pqa = new Triangle(p, q, a);
                            Triangle pqb = new Triangle(p, q, b);
//                                            if (isCircleNotContainsPoints(Circle.getCircumcircle(pqa), pqa)) {
//                                                r = a;
//                                            } else if (isCircleNotContainsPoints(Circle.getCircumcircle(pqb), pqb)) {
//                                                r = b;
//                                            } else {
//                                                continue;
//                                            }
                            if (Circle.getCircumcircle(pqa).doNotContainPoints(triangles)) {
                                r = a;
                            } else if (Circle.getCircumcircle(pqb).doNotContainPoints(triangles)) {
                                r = b;
                            } else {
                                continue;
                            }

                            if (!doIntersect(p, q, a, b) && (!outsidePoints.contains(p) || !outsidePoints.contains(q))) {
                                outsidePoints.add(p);
                                outsidePoints.add(q);

                                addTriangle(p, q, r);
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
            rebuildTriangles(triangle1, neighbour);
        }

        Set<Triangle> neighbours2 = triangle2.getNeighbours();
        neighbours2.remove(triangle1);

        for (Triangle neighbour : neighbours2) {
            rebuildTriangles(triangle2, neighbour);
        }
    }

    private synchronized void rebuildTriangles(Triangle triangle1, Triangle triangle2) {
        //if (!isCircleNotContainsPoints(triangle1.getCircumCircle(), triangle1) || !isCircleNotContainsPoints(triangle2.getCircumCircle(), triangle2)) {
        if (!triangle1.getCircumCircle().doNotContainPoints(triangles) || !triangle2.getCircumCircle().doNotContainPoints(triangles)) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }

            removeTriangle(triangle1);
            removeTriangle(triangle2);

            Set<Point> commonEdge = Utils.getCommonEdge(triangle1, triangle2);

            Point p1 = triangle1.getLastPoint(commonEdge);
            Point p2 = triangle2.getLastPoint(commonEdge);

            Triangle newTriangle1 = addTriangle(p1, p2, Iterables.get(commonEdge, 0));
            Triangle newTriangle2 = addTriangle(p1, p2, Iterables.get(commonEdge, 1));

            //setWait(true);

            flip(newTriangle1, newTriangle2);
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
            try {
                wait();
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
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

    private boolean isCircleNotContainsPoints(Circle circle, Triangle triangle) {
        for (Circle c : circles) {
            if (!Arrays.equals(c.getColor(), RED) && c.containsSomePointOfTriangle(triangle)){
                return false;
            }
        }

        List<Point> points = triangles.stream()
                .map(tr -> Arrays.asList(tr.getPoints()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        Set<Point> trianglePoints = ImmutableSet.of(triangle.getFirst(), triangle.getSecond(), triangle.getThird());
        for (Point point : points){
            if (!trianglePoints.contains(point) && circle.containsPoint(point)) {
                return false;
            }
        }

        return true;
    }

    public List<Circle> getCircles() {
        return circles;
    }

    public List<Triangle> getTriangles() {
        return triangles;
    }
}
