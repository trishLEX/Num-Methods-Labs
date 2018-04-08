package ru.bmstu.NumMethodsLabs;

public class Integrate {
    private static final int N = 2;
    private static final int A = 0;
    private static final int B = 1;
    private static final double E = 0.001;

    public static void main(String[] args) {
        double ideal = Math.E - 1;

        double IbyCentralRects  = centralRects(N);
        double IbyCentralRects2 = centralRects(N * 2);
        double R = Math.abs(IbyCentralRects2 - IbyCentralRects) / 3;
        int n;
        for (n = 4; R > E; n *= 2) {
            IbyCentralRects  = centralRects(n);
            IbyCentralRects2 = centralRects(n * 2);
            R = Math.abs(IbyCentralRects2 - IbyCentralRects) / 3;
        }

        System.out.println("By central rects I* = " + IbyCentralRects2 + " error = " + Math.abs(ideal - IbyCentralRects2)
                + " N = " + n + " I* + R = " + (IbyCentralRects2 + R) + " errorR = " + Math.abs(IbyCentralRects2 + R - ideal));

        double IbyTrapeze  = trapeze(N);
        double IbyTrapeze2 = trapeze(N * 2);
        R = Math.abs(IbyTrapeze2 - IbyTrapeze) / 3;
        for (n = 4; R > E; n *= 2) {
            IbyTrapeze  = trapeze(n);
            IbyTrapeze2 = trapeze(n * 2);
            R = Math.abs(IbyTrapeze2 - IbyTrapeze) / 3;
        }

        System.out.println("By trapeze I* = " + IbyTrapeze2 + " error = " + Math.abs(ideal - IbyTrapeze2)
                + " N = " + n + " I* + R = " + (IbyTrapeze2 + R) + " errorR = " + Math.abs(IbyTrapeze + R - ideal));

        System.out.println("Ideal I = " + ideal);
    }

    private static double centralRects(int N) {
        double[] x = new double[N + 1];
        double h = (double) (B - A) / N;

        for (int i = 0; i <= N; i++)
            x[i] = (double) i / N;

        double I = 0;

        for (int i = 1; i <= N; i++) {
            I += h * Math.exp(x[i - 1] + (x[i] - x[i - 1]) / 2);
        }

        return I;
    }

    private static double trapeze(int N) {
        double[] x = new double[N + 1];
        double h = (double) (B - A) / N;

        for (int i = 0; i <= N; i++)
            x[i] = (double) i / N;

        double I = h * (Math.exp(A) + Math.exp(B)) / 2;

        for (int i = 1; i < N; i++) {
            I += Math.exp(x[i]) * h;
        }

        return I;
    }
}
