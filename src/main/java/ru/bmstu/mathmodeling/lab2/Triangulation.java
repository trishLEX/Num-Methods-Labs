package ru.bmstu.mathmodeling.lab2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.bmstu.mathmodeling.lab2.Utils.*;

public class Triangulation {
    private List<Circle> circles;
    private List<Triangle> triangles;

    public Triangulation() {
        circles = new ArrayList<>();
        triangles = new ArrayList<>();
    }

    public void triangulate(List<Point> points) {
        triangles.add(new Triangle(points.get(0), points.get(1), points.get(2)));

        if (points.size() > 3) {
            for (int i = 3; i < points.size(); i++) {
                Point point = points.get(i);
                Triangle closest = Utils.getClosestTriangle(point, triangles);
                if (closest.isPointInside(point)) {
                    Triangle newTriangle1 = new Triangle(point, closest.getFirst(), closest.getSecond());
                    Triangle newTriangle2 = new Triangle(point, closest.getFirst(), closest.getThird());
                    Triangle newTriangle3 = new Triangle(point, closest.getSecond(), closest.getThird());

                    triangles.add(newTriangle1);
                    triangles.add(newTriangle2);
                    triangles.add(newTriangle3);

                    double[] center1 = getCircumcenter(point, closest.getFirst(), closest.getSecond());
                    double radius1 = getDistance(point.getX(), point.getY(), center1[0], center1[1]);

                    double[] center2 = getCircumcenter(point, closest.getFirst(), closest.getThird());
                    double radius2 = getDistance(point.getX(), point.getY(), center2[0], center1[1]);

                    double[] center3 = getCircumcenter(point, closest.getSecond(), closest.getThird());
                    double radius3 = getDistance(point.getX(), point.getY(), center3[0], center3[1]);

                    circles.addAll(Arrays.asList(
                            new Circle(center1, radius1),
                            new Circle(center2, radius2),
                            new Circle(center3, radius3))
                    );
                } else {
                    tryTriangulate(point, closest);
                }
            }
        }
    }

    //TODO если не получилось вставить в триангуляцию в один треугольник, надо пробоавть в другие
    private void tryTriangulate(Point point, Triangle triangle) {
        Point a = triangle.getFirst();
        Point b = triangle.getSecond();
        Point c = triangle.getThird();

        double[] center1 = getCircumcenter(point, a, b);
        double radius1 = Utils.getDistance(point.getX(), point.getY(), center1[0], center1[1]);
        if (radius1 <= Utils.getDistance(c.getX(), c.getY(), center1[0], center1[1])) {
            Circle circle = new Circle(center1, radius1);
            if (isCircleNotContainsPoints(circle)) {
                triangles.add(new Triangle(point, a, b));
                circles.add(circle);
            }
        }

        double[] center2 = getCircumcenter(point, a, c);
        double radius2 = Utils.getDistance(point.getX(), point.getY(), center2[0], center1[1]);
        if (radius2 <= Utils.getDistance(b.getX(), b.getY(), center2[0], center2[1])) {
            Circle circle = new Circle(center2, radius2);
            if (isCircleNotContainsPoints(circle)) {
                triangles.add(new Triangle(point, a, c));
                circles.add(circle);
            }
        }

        double[] center3 = getCircumcenter(point, b, c);
        double radius3 = Utils.getDistance(point.getX(), point.getY(), center3[0], center3[1]);
        if (radius3 <= Utils.getDistance(a.getX(), a.getY(), center3[0], center3[1])) {
            Circle circle = new Circle(center3, radius3);
            if (isCircleNotContainsPoints(circle)) {
                triangles.add(new Triangle(point, b, c));
                circles.add(new Circle(center3, radius3));
            }
        }
    }

    private boolean isCircleNotContainsPoints(Circle circle) {
        for (Triangle triangle : triangles) {
            if (circle.getRadius() > Utils.getDistance(circle.getCenter()[0], circle.getCenter()[1], triangle.getFirst().getX(), triangle.getFirst().getY())) {
                return false;
            }

            if (circle.getRadius() > Utils.getDistance(circle.getCenter()[0], circle.getCenter()[1], triangle.getSecond().getX(), triangle.getSecond().getY())) {
                return false;
            }

            if (circle.getRadius() > Utils.getDistance(circle.getCenter()[0], circle.getCenter()[1], triangle.getThird().getX(), triangle.getThird().getY())) {
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
