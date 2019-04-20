package ru.bmstu.mathmodeling.lab4;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Clusterization {
    private int clustersCount;
    private Map<Point, Cluster> pointToCluster;

    public Clusterization(int clustersCount) {
        this.clustersCount = clustersCount;
        this.pointToCluster = new HashMap<>();
    }

    public List<Cluster> clusterize(List<Point> points) {
        List<Cluster> clusters = new ArrayList<>(clustersCount);
        for (int i = 0; i < clustersCount; i++) {
            clusters.add(new Cluster(points.get(i)));
        }

        boolean wasChange = false;
        int countChangeCycles = 0;
        int count = 0;

        while (countChangeCycles < 3) {
            for (Point point : points) {
                List<Double> distances = clusters.stream()
                        .map(cluster -> ClusterizationUtils.getEuclideanDistance(cluster.getCenter(), point))
                        .collect(Collectors.toList());

                int minIndex = IntStream.range(0, clustersCount)
                        .boxed()
                        .min(Comparator.comparingDouble(distances::get))
                        .orElseThrow();

                wasChange = addPointToCluster(point, clusters.get(minIndex)) || wasChange;
            }

            if (!wasChange) {
                countChangeCycles++;
            } else {
                countChangeCycles = 0;
                wasChange = false;
            }
        }

        return clusters;
    }

    private boolean addPointToCluster(Point point, Cluster cluster) {
        cluster.addPoint(point);
        Cluster old = pointToCluster.put(point, cluster);
        if (old != null && old != cluster) {
            old.removePoint(point);
        }
        return cluster != old;
    }
}
