package ru.bmstu.NumMethodsLabs;

public class Integrate {
    private static final int N = 10;
    private static final int A = 0;
    private static final int B = 1;

    public static void main(String[] args) {
        double[] x = new double[N + 1];
        double[] y = new double[N + 1];
        double h = (double) (B - A) / N;

        for (int i = 0; i <= N; i++) {
            x[i] = (double) i / N;
            y[i] = Math.exp(x[i]);
        }

        double IbyCentralRects = centralRects(x, h);
        double IbyTrapeze = trapeze(x, h);
        double ideal = Math.E - 1;

        System.out.println("By central rects I* = " + IbyCentralRects + " error = " + Math.abs(ideal - IbyCentralRects));
        System.out.println("By trapeze I* = " + IbyTrapeze + " error = " + Math.abs(ideal - IbyTrapeze));
        System.out.println("Ideal I = " + ideal);
    }

    private static double centralRects(double[] x, double h) {
        double I = 0;

        for (int i = 1; i <= N; i++) {
            I += h * Math.exp(x[i - 1] + (x[i] - x[i - 1]) / 2);
        }

        return I;
    }

    private static double trapeze(double[] x, double h) {
        double I = h * (Math.exp(A) + Math.exp(B)) / 2;

        for (int i = 1; i < N; i++) {
            I += Math.exp(x[i]) * h;
        }

        return I;
    }
}
