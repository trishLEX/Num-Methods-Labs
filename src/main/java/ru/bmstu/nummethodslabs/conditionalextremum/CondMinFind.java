package ru.bmstu.nummethodslabs.conditionalextremum;

import ru.bmstu.nummethodslabs.MinFind;
import ru.bmstu.nummethodslabs.multidimfind.Matrix;
import ru.bmstu.nummethodslabs.multidimfind.Vector;
import ru.bmstu.nummethodslabs.simplex.NMSimplex;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CondMinFind {
    private final static double E = 0.001;
    private final static int METHOD = 0;

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

    private static double g1(Vector vector) {
        double[] x = vector.getData();
        return x[0] * x[0] + x[1] * x[1] - 3 * x[2] + x[3] * x[3] - 5;
    }

    private static double g2(Vector vector) {
        double[] x = vector.getData();
        return -x[0];
    }

    private static double g3(Vector vector) {
        double[] x = vector.getData();
        return -x[1];
    }

    private static double g4(Vector vector) {
        double[] x = vector.getData();
        return -x[2];
    }

    private static double g5(Vector vector) {
        double[] x = vector.getData();
        return -x[3];
    }

    private static Vector g1Gradient(Vector vector){
        double[] x = vector.getData();
        return new Vector(2 * x[0], 2 * x[1], -3 * x[2], 2 * x[3]);
    }

    private static Vector g2Gradient(Vector vector) {
        return new Vector(-1, 0, 0, 0);
    }

    private static Vector g3Gradient(Vector vector) {
        return new Vector(0, -1, 0, 0);
    }

    private static Vector g4Gradient(Vector vector) {
        return new Vector(0, 0, -1, 0);
    }

    private static Vector g5Gradient(Vector vector) {
        return new Vector(0, 0, 0, -1);
    }

    private static double cutFunction(double x) {
        return x > 0 ? x : 0;
    }

    private final static List<Function<Vector, Double>> G_LIST = List.of(
            CondMinFind::g1,
            CondMinFind::g2,
            CondMinFind::g3,
            CondMinFind::g4,
            CondMinFind::g5
    );

    private final static List<Function<Vector, Vector>> DERIV_G_LIST = List.of(
            CondMinFind::g1Gradient,
            CondMinFind::g2Gradient,
            CondMinFind::g3Gradient,
            CondMinFind::g4Gradient,
            CondMinFind::g5Gradient
    );

    private static Vector penaltyFunctionsMethod(Vector x, double r, double c) {
        int iter = 0;
        Vector x0 = x.copy();

        BiFunction<Vector, Double, Double> penaltyFunction = (vector, pen) -> G_LIST.stream()
                .map(g -> g.apply(vector))
                .map(CondMinFind::cutFunction)
                .map(val -> val * val)
                .reduce((x1, x2) -> x1 + x2)
                .map(res ->  res * pen / 2)
                .orElseThrow(IllegalStateException::new);

        while (true) {
            double finalR = r;

            Function<double[], Double> function = vector -> rosenbrock(new Vector(vector)) + penaltyFunction.apply(new Vector(vector), finalR);

            Vector xNew = new Vector(NMSimplex.NMSimplex(x0.getData(), 4, E, 0.01, function));
            double penaltyValue = penaltyFunction.apply(xNew, finalR);

            iter++;

            if (Math.abs(penaltyValue) < E) {
                System.out.println("PENALTY METHOD ITERS: " + iter);
                return xNew;
            } else {
                r *= c;
                x0 = xNew;
            }
        }
    }

    private static Vector barrierMethod(Vector x, double r, double c) {
        int iter = 0;
        double curPenalty = r;
        Vector xCur = x.copy();

        BiFunction<Vector, Double, Double> penaltyFunction = (vector, pen) -> G_LIST.stream()
                .map(g -> g.apply(vector))
                .map(val -> pen / val)
                .reduce((x1, x2) -> x1 + x2)
                .orElseThrow(IllegalStateException::new);

        while (true) {
            double finalCurPenalty = curPenalty;
            Function<double[], Double> function = vector -> rosenbrock(new Vector(vector)) - penaltyFunction.apply(new Vector(vector), finalCurPenalty);

            Vector xNew = new Vector(NMSimplex.NMSimplex(xCur.getData(), 4, E, 0.01, function));
            double penaltyValue = penaltyFunction.apply(xNew, curPenalty);

            iter++;

            if (Math.abs(penaltyValue) < E) {
                System.out.println("BARRIER METHOD ITERS: " + iter);
                return xNew;
            } else {
                curPenalty /= c;
                xCur = xNew;
            }
        }
    }

    private static Vector mixedMethod(Vector x, double r, double c) {
        int iter = 0;
        double curPenalty = r;
        Vector xCur = x.copy();

        BiFunction<Vector, Double, Double> penaltyFunction1 = (vector, pen) -> G_LIST.stream()
                .map(g -> g.apply(vector))
                .map(CondMinFind::cutFunction)
                .map(val -> val * val)
                .reduce((x1, x2) -> x1 + x2)
                .map(res -> res * pen / 2)
                .orElseThrow(IllegalStateException::new);

        BiFunction<Vector, Double, Double> penaltyFunction2 = (vector, pen) -> - G_LIST.stream()
                .map(g -> g.apply(vector))
                .map(val -> pen / val)
                .reduce((x1, x2) -> x1 + x2)
                .orElseThrow(IllegalStateException::new);

        while (true) {
            double finalCurPenalty = curPenalty;
            Function<double[], Double> function = vector -> rosenbrock(new Vector(vector))
                    + penaltyFunction1.apply(new Vector(vector), finalCurPenalty)
                    + penaltyFunction2.apply(new Vector(vector), finalCurPenalty);

            Vector xNew = new Vector(NMSimplex.NMSimplex(xCur.getData(), 4, E, 0.001, function));
            double penaltyValue = penaltyFunction1.apply(xNew, curPenalty) + penaltyFunction2.apply(xNew, curPenalty);

            iter++;

            if (Math.abs(penaltyValue) < E) {
                System.out.println("MIXED METHOD ITERS: " + iter);
                return xNew;
            } else {
                curPenalty /= c;
                xCur = xNew;
            }
        }
    }

    private static Vector modifiedLagrangeMethod(Vector x, double r, double c, List<Double> mu) {
        int iter = 0;
        Vector x0 = x.copy();

        while (true) {
            List<Double> finalMu = mu;
            double finalR = r;

            Function<Vector, Double> fourth = vector -> IntStream
                    .range(0, finalMu.size())
                    .boxed()
                    .map(i -> Math.pow(cutFunction(finalMu.get(i) + finalR * G_LIST.get(i).apply(vector)), 2) - finalMu.get(i) * finalMu.get(i))
                    .reduce((x1, x2) -> x1 + x2)
                    .orElseThrow(IllegalStateException::new);

            Function<double[], Double> function = vector -> rosenbrock(new Vector(vector)) + 0.5 / finalR * fourth.apply(new Vector(vector));

            Vector xNew = new Vector(NMSimplex.NMSimplex(x0.getData(), 4, E, 0.01, function));
            double penaltyValue = 0.5 / r * fourth.apply(xNew);

            iter++;

            if (Math.abs(penaltyValue) < E) {
                System.out.println("MODIFIED LAGRANGE ITERS: " + iter);
                return xNew;
            } else {
                x0 = xNew;

                mu = IntStream
                        .range(0, mu.size())
                        .boxed()
                        .map(i -> cutFunction(finalMu.get(i) + finalR * G_LIST.get(i).apply(xNew)))
                        .collect(Collectors.toList());
                r *= c;
            }
        }
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

    private static Vector gradientsProjection(Vector x) {
        int iter = 0;

        Matrix identity = Matrix.identity(4);

        double[][] aData = new double[5][4];
        for (int i = 0; i < 5; i++) {
            Vector vector = DERIV_G_LIST.get(i).apply(x);
            aData[i] = vector.getData();
        }
        Matrix a = new Matrix(aData);

        double[] stepData = new double[5];
        for (int i = 0; i < 5; i++) {
            stepData[i] = - G_LIST.get(i).apply(x);
        }
        Vector step = new Vector(stepData);

        Matrix aT = a.transpose();
        Matrix aAt = a.mul(aT);
        Matrix aTInv = aAt.inverse();

        Matrix atAtInv = aT.mul(aTInv);
        Vector deltaX = step.mul(atAtInv.transpose());

        x = x.add(deltaX);

        while (true) {
            if (iter > 1000) {
                System.out.println("GRADIENT METHOD ITERS: " + iter);
                return x;
            }

            Vector gradient = gradient(x);
            Matrix atAtInvA = atAtInv.mul(a);
            Vector delta = identity.sub(atAtInvA).mul(gradient).mul(-1);
            double norm = delta.norm();

            if (norm < E) {
                System.out.println("GRADIENT METHOD ITERS: " + iter);
                return x;
            }

            Vector finalX = x;
            Function<Double, Double> function = val -> rosenbrock(finalX.add(delta.mul(val)));
            double minStep = findMin(0.0, function);

            x = x.add(delta.mul(minStep));
            iter++;
        }
    }

    public static void main(String[] args) {
        Vector start = new Vector(0, 0, 0, 0);
        Vector ideal = new Vector(1, 1, 1, 1);

        Vector penaltyFunction = penaltyFunctionsMethod(start, 1, 4);
        System.out.println("PENALTY METHOD RES: " + penaltyFunction + " ERROR: " + Math.abs(rosenbrock(ideal) - rosenbrock(penaltyFunction)));

        Vector barrierMethod = barrierMethod(start, 1, 10);
        System.out.println("BARRIER METHOD RES: " + barrierMethod + " ERROR: " + Math.abs(rosenbrock(ideal) - rosenbrock(barrierMethod)));

        Vector mixedMethod = mixedMethod(start, 1, 10);
        System.out.println("MIXED METHOD RES: " + mixedMethod + " ERROR " + Math.abs(rosenbrock(ideal) - rosenbrock(mixedMethod)));

        Vector lagrange = modifiedLagrangeMethod(start, 1, 4, List.of(-0.33, 10.0, 10.0, 1.0, 10.0));
        System.out.println("MODIFIED LAGRANGE METHOD RES: " + lagrange + " ERROR: " + Math.abs(rosenbrock(ideal) - rosenbrock(lagrange)));

        Vector gradients = gradientsProjection(start);
        System.out.println("GRADIENT METHOD RES: " + gradients + " ERROR: " + Math.abs(rosenbrock(ideal) - rosenbrock(gradients)));
    }
}
