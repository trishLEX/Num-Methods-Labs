package ru.bmstu.nummethodslabs.hookejeeves;

import ru.bmstu.nummethodslabs.MinFind;

public class Hooke {
    /** Constant. The maximum number of variables. */
    public static final int VARS = 4;

    /** Constant. The ending value of stepsize. */
    public static final double EPSMIN = 0.0001;

    /** Constant. The maximum number of iterations. */
    public static final int IMAX = 5000;

    /** Helper constants. */
    public  static final int    INDEX_ZERO      = 0;
    public  static final int    INDEX_ONE       = 1;
    private static final double ZERO_POINT_FIVE = 0.5;

    /**
     * 0 - bisection
     * 1 - golden section
     * 2 - fibonacci
     */
    private static final int METHOD = 2;

    private double bestNearby(double[] point) {
        double[] z = new double[VARS];

        System.arraycopy(point, 0, z, 0, VARS);

        for (int i = 0; i < VARS; i++) {
            double[] segment = MinFind.svenn(1, 0.001, x -> rosenbrock(z));

            switch (METHOD) {
                case 0:
                    z[i] = MinFind.bisection(segment[0], segment[1], 0, x -> rosenbrock(z));
                    break;
                case 1:
                    z[i] = MinFind.golden(segment[0], segment[1], 0, x -> rosenbrock(z));
                    break;
                case 2:
                    z[i] = MinFind.fibonacci(segment[0], segment[1], x -> rosenbrock(z));
                    break;
            }
        }

        System.arraycopy(z, 0, point, 0, VARS);

        return rosenbrock(z);
    }

    public int hooke(final int nVars,
                     final double[] startPt,
                     final double[] endPt,
                     final double rho,
                     final double epsilon,
                     final int iterMax) {

        int i;
        int iAdj;
        int iters;
        int j;
        int keep;

        double[] newX    = new double[VARS];
        double[] xBefore = new double[VARS];
        double[] delta   = new double[VARS];
        double stepLength;
        double fBefore;
        double newF;
        double tmp;

        for (i = 0; i < nVars; i++) {
            xBefore[i] = startPt[i];
            newX[i]    = xBefore[i];

            delta[i] = Math.abs(startPt[i] * rho);

            if (delta[i] == 0.0) {
                delta[i] = rho;
            }
        }

        iAdj       = 0;
        stepLength = rho;
        iters      = 0;

        fBefore = rosenbrock(newX);

        newF = fBefore;

        while ((iters < iterMax) && (stepLength > epsilon)) {
            iters++;
            iAdj++;

            for (j = 0; j < nVars; j++) {
                System.out.printf("   x[%2d] = %.4e\n", j, xBefore[j]);
            }

            for (i = 0; i < nVars; i++) {
                newX[i] = xBefore[i];
            }

            newF = bestNearby(newX);

            keep = 1;

            while ((newF < fBefore) && (keep == 1)) {
                iAdj = 0;

                for (i = 0; i < nVars; i++) {
                    if (newX[i] <= xBefore[i]) {
                        delta[i] = 0.0 - Math.abs(delta[i]);
                    } else {
                        delta[i] = Math.abs(delta[i]);
                    }

                    tmp        = xBefore[i];
                    xBefore[i] = newX[i];
                    newX[i]    = newX[i] + newX[i] - tmp;
                }

                fBefore = newF;

                newF = bestNearby(newX);

                if (newF >= fBefore) {
                    break;
                }

                keep = 0;

                for (i = 0; i < nVars; i++) {
                    keep = 1;

                    if (Math.abs(newX[i] - xBefore[i])
                            > (ZERO_POINT_FIVE * Math.abs(delta[i]))) {

                        break;
                    } else {
                        keep = 0;
                    }
                }
            }

            if ((stepLength >= epsilon) && (newF >= fBefore)) {
                stepLength = stepLength * rho;

                for (i = 0; i < nVars; i++) {
                    delta[i] *= rho;
                }
            }
        }

        for (i = 0; i < nVars; i++) {
            endPt[i] = xBefore[i];
        }

        return iters;
    }

    private static double rosenbrock(double[] x){
        double sum = 80;
        double a = 30;
        double b = 2;
        for (int i = 0; i < 3; i++) {
            sum += a * Math.pow(x[i] * x[i] - x[i+1], 2) + b * Math.pow(x[i] - 1, 2);
        }

        return sum;
    }

    public static void main(String[] args) {
        Hooke hooke = new Hooke();
        int nVars;
        int iterMax;
        int jj;
        int i;

        double[] startPt = new double[Hooke.VARS];
        double rho;
        double epsilon;
        double[] endPt   = new double[Hooke.VARS];

        nVars                     = 4;
        startPt[Hooke.INDEX_ZERO] = -1.2;
        startPt[Hooke.INDEX_ONE]  = 0.1;
        iterMax                   = Hooke.IMAX;
        rho                       = 0.5;
        epsilon                   = Hooke.EPSMIN;

        jj = hooke.hooke(
                nVars, startPt, endPt, rho, epsilon, iterMax
        );

        System.out.println(
                "\n\n\nHOOKE USED " + jj + " ITERATIONS, AND RETURNED"
        );

        for (i = 0; i < nVars; i++) {
            System.out.printf("%f\n", endPt[i]);
        }

        System.out.println(rosenbrock(endPt));
        System.out.println(rosenbrock(new double[]{1, 1, 1, 1}) - rosenbrock(endPt));
    }
}
