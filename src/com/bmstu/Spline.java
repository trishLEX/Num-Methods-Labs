package com.bmstu;

public class Spline {
    private static final int N = 10;    //N - количество точек => N - 1 - количество интервалов
    private static final double A = 0;
    private static final double B = 1;

    public static void main(String[] args) {
        double[] b = new double[N - 2];
        double[] c = new double[N - 3];
        double[] a = new double[N - 3];

        double[] d = new double[N - 2];

        double h = (B - A) / (N - 1);

        double[] xs = new double[N];
        for (int i = 0; i < N; i++)
            xs[i] = A + i * h;

        for (int i = 0; i < N; i++) {
            System.out.println("x[" + i + "] = " + xs[i]);
        }
        System.out.println();

        double[] y = new double[N];
        for (int i = 0; i < N; i++)
            y[i] = Math.exp(i * h);

        for (int i = 0; i < N; i++) {
            System.out.println("y[" + i + "] = " + y[i]);
        }
        System.out.println();

        for (int i = 1; i < N - 1; i++) {
            //System.out.println("i = " + i);
            d[i - 1] = (3 / h) / h * (y[i + 1] - 2 * y[i] + y[i - 1]);
        }

        //System.out.println();
        for (int i = 0; i < N - 2; i++)
            System.out.println("d[" + i + "] = " + d[i]);

        for (int i = 0; i < N - 2; i++)
            b[i] = 4;

        for (int i = 0; i < N - 3; i++) {
            a[i] = c[i] = 1;
        }

        double[] x = solve(a, b, c, d, N - 2);

        System.out.println();
        for (int i = 0; i < N - 2; i++)
            System.out.println(x[i]);

        double[] cRes = new double[N];
        cRes[0] = cRes[N - 1] = 0;
        for (int i = 1; i < N - 1; i++)
            cRes[i] = x[i - 1];

        System.out.println();
        for (int i = 0; i < N; i++)
            System.out.println("cRes[" + i + "] = " + cRes[i]);

        double[] aRes = new double[N];
        double[] bRes = new double[N];
        double[] dRes = new double[N];

        for (int i = 0; i < N - 1; i++) {
            aRes[i] = y[i];
            bRes[i] = (y[i + 1] - y[i]) / h - (h / 3) * (cRes[i + 1] + 2 * cRes[i]);
            dRes[i] = (cRes[i + 1] - cRes[i]) / (3 * h);
            //bRes[i] = (y[i + 1] - y[i]) / h - cRes[i+1] * h + dRes[i] * h * h;
        }

        System.out.println();
        System.out.println(countAt(0.25, xs, aRes, bRes, cRes, dRes));

        for (double p: xs)
            System.out.println(countAt(p, xs, aRes, bRes, cRes, dRes) + " " + Math.abs(countAt(p, xs, aRes, bRes, cRes, dRes) - Math.exp(p)));

        for (double i = 0; i < 1; i += (double)1 / 20) {
            double e = Math.exp(i);
            double eRes = countAt(i, xs, aRes, bRes, cRes, dRes);
            System.out.println("x = " + i + "| y(x) = " + e + "| S(x) = " + eRes + "| y(x) - S(x) " + Math.abs(e - eRes));
        }
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

    private static double countAt(double x, double[] xs, double[] a, double[] b, double[] c, double[] d) {
        if (x < A)
            x = A;
        else if (x > B)//>=B - h
            x = B;

        int i;
        for (i = 0; i < N - 1; i++)
            if (x >= xs[i] && x <= xs[i + 1])
                break;

        return a[i] + b[i] * (x - xs[i]) + c[i] * (x - xs[i]) * (x - xs[i]) + d[i] * (x - xs[i]) * (x - xs[i]) * (x - xs[i]);
    }
}
