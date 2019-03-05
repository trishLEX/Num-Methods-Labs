package ru.bmstu.nummethodslabs.simplex;

import java.io.*;

public class Testfun
{
	public static void main(String[] args) throws IOException
	{
		double[] start = {0, 0, 0, 0};
		int dim = 4;
		double eps = 0.001;
		double scale = 0.01;

		NMSimplex simplex = new NMSimplex(start, dim, eps, scale);

		for (int i = 0; i < dim; i++) {
			System.out.format("%f\n", start[i]);
		}
	}

}