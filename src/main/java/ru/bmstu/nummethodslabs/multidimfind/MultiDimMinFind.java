package ru.bmstu.nummethodslabs.multidimfind;

import ru.bmstu.nummethodslabs.MinFind;

import java.util.function.Function;

public class MultiDimMinFind {
    private static final int MAX_ITER = 1000;
    private static int METHOD = 0;
    public static int iters;

    private static double rosenbrock(Vector vector){
        double[] x = vector.getData();

        double sum = 80;
        double a = 30;
        double b = 2;
        for (int i = 0; i < 3; i++) {
            sum += a * Math.pow(x[i] * x[i] - x[i+1], 2) + b * Math.pow(x[i] - 1, 2);
        }

        return sum;
    }

    private static Vector gradient(Vector vector) {
        double[] x = vector.getData();
        return new Vector(4 * (30 * Math.pow(x[0], 3) - 30 * x[0] * x[1] + x[0] - 1),
                -4 * (15 * x[0] * x[0] - 30 * Math.pow(x[1], 3) + 2 * x[1] * (15 * x[2] - 8) + 1),
                -4 * (15 * x[1] * x[1] - 30 * Math.pow(x[2], 3) + 2 * x[2] * (15 * x[3] - 8) + 1),
                -60 * (x[2] * x[2] - x[3]));
    }

    private static Function<Double, Double> minimizingFunction(Vector point, Vector gradValue) {
        return gamma -> rosenbrock(point.sub(gradValue.mul(gamma)));
    }

    private static double findMin(double start, Function<Double, Double> function) {
        double step = 0.01;
        double[] segment = MinFind.svenn(start, step, function);

        switch (METHOD) {
            case 0:
                return MinFind.bisection(segment[0], segment[1], 0, function);
            case 1:
                return MinFind.golden(segment[0], segment[1], 0, function);
            case 2:
                return MinFind.fibonacci(segment[0], segment[1], function);
            default:
                throw new IllegalStateException("Wrong id of method");
        }
    }

    public static Vector polakRibier(Vector x0, double step, double grad, int maxIter) {
        Vector xk = x0.copy();
        iters = 0;

        boolean prevAcc = false;

        while (true) {
            Vector gradValue = gradient(x0);
            double gradNorm = gradValue.norm();

            if (gradNorm < grad || iters > maxIter) {
                System.out.println("ITERS: " + iters);
                return x0;
            }

            Function<Double, Double> minimizingFunction = minimizingFunction(xk, gradValue);
            double gamma = findMin(0.0, minimizingFunction);

            xk = x0.sub(gradValue.mul(gamma));
            if (xk.sub(x0).norm() < step && Math.abs(rosenbrock(xk) - rosenbrock(x0)) < step) {
                if (prevAcc) {
                    System.out.println("POLAK: " + "ITERS: " + iters);
                    return x0;
                } else {
                    prevAcc = true;
                }
            }

            iters++;
            x0 = xk.copy();
        }
    }

    public static Vector flatcherRivz(Vector x0, double step, double grad, int maxIter) {
        Vector xNew = x0.copy();
        Vector xOld = x0.copy();
        iters = 0;
        Vector d = gradient(xNew).mul(-1);
        Vector gradValueNew = gradient(xOld);
        Vector gradValueOld = gradValueNew;

        while (true) {
            gradValueNew = gradient(xOld);
            if (gradValueNew.norm() < grad || iters > maxIter) {
                System.out.println("FLATCHER: " + iters);
                return xOld;
            }

            double beta = Matrix.dot(gradValueNew, gradValueNew.sub(gradValueOld)) / Math.pow(gradValueOld.norm(), 2);

            d = gradient(xNew).mul(-1).add(d.mul(beta));

            Function<Double, Double> minimizingFunction = minimizingFunction(xOld, d.mul(-1));

            double alpha = findMin(0.0, minimizingFunction);

            xNew = xOld.add(d.mul(alpha));

            double norm = xNew.sub(xOld).norm();
            if ((norm < step) && (Math.abs(rosenbrock(xNew) - rosenbrock(xOld)) < step)) {
                System.out.println("FLATCHER: " + iters);
                return xNew;
            } else {
                xOld = xNew;
                gradValueOld = gradValueNew;
                iters++;
            }
        }
    }

