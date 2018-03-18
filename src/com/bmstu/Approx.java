package com.bmstu;

public class Approx {
    private static final int N = 10;
    private static final int A = 0;
    private static final int B = 1;

    public static void main(String[] args) {
        double[] x = new double[N];
        double[] y = new double[N];
        double[] lnY = new double[N];

        for (int i = 0; i < N; i++) {
            x[i] = (double) i / N;
            y[i] = Math.exp(x[i]);
            lnY[i] = Math.log(y[i]);
            //System.out.println(x[i]);
        }

        double A = 0;
        double B = 0;
        double C = N;
        double D = 0;
        double F = 0;
        for (int i = 0; i < N; i++) {
            A += x[i] * x[i];
            B += x[i];
            D += x[i] * lnY[i];
            F += lnY[i];
        }

        double[] abAST = findAB(A, B, C, D, F);
        System.out.println(abAST[0] + " " + abAST[1]);

        double b = abAST[0];
        double a = Math.exp(abAST[1]);

        for (int i = 0; i <= 2 * N; i++) {
            double p = (double) i / (2 * N);
            double e = Math.exp(p);
            double error = Math.abs(e - a * Math.exp(b * p));
            System.out.println("y(" + p + ") = " + (a * Math.exp(b * p)) + " e^x = " + Math.exp(p) + " error = " + error);
        }
    }

    private static double[] findAB (double A, double B, double C, double D, double F) {
        double[] res = new double[2];

        res[0] = (D / B - F / C) / (A / B - B / C);
        res[1] = F / C - (B / C) * res[0];

        return res;
    }
}
