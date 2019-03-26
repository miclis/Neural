package com.company.Delta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class TrainingPattern {

    private List<Double> inputs;
    private double expectedOutput;
    private int inputCount;

    /**
     * Training Pattern constructor
     * @param inputs input values
     * @param expectedOutput expected output value
     */
    public TrainingPattern(List<Double> inputs, double expectedOutput) {
        this.inputs = inputs;
        this.inputCount = inputs.size();
        this.expectedOutput = expectedOutput;
    }

    /**
     * Standard getters
     */
    public List<Double> getInputs() {
        return inputs;
    }

    public double getExpectedOutput() {
        return expectedOutput;
    }

    public int getInputCount() {
        return inputCount;
    }

    /**
     * Generates random Training Patterns
     * @param numberOfPatterns number of Training Patterns to be generated
     * @param inputCountOfPattern count of inputs in a Training Pattern
     * @param minValue lower boundary for random number generator
     * @param maxValue upper boundary for random number generator
     * @return List of generated Training Patterns
     */
    public static List<TrainingPattern> generatePatterns(int numberOfPatterns, int inputCountOfPattern, double minValue,
                                                         double maxValue) {
        Random rand = new Random();
        List<TrainingPattern> patterns = new ArrayList<>();

        if (minValue > maxValue) minValue = maxValue;

        for (int i = 0; i < numberOfPatterns; i++) {
            List<Double> valuesList = new ArrayList<>();
            for (int j = 0; j < inputCountOfPattern; j++) {
                valuesList.add(rand.nextDouble()*(maxValue - minValue) + minValue);
            }
            double randomExpectedOutput = rand.nextDouble()*(maxValue - minValue) + minValue;

            patterns.add(new TrainingPattern(valuesList, randomExpectedOutput));
        }

        return patterns;
    }

    @Override
    public String toString() {
        return "Input pattern values:\n" +
                inputs.stream()
                        .map(Object::toString)
                        .map(i -> i.substring(0, Math.min(6, i.length())))
                        .collect(Collectors.joining(", ")) +
                "\nExpected output: " + expectedOutput;
    }
}
