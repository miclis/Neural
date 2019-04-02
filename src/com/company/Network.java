package com.company;

import com.company.Delta.Neuron;
import com.company.Delta.TrainingPattern;
import com.company.Kohonen.Kohonen;
import com.company.Madaline.Madaline;
import com.company.Multilayer.Multilayer;
import com.company.Multilayer.MultilayerTrainingPattern;
import com.company.SGA.GeneticAlgorithm;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class Network {

    /**
     * Tests & shows the operation of single Neuron using single training patterns(Delta Rule)
     */
    void deltaSingle() {
        System.out.println("=======Delta Rule - Single=======");
        // Test 1
        System.out.println("=======Test 1=======");
        List<Double> inputs1 = new ArrayList<>();
        inputs1.add(3.43776);
        inputs1.add(4.51051);
        double expectedOutput1 = 0.71427;
        TrainingPattern pattern1 = new TrainingPattern(inputs1, expectedOutput1);
        Neuron neuron1 = new Neuron(pattern1.getInputCount(), 0, 1);
        neuron1.teach(pattern1, 50, 0.01);
        System.out.println(pattern1.toString());
        System.out.println(neuron1.toString());
        System.out.println("Calculated output: " + neuron1.calculateOutput(inputs1));

        // Test 2
        System.out.println("=======Test 2=======");
        List<Double> inputs2 = new ArrayList<>();
        inputs2.add(-7.40160);
        inputs2.add(4.66901);
        double expectedOutput2 = -0.12471;
        TrainingPattern pattern2 = new TrainingPattern(inputs2, expectedOutput2);
        Neuron neuron2 = new Neuron(pattern2.getInputCount(), 0, 3);
        neuron2.teach(pattern2, 50, 0.01);
        System.out.println(pattern2.toString());
        System.out.println(neuron2.toString());
        System.out.println("Calculated output: " + neuron2.calculateOutput(inputs2));

        // Test 3
        System.out.println("=======Test 3=======");
        List<Double> inputs3 = new ArrayList<>();
        inputs3.add(5.03800);
        inputs3.add(-9.59690);
        inputs3.add(5.60993);
        inputs3.add(-1.18243);
        inputs3.add(-0.95779);
        inputs3.add(-7.46030);
        inputs3.add(-8.23987);
        inputs3.add(-6.96355);
        inputs3.add(6.05941);
        inputs3.add(1.17025);
        double expectedOutput3 = 111.31914;
        TrainingPattern pattern3 = new TrainingPattern(inputs3, expectedOutput3);
        Neuron neuron3 = new Neuron(pattern3.getInputCount(), 0, 3);
        neuron3.teach(pattern3, 100, 0.001);
        System.out.println(pattern3.toString());
        System.out.println(neuron3.toString());
        System.out.println("Calculated output: " + neuron3.calculateOutput(inputs3));
    }

    /**
     * Tests & shows the operation of single Neuron using multiple training patterns(Delta Rule)
     */
    void deltaMultiple() {
        System.out.println("=======Delta Rule - Multiple=======");
        // Test 1
        System.out.println("=======Test 1=======");
        List<Double> inputs1 = new ArrayList<>();
        inputs1.add(3.43776);
        inputs1.add(4.51051);
        double expectedOutput1 = 0.71427;
        List<TrainingPattern> trainingPatterns1 = new ArrayList<>();
        TrainingPattern pattern1 = new TrainingPattern(inputs1, expectedOutput1);
        List<Double> inputs2 = new ArrayList<>();
        inputs2.add(-7.40160);
        inputs2.add(4.66901);
        double expectedOutput2 = -0.12471;
        TrainingPattern pattern2 = new TrainingPattern(inputs2, expectedOutput2);
        trainingPatterns1.add(pattern1);
        trainingPatterns1.add(pattern2);
        Neuron neuron1 = new Neuron(pattern1.getInputCount(), 0, 1);
        neuron1.teach(trainingPatterns1, 100, 0.01);
        System.out.println(pattern1.toString());
        System.out.println(pattern2.toString());
        System.out.println(neuron1.toString());
        System.out.println("Calculated output: " + neuron1.calculateOutput(inputs1));
        System.out.println("Calculated output: " + neuron1.calculateOutput(inputs2));

        // Test 2
        System.out.println("=======Test 2=======");
        List<Double> inputs3 = new ArrayList<>();
        inputs3.add(5.03800);
        inputs3.add(-9.59690);
        inputs3.add(5.60993);
        inputs3.add(-1.18243);
        inputs3.add(-0.95779);
        inputs3.add(-7.46030);
        inputs3.add(-8.23987);
        inputs3.add(-6.96355);
        inputs3.add(6.05941);
        inputs3.add(1.17025);
        double expectedOutput3 = 111.31914;
        List<TrainingPattern> trainingPatterns2 = new ArrayList<>();
        TrainingPattern pattern3 = new TrainingPattern(inputs3, expectedOutput3);
        List<Double> inputs4 = new ArrayList<>();
        inputs4.add(2.22802);
        inputs4.add(1.90442);
        inputs4.add(-8.88582);
        inputs4.add(-6.29559);
        inputs4.add(6.71617);
        inputs4.add(1.28940);
        inputs4.add(-9.24163);
        inputs4.add(0.75831);
        inputs4.add(-7.83095);
        inputs4.add(-0.66526);
        double expectedOutput4 = 362.62635;
        TrainingPattern pattern4 = new TrainingPattern(inputs4, expectedOutput4);
        trainingPatterns2.add(pattern3);
        trainingPatterns2.add(pattern4);
        Neuron neuron2 = new Neuron(pattern3.getInputCount(), 0, 1);
        neuron2.teach(trainingPatterns2, 120, 0.001);
        System.out.println(pattern3.toString());
        System.out.println(pattern4.toString());
        System.out.println(neuron2.toString());
        System.out.println("Calculated output: " + neuron2.calculateOutput(inputs3));
        System.out.println("Calculated output: " + neuron2.calculateOutput(inputs4));
    }

    /**
     * Writes to the screen all necessary data for Task 1
     */
    void deltaTask1() {
        System.out.println("=======Delta Rule - Single=======");
        // Test 1
        System.out.println("=======Test 1=======");
        int numberOfInputs = 4;
        int numberOfTrainingEpochs = 50;
        double trainingStep = 0.01;
        double minWeight = 0;
        double maxWeight = 1;
        double minInput = -1;
        double maxInput = 10;

        List<TrainingPattern> pattern1 = TrainingPattern.generatePatterns(1, numberOfInputs, minInput, maxInput);
        Neuron neuron1 = new Neuron(numberOfInputs, minWeight, maxWeight);
        neuron1.teach(pattern1, numberOfTrainingEpochs, trainingStep);
        System.out.println(pattern1.get(0).toString());
        System.out.println(neuron1.toString());
        System.out.println("Calculated output: " + neuron1.calculateOutput(pattern1.get(0).getInputs()));

        // Test 2
        System.out.println("=======Test 2=======");
        numberOfInputs = 4;
        numberOfTrainingEpochs = 50;
        trainingStep = 0.01;
        minWeight = 0;
        maxWeight = 2;
        minInput = -10;
        maxInput = 10;

        List<TrainingPattern> pattern2 = TrainingPattern.generatePatterns(1, numberOfInputs, minInput, maxInput);
        Neuron neuron2 = new Neuron(numberOfInputs, minWeight, maxWeight);
        neuron2.teach(pattern2.get(0), numberOfTrainingEpochs, trainingStep);
        System.out.println(pattern2.get(0).toString());
        System.out.println(neuron2.toString());
        System.out.println("Calculated output: " + neuron2.calculateOutput(pattern2.get(0).getInputs()));

        // Test 3
        System.out.println("=======Test 3=======");
        numberOfInputs = 4;
        numberOfTrainingEpochs = 70;
        trainingStep = 0.01;
        minWeight = 0;
        maxWeight = 2;
        minInput = -10;
        maxInput = 10;

        List<TrainingPattern> pattern3 = TrainingPattern.generatePatterns(1, numberOfInputs, minInput, maxInput);
        Neuron neuron3 = new Neuron(numberOfInputs, minWeight, maxWeight);
        neuron3.teach(pattern3.get(0), numberOfTrainingEpochs, trainingStep);
        System.out.println(pattern3.get(0).toString());
        System.out.println(neuron3.toString());
        System.out.println("Calculated output: " + neuron3.calculateOutput(pattern3.get(0).getInputs()));

        System.out.println("=======Delta Rule - Multiple=======");
        // Test 4
        System.out.println("=======Test 4=======");
        numberOfInputs = 4;
        numberOfTrainingEpochs = 100;
        trainingStep = 0.01;
        minWeight = 0;
        maxWeight = 2;
        minInput = 0;
        maxInput = 10;
        int numberOfPatterns = 2;   // (a) numberOfPatterns < numberOfInputs

        List<TrainingPattern> pattern4 = TrainingPattern.generatePatterns(numberOfPatterns, numberOfInputs, minInput, maxInput);
        Neuron neuron4 = new Neuron(numberOfInputs, minWeight, maxWeight);
        neuron4.teach(pattern4, numberOfTrainingEpochs, trainingStep);
        System.out.println(neuron4.toString());

        for (TrainingPattern pat : pattern4) {
            System.out.println("Expected output: " + pat.getExpectedOutput());
            System.out.println("Calculated output: " + neuron4.calculateOutput(pat.getInputs()));
        }

        // Test 5
        System.out.println("=======Test 5=======");
        numberOfInputs = 4;
        numberOfTrainingEpochs = 100;
        trainingStep = 0.01;
        minWeight = 0;
        maxWeight = 2;
        minInput = 0;
        maxInput = 10;
        numberOfPatterns = 4;   // (a) numberOfPatterns == numberOfInputs

        List<TrainingPattern> pattern5 = TrainingPattern.generatePatterns(numberOfPatterns, numberOfInputs, minInput, maxInput);
        Neuron neuron5 = new Neuron(numberOfInputs, minWeight, maxWeight);
        neuron5.teach(pattern5, numberOfTrainingEpochs, trainingStep);
        System.out.println(neuron5.toString());

        for (TrainingPattern pat : pattern5) {
            System.out.println("Expected output: " + pat.getExpectedOutput());
            System.out.println("Calculated output: " + neuron5.calculateOutput(pat.getInputs()));
        }

        // Test 6
        System.out.println("=======Test 6=======");
        numberOfInputs = 4;
        numberOfTrainingEpochs = 100;
        trainingStep = 0.01;
        minWeight = 0;
        maxWeight = 2;
        minInput = 0;
        maxInput = 10;
        numberOfPatterns = 6;   // (a) numberOfPatterns > numberOfInputs

        List<TrainingPattern> pattern6 = TrainingPattern.generatePatterns(numberOfPatterns, numberOfInputs, minInput, maxInput);
        Neuron neuron6 = new Neuron(numberOfInputs, minWeight, maxWeight);
        neuron6.teach(pattern6, numberOfTrainingEpochs, trainingStep);
        System.out.println(neuron6.toString());

        for (TrainingPattern pat : pattern6) {
            System.out.println("Expected output: " + pat.getExpectedOutput());
            System.out.println("Calculated output: " + neuron6.calculateOutput(pat.getInputs()));
        }
    }

    void madalineTask2() {
        Madaline madaline = new Madaline(new File("res/constructionFile.txt"));
        madaline.showOutput(new File("res/testFile.txt"));
    }

    void multilayerPerception() {
        Multilayer multilayer1 = new Multilayer(2,4,-0.5,0.5, false);
        Multilayer multilayer2 = new Multilayer(2,4,-0.5,0.5, true);

        List<Double> inputs1 = new ArrayList<>(Arrays.asList(1.0,0.0,0.0,0.0));
        List<Double> outputs1 = new ArrayList<>(Arrays.asList(1.0,0.0,0.0,0.0));

        List<Double> inputs2 = new ArrayList<>(Arrays.asList(0.0,1.0,0.0,0.0));
        List<Double> outputs2 = new ArrayList<>(Arrays.asList(0.0,1.0,0.0,0.0));

        List<Double> inputs3 = new ArrayList<>(Arrays.asList(0.0,0.0,1.0,0.0));
        List<Double> outputs3 = new ArrayList<>(Arrays.asList(0.0,0.0,1.0,0.0));

        List<Double> inputs4 = new ArrayList<>(Arrays.asList(0.0,0.0,0.0,1.0));
        List<Double> outputs4 = new ArrayList<>( Arrays.asList(0.0,0.0,0.0,1.0));

        List<MultilayerTrainingPattern> trainingPatterns = new ArrayList<>();
        MultilayerTrainingPattern multilayerTrainingPattern1 = new MultilayerTrainingPattern(inputs1,outputs1);
        MultilayerTrainingPattern multilayerTrainingPattern2 = new MultilayerTrainingPattern(inputs2,outputs2);
        MultilayerTrainingPattern multilayerTrainingPattern3 = new MultilayerTrainingPattern(inputs3,outputs3);
        MultilayerTrainingPattern multilayerTrainingPattern4 = new MultilayerTrainingPattern(inputs4,outputs4);
        trainingPatterns.add(multilayerTrainingPattern1);
        trainingPatterns.add(multilayerTrainingPattern2);
        trainingPatterns.add(multilayerTrainingPattern3);
        trainingPatterns.add(multilayerTrainingPattern4);

        multilayer1.teach(trainingPatterns, 50000, 0.005);
        multilayer2.teach(trainingPatterns, 30000, 0.03);

        System.out.println("BAIS off:");
        System.out.println("Hidden neurons:");
        System.out.println(multilayer1.getHiddenNeurons());
        System.out.println("===============");
        System.out.println("Output neurons:");
        System.out.println(multilayer1.getOutputNeurons());
        System.out.println("===============");

        System.out.println("Result of first input: ");
        System.out.println(multilayer1.calculateOutputs(inputs1));
        System.out.println("Result of second input: ");
        System.out.println(multilayer1.calculateOutputs(inputs2));
        System.out.println("Result of third input: ");
        System.out.println(multilayer1.calculateOutputs(inputs3));
        System.out.println("Result of fourth input: ");
        System.out.println(multilayer1.calculateOutputs(inputs4));

        System.out.println("\nBAIS active:");
        System.out.println("Hidden neurons:");
        System.out.println(multilayer2.getHiddenNeurons());
        System.out.println("===============");
        System.out.println("Output neurons:");
        System.out.println(multilayer2.getOutputNeurons());
        System.out.println("===============");

        System.out.println("Result of first input: ");
        System.out.println(multilayer2.calculateOutputs(inputs1));
        System.out.println("Result of second input: ");
        System.out.println(multilayer2.calculateOutputs(inputs2));
        System.out.println("Result of third input: ");
        System.out.println(multilayer2.calculateOutputs(inputs3));
        System.out.println("Result of fourth input: ");
        System.out.println(multilayer2.calculateOutputs(inputs4));

        // Showing hidden layer neurons after the training process for each of the training patterns
        Multilayer multilayer3 = new Multilayer(2,4,-0.5,0.5, true);

        multilayer3.teach(trainingPatterns, 30000, 0.03);

        System.out.println("=======================================");
        System.out.println("Showing hidden layers:");
        System.out.println("Pattern 1");
        multilayer3.calculateOutputs(inputs1);
        System.out.println(multilayer3.lastInputs);

        System.out.println("Pattern 2");
        multilayer3.calculateOutputs(inputs2);
        System.out.println(multilayer3.lastInputs);

        System.out.println("Pattern 3");
        multilayer3.calculateOutputs(inputs3);
        System.out.println(multilayer3.lastInputs);

        System.out.println("Pattern 4");
        multilayer3.calculateOutputs(inputs4);
        System.out.println(multilayer3.lastInputs);




        /**
         * THOUGHTS
         * BAIS off - two patterns can be trained
         * BAIS active - all 4 patterns trained
         */
    }

    void simpleGeneticAlgorithm() {
        List<Double> resultsList = GeneticAlgorithm.GeneticAlgorithm(20, 20, 0.1, 0.1, GeneticAlgorithm.function);

        List<Double> afterCalculationList = resultsList.stream().map(value -> GeneticAlgorithm.function.apply(value)).collect(Collectors.toList());

        System.out.println("Initial values: ");
        for (Double d : resultsList) {
            System.out.println(d);
        }
        System.out.println("=======================");
        System.out.println("Result values: ");
        for (Double d : afterCalculationList) {
            System.out.println(d);
        }
        System.out.println("=======================");
        System.out.println("Biggest initial value: " + resultsList.stream().max(Double::compareTo).get());
        System.out.println("Biggest result value: " + afterCalculationList.stream().max(Double::compareTo).get());
    }

    void kohonen(){

        Kohonen kohonen = new Kohonen(0, true);
        kohonen.init();
        kohonen.teach();
        kohonen.compress();

        BufferedImage imageDecompressed = kohonen.decompress();
        System.out.println("Peak signal-to-noise ratio equals: " + kohonen.psnr(imageDecompressed) + " dB");
    }
}
