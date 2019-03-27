package com.company.SGA;

import java.nio.ByteBuffer;
import java.util.function.DoubleFunction;

public class Subject {

    private byte[] genes;
    private double fitness = 0;

    /**
     * Subject constructor accepting a byte array of genes
     * @param genes gene byte array
     */
    public Subject(byte[] genes) {
        this.genes = genes;
    }

    /**
     * Subject constructor accepting a length of genome
     * Creates a random genome
     * @param DNALength genome's length
     */
    public Subject(int DNALength) {
        genes = new byte[DNALength];

        double value = 2*Math.random() + 0.5;
        ByteBuffer.wrap(genes).putDouble(value);
    }

    /**
     * Customized Fitness getter
     * @param function function accepting double parameter & resulting with double value
     * @return fitness
     */
    public double getFitness(DoubleFunction<Double> function) {

        if (fitness == 0) {
            double value = ByteBuffer.wrap(genes).getDouble();
            if (value >= 0.5 && value <= 2.5) {
                fitness = function.apply(value);
            }
        }
        return fitness;
    }

    /**
     * Standard getters & setters
     */
    public void setGenes(byte[] genes) {
        this.genes = genes;
    }

    public byte[] getGenes() {
        return genes;
    }
}
