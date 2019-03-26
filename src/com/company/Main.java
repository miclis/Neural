package com.company;

import com.company.Delta.Neuron;
import com.company.Delta.TrainingPattern;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Network network = new Network();

        // DELTA RULE
        network.deltaSingle();
        network.deltaMultiple();
    }
}
