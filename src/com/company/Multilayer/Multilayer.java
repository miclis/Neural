package com.company.Multilayer;

import com.company.Delta.Neuron;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Multilayer {

    private List<Neuron> outputNeurons;
    private List<Neuron> hiddenNeurons;
    private boolean BIASactive;
    public List<Double> lastInputs;

    /**
     * Basic constructor for a multilayer perception neural network
     * @param hiddenLayerSize number of hidden neurons
     * @param inputOutputLayerSize number of input and output neurons
     * @param minWeight lower boundary for random number generator
     * @param maxWeight upper boundary for random number generator
     * @param BIASactive flag if BIAS neuron should be added
     */
    public Multilayer(int hiddenLayerSize, int inputOutputLayerSize, double minWeight, double maxWeight,
                      boolean BIASactive) {

        this.BIASactive = BIASactive;

        int increaseSize = BIASactive ? 1 : 0;
        this.hiddenNeurons = new ArrayList<>();
        for (int i = 0; i < hiddenLayerSize; i++) {
            hiddenNeurons.add(new Neuron(inputOutputLayerSize + increaseSize, minWeight, maxWeight));
        }

        this.outputNeurons = new ArrayList<>();
        for (int i = 0; i < inputOutputLayerSize; i++) {
            outputNeurons.add(new Neuron(hiddenLayerSize + increaseSize, minWeight, maxWeight));
        }
    }

    /**
     * Calculates output results for provided inputs
     * @param inputs list of input values
     * @return list of output values
     */
    public List<Double> calculateOutputs(List<Double> inputs) {
        List<Double> modifiedInputs = new ArrayList<>(inputs);

        if (BIASactive) modifiedInputs.add(1.0);

        List<Double> v = getResults(inputs, hiddenNeurons);
        List<Double> modifiedHiddenOutputs = new ArrayList<>(v);
        lastInputs = new ArrayList<>(v);

        if (BIASactive) {
            modifiedHiddenOutputs.add(1.0);
        }
        return getResults(modifiedHiddenOutputs, outputNeurons);
    }

    /**
     * Teaches Multilayer Perception neural network on single provided patterns
     * @param pattern pattern to learn from
     * @param epochs number of training epochs
     * @param step training step
     */
    public void teach(MultilayerTrainingPattern pattern, int epochs, double step) {
        for (int i = 0; i < epochs; i++) {
            teach(pattern, step);
        }
    }

    /**
     * Teaches Multilayer Perception neural network on multiple provided patterns
     * @param patterns list of training patterns
     * @param epochs number of training epochs
     * @param step training step
     */
    public void teach(List<MultilayerTrainingPattern> patterns, int epochs, double step) {
        for (int i = 0; i < epochs; i++) {
            for (MultilayerTrainingPattern pattern : patterns) {
                teach(pattern, step);
            }
        }
    }

    /**
     * Teaches Multilayer Perception neural network on single provided pattern
     * @param pattern pattern to learn from
     * @param step training step
     */
    public void teach(MultilayerTrainingPattern pattern, double step) {

        // Input layer
        List<Double> inputs = new ArrayList<>(pattern.getInputs());

        if (BIASactive) inputs.add(1.0);    // Adds BIAS Neuron input equal to 1

        // Applies inputs from input layer to the hidden layer; Prepares inputs for output layer
        List<Double> v1 = getResults(inputs, hiddenNeurons);

        if (BIASactive) v1.add(1.0);    // Adds BIAS Neuron input equal to 1

        // Applies inputs to the output layer
        List<Double> v2 = getResults(v1, outputNeurons);

        // Calculates signal error for output layer
        List<Double> signalErrorsOutput = IntStream.range(0, v2.size()).mapToObj(i ->
                (pattern.getExpectedOutputs()
                        .get(i) - v2.get(i))*sigmoidal(v2.get(i)
                )
        ).collect(Collectors.toList());

        // Calculates signal error for hidden layer
        List<Double> signalErrorsHidden = IntStream.range(0, v1.size()).mapToObj(i ->
                sigmoidal(v1.get(i))*(IntStream.range(0, v2.size()).mapToDouble(j ->
                        outputNeurons.get(j)
                                .getWeights()
                                .get(i)*signalErrorsOutput.get(j)
                ).sum())
        ).collect(Collectors.toList());

        outputNeurons = adjustWeights(outputNeurons, signalErrorsOutput, v1, step);
        hiddenNeurons = adjustWeights(hiddenNeurons, signalErrorsHidden, inputs, step);
    }

    /**
     * Adjusts weights of neurons
     * @param neuronList neurons to have their weights adjusted
     * @param errorList corresponding error values
     * @param previousInputs values of previous layer
     * @param step training step
     * @return list of neurons with adjusted weights
     */
    private List<Neuron> adjustWeights(List<Neuron> neuronList, List<Double> errorList, List<Double> previousInputs, double step) {

        List<Neuron> resultList = new ArrayList<>();

        IntStream.range(0, neuronList.size()).forEach(i -> {
            Neuron neuron = neuronList.get(i);
            List<Double> newWeights = new ArrayList<>();

            IntStream.range(0, neuron.getWeights().size()).forEach(j -> {
                Double weight = neuron.getWeights().get(j);
                newWeights.add(weight + step*errorList.get(i)*previousInputs.get(j));
            });

            resultList.add(new Neuron(newWeights, ""));
        });
        return resultList;
    }

    /**
     * Applies inputs to neurons ang gets results
     * @param inputs input values to be applied on neurons
     * @param neurons neurons to which inputs will be applied
     * @return results
     */
    private List<Double> getResults(List<Double> inputs, List<Neuron> neurons) {

        List<Double> resultsList = new ArrayList<>();
        int inputCount = inputs.size();

        neurons.forEach(neuron -> {
            double sum = 0;

            for (int i = 0; i < inputCount; i++) {
                sum += inputs.get(i)*neuron.getWeights().get(i);
            }
            resultsList.add(1/(1 + Math.exp(0 - sum)));
        });

        return resultsList;
    }

    /**
     * Calculates sigmoidal value
     * @param value base value
     * @return sigmoidal
     */
    private static double sigmoidal(double value) {
        double e = Math.exp(value);
        return Math.pow(e/(1 + e), 2);
    }

    /**
     * Standard getters
     */
    public List<Neuron> getHiddenNeurons() {
        return hiddenNeurons;
    }

    public List<Neuron> getOutputNeurons() {
        return outputNeurons;
    }
}
