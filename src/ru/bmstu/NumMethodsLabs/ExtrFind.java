package ru.bmstu.NumMethodsLabs;

public class ExtrFind {
    private static final double E = 0.00001;

    private static double f(double[] x) {
        return x[0] * x[0] + 10 * (x[1] - Math.sin(x[0])) * (x[1] - Math.sin(x[0]));
    }

    private static double derivFx(double[] x) {
        return 2 * x[0] - 20 * (x[1] - Math.sin(x[0])) * Math.cos(x[0]);
    }

    private static double derivFy(double[] x) {
        return 20 * (x[1] - Math.sin(x[0]));
    }

    private static double derivFxx(double[] x) {
        return 2 - 20 * (- Math.cos(x[0]) * Math.cos(x[0]) - Math.sin(x[0]) * (x[1] - Math.sin(x[0])));
    }

    private static double derivFxy(double[] x) {
        return - 20 * Math.cos(x[0]);
    }

    private static double derivFyy() {
        return 20;
    }

    private static double[][] invertMatrix(double[][] A) {
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

    private static double[] mul(double[][] A, double[] b) {
        double[] res = new double[2];
        res[0] = A[0][0] * b[0] + A[0][1] * b[1];
        res[1] = A[1][0] * b[0] + A[1][1] * b[1];
        return res;
    }

    private static double[] sub(double[] a, double[] b) {
        return new double[]{a[0] - b[0], a[1] - b[1]};
    }

    public static void main(String[] args) {
        double[] xPrev = {1, 1};
        double[] g = {derivFx(xPrev), derivFy(xPrev)};
        double[][] G = {{derivFxx(xPrev), derivFxy(xPrev)},
                        {derivFxy(xPrev), derivFyy()}};
        double[] xNext = sub(xPrev, mul(invertMatrix(G), g));

        while (Math.max(Math.abs(xNext[0] - xPrev[0]), Math.abs(xNext[1] - xPrev[0])) > E) {
            xPrev = xNext.clone();
            xNext = sub(xPrev, mul(invertMatrix(G), g));
            G[0][0] = derivFxx(xNext);
            G[1][0] = G[0][1] = derivFxy(xNext);
            G[1][1] = derivFyy();

            g[0] = derivFx(xNext);
            g[1] = derivFy(xNext);
        }

        System.out.println("RESULT: (" + xNext[0] + ", " + xNext[1] + ")");
    }
}
