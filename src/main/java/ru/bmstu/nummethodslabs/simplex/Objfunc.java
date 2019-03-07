package ru.bmstu.nummethodslabs.simplex;

public class Objfunc
{
    public double evalObjfun(double[] x){
        double sum = 80;
        double a = 30;
        double b = 2;
        for (int i = 0; i < 3; i++) {
            sum += a * Math.pow(x[i] * x[i] - x[i+1], 2) + b * Math.pow(x[i] - 1, 2);
        }

        return sum;
    }
}