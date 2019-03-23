package ru.bmstu.nummethodslabs.simplex;

import java.io.IOException;

public class Testfun {
    public static void main(String[] args) throws IOException {
        double[] start = {0, 0, 0, 0};
        int dim = 4;
        double eps = 0.001;
        double scale = 0.01;

        start = NMSimplex.NMSimplex(start, dim, eps, scale, Testfun::rosenbrock);

        for (int i = 0; i < dim; i++) {
            System.out.format("%f\n", start[i]);
        }
    }

    private static double rosenbrock(double[] x) {
        double sum = 80;
        double a = 30;
        double b = 2;
        for (int i = 0; i < 3; i++) {
            sum += a * Math.pow(x[i] * x[i] - x[i+1], 2) + b * Math.pow(x[i] - 1, 2);
        }

        return sum;
    }
}