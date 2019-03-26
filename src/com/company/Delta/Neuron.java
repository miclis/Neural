package com.company.Delta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
     * Calculates Neuron's output
     * @param inputs list of inputs
     * @return output of the Neuron
     */
    private double calculateOutput(List<Double> inputs) {
        double output = 0;

        for (int i = 0; i < inputCount; i++) {
            output += inputs.get(i)*weights.get(i);
        }
        return output;
    }

}
