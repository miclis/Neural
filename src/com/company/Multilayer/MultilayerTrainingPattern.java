package com.company.Multilayer;

import com.company.Delta.TrainingPattern;

import java.util.List;
import java.util.stream.Collectors;


public class MultilayerTrainingPattern extends TrainingPattern {

    private List<Double> expectedOutputs;

    /**
     * Multilayer Training Pattern constructor
     * @param inputs input values
     * @param expectedOutputs output expected output values
     */
    public MultilayerTrainingPattern(List<Double> inputs, List<Double> expectedOutputs) {
        this.inputCount = inputs.size();
        this.inputs = inputs;
        this.expectedOutputs = expectedOutputs;
    }

    public List<Double> getExpectedOutputs() {
        return expectedOutputs;
    }

    @Override
    public String toString() {
        return "Input pattern values:\n" +
                inputs.stream()
                        .map(Object::toString)
                        .map(i -> i.substring(0, Math.min(6, i.length())))
                        .collect(Collectors.joining(", ")) +
                "\nOutputs:\n" +
                expectedOutputs.stream()
                        .map(Object::toString)
                        .map(i -> i.substring(0, Math.min(6, i.length())))
                        .collect(Collectors.joining(", "));
    }
}
