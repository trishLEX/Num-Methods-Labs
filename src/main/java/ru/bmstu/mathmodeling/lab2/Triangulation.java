package ru.bmstu.mathmodeling.lab2;

import com.google.common.base.Predicates;
import com.google.common.collect.*;

import java.util.*;
import java.util.stream.Collectors;

import static ru.bmstu.common.Drawer.GRAY;
import static ru.bmstu.common.Drawer.RED;
import static ru.bmstu.mathmodeling.lab2.Utils.*;

public class Triangulation {
    private List<Circle> circles;
    private List<Triangle> triangles;
    private List<Point> points;

    private static final int OPTION = 1;

    public Triangulation(List<Point> points) {
        this.points = points;

        this.circles = new ArrayList<>();
        this.triangles = new ArrayList<>();
    }

    public void triangulate() {
        points.sort(Comparator.comparingLong(Point::getZCode));
        System.out.println(points);

        addTriangleWithCircle(points.subList(0, 3));

        if (points.size() > 3) {
            for (int i = 3; i < points.size(); i++) {
                Point point = points.get(i);
                boolean wasTriangulation = false;
                //for (Triangle triangle : Lists.newArrayList(triangles)) {
                Triangle triangle = Utils.getClosestTriangle(point, triangles);
                    if (triangle.isPointInside(point)) {
                        //TODO delete old one
//
//                        Triangle newTriangle1 = new Triangle(point, triangle.getFirst(), triangle.getSecond());
//                        Triangle newTriangle2 = new Triangle(point, triangle.getFirst(), triangle.getThird());
//                        Triangle newTriangle3 = new Triangle(point, triangle.getSecond(), triangle.getThird());
//
//                        triangles.add(newTriangle1);
//                        triangles.add(newTriangle2);
//                        triangles.add(newTriangle3);
//
//                        Circle circle1 = Circle.getCircumcircle(newTriangle1).withColor(GRAY);
//                        newTriangle1.setCircumCircle(circle1);
//
//                        Circle circle2 = Circle.getCircumcircle(newTriangle2).withColor(GRAY);
//                        newTriangle2.setCircumCircle(circle2);
//
//                        Circle circle3 = Circle.getCircumcircle(newTriangle3).withColor(GRAY);
//                        newTriangle3.setCircumCircle(circle3);
//
//                        circles.addAll(Arrays.asList(
//                                circle1,
//                                circle2,
//                                circle3
//                        ));

                        wasTriangulation = true;
                    } else {
                        Set<Point> closestEdge = triangle.getClosestEdge(point);

                        Point edgePoint1 = Iterables.get(closestEdge, 0);
                        Point edgePoint2 = Iterables.get(closestEdge, 1);

                        boolean canTriangulate = tryTriangulate(point, edgePoint1, edgePoint2);

                        if (!canTriangulate) {
                            triangles.remove(triangle);
                            circles.remove(triangle.getCircumCircle());

                            Sets.SetView<Point> dif =
                                    Sets.difference(Sets.newHashSet(triangle.getPoints()), closestEdge);
                            if (dif.size() != 1) {
                                throw new IllegalStateException("One element expected");
                            }

                            Point mid = Iterables.getOnlyElement(dif);

                            Triangle triangle1 = new Triangle(point, mid, edgePoint1);
                            Circle circle1 = Circle.getCircumcircle(triangle1).withColor(GRAY);
                            triangle1.setCircumCircle(circle1);

                            Triangle triangle2 = new Triangle(point, mid, edgePoint2);
                            Circle circle2 = Circle.getCircumcircle(triangle2).withColor(GRAY);
                            triangle2.setCircumCircle(circle2);

                            triangles.add(triangle1);
                            triangles.add(triangle2);

                            circles.add(circle1);
                            circles.add(circle2);

                            flip(triangle1, triangle2);
                        }
                    }
                //}
            }
        }
    }

    private void flip(Triangle triangle1, Triangle triangle2) {
        Set<Triangle> neighbours1 = triangle1.getNeighbours();
        neighbours1.remove(triangle2);

        for (Triangle neighbour : neighbours1) {
            if (!neighbour.equals(triangle2)) {
                rebuildTriangles(triangle1, neighbour);
            }
        }

        Set<Triangle> neighbours2 = triangle2.getNeighbours();
        neighbours2.remove(triangle1);

        for (Triangle neighbour : neighbours2) {
            if (!neighbour.equals(triangle1)) {
                rebuildTriangles(triangle2, neighbour);
            }
        }
    }

