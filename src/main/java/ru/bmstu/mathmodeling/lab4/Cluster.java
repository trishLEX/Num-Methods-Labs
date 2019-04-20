package ru.bmstu.mathmodeling.lab4;

import com.google.common.collect.Sets;

import java.util.Set;

public class Cluster {
    private Set<Point> points;
    private Point center;

    public Cluster(Point point) {
        points = Sets.newHashSet(point);
        center = new Point(point.getX(), point.getY(), -1);
    }

    public Point getCenter() {
        return center;
    }

    public void addPoint(Point p) {
        points.add(p);

        updateCenter();
    }

    private void updateCenter() {
        double x = 0;
        double y = 0;
        for (Point point : points) {
            x += point.getX();
            y += point.getY();
        }
        center.setX(x / points.size());
        center.setY(y / points.size());
    }

    public void removePoint(Point p) {
        points.remove(p);

        updateCenter();
    }

    public Set<Point> getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return points.toString();
    }
}
