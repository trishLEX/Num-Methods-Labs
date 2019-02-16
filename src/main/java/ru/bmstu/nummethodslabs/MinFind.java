package ru.bmstu.nummethodslabs;

public class MinFind {
    private static double E = 0.01;
    private static double A = -3;
    private static double B = 0;

    private static double f(double x) {
        return x * x + 2 * x + 1;
    }

    public static void main(String[] args) {
        double b = bisection(A, B, 0);
        System.out.println("BISECTION: " + b + " error: " + Math.abs(b + 1.0));
        double g = golden(A, B, 0);
        System.out.println("GOLDEN: " + g + " error: " + Math.abs(g + 1.0));
    }

    private static double bisection(double a, double b, int n) {
        System.out.println("BISECTION: " + n);
        double x = (a + b) / 2;
        if (Math.abs(a - b) < E)
            return x;
        else {
            if (f(x) < f(x + E))
                return bisection(a, x, ++n);
            else
                return bisection(x, b, ++n);
        }
    }

    private static double golden(double a, double b, int n) {
        System.out.println("GOLDEN: " + n);
        double x = (a + b) / 2;
        if (Math.abs(a - b) < E)
            return x;
        else {
            double alpha = a + 2 / (3 + Math.sqrt(5)) * (b - a);
            double beta  = a + 2 / (1 + Math.sqrt(5)) * (b - a);
            if (f(alpha) > f(beta))
                return golden(alpha, b, ++n);
            else
                return golden(a, beta, ++n);
        }
    }
}
