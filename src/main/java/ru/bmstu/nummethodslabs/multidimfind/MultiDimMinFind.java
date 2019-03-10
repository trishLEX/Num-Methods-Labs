package ru.bmstu.nummethodslabs.multidimfind;

import ru.bmstu.nummethodslabs.MinFind;

import java.util.function.Function;

public class MultiDimMinFind {
    private static final int MAX_ITER = 10000;

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
        return new Vector(new double[] {
                4 * (30 * Math.pow(x[0], 3) - 30 * x[0] * x[1] + x[0] - 1),
                -4 * (15 * x[0] * x[0] - 30 * Math.pow(x[1], 3) + 2 * x[1] * (15 * x[2] - 8) + 1),
                -4 * (15 * x[1] * x[1] - 30 * Math.pow(x[2], 3) + 2 * x[2] * (15 * x[3] - 8) + 1),
                -60 * (x[2] * x[2] - x[3])
        });
    }

    private static Function<Double, Double> minimizingFunction(Vector point, Vector gradValue) {
        return gamma -> rosenbrock(point.sub(gradValue.mul(gamma)));
    }

    private static double findMin(double start, Function<Double, Double> function) {
        double step = 0.1;
        double[] segment = MinFind.svenn(start, step, function);
        return MinFind.golden(segment[0], segment[1], 0, function);
    }

    private static Vector polakRibier(Vector x0, double step, double grad, int maxIter) {
        Vector xk = x0.copy();

        int iters = 0;
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
                    System.out.println("ITERS: " + iters);
                    return x0;
                } else {
                    prevAcc = true;
                }
            }

            iters++;
            x0 = xk.copy();
        }
    }

    private static Vector flatcherRivz(Vector x0, double step, double grad, int maxIter) {
        Vector xk = x0.copy();
        Vector xkNew = x0.copy();
        Vector xkOld = x0.copy();
        int iters = 0;
        Vector d = gradient(xk).mul(-1);

        while (true) {
            Vector gradValue = gradient(xk);
            if (gradValue.norm() < grad || iters > maxIter) {
                System.out.println("ITERS: " + iters);
                return xk;
            }

            double beta = gradient(xkNew).norm() / gradient(xkOld).norm();

            Vector dNew = gradient(xkNew).mul(-1).add(d.mul(beta));

            Function<Double, Double> minimizingFunction = minimizingFunction(xk, dNew.mul(-1));

            double alpha = findMin(0.0, minimizingFunction);

            xkNew = xk.add(dNew.mul(alpha));

            if (xkNew.sub(xk).norm() < step && Double.compare(Math.abs(rosenbrock(xkNew) - rosenbrock(xk)), 0) != 0) {
                System.out.println("ITERS: " + iters);
                return xkNew;
            } else {
                xkOld = xk;
                xk = xkNew;
                d = dNew;

                iters++;
            }
        }
    }

    private static Matrix computeMatrixDg(Matrix G, Vector xOld, Vector xNew) {
        Vector dg = gradient(xNew).sub(gradient(xOld));
        Vector dx = xOld.sub(xOld);

        Matrix numerator = dx.mul(dx);
        double denumerator = Matrix.dot(dx, dx);
        if (Double.compare(denumerator, 0) == 0) {
            System.out.println("A " + numerator);
            denumerator = 1;
        }
        Matrix first = numerator.div(denumerator);


        numerator = G.mul(dg).mul(dg).mul(G);
        denumerator = Matrix.dot(G.mul(dg), dg);
        if (Double.compare(denumerator, 0) == 0) {
            System.out.println("A " + numerator);
            denumerator = 1;
        }
        Matrix second = numerator.div(denumerator);

        return first.sub(second);
    }

    //TODO не работает
    private static Vector davidonFlatcherPowell(Vector x0, double step, double grad, int maxIter) {
        Vector xOld = x0.copy();
        Vector xNew = x0.copy();
        int iters = 0;
        Matrix G = Matrix.identity(4);

        while (true) {
            Vector gradValue = gradient(xOld);

            if (gradValue.norm() < grad || iters > maxIter) {
                System.out.println("ITERS: " + iters);
                return xNew;
            }

            Function<Double, Double> minimizingFunction = minimizingFunction(xNew, G.mul(gradValue));

            double alpha = findMin(0.0, minimizingFunction);

            xOld = xNew;
            xNew = xOld.sub(G.mul(gradValue).mul(-alpha));

            Matrix dG = computeMatrixDg(G, xOld, xNew);
            G = G.add(dG);

            if (xNew.sub(xOld).norm() < step && Math.abs(rosenbrock(xNew) - rosenbrock(xOld)) < step) {
                System.out.println("ITERS: " + iters);
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

    private static Vector levenbergMarkvardt(Vector x0, double step, double grad, int maxIter) {
        int iters = 0;
        Vector xk = x0.copy();
        double nuk = Math.pow(10, 4);

        while (true) {
            Vector gradValue = gradient(xk);

            if (gradValue.norm() < grad || iters > maxIter) {
                System.out.println("ITERS: " + iters);
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

    public static void main(String[] args) {
        Vector ideal = new Vector(1, 1, 1, 1);

        Vector polakRibier = polakRibier(new Vector(0, 0, 0, 0), 0.001, 0.001, MAX_ITER);
        System.out.println(polakRibier + " " + Math.abs(rosenbrock(polakRibier) - rosenbrock(ideal)));

        Vector flatcherRivz = flatcherRivz(new Vector(0, 0, 0, 0), 0.001, 0.001, MAX_ITER);
        System.out.println(flatcherRivz + " " + Math.abs(rosenbrock(flatcherRivz) - rosenbrock(ideal)));

        Vector davidonFlatcherPowell = davidonFlatcherPowell(new Vector(0, 0, 0, 0), 0.00001, 0.000001, MAX_ITER);
        System.out.println(davidonFlatcherPowell + " " + Math.abs(rosenbrock(davidonFlatcherPowell) - rosenbrock(ideal)));

        Vector levenbergMarkvardt = levenbergMarkvardt(new Vector(0, 0, 0, 0), 0.001, 0.001, MAX_ITER);
        System.out.println(levenbergMarkvardt + " " + Math.abs(rosenbrock(levenbergMarkvardt) - rosenbrock(ideal)));
    }
}
