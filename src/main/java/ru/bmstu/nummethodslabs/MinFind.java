package ru.bmstu.nummethodslabs;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

public class MinFind {
    private static final double E = 0.001;
    private static final double X_0 = -13;
    private static final double DELTA = 3;

    private static double f(double x) {
        return 5 * Math.pow(x, 6) - 36 * Math.pow(x, 5) - 165.0 / 2.0 *
                Math.pow(x, 4) - 60 * Math.pow(x, 3) + 36;
    }

    private static double fDeriv(double x) {
        return 30 * Math.pow(x, 5) - 36 * 5 * Math.pow(x, 4) - 165 * 2 * Math.pow(x, 3) - 180 * x * x;
    }

    public static void main(String[] args) {
//        double[] segment = svenn(X_0, DELTA);
//        System.out.println("SVENN: " + Arrays.toString(segment));
//        double b = bisection(segment[0], segment[1], 0);
//        System.out.println("BISECTION: " + b + " error: " + Math.abs(b -
//                7.56001) + " min: " + f(b));
//        double g = golden(segment[0], segment[1], 0);
//        System.out.println("GOLDEN: " + g + " error: " + Math.abs(g -
//                7.56001) + " min: " + f(g));
//        double f = fibonacci(segment[0], segment[1]);
//        System.out.println("FIBONACCI: " + f + " error: " + Math.abs(f -
//                7.56001) + " min: " + f(f));

        Coordinate coordinate = quadraticInterpolation();
        System.out.println(coordinate);
        Coordinate coordinate1 = cubicInterpolation();
        System.out.println(coordinate1);
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

        while (numbers.get(numbers.size() - 1) < Math.abs(b - a) / E) {
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

        System.out.println("FIBONACCI: " + k);
        return (a + b) / 2.0;
    }

    private static Coordinate coordinate(double x) {
        return new Coordinate(x, f(x), fDeriv(x));
    }

    private static Coordinate quadraticInterpolation() {
        List<Coordinate> interpolation = Lists.newArrayList(coordinate(X_0), coordinate(0.0), coordinate(0.0));
        int k = 0;
        double res = 0.0;

        interpolation.set(1, coordinate(interpolation.get(0).x + DELTA));
        interpolation.set(2, interpolation.get(0).y > interpolation.get(1).y ?
                coordinate(interpolation.get(0).x + 2 * DELTA) :
                coordinate(interpolation.get(0).x - DELTA));

        while (true) {
            Coordinate minValueCoord = min(interpolation);
            double numerator = (Math.pow(interpolation.get(1).x, 2) - Math.pow(interpolation.get(2).x, 2)) * interpolation.get(0).y +
                    (Math.pow(interpolation.get(2).x, 2) - Math.pow(interpolation.get(0).x, 2)) * interpolation.get(1).y +
                    (Math.pow(interpolation.get(0).x, 2) - Math.pow(interpolation.get(1).x, 2)) * interpolation.get(2).y;

            double denumerator = (interpolation.get(1).x - interpolation.get(2).x) * interpolation.get(0).y +
                    (interpolation.get(2).x - interpolation.get(0).x) * interpolation.get(1).y +
                    (interpolation.get(0).x - interpolation.get(1).x) * interpolation.get(2).y;

            if (Double.compare(denumerator, 0.0) == 0) {
                interpolation.set(0, coordinate(minValueCoord.x));
                continue;
            }

            Coordinate minFromPolynom = coordinate(numerator / 2.0 / denumerator);
            boolean belongs = interpolation.get(0).x <= minFromPolynom.x && minFromPolynom.x <= interpolation.get(2).x;
            if (!belongs) {
                interpolation.set(1, minFromPolynom);
                interpolation.set(0, coordinate(interpolation.get(1).x - DELTA));
                interpolation.set(2, coordinate(interpolation.get(1).x + DELTA));
                continue;
            }

            boolean yError = Math.abs((minValueCoord.y - minFromPolynom.y) / minFromPolynom.y) < E;
            boolean xError = Math.abs((minValueCoord.x - minFromPolynom.x) / minFromPolynom.y) < E;

            if (xError && yError) {
                res = minFromPolynom.x;
                break;
            }

            k++;
            int minIndex;
            interpolation.add(minFromPolynom);
            interpolation.sort(Coordinate::compareTo);
            if (minValueCoord.y < minFromPolynom.y) {
                minIndex = interpolation.indexOf(minValueCoord);
            } else {
                minIndex = interpolation.indexOf(minFromPolynom);
            }

            switch (minIndex) {
                case 0:
                    interpolation.remove(interpolation.size() - 1);
                    interpolation.remove(interpolation.size() - 1);
                    interpolation.add(0, coordinate(interpolation.get(0).x - DELTA));
                    break;
                case 1:
                    interpolation.remove(interpolation.size() - 1);
                    break;
                case 2:
                    interpolation.remove(0);
                    break;
                case 3:
                    interpolation.remove(0);
                    interpolation.remove(0);
                    interpolation.add(coordinate(interpolation.get(interpolation.size() - 1).x - DELTA));
                    break;
                default:
                    throw new IllegalStateException("BAD CASE");
            }
        }

        return coordinate(res);
    }

    private static Coordinate min(List<Coordinate> coordinates) {
        Coordinate min = coordinates.get(0);

        for (int i = 1; i < coordinates.size(); i++) {
            if (coordinates.get(i).y < min.y) {
                min = coordinates.get(i);
            }
        }

        return min;
    }

    private static Coordinate cubicInterpolation() {
        Coordinate startCoord = coordinate(X_0);
        List<Coordinate> interpolation = Lists.newArrayList(startCoord, startCoord, startCoord);
        int k = 0;
        double res = 0.0;

        if (startCoord.derivValue < 0.0) {
            do {
                interpolation.set(0, interpolation.get(1));
                interpolation.set(1, coordinate(interpolation.get(0).x + (1 << k) * DELTA));
                k++;
            } while (interpolation.get(0).derivValue * interpolation.get(1).derivValue >= 0);
        } else {
            do {
                interpolation.set(0, interpolation.get(1));
                interpolation.set(1, coordinate(interpolation.get(0).x - (1 << k) * DELTA));
                k++;
            } while (interpolation.get(0).derivValue * interpolation.get(1).derivValue >= 0);
        }

        k = 0;
        while (true) {
            double z = 3.0 * (interpolation.get(0).y - interpolation.get(1).y) / (interpolation.get(1).x - interpolation.get(0).x) +
                    interpolation.get(0).derivValue + interpolation.get(1).derivValue;
            double w = Math.sqrt(Math.pow(z, 2) - interpolation.get(0).derivValue * interpolation.get(1).derivValue);
            w = interpolation.get(0).x < interpolation.get(1).x ? w : -w;

            double mu = (interpolation.get(1).derivValue + w - z) /
                    (interpolation.get(1).derivValue - interpolation.get(0).derivValue + 2.0 * w);

            if (mu < 0) {
                interpolation.set(2, interpolation.get(1));
            } else if (mu > 1) {
                interpolation.set(2, interpolation.get(0));
            } else {
                interpolation.set(2, coordinate(interpolation.get(1).x - mu * (interpolation.get(1).x - interpolation.get(0).x)));
            }

            while (interpolation.get(2).y > interpolation.get(0).y) {
                interpolation.set(2, coordinate(interpolation.get(2).x - 0.5 * (interpolation.get(2).x - interpolation.get(0).x)));
            }

            boolean yError = Math.abs(interpolation.get(2).derivValue) < E;
            boolean xError = Math.abs(interpolation.get(2).x - interpolation.get(0).x) / interpolation.get(2).x < E;
            k++;

            if (xError && yError) {
                res = interpolation.get(2).x;
                break;
            }

            if (interpolation.get(2).derivValue * interpolation.get(0).derivValue < 0) {
                interpolation.set(1, interpolation.get(0));
                interpolation.set(0, interpolation.get(2));
            } else if (interpolation.get(2).derivValue * interpolation.get(1).derivValue < 0) {
                interpolation.set(0, interpolation.get(2));
            }
        }

        return coordinate(res);
    }

    private static class Coordinate implements Comparable<Coordinate> {
        double x;
        double y;
        double derivValue;

        public Coordinate(double x, double y, double derivValue) {
            this.x = x;
            this.y = y;
            this.derivValue = derivValue;
        }

        @Override
        public int compareTo(Coordinate o) {
            if (this.x != o.x) {
                return Double.compare(x, o.x);
            } else if (this.y != o.y) {
                return Double.compare(this.y, o.y);
            } else {
                return Double.compare(this.derivValue, o.derivValue);
            }
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (obj == null || this.getClass() != obj.getClass()) {
                return false;
            }

            Coordinate c = (Coordinate) obj;

            return this.x == c.x && this.y == c.y && this.derivValue == c.derivValue;
        }

        @Override
        public String toString() {
            return "Coordinate{" +
                    "x=" + x +
                    ", y=" + y +
                    ", derivValue=" + derivValue +
                    '}';
        }
    }
}
