package ru.bmstu.nummethodslabs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MinFind {
    private static double E = 0.01;
    private static double A = -3;
    private static double B = 0;

    private static Map<Integer, Integer> fibonacci = new HashMap<>();

    private static double f(double x) {
        return x * x + 2 * x + 1;
    }

    public static void main(String[] args) {
        double b = bisection(A, B, 0);
        System.out.println("BISECTION: " + b + " error: " + Math.abs(b + 1.0));
        double g = golden(A, B, 0);
        System.out.println("GOLDEN: " + g + " error: " + Math.abs(g + 1.0));
        double f = fibonacci(A, B);
        System.out.println("FIBONACCI: " + f + " error: " + Math.abs(f + 1.0));
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
