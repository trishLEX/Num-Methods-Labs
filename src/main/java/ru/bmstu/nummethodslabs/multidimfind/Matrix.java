package ru.bmstu.nummethodslabs.multidimfind;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;

import java.util.Arrays;

public class Matrix extends Array2DRowRealMatrix {
    public Matrix(double[][] data) {
        super(data);
    }

    public static Matrix createMatrix(double scalar, int size) {
        double[][] data = new double[size][size];
        for (int i = 0; i < size; i++) {
            Arrays.fill(data[i], scalar);
        }

        return new Matrix(data);
    }

    public static double dot(Vector a, Vector b) {
        double[] x = a.getData();
        double[] y = b.getData();

        if (x.length != y.length) {
            throw new IllegalArgumentException("Illegal vector dimensions.");
        }

        double sum = 0.0;
        for (int i = 0; i < x.length; i++)
            sum += x[i] * y[i];
        return sum;
    }

    public Matrix mul(Matrix other) {
        return new Matrix(this.multiply(other).getData());
    }

    public Matrix mul(double scalar) {
        double[][] data = new double[getDataRef().length][getDataRef()[0].length];

        for (int i = 0; i < getDataRef().length; i++) {
            for (int j = 0; j < getDataRef()[0].length; j++) {
                data[i][j] = getDataRef()[i][j] * scalar;
            }
        }

        return new Matrix(data);
    }

    public Matrix div(double scalar) {
        return mul(1 / scalar);
    }

    public Vector mul(Vector vector) {
        return new Vector(super.preMultiply(vector.getData()));
    }

    public Matrix add(Matrix other) {
        return new Matrix(super.add(other).getDataRef());
    }

    public Matrix sub(Matrix other) {
        return new Matrix(super.subtract(other).getDataRef());
    }

    public static Matrix identity(int size) {
        double[][] data = new double[size][size];
        for (int i = 0; i < size; i++) {
            data[i][i] = 1.0;
        }

        return new Matrix(data);
    }

    public Matrix inverse() {
        if (getRowDimension() != getColumnDimension()) {
            throw new IllegalStateException("Try to inverse not square matrix");
        }

        if (getColumnDimension() == 2) {
            double[][] data = new double[2][2];
            data[0][0] = getDataRef()[1][1];
            data[0][1] = -getDataRef()[0][1];
            data[1][0] = -getDataRef()[1][0];
            data[1][1] = getDataRef()[0][0];
            return new Matrix(data).mul(1 / determinant(getDataRef()));
        }

        double[][] inverse = new double[this.getRowDimension()][this.getColumnDimension()];

        // minors and cofactors
        for (int i = 0; i < getRowDimension(); i++)
            for (int j = 0; j < getRowDimension(); j++)
                inverse[i][j] = Math.pow(-1, i + j)
                        * determinant(minor(getDataRef(), i, j));

        // adjugate and determinant
        double det = 1.0 / determinant(getDataRef());
        if (Double.compare(determinant(getDataRef()), 0.0) == 0) {
            det = 0;
        }
        for (int i = 0; i < inverse.length; i++) {
            for (int j = 0; j <= i; j++) {
                double temp = inverse[i][j];
                inverse[i][j] = inverse[j][i] * det;
                inverse[j][i] = temp * det;
            }
        }

        return new Matrix(inverse);
    }

    public double determinant() {
        return determinant(getDataRef());
    }

    private double determinant(double[][] matrix) {
        if (matrix.length != matrix[0].length)
            throw new IllegalStateException("invalid dimensions");

        if (matrix.length == 2)
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];

        double det = 0;
        for (int i = 0; i < matrix[0].length; i++)
            det += Math.pow(-1, i) * matrix[0][i]
                    * determinant(minor(matrix, 0, i));
        return det;
    }

    private static double[][] minor(double[][] matrix, int row, int column) {
        double[][] minor = new double[matrix.length - 1][matrix.length - 1];

        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; i != row && j < matrix[i].length; j++)
                if (j != column)
                    minor[i < row ? i : i - 1][j < column ? j : j - 1] = matrix[i][j];
        return minor;
    }

    public Matrix transpose(){
        double[][] m = getDataRef();
        double[][] temp = new double[m[0].length][m.length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                temp[j][i] = m[i][j];
            }
        }

        return new Matrix(temp);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