    private void rebuildTriangles(Triangle triangle1, Triangle triangle2) {
        if (!isCircleNotContainsPoints(triangle1.getCircumCircle(), triangle1) || !isCircleNotContainsPoints(triangle2.getCircumCircle(), triangle2)) {
            triangles.remove(triangle1);
            circles.remove(triangle1.getCircumCircle());

            triangles.remove(triangle2);
            circles.remove(triangle2.getCircumCircle());

            Edge commonEdge = Utils.getCommonEdge(triangle1, triangle2);

            Point p1 = triangle1.getLastPoint(commonEdge);
            Point p2 = triangle2.getLastPoint(commonEdge);

            Triangle newTriangle1 = new Triangle(p1, p2, commonEdge.getFirst());
            Circle circle1 = Circle.getCircumcircle(newTriangle1).withColor(GRAY);
            newTriangle1.setCircumCircle(circle1);

            Triangle newTriangle2 = new Triangle(p1, p2, commonEdge.getSecond());
            Circle circle2 = Circle.getCircumcircle(newTriangle2).withColor(GRAY);
            newTriangle2.setCircumCircle(circle2);

            triangles.add(newTriangle1);
            triangles.add(newTriangle2);

            circles.add(circle1);
            circles.add(circle2);

            flip(newTriangle1, newTriangle2);
        }
    }

    private void createNewPointAndTriangulate(List<Point> points, int i, Point point, Triangle triangle, Point circumCenter) {
        Set<Point> closestEdge = triangle.getClosestEdge(point);
        Sets.SetView<Point> dif = Sets.difference(Sets.newHashSet(triangle.getPoints()), Sets.newHashSet(closestEdge));
        if (dif.size() != 1) {
            throw new IllegalStateException("One element expected");
        }
        Point mid = dif.iterator().next();

        Triangle newTriangle1 = new Triangle(circumCenter, mid, Iterables.get(closestEdge, 0));
        Circle circle1 = Circle.getCircumcircle(newTriangle1).withColor(GRAY);
        newTriangle1.setCircumCircle(circle1);

        Triangle newTriangle2 = new Triangle(circumCenter, mid, Iterables.get(closestEdge, 1));
        Circle circle2 = Circle.getCircumcircle(newTriangle2).withColor(GRAY);
        newTriangle2.setCircumCircle(circle2);

        triangles.add(newTriangle1);
        triangles.add(newTriangle2);

        circles.add(circle1);
        circles.add(circle2);

        points.add(i, circumCenter);
    }

    private boolean canDeleteTriangle(Triangle triangle) {
        boolean canRemoveFirst = false;
        boolean canRemoveSecond = false;
        boolean canRemoveThird = false;
        for (Triangle tr : triangles) {
            if (!tr.equals(triangle)) {
                canRemoveFirst = canRemoveFirst || tr.contains(triangle.getFirst());
                canRemoveSecond = canRemoveSecond || tr.contains(triangle.getSecond());
                canRemoveThird = canRemoveThird || tr.contains(triangle.getThird());
            }

            if (canRemoveFirst && canRemoveSecond && canRemoveThird) {
                return true;
            }
        }

        return false;
    }

    private void addTriangleWithCircle(List<Point> points) {
        if (points.size() != 3) {
            throw new IllegalArgumentException("Wrong size of points");
        }

        Triangle triangle = new Triangle(points.get(0), points.get(1), points.get(2));
        triangles.add(triangle);
        Circle circle = Circle.getCircumcircle(triangle).withColor(GRAY);
        triangle.setCircumCircle(circle);
        circles.add(circle);
    }

    private boolean tryTriangulate(Point point, Triangle triangle) {
        Point a = triangle.getFirst();
        Point b = triangle.getSecond();
        Point c = triangle.getThird();

        boolean wasTriangulation = tryTriangulate(point, a, b);

        wasTriangulation = tryTriangulate(point, a, c) || wasTriangulation;

        wasTriangulation = tryTriangulate(point, b, c) || wasTriangulation;

        return wasTriangulation;
    }

    private boolean tryTriangulate(Point point, Point a, Point b) {
        boolean wasTriangulation = false;
        Triangle pab = new Triangle(point, a, b);
        Circle pabCircle = Circle.getCircumcircle(pab);
        circles.add(pabCircle);
        if (isCircleNotContainsPoints(pabCircle, pab)) {
            triangles.add(pab);
            pabCircle.setColor(GRAY);
            pab.setCircumCircle(pabCircle);
            wasTriangulation = true;
        }

        return wasTriangulation;
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
