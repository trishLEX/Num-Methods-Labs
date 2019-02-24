package ru.bmstu.mathmodeling.lab2;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.*;
import java.util.stream.Collectors;

import static ru.bmstu.common.Drawer.GRAY;
import static ru.bmstu.common.Drawer.RED;
import static ru.bmstu.mathmodeling.lab2.Utils.*;

public class Triangulation {
    private List<Circle> circles;
    private List<Triangle> triangles;

    public Triangulation() {
        circles = new ArrayList<>();
        triangles = new ArrayList<>();
    }

    public void triangulate(List<Point> points) {
        points.sort(Comparator.comparingLong(Point::getZCode));
        System.out.println(points);

        addTriangleWithCircle(points);

        if (points.size() > 3) {
            for (int i = 3; i < points.size(); i++) {
                Point point = points.get(i);
                boolean wasTriangulation = false;
                for (Triangle triangle : Lists.newArrayList(triangles)) {
                    if (triangle.isPointInside(point)) {
                        Triangle newTriangle1 = new Triangle(point, triangle.getFirst(), triangle.getSecond());
                        Triangle newTriangle2 = new Triangle(point, triangle.getFirst(), triangle.getThird());
                        Triangle newTriangle3 = new Triangle(point, triangle.getSecond(), triangle.getThird());

                        triangles.add(newTriangle1);
                        triangles.add(newTriangle2);
                        triangles.add(newTriangle3);

                        circles.addAll(Arrays.asList(
                                Circle.getCircumcircle(newTriangle1).withColor(GRAY),
                                Circle.getCircumcircle(newTriangle2).withColor(GRAY),
                                Circle.getCircumcircle(newTriangle3).withColor(GRAY)
                        ));

                        wasTriangulation = true;
                    } else {
                        wasTriangulation = tryTriangulate(point, triangle);
                    }
                }

                if (!wasTriangulation) {
                    Triangle closest = Utils.getClosestTriangle(point, triangles);
                    triangles.remove(closest);
                    circles.remove(Circle.getCircumcircle(closest));

                    List<Point> restPoints = Arrays.asList(closest.getFirst(), closest.getSecond(), closest.getThird(), point);
                    restPoints.sort(Comparator.comparingDouble(p -> Math.sqrt(Math.pow(p.getX(), 2) + Math.pow(p.getY(), 2))));

                    addTriangleWithCircle(restPoints.subList(0, 3));
                    addTriangleWithCircle(restPoints.subList(1, 4));
                }
            }
        }
    }

    private void addTriangleWithCircle(List<Point> points) {
        Triangle triangle = new Triangle(points.get(0), points.get(1), points.get(2));
        triangles.add(triangle);
        circles.add(Circle.getCircumcircle(triangle));
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