    public static Vector davidonFlatcherPowell(Vector x0, double step, double grad, int maxIter) {
        Vector xOld = x0.copy();
        Vector xNew = x0.copy();
        iters = 0;
        Matrix GNew = Matrix.identity(4);
        Matrix GOld = Matrix.identity(4);

        while (true) {
            Vector gradValue = gradient(xOld);

            if (gradValue.norm() < grad || iters > maxIter) {
                System.out.println("DAVIDON: " + iters);
                return xOld;
            }

            if (iters > 0) {
                Vector dg = gradient(xNew).sub(gradValue);
                Vector dx = xNew.sub(xOld);

                Matrix numerator = dx.mul(dx);
                double denumerator = Matrix.dot(dx, dg);
                Matrix first = numerator.div(denumerator);

                Matrix numerator1 = GOld.mul(dg.mul(dg)).mul(GOld);
                double denumerator1 = Matrix.dot(GOld.mul(dg), dg);
                Matrix second = numerator1.div(denumerator1);

                Matrix dG = first.sub(second);
                GOld = GNew;
                GNew = GOld.add(dG);
            }

            Function<Double, Double> minimizingFunction = minimizingFunction(xNew, GNew.mul(gradValue));

            double alpha = findMin(0.0, minimizingFunction);

            xOld = xNew;
            xNew = xOld.sub(GNew.mul(gradValue).mul(alpha));

            if (xNew.sub(xOld).norm() < step && Math.abs(rosenbrock(xNew) - rosenbrock(xOld)) < step) {
                System.out.println("DAVIDON: " + iters);
                return xNew;
            }

            iters++;
        }
    }

    private static Matrix hessian(Vector vector) {
        double[] x = vector.getData();
        double[][] hessian = new double[x.length][x.length];

        hessian[0][0] = 120 * (x[0] * x[0] - x[1]) + 240 * x[0] * x[0] + 4;
        hessian[0][1] = -120 * x[0];
        hessian[1][0] = hessian[0][1];
        hessian[1][1] = 120 * (x[1] * x[1] - x[2]) + 240 * x[1] * x[1] + 64;
        hessian[1][2] = -120 * x[1];
        hessian[2][1] = hessian[1][2];
        hessian[2][2] = 120 * (x[2] * x[2] - x[3]) + 240 * x[2] * x[2] + 64;
        hessian[2][3] = -120 * x[2];
        hessian[3][2] = hessian[2][3];
        hessian[3][3] = 60;
        return new Matrix(hessian);
    }

    public static Vector levenbergMarkvardt(Vector x0, double step, double grad, int maxIter) {
        iters = 0;
        Vector xk = x0.copy();
        double nuk = Math.pow(10, 4);

        while (true) {
            Vector gradValue = gradient(xk);

            if (gradValue.norm() < grad || iters > maxIter) {
                System.out.println("LEVENBERG: " + iters);
                return xk;
            }

            while (true) {
                Matrix hessian = hessian(xk);
                Matrix temp = hessian.add(Matrix.identity(4).mul(nuk)).inverse();

                Vector dk = temp.mul(gradValue).mul(-1);

                Vector xkNew = xk.add(dk);
                if (rosenbrock(xkNew) < rosenbrock(xk)) {
                    iters++;
                    nuk /= 2;
                    xk = xkNew;
                    break;
                } else {
                    nuk *= 2;
                }
            }
        }
    }

    private static void calc() {
        Vector ideal = new Vector(1, 1, 1, 1);

        Vector polakRibier = polakRibier(new Vector(0, 0, 0, 0), 0.001, 0.001, MAX_ITER);
        System.out.println("POLAK: " + rosenbrock(polakRibier) + " " + Math.abs(rosenbrock(polakRibier) - rosenbrock(ideal)) + "\n");

        Vector flatcherRivz = flatcherRivz(new Vector(0, 0, 0, 0), 0.001, 0.001, MAX_ITER);
        System.out.println("FLATCHER: " + rosenbrock(flatcherRivz) + " " + Math.abs(rosenbrock(flatcherRivz) - rosenbrock(ideal)) + "\n");

        Vector davidonFlatcherPowell = davidonFlatcherPowell(new Vector(0.0, 0.0, 0.0, 0.0), 0.001, 0.001, MAX_ITER);
        System.out.println("DAVIDON: " + rosenbrock(davidonFlatcherPowell) + " " + Math.abs(rosenbrock(davidonFlatcherPowell) - rosenbrock(ideal)) + "\n");

        Vector levenbergMarkvardt = levenbergMarkvardt(new Vector(0, 0, 0, 0), 0.001, 0.001, MAX_ITER);
        System.out.println("LEVENBERG: " + rosenbrock(levenbergMarkvardt) + " " + Math.abs(rosenbrock(levenbergMarkvardt) - rosenbrock(ideal)));

        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("BISECTION");
        METHOD = 0;
        calc();

        System.out.println("GOLDEN");
        METHOD = 1;
        calc();

        System.out.println("FIBONACCI");
        METHOD = 2;
        calc();
    }
}
