package ru.bmstu.nummethodslabs.multistart;

import com.google.common.collect.Lists;
import ru.bmstu.nummethodslabs.hookejeeves.Hooke;
import ru.bmstu.nummethodslabs.multidimfind.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Multistart {
    private static final double A = 1.0;
    private static final double B = 1.0;
    private static final double C = 2.0;
    private static final double ALPHA = 0.0;
    private static final double BETA = 8.0;

    private static double shekel(Vector x) {
        double[] phi = {-1.0/3.0, -1.0/3.0, -1.0/3.0};

        assert Arrays.stream(phi).reduce((p1, p2) -> p1 + p2).getAsDouble() == C;

        double[][] xij = {
                {1.0, 1.0, 1.0},
                {1.0, 1.0, 1.0},
                {1.0, 1.0, 1.0}
        };

        double sum = 0;
        double[] vector = x.getData();
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            sum -= A / (phi[i] + B * IntStream
                    .range(0, 3)
                    .boxed()
                    .map(j -> Math.pow(vector[j] - xij[finalI][j], 2))
                    .reduce((p1, p2) -> p1 + p2)
                    .orElseThrow());
        }

        return sum;
    }

    private static List<List<Vector>> nearestNeighbour(List<Vector> points, double delta) {
        List<List<Vector>> clusters = points.stream().map(Lists::newArrayList).collect(Collectors.toList());

        while (clusters.size() > 1) {
            double minNorm = Double.MAX_VALUE;
            List<Vector> points1 = null;
            List<Vector> points2 = null;

            for (List<Vector> cluster1: clusters) {
                for (List<Vector> cluster2: clusters) {
                    if (cluster1 == cluster2) {
                        continue;
                    }

                    double norm = Lists
                            .cartesianProduct(cluster1, cluster2)
                            .stream()
                            .map(pair -> {if (pair.size() != 2) throw new IllegalStateException(); else return pair.get(1).sub(pair.get(0)).norm();})
                            .min(Double::compareTo)
                            .orElseThrow();

                    if (norm < minNorm) {
                        minNorm = norm;
                        points1 = cluster1;
                        points2 = cluster2;
                    }
                }
            }

            if (minNorm > delta) {
                break;
            }

            points1.addAll(points2);
            clusters.remove(points2);
        }

        //System.out.println("clusters: " + clusters.size());
        return clusters;
    }

    private static Vector hooke(Function<Vector, Double> function, Vector x) {
        Hooke hooke = new Hooke();

        double rho;
        double epsilon;
        int nVars = 3;
        double[] endPt   = new double[nVars];

        int iterMax = Hooke.IMAX;
        rho = 0.5;
        epsilon = Hooke.EPSMIN;

        hooke.hooke(
                nVars, x.getData(), endPt, rho, epsilon, iterMax, function
        );

        return new Vector(endPt);
    }

    private static Vector concurrentPoints(Function<Vector, Double> function, int k, int n, double alpha, double beta) {
        List<List<Integer>> temp = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            temp.add(IntStream.range(0, k).boxed().collect(Collectors.toList()));
        }

        //System.out.println(temp);

        double step = (beta - alpha) / k;
        List<Vector> points = new ArrayList<>();
        for (List<Integer> item : Lists.cartesianProduct(temp)) {
            List<Double> point = new ArrayList<>();
            for (int i : item) {
                point.add(alpha + step * i);
            }
            points.add(new Vector(point));
        }

        double delta = 0.1;
        List<List<Vector>> clusters;
        while (true) {
            List<Vector> zs = points.stream().map(vec -> hooke(function, vec)).collect(Collectors.toList());
            clusters = nearestNeighbour(zs, delta);

            if (clusters.size() == zs.size()) {
                delta *= 2;
                continue;
            }

            if (clusters.size() == 1) {
                break;
            }

            points = new ArrayList<>();
            for (List<Vector> cluster : clusters) {
                Vector min = cluster.stream().min(Comparator.comparingDouble(function::apply)).orElseThrow();
                points.add(min);
            }
        }

        Vector c = clusters.get(0)
                .stream()
                .min(Comparator.comparingDouble(vector -> function.apply(hooke(function, vector))))
                .orElseThrow();
        return hooke(function, c);
    }

    public static void main(String[] args) {
        Vector x = concurrentPoints(Multistart::shekel, 5, 3, ALPHA, BETA);
        System.out.println(x + " " + shekel(x));
    }
}
