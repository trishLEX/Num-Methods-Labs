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

    private static final int OPTION = 1;

    public Triangulation() {
        circles = new ArrayList<>();
        triangles = new ArrayList<>();
    }

    public void triangulate(List<Point> points) {
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
                        Triangle newTriangle1 = new Triangle(point, triangle.getFirst(), triangle.getSecond());
                        Triangle newTriangle2 = new Triangle(point, triangle.getFirst(), triangle.getThird());
                        Triangle newTriangle3 = new Triangle(point, triangle.getSecond(), triangle.getThird());

                        triangles.add(newTriangle1);
                        triangles.add(newTriangle2);
                        triangles.add(newTriangle3);

                        Circle circle1 = Circle.getCircumcircle(newTriangle1).withColor(GRAY);
                        newTriangle1.setCircumCircle(circle1);

                        Circle circle2 = Circle.getCircumcircle(newTriangle2).withColor(GRAY);
                        newTriangle2.setCircumCircle(circle2);

                        Circle circle3 = Circle.getCircumcircle(newTriangle3).withColor(GRAY);
                        newTriangle3.setCircumCircle(circle3);

                        circles.addAll(Arrays.asList(
                                circle1,
                                circle2,
                                circle3
                        ));

                        wasTriangulation = true;
                    } else {
                        wasTriangulation = tryTriangulate(point, triangle);
                    }
                //}

                if (!wasTriangulation) {
                    switch (OPTION) {
                        case 1:
                            Triangle closest = Utils.getClosestTriangle(point, triangles);
                            triangles.remove(closest);
                            circles.remove(Circle.getCircumcircle(closest));

                            List<Point> restPoints = Arrays.asList(closest.getFirst(), closest.getSecond(), closest.getThird(), point);
                            restPoints.sort(Comparator.comparingDouble(p -> Math.sqrt(Math.pow(p.getX(), 2) + Math.pow(p.getY(), 2))));

                            addTriangleWithCircle(restPoints.subList(0, 3));
                            addTriangleWithCircle(restPoints.subList(1, 4));
                            break;
                        case 2:
                            triangles.remove(triangle);
                            circles.remove(triangle.getCircumCircle());

                            //List<Point> pointsInside = Circle.getCircumcircle(new Triangle(point, closestEdge.get(0), closestEdge.get(1))).filterPointsInside(points);
                            Point circumCenter = new Point(triangle.getCircumCircle().getCenter());

                            if (triangle.isPointInside(circumCenter)) {
                                createNewPointAndTriangulate(points, i, point, triangle, circumCenter);
                            } else {
                                createNewPointAndTriangulate(points, i, circumCenter, triangle, circumCenter);
                            }
                            break;
                        default:
                            //skip
                    }
                }
            }
        }
    }

    private void createNewPointAndTriangulate(List<Point> points, int i, Point point, Triangle triangle, Point circumCenter) {
        List<Point> closestEdge = triangle.getClosestEdge(point);
        Sets.SetView<Point> dif = Sets.difference(Sets.newHashSet(triangle.getPoints()), Sets.newHashSet(closestEdge));
        if (dif.size() != 1) {
            throw new IllegalStateException("One element expected");
        }
        Point mid = dif.iterator().next();

        Triangle newTriangle1 = new Triangle(circumCenter, mid, closestEdge.get(0));
        Circle circle1 = Circle.getCircumcircle(newTriangle1).withColor(GRAY);
        newTriangle1.setCircumCircle(circle1);

        Triangle newTriangle2 = new Triangle(circumCenter, mid, closestEdge.get(1));
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

    //TODO если не получилось вставить в триангуляцию в один треугольник, надо пробоавть в другие
    private boolean tryTriangulate(Point point, Triangle triangle) {
        Point a = triangle.getFirst();
        Point b = triangle.getSecond();
        Point c = triangle.getThird();

        boolean wasTriangulation = tryTriangulate(point, a, b);

        wasTriangulation = tryTriangulate(point, a, c) || wasTriangulation;

        wasTriangulation = tryTriangulate(point, b, c) || wasTriangulation;

        return wasTriangulation;

//        //TODO fix it
//        if (!wasTriangulation) {
//            triangles.remove(triangle);
//            circles.remove(Circle.getCircumcircle(triangle));
//
//            List<Point> points = Arrays.asList(triangle.getFirst(), triangle.getSecond(), triangle.getThird(), point);
//            points.sort(Comparator.comparingDouble(p -> Math.sqrt(Math.pow(p.getX(), 2) + Math.pow(p.getY(), 2))));
//
//            addTriangleWithCircle(points.subList(0, 3));
//            addTriangleWithCircle(points.subList(1, 4));
//        }
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
