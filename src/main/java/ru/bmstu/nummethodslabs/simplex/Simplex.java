package ru.bmstu.nummethodslabs.simplex;

import org.apache.commons.math3.util.Pair;
import ru.bmstu.nummethodslabs.multidimfind.Matrix;
import ru.bmstu.nummethodslabs.multidimfind.Vector;

import java.util.function.Function;

public class Simplex {
    private static final double E = 0.00001;

    private static Vector gdSimplex(Matrix condVectors, Vector condValues, Function<Vector, Double> function) {
        int dimension = condVectors.getColumnDimension();
        int condCount = condVectors.getRowDimension();

        double[] funcM = new double[dimension];
        for (int i = 0; i < dimension; i++) {
            double[] vector = new double[dimension];
            vector[i] = 1;
            funcM[i] = function.apply(new Vector(vector));
        }
        Vector func = new Vector(funcM);

        Matrix bM = condVectors.slice(0, condCount, 0, condCount);
        Matrix nM = condVectors.slice(0, condCount, condCount, dimension);
        Vector bF = func.slice(0, condCount);
        Vector nF = func.slice(condCount, dimension);

        Vector zeroVector = new Vector(dimension);
        Vector x = zeroVector.copy();
        int iter = 0;

        while (true) {
            iter++;

            Matrix inv = bM.inverse();

            Vector pi = bF.mul(inv);
            Vector z = pi.mul(nM);
            Vector d = z.sub(nF);

            Pair<Double, Integer> minInVector = minInVector(d);

            Vector beta = inv.mul(condValues);

            if (minInVector.getFirst() > 0) {
                Vector temp = zeroVector.slice(0, condCount);
                double[] tempData = temp.getData();
                for (int i = condCount; i < dimension; i++) {
                    tempData[i - condCount] = x.getData()[i];
                }
                temp = new Vector(tempData);
                temp = beta.sub(inv.mul(nM).mul(temp));

                double[] xData = x.getData();
                for (int i = 0; i < condCount; i++) {
                    xData[i] = temp.getData()[i];
                }
                System.out.println(iter);
                return new Vector(xData);
            }

            Vector alpha = inv.mul(nM.row(minInVector.getSecond()));

            Pair<Double, Integer> min = new Pair<>(Double.MAX_VALUE, -1);
            for (int i = 0; i < condCount; i++) {
                if (alpha.getData()[i] > E && beta.getData()[i] / alpha.getData()[i] < min.getFirst()) {
                    min = new Pair<>(beta.getData()[i] / alpha.getData()[i], i);
                }
            }

            if (min.getSecond() < 0) {
                throw new IllegalStateException();
            }
            double[] xData = x.getData();
            xData[min.getSecond() + condCount] = min.getFirst();
            x = new Vector(xData);

            double[][] bData = bM.getDataRef();
            for (int i = 0; i < bM.getColumnDimension(); i++) {
                bData[i][min.getSecond()] = nM.getData()[i][min.getSecond()];
            }
            bM = new Matrix(bData);

            if (iter > 100) {
                break;
            }
        }

        System.out.println(iter);
        return x;
    }

    private static Pair<Double, Integer> minInVector(Vector vector) {
        double[] data = vector.getData();
        Pair<Double, Integer> min = new Pair<>(data[0], 0);

        for (int i = 1; i < data.length; i++) {
            if (data[i] < min.getFirst()) {
                min = new Pair<>(data[i], i);
            }
        }

        return min;
    }

    public static void main(String[] args) {
        double[][] A = {
                {  2,  1,  3, 0 },
                {  3,  1,  0, 1 }
        };
        double[] b = { 24, 12 };

        Matrix condVectors = new Matrix(A);
        Vector condValues = new Vector(b);

        Function<Vector, Double> function = vec -> vec.getData()[0] * 3 + vec.getData()[1] * 4 - vec.getData()[2] * 1 + vec.getData()[3];

        Vector vector = gdSimplex(condVectors, condValues, function);
        System.out.println(vector);
        System.out.println(function.apply(vector));
    }
}
