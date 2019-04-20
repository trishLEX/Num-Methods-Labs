package ru.bmstu.mathmodeling.lab4;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class ClusterizationUtils {
    public static double getEuclideanDistance(Point a, Point b) {
        return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
    }

    public static void normalizeCoordinates(List<Cluster> clusters) {
        double maxX = clusters.stream()
                .map(Cluster::getPoints)
                .flatMap(Collection::stream)
                .map(Point::getX)
                .max(Comparator.comparingDouble(Math::abs))
                .orElseThrow();

        double maxY = clusters.stream()
                .map(Cluster::getPoints)
                .flatMap(Collection::stream)
                .map(Point::getY)
                .max(Comparator.comparingDouble(Math::abs))
                .orElseThrow();

        for (Cluster cluster : clusters) {
            for (Point point : cluster.getPoints()) {
                point.setX(point.getX() / maxX);
                point.setY(point.getY() / maxY);
            }
            Point center = cluster.getCenter();
            center.setX(center.getX() / maxX);
            center.setY(center.getY() / maxY);
        }
    }
}
