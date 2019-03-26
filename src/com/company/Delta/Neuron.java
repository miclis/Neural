package com.company.Delta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Neuron {

    private int inputCount;
    private List<Double> weights;
    private String name;

    /**
     * Basic Neuron constructor that generates random weights
     * @param inputCount count of inputs - also weights count
     * @param minWeight lower boundary for random number generator
     * @param maxWeight upper boundary for random number generator
     */
    public Neuron(int inputCount, double minWeight, double maxWeight) {
        this.inputCount = inputCount;
        Random rand = new Random();
        weights = new ArrayList<>();

        if (minWeight > maxWeight) minWeight = maxWeight;

        for (int i = 0; i < inputCount; i++) {
            weights.add(rand.nextDouble()*(maxWeight - minWeight) + minWeight);
        }
        name = "";
    }

    /**
     * Custom Neuron constructor that accepts predefined weights and custom name to be recognizable
     * @param weights list of predefined weights
     * @param name custom name
     */
    public Neuron(List<Double> weights, String name) {
        this.weights = weights;
        this.inputCount = weights.size();
        this.name = name;
    }

    /**
     * Calculates Neuron's output based on list of inputs
     * @param inputs list of inputs
     * @return output of the Neuron
     * @throws IllegalArgumentException if input size does not match number of loaded weights
     */
    public double calculateOutput(List<Double> inputs) {
        if (inputs.size() != weights.size()) {
            throw new IllegalArgumentException("The number of inputs and weights does not match!");
        }

        double output = 0;

        for (int i = 0; i < inputCount; i++) {
            output += inputs.get(i)*weights.get(i);
        }
        return output;
    }

    /**
     * Teaches neuron on a single pattern
     * @param pattern pattern to teach on
     * @param epochs number of training epochs
     * @param step size of training step
     */
    public void teach(TrainingPattern pattern, int epochs, double step) {
        teach(Collections.singletonList(pattern), epochs, step);
    }

    /**
     * Teaches neuron on multiple patterns
     * @param patterns patterns to teach on
     * @param epochs number of training epochs per pattern
     * @param step size of training step per pattern pattern
     */
    public void teach(List<TrainingPattern> patterns, int epochs, double step) {

        for (int i = 0; i < epochs; i++) {
            for (TrainingPattern singlePattern : patterns) {

                double y = calculateOutput(singlePattern.getInputs());
                for (int j = 0; j < inputCount; j++) {
                    double newWeight = weights.get(j) +
                                        step*(singlePattern.getExpectedOutput() - y)*singlePattern.getInputs().get(j);
                    weights.set(j, newWeight);
                }
            }
        }
    }

    /**
     * Standard getters & setters
     */
    public void setWeights(List<Double> weights) {
        this.weights = weights;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Double> getWeights() {
        return weights;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Neuron size: " +
                inputCount +
                "\nWeights in neuron:\n" +
                weights.stream()
                        .map(Object::toString)
                        .map(w -> w.substring(0,Math.min(7, w.length())))
                        .collect(Collectors.joining(", "));
    }
}
