package ru.bmstu.nummethodslabs.simplex;

import java.util.function.Function;

public class NMSimplex {
    private static final int MAX_IT = 1000;
    private static final double ALPHA = 1.0;
    private static final double BETA = 0.5;
    private static final double GAMMA = 2.0;

    public static double[] NMSimplex(double[] start, int n, double EPSILON, double scale, Function<double[], Double> fn) {
        double[][] v = new double[n + 1][n];
        double[] f = new double[n + 1];
        double[] vr = new double[n];
        double[] ve = new double[n];
        double[] vc = new double[n];
        double[] vm = new double[n];
        double fr;      /* value of function at reflection point */
        double fe;      /* value of function at expansion point */
        double fc;      /* value of function at contraction point */
        double pn, qn;   /* values used to create initial simplex */
        double fsum, favg, s, cent;
        int vs;         /* vertex with smallest value */
        int vh;         /* vertex with next smallest value */
        int vg;         /* vertex with largest value */
        int i, j, m, row = 0;
        int k;          /* track the number of function evaluations */
        int itr;          /* track the number of iterations */

        /* create the initial simplex */
        /* assume one of the vertices is 0,0 */

        pn = scale * (Math.sqrt(n + 1) - 1 + n) / (n * Math.sqrt(2));
        qn = scale * (Math.sqrt(n + 1) - 1) / (n * Math.sqrt(2));

        for (i = 0; i < n; i++) {
            v[0][i] = start[i];
        }

        for (i = 1; i <= n; i++) {
            for (j = 0; j < n; j++) {
                if (i - 1 == j) {
                    v[i][j] = pn + start[j];
                } else {
                    v[i][j] = qn + start[j];
                }
            }
        }

        /* find the initial function values */
        for (j = 0; j <= n; j++) {
            f[j] = fn.apply(v[j]);
        }

        k = n + 1;

        /* begin the main loop of the minimization */
        for (itr = 1; itr <= MAX_IT; itr++) {
            /* find the index of the largest value */
            vg = 0;
            for (j = 0; j <= n; j++) {
                if (f[j] > f[vg]) {
                    vg = j;
                }
            }

            /* find the index of the smallest value */
            vs = 0;
            for (j = 0; j <= n; j++) {
                if (f[j] < f[vs]) {
                    vs = j;
                }
            }

            /* find the index of the second largest value */
            vh = vs;
            for (j = 0; j <= n; j++) {
                if (f[j] > f[vh] && f[j] < f[vg]) {
                    vh = j;
                }
            }

            for (j = 0; j <= n - 1; j++) {
                cent = 0.0;
                for (m = 0; m <= n; m++) {
                    if (m != vg) {
                        cent += v[m][j];
                    }
                }
                vm[j] = cent / n;
            }

            /* reflect vg to new vertex vr */
            for (j = 0; j <= n - 1; j++) {
                vr[j] = vm[j] + ALPHA * (vm[j] - v[vg][j]);
            }

            fr = fn.apply(vr);
            k++;

            if (fr < f[vh] && fr >= f[vs]) {
                for (j = 0; j <= n - 1; j++) {
                    v[vg][j] = vr[j];
                }
                f[vg] = fr;
            }

            /* investigate a step further in this direction */
            if (fr < f[vs]) {
                for (j = 0; j <= n - 1; j++) {
                    ve[j] = vm[j] + GAMMA * (vr[j] - vm[j]);
                }

                fe = fn.apply(ve);
                k++;

                if (fe < fr) {
                    for (j = 0; j <= n - 1; j++) {
                        v[vg][j] = ve[j];
                    }
                    f[vg] = fe;
                } else {
                    for (j = 0; j <= n - 1; j++) {
                        v[vg][j] = vr[j];
                    }
                    f[vg] = fr;
                }
            }

            if (fr >= f[vh]) {
                if (fr < f[vg] && fr >= f[vh]) {
                    for (j = 0; j <= n - 1; j++) {
                        vc[j] = vm[j] + BETA * (vr[j] - vm[j]);
                    }

                    fc = fn.apply(vc);
                    k++;
                } else {
                    for (j = 0; j <= n - 1; j++) {
                        vc[j] = vm[j] - BETA * (vm[j] - v[vg][j]);
                    }

                    fc = fn.apply(vc);
                    k++;
                }


                if (fc < f[vg]) {
                    for (j = 0; j <= n - 1; j++) {
                        v[vg][j] = vc[j];
                    }
                    f[vg] = fc;
                }

                else {
                    for (row = 0; row <= n; row++) {
                        if (row != vs) {
                            for (j = 0; j <= n - 1; j++) {
                                v[row][j] = v[vs][j] + (v[row][j] - v[vs][j]) / 2.0;
                            }
                        }
                    }

                    f[vg] = fn.apply(v[vg]);
                    k++;

                    f[vh] = fn.apply(v[vh]);
                    k++;
                }
            }

            fsum = 0.0;
            for (j = 0; j <= n; j++) {
                fsum += f[j];
            }
            favg = fsum / (n + 1);
            s = 0.0;
            for (j = 0; j <= n; j++) {
                s += Math.pow((f[j] - favg), 2.0) / (n);
            }
            s = Math.sqrt(s);
            if (s < EPSILON) break;
        }

        vs = 0;
        for (j = 0; j <= n; j++) {
            if (f[j] < f[vs]) {
                vs = j;
            }
        }

        for (j = 0; j < n; j++) {
            start[j] = v[vs][j];
        }

        k++;
//        System.out.format("%d Function Evaluations\n", k);
//        System.out.format("%d Iterations through program\n", itr);
//        System.out.println(fn.apply(start));
//        System.out.println(fn.apply(new double[]{1, 1, 1, 1}) - fn.apply(start));

        return start;
    }

}
