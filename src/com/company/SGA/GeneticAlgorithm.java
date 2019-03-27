package com.company.SGA;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.DoubleFunction;

public final class GeneticAlgorithm {

    public static int GENOM_LENGTH = 8;
    public static DoubleFunction<Double> function = (value -> (Math.exp(value)*Math.sin(10*value*Math.PI) + 1)/value +5);

    /**
     * Runs Simple Genetic Algorithm
     * @param M
     * @param N
     * @param Pc
     * @param Pm
     * @param function
     * @return
     */
    public static List<Double> GeneticAlgorithm(int M, int N, double Pc, double Pm, DoubleFunction<Double> function) {
        List<Double> resultsList = new ArrayList<>();

        for (int i = 0; i< N; i++) {
            resultsList.add(createPopulation(M, Pc, Pm, function));
        }
        return resultsList;
    }

    /**
     * Creates population based on input parameters & function
     * @param M
     * @param Pc
     * @param Pm
     * @param function
     * @return
     */
    private static double createPopulation(int M, double Pc, double Pm, DoubleFunction<Double> function) {
        List<Subject> population = new ArrayList<>();

        for (int i = 0; i < M; i++) {
            population.add(new Subject(GENOM_LENGTH));
        }

        for (int i = 0; i < 100; i++) {
            population = createGeneration(population, Pc, Pm, function);
        }

        return ByteBuffer.wrap(chooseBest(population, function).getGenes()).getDouble();
    }

    private static List<Subject> createGeneration(List<Subject> population, double Pc, double Pm,
                                                  DoubleFunction<Double> function) {

        population.forEach(subject -> subject.getFitness(function));

        List<Subject> newPopulation = new ArrayList<>();
        population.forEach(subject -> {

            double overallFF = population.stream().mapToDouble(sSubject -> sSubject.getFitness(function)).sum(); // Consider putting outside of forEach

            double r = Math.random();
            Subject candidate1 = chooseCandidate(population, overallFF, function);


        });
    }

    /**
     * Chooses candidate from population based on function & overalFF
     * @param population population to choose from
     * @param overalFF
     * @param function function determining principles of choice
     * @return chosen Subject
     */
    private static Subject chooseCandidate(List<Subject> population, double overalFF, DoubleFunction<Double> function) {

        double randomizer = Math.random();

//        MutableDouble sum = new MutableDouble(0);
    }

    /**
     * Chooses best candidate from population based on function
     * @param population population to choose from
     * @param function function determining principles of choice
     * @return best Subject
     */
    private static Subject chooseBest(List<Subject> population, DoubleFunction<Double> function) {
        return population.stream().max(Comparator.comparingDouble(value -> value.getFitness(function))).get();
    }
}
