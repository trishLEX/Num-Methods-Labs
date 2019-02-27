package ru.bmstu.nummethodslabs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MinFind {
    private static final double E = 0.01;
    private static final double A = -3;
    private static final double B = 0;
    private static final double X_0 = -13;
    private static final double DELTA = 0.01;

    private static double f(double x) {
        return 5 * Math.pow(x, 6) - 36 * Math.pow(x, 5) - 165.0 / 2.0 *
                Math.pow(x, 4) - 60 * Math.pow(x, 3) + 36;
    }

    public static void main(String[] args) {
        double[] segment = svenn(X_0, DELTA);
        System.out.println("SVENN: " + Arrays.toString(segment));
        double b = bisection(segment[0], segment[1], 0);
        System.out.println("BISECTION: " + b + " error: " + Math.abs(b -
                7.56001) + " min: " + f(b));
        double g = golden(segment[0], segment[1], 0);
        System.out.println("GOLDEN: " + g + " error: " + Math.abs(g -
                7.56001) + " min: " + f(g));
        double f = fibonacci(segment[0], segment[1]);
        System.out.println("FIBONACCI: " + f + " error: " + Math.abs(f -
                7.56001) + " min: " + f(f));
    }

    private static double[] svenn(double xPrev, double delta) {
        double xCur;
        if (f(xPrev + delta) < f(xPrev)) {
            xCur = xPrev + delta;
        } else if (f(xPrev - delta) < f(xPrev)) {
            delta = -delta;
            xCur = xPrev + delta;
        } else {
            return new double[]{xPrev - delta, xPrev + delta};
        }

        int k = 0;
        int kMax = 100;
        double xNext;
        do {
            k++;
            delta = 2 * delta;
            xNext = xCur + delta;
            if (k > kMax) {
                throw new IllegalStateException("Too many iterations");
            }

            double temp = xCur;
            xCur = xNext;
            xPrev = temp;
        } while ((f(xPrev) > f(xCur)));

        if (xPrev < xNext) {
            return new double[]{xPrev, xNext};
        } else {
            return new double[]{xNext, xPrev};
        }
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

    private static double fibonacci(double a, double b) {
        List<Double> numbers = new ArrayList<>();
        numbers.add(0.0);
        numbers.add(1.0);

        while (numbers.get(numbers.size() - 1) < Math.abs(B - A) / E) {
            numbers.add(numbers.get(numbers.size() - 1) + numbers.get(numbers.size() - 2));
        }

        int count = numbers.size();
        int k = 0;

        double left = a + numbers.get(count - 4) / numbers.get(count - 2) * Math.abs(b - a);
        double right = a + numbers.get(count - 3) / numbers.get(count - 2) * Math.abs(b - a);

        do {
            if (f(left) <= f(right)) {
                b = right;
                right = left;
                left = a + numbers.get(count - k - 4) / numbers.get(count - k - 2) * Math.abs(b - a);
            } else {
                a = left;
                left = right;
                right = a + numbers.get(count - k - 3) / numbers.get(count - k - 2) * Math.abs(b - a);
            }

            k++;
        } while (k < count - 3);

        right = left + E;

        if (f(left) <= f(right)) {
            b = right;
        } else {
            a = left;
        }

        return (a + b) / 2.0;
    }
}
