package ru.bmstu.NumMethodsLabs;

public class DifEqs {
    private static int N = 10;
    private static int A = 0;
    private static int B = 1;

    public static void main(String[] args) {
        double h = (double) (B - A) / N;

        double[] y = new double[N + 1];
        double[] x = new double[N + 1];
        for (int i = 0; i <= N; i++) {
            x[i] = A + h * i;
        }

        y[0] = Math.exp(A);
        y[N] = Math.exp(B);

        double[] b = new double[N - 1];
        double[] a = new double[N - 2];
        double[] c = new double[N - 2];
        double[] d = new double[N - 1];

        for (int i = 0; i <= N - 2; i++) {
            b[i] = h * h - 2;
        }

        for (int i = 0; i <= N - 3; i++) {
            c[i] = 1 + h / 2;
            a[i] = 1 - h / 2;
        }

        d[0] =     3 * Math.exp(x[1])     * h * h - Math.exp(A) * (1 - h / 2);
        d[N - 2] = 3 * Math.exp(x[N - 1]) * h * h - Math.exp(B) * (1 + h / 2);

        for (int i = 1; i < N - 2; i++) {
            d[i] = 3 * Math.exp(x[i + 1]) * h * h;
        }

        double[] res = solve(a, b, c, d, N - 1);

        for (int i = 0; i < N - 1; i++) {
            y[i + 1] = res[i];
        }

        for (int i = 0; i <= N; i++)
            System.out.println("res = "  + y[i] + " " + Math.exp(x[i]) + " error = " + Math.abs(Math.exp(x[i]) - y[i]));
    }

    private static double[] solve(double[] a, double[] b, double[] c, double[] d, int n) {
        double[] alpha = new double[n];
        double[] beta = new double[n];
        double[] x = new double[n];

        alpha[0] = - c[0] / b[0];
        beta[0] = d[0] / b[0];

        for (int i = 1; i < n - 1; i++) {
            alpha[i] = - c[i] / (a[i - 1] * alpha[i - 1] + b[i]);
            beta[i] = (d[i] - a[i - 1] * beta[i- 1]) / (a[i - 1] * alpha[i - 1] + b[i]);
        }

        x[n - 1] = (d[n - 1] - a[n - 2] * beta[n - 2]) / (a[n - 2] * alpha[n - 2] + b[n - 1]);

        for (int i = n - 2; i >= 0; i--) {
            x[i] = alpha[i] * x[i + 1] + beta[i];
        }

        return x;
    }
}
