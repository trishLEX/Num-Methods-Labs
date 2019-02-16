package ru.bmstu.nummethodslabs;

public class Main {
    private static final int N = 3;

    public static void main(String[] args) {
        double[] b = {4, 4, 4};
        double[] c = {1, 1};
        double[] a = {1, 1};
        double[] d = {5, 6, 5};


        double[] x = solve(a, b, c, d);
        for (int i = 0; i < N; i++)
            System.out.println(x[i]);
    }

    private static double[] solve(double[] a, double[] b, double[] c, double[] d) {
        double[] alpha = new double[N];
        double[] beta = new double[N];
        double[] x = new double[N];

        alpha[0] = - c[0] / b[0];
        beta[0] = d[0] / b[0];

        for (int i = 1; i < N - 1; i++) {
            alpha[i] = - c[i] / (a[i - 1] * alpha[i - 1] + b[i]);
            beta[i] = (d[i] - a[i - 1] * beta[i- 1]) / (a[i - 1] * alpha[i - 1] + b[i]);
        }

        x[N - 1] = (d[N - 1] - a[N - 2] * beta[N - 2]) / (a[N - 2] * alpha[N - 2] + b[N - 1]);

        for (int i = N - 2; i >= 0; i--) {
            x[i] = alpha[i] * x[i + 1] + beta[i];
        }

        return x;
    }
}
