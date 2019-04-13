package ru.bmstu.nummethodslabs.multidimfind;

import java.util.Arrays;
import java.util.List;

public class Vector {
    private double[] data;

    public Vector(double ... data) {
        this.data = data;
    }

    public Vector(List<Double> data) {
        this.data = new double[data.size()];
        for (int i = 0; i < data.size(); i++) {
            this.data[i] = data.get(i);
        }
    }

    public Vector(int n) {
        this.data = new double[n];
    }

    public double norm() {
        double res = 0;
        for (double datum : data) {
            res += Math.sqrt(datum * datum);
        }

        return res;
    }

    public Vector mul(double scalar) {
        double[] x = data.clone();
        for (int i = 0; i < x.length; i++) {
            x[i] *= scalar;
        }

        return new Vector(x);
    }

    public Matrix mul(Vector vector) {
        double[][] data = new double[this.data.length][vector.data.length];

        for (int i = 0; i < this.data.length; i++) {
            for (int j = 0; j < vector.data.length; j++) {
                data[i][j] = this.data[i] * vector.data[j];
            }
        }

        return new Matrix(data);
    }

    public Vector mul(Matrix matrix) {
        return new Vector(matrix.preMultiply(this.getData()));
    }

    public Vector div(double scalar) {
        return mul(1 / scalar);
    }

    public Vector sub(Vector other) {
        double[] x = data.clone();
        for (int i = 0; i < x.length; i++) {
            x[i] = data[i] - other.data[i];
        }

        return new Vector(x);
    }

    public Vector add(Vector other) {
        double[] x = data.clone();
        for (int i = 0; i < x.length; i++) {
            x[i] = data[i] + other.data[i];
        }

        return new Vector(x);
    }

    public Vector slice(int fromInclusive, int toExclusive) {
        return new Vector(Arrays.copyOfRange(data, fromInclusive, toExclusive));
    }

    public Vector copy() {
        return new Vector(data.clone());
    }

    public double[] getData() {
        return data;
    }

    public int size() {
        return data.length;
    }

    @Override
    public String toString() {
        return Arrays.toString(data);
    }
}
