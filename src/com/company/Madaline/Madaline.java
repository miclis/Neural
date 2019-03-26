package com.company.Madaline;

import com.company.Delta.Neuron;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Madaline {

    private List<Neuron> copyingNeurons;
    private int networkX;
    private int networkY;

    /**
     * Constructs MADALINE network (teaches) using provided file
     * @param inputFile input file
     */
    public Madaline(File inputFile) {

        FileReader reader;
        try {
            reader = new FileReader(inputFile);
            Scanner in = new Scanner(reader);

            copyingNeurons = new ArrayList<>();
            int outputNumber = in.nextInt();
            networkX = in.nextInt();
            networkY = in.nextInt();

            for (int i = 0; i < outputNumber; i++) {
                in.nextLine();
                List<Double> inputValues = new ArrayList<>();
                String name = in.next();
                int sum = 0;

                for(int j = 0; j < networkX; j++) {
                    char[] inputArray = in.next().toCharArray();

                    for (int k = 0; k < networkY; k++) {
                        if (inputArray[k] == '#') {
                            sum++;
                            inputValues.add(1.0);
                        } else {
                            inputValues.add(0.0);
                        }
                    }
                }
                double normalizer = sum == 0 ? 0 : 1/Math.sqrt(sum);

                inputValues = inputValues.stream().map(x -> x*normalizer).collect(Collectors.toList());
                copyingNeurons.add(new Neuron(inputValues, name));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Private method to get output Neurons based on a file
     * @param inputFile file to examine
     * @return list of output neurons
     */
    private List<Neuron> getOutputNeurons(File inputFile) {

        List<Neuron> outputNeurons = new ArrayList<>();
        FileReader reader;
        try {
            reader = new FileReader(inputFile);
            Scanner in = new Scanner(reader);

            int outputNumber = in.nextInt();

            for(int i = 0; i < outputNumber; i++) {
                in.nextLine();

                List<Double> outputValues = new ArrayList<>();
                int sum = 0;

                for (int j = 0; j < networkX; j++) {
                    char[] inputArray = in.next().toCharArray();

                    for ( int k = 0; k < networkY; k++) {
                        if (inputArray[k] == '#') {
                            sum++;
                            outputValues.add(1.0);
                        } else {
                            outputValues.add(0.0);
                        }
                    }
                }
                double normalizer = 1/Math.sqrt(sum);
                outputValues = outputValues.stream().map(x -> x*normalizer).collect(Collectors.toList());
                outputNeurons.add(new Neuron(outputValues, i + 1 + " test pattern"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputNeurons;
    }

    /**
     * Calculates and shows the result of testing process
     * @param inputFile file to examine
     */
    public void showOutput(File inputFile) {
        List<Neuron> outputNeurons = getOutputNeurons(inputFile);

        outputNeurons.forEach(oNeuron -> {
            double[] highest = {0.0};   // Array initilizer, because should final or effectively final
            String[] highestName = {""};
            System.out.println(oNeuron.getName() + ":");

            copyingNeurons.forEach(cNeuron -> {
                double output = cNeuron.calculateOutput(oNeuron.getWeights());
                System.out.println("Neuron " + cNeuron.getName() + " has given output of " + output);

                if(output > highest[0]) {
                    highest[0] = output;
                    highestName[0] = cNeuron.getName();
                }
            });
            System.out.println("=> Network has recognized letter " + highestName[0] +
                                ".\nLevel of confidence = " + highest[0]);
        });
    }
}
