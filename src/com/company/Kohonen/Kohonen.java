package com.company.Kohonen;

import java.awt.image.BufferedImage;
import java.util.List;

public class Kohonen {

    /**
     * Static parameters
     */
    private static final int FRAME_SIZE = 3;
    private static final int NEURON_COUNT = 10;
    private static final double STEP = 0.01;
    private static final int EPOCHS = 150;
    private static final String[] PICTURES = {
            "res/kohonen/boat.png",
            "res/kohonen/house.png",
            "res/kohonen/lena.png",
            "res/kohonen/mandrill.png",
            "res/kohonen/parrot.png"
    };

    private List<List<Double>> weights;
    private boolean isUsed[];
    private String imagePath;
    private BufferedImage image;
    private int compressedFramesNeuronsId[][];
    private double compressedFramesLength[][];

    /**
     * Constructor setting the image path
     * @param imagePathIndex index of the image path from PICTURES table
     */
    public Kohonen(int imagePathIndex) {
        imagePath = PICTURES[imagePathIndex];
    }

}
