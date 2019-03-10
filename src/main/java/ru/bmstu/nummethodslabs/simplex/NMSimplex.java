package ru.bmstu.nummethodslabs.simplex;

public class NMSimplex {
    private static final int MAX_IT = 1000;
    private static final double ALPHA = 1.0;
    private static final double BETA = 0.5;
    private static final double GAMMA = 2.0;

    private double rosenbrock(double[] x){
        double sum = 80;
        double a = 30;
        double b = 2;
        for (int i = 0; i < 3; i++) {
            sum += a * Math.pow(x[i] * x[i] - x[i+1], 2) + b * Math.pow(x[i] - 1, 2);
        }

        return sum;
    }

    public NMSimplex(double[] start, int n, double EPSILON, double scale) {
        double[][] v = new double[n + 1][n];
        double[] f = new double[n + 1];
        double[] vr = new double[n];
        double[] ve = new double[n];
        double[] vc = new double[n];
        double[] vm = new double[n];
        double fr;
        double fe;
        double fc;
        double pn, qn;
        double fsum, favg, s, cent;
        int vs;
        int vh;
        int vg;
        int i, j, m, row = 0;
        int k;
        int itr;

        System.out.format("Starting from : %f\n", start[0]);

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

        for (j = 0; j <= n; j++) {
            f[j] = rosenbrock(v[j]);
        }

        k = n + 1;

        System.out.println("Initial Values");
        for (j = 0; j <= n; j++) {
            for (i = 0; i < n; i++) {
                System.out.format("%f %f\n", v[j][i], f[j]);
            }
        }

        for (itr = 1; itr <= MAX_IT; itr++) {
            vg = 0;
            for (j = 0; j <= n; j++) {
                if (f[j] > f[vg]) {
                    vg = j;
                }
            }

            vs = 0;
            for (j = 0; j <= n; j++) {
                if (f[j] < f[vs]) {
                    vs = j;
                }
            }

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

            for (j = 0; j <= n - 1; j++) {
                vr[j] = vm[j] + ALPHA * (vm[j] - v[vg][j]);
            }

            fr = rosenbrock(vr);
            k++;

            if (fr < f[vh] && fr >= f[vs]) {
                for (j = 0; j <= n - 1; j++) {
                    v[vg][j] = vr[j];
                }
                f[vg] = fr;
            }

            if (fr < f[vs]) {
                for (j = 0; j <= n - 1; j++) {
                    ve[j] = vm[j] + GAMMA * (vr[j] - vm[j]);
                }

                fe = rosenbrock(ve);
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

                    fc = rosenbrock(vc);
                    k++;
                } else {
                    for (j = 0; j <= n - 1; j++) {
                        vc[j] = vm[j] - BETA * (vm[j] - v[vg][j]);
                    }

                    fc = rosenbrock(vc);
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

                    f[vg] = rosenbrock(v[vg]);
                    k++;

                    f[vh] = rosenbrock(v[vh]);
                    k++;
                }
            }

            System.out.format("Iteration %d\n", itr);
            for (j = 0; j <= n; j++) {
                for (i = 0; i < n; i++) {
                    System.out.format("%f %f\n", v[j][i], f[j]);
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

        System.out.format("The minimum was found at\n");
        for (j = 0; j < n; j++) {
            System.out.format("%.2f\n", v[vs][j]);
            start[j] = v[vs][j];
        }
        k++;
        System.out.format("%d Function Evaluations\n", k);
        System.out.format("%d Iterations through program\n", itr);
        System.out.println(rosenbrock(start));
        System.out.println(rosenbrock(new double[]{1, 1, 1, 1}) - rosenbrock(start));
    }

}