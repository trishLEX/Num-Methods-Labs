package ru.bmstu.nummethodslabs.multistart;

import java.util.*;
import java.util.stream.IntStream;

public class GA {
    private static final int SOL_PER_POP = 8;
    private static final int N = 3;
    private static final int NUM_PARENTS_MATING = 4;
    private static final int ALPHA = 0;
    private static final int BETA = 20;
    private static final Random RANDOM = new Random();

    private static double f(double[] vector) {
        //return Arrays.stream(data).map(datum -> datum * datum - 10 * Math.cos(2 * Math.PI * datum) + 10).sum();
        double[] phi = {-1.0/6.0, -2.0/6.0, -3.0/6.0};

        double[][] xij = {
                {4.0, 4.0, 4.0},
                {4.0, 4.0, 4.0},
                {4.0, 4.0, 4.0}
        };

        double sum = 0;
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            sum -= 2 / (phi[i] + 2 * IntStream
                    .range(0, 3)
                    .boxed()
                    .map(j -> Math.pow(vector[j] - xij[finalI][j], 2))
                    .reduce((p1, p2) -> p1 + p2)
                    .orElseThrow());
        }

        return sum;
    }

    private static double[] fitness(double[][] population) {
        double[] values = new double[population.length];
        for (int i = 0; i < population.length; i++) {
            values[i] = 1 / f(population[i]);
        }

        return values;
    }

    private static double[][] mating(double[][] population, double[] fitness, int parentsCount) {
        double[][] parents = new double[parentsCount][N];
        for (int parentIdx = 0; parentIdx < parentsCount; parentIdx++) {
            int maxIdx = IntStream.range(0, fitness.length).boxed().max(Comparator.comparingDouble(i -> fitness[i])).orElseThrow();
            fitness[maxIdx] = Double.MIN_VALUE;
            parents[parentIdx] = population[maxIdx];
        }

        return parents;
    }

    private static double[][] crossover(double[][] parents, int rows) {
        double[][] offspring = new double[rows][N];
        int crossoverPoint = N / 2;

        for (int i = 0; i < rows; i++) {
            int parentIdx1 = i % parents.length;
            int parentIdx2 = (i + 1) % parents.length;

            System.arraycopy(parents[parentIdx1], 0, offspring[i], 0, crossoverPoint);
            System.arraycopy(parents[parentIdx2], crossoverPoint, offspring[i], crossoverPoint, N - crossoverPoint);
        }

        return offspring;
    }

    private static double[][] mutation(double[][] crossover) {
        for (int i = 0; i < crossover.length; i++) {
            double randomValue =  (BETA - ALPHA) * RANDOM.nextDouble() + ALPHA;
            int randomGene = RANDOM.nextInt(N);
            crossover[i][randomGene] = randomValue;
        }

        return crossover;
    }

    public static void main(String[] args) {
        double[][] population = new double[SOL_PER_POP][N];
        List<Double> bestOutputs = new ArrayList<>();
        for (int i = 0; i < SOL_PER_POP; i++) {
            for (int j = 0; j < N; j++) {
                population[i][j] = (BETA - ALPHA) * RANDOM.nextDouble() + ALPHA;
            }
        }

        for (int genIdx = 0; genIdx < 1000; genIdx++) {
            double[] fitness = fitness(population);

            bestOutputs.add(Arrays.stream(fitness).max().orElseThrow());

            double[][] parents = mating(population, fitness, NUM_PARENTS_MATING);
            double[][] crossover = crossover(parents, SOL_PER_POP - parents.length);
            double[][] mutation = mutation(crossover);

            for (int i = 0; i < parents.length; i++) {
                population[i] = parents[i];
            }

            for (int i = parents.length; i < population.length; i++) {
                population[i] = mutation[i - parents.length];
            }
        }

        double[] fitness = fitness(population);
        int maxIdx = IntStream.range(0, fitness.length).boxed().max(Comparator.comparingDouble(i -> fitness[i])).orElseThrow();
        System.out.println(Arrays.toString(population[maxIdx]) + " " + f(population[maxIdx]));
    }
}
