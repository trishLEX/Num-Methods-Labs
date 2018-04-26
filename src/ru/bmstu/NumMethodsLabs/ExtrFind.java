package ru.bmstu.NumMethodsLabs;

public class ExtrFind {
    private static final double E = 0.00001;

    private double f(double x, double y) {
        return x * x + 10 * (y - Math.sin(x)) * (y - Math.sin(x));
    }

    private double derivFx(double x, double y) {
        return 2 * x - 20 * (y - Math.sin(x)) * Math.cos(x);
    }

    private double derivFy(double x, double y) {
        return 20 * (y - Math.sin(x));
    }

    private double derivFxx(double x, double y) {
        return 2 - 20 * (- Math.cos(x) * Math.cos(x) - Math.sin(x) * (y - Math.sin(x)));
    }

    private double derivFxy(double x) {
        return - 20 * Math.cos(x);
    }

    private double derivFyy() {
        return 20;
    }

    private double[][] invertMatrix(double[][] A) {
        double[][] res = new double[2][2];
        res[0][0] = A[1][1];
        res[0][1] = - A[0][1];
        res[1][0] = - A[1][0];
        res[1][1] = A[0][0];

        double det = A[0][0] * A[1][1] - A[0][1] * A[1][0];

        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 2; j++)
                res[i][j] /= det;

        return res;
    }

    public static void main(String[] args) {
        double[] x = {0.572, 0.610};
        double[] g = {0.289, 3.171};
        double[][] G = {{10.507, -10.806},
                        {10.806, 20}};
        double[] alpha = {1, 1};
        double[] p = {0.428, -0.390};

        while (Math.max(x[0], x[1]) > E) {

        }
    }
}
