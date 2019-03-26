package com.company;

import com.company.Delta.Neuron;
import com.company.Delta.TrainingPattern;

import java.util.ArrayList;
import java.util.List;

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
        System.out.println("Expected output: " + pattern4.get(0).getExpectedOutput());
        System.out.println(neuron4.toString());
        System.out.println("Calculated output: " + neuron4.calculateOutput(pattern4.get(0).getInputs()));

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
        System.out.println("Expected output: " + pattern5.get(0).getExpectedOutput());
        System.out.println(neuron5.toString());
        System.out.println("Calculated output: " + neuron5.calculateOutput(pattern5.get(0).getInputs()));

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
        System.out.println("Expected output: " + pattern6.get(0).getExpectedOutput());
        System.out.println(neuron6.toString());
        System.out.println("Calculated output: " + neuron6.calculateOutput(pattern6.get(0).getInputs()));
    }
}
