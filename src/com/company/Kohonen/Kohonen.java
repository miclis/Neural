package com.company.Kohonen;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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

    /**
     * Initializes the neural network, sets initial random weights
     */
    public void init() {
        try {
            image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            System.out.println("Something went wrong during file loading...");
            return;
        }

        compressedFramesNeuronsId = new int[image.getHeight()/FRAME_SIZE][image.getWidth()/FRAME_SIZE];
        compressedFramesLength = new double[image.getHeight()/FRAME_SIZE][image.getWidth()/FRAME_SIZE];

        this.weights = new ArrayList<>();
        isUsed = new boolean[NEURON_COUNT];

        Random random = new Random();

        for (int i = 0; i < NEURON_COUNT; i++) {
            List<Double> weights = new ArrayList<>();

            for (int j = 0; j< FRAME_SIZE; ++j) {
                for (int k = 0; k < FRAME_SIZE; ++k) {
                    weights.add(random.nextDouble());
                }
            }
            weights = normalize(weights);
            this.weights.add(weights);
        }
    }

    /**
     * Compresses the image
     */
    public void compress() {
        // Starts from coordinates of the central pixel
        for (int y = FRAME_SIZE/2, idY = 0; y < image.getHeight() - FRAME_SIZE/2; y += FRAME_SIZE, ++idY) {
            for(int x = FRAME_SIZE/2, idX = 0; x < image.getWidth() - FRAME_SIZE/2; x += FRAME_SIZE, ++idX) {

                List<Double> weights = getFrameValues(x, y);
                compressedFramesLength[idX][idY] = getVectorLength(weights);
                weights = normalize(weights);

                int id = getBestNeuronId(weights);
                compressedFramesNeuronsId[idX][idY] = id;
            }
        }
    }

    /**
     * Decompresses the picture and saves it to the file
     * @return decompressed picture
     */
    public BufferedImage decompress() {
        BufferedImage decompressedImage = new BufferedImage(image.getHeight(), image.getWidth(), image.getType());
        // Starts from coordinates of the central pixel
        for (int y = FRAME_SIZE/2, idY = 0; y < image.getHeight() - FRAME_SIZE/2; y += FRAME_SIZE, ++idY) {
            for(int x = FRAME_SIZE/2, idX = 0; x < image.getWidth() - FRAME_SIZE/2; x += FRAME_SIZE, ++idX) {

                List<Double> weights = this.weights.get(compressedFramesNeuronsId[idX][idY]);

                for (int i = -FRAME_SIZE/2; i <= FRAME_SIZE/2; ++i) {
                    for (int j = -FRAME_SIZE/2, k = 0; j <= FRAME_SIZE/2; ++j, ++k) {
                        decompressedImage.getRaster()
                                .setPixel(
                                        x + i,
                                        y + j,
                                        new int[] {(int) (weights.get(k)*compressedFramesLength[idX][idY])});
                    }
                }
            }
        }
        try {
            ImageIO.write(decompressedImage, "png", new File("Decompressed.png"));
        } catch (IOException e) {
            System.out.println("Something went wrong during saving of the decompressed picture...");
        }
        return decompressedImage;
    }

    /**
     * Finds ID of best suited Neuron
     * @param weights list of normalized weights from the frame
     * @return best suited Neuron's ID
     */
    private int getBestNeuronId(List<Double> weights) {
        double bestValue = Double.POSITIVE_INFINITY;
        int bestId = -1;

        for (int i = 0; i < this.weights.size(); i++) {
            List<Double> neuronWeights = this.weights.get(i);

            double euclidean = euclidean(neuronWeights, weights);
            if (euclidean < bestValue) {
                bestValue = euclidean;
                bestId = i;
            }
        }
        return bestId;
    }

    /**
     * Method to get pixel values from inside of the frame
     * @param x X index
     * @param y Y index
     * @return list of pixel values
     */
    private List<Double> getFrameValues(int x, int y) {
        List<Double> frameValues = new ArrayList<>();

        for (int i = -FRAME_SIZE/2; i <= FRAME_SIZE; ++i) {
            for (int j = -FRAME_SIZE/2; j <= FRAME_SIZE; ++j) {

                double[] pixels = image.getRaster().getPixel(x + i, y + j, (double[]) null);
                frameValues.add(pixels[0]);
            }
        }
        return frameValues;
    }

    /**
     * Method to get vector's length
     * @param vector
     * @return
     */
    private static double getVectorLength(List<Double> vector) {
        return euclidean(vector, Collections.nCopies(vector.size(), 0.0));
    }

    /**
     * Calculates Euclidean distance
     * @param list1 first point
     * @param list2 second point
     * @return euclidean distance value
     */
    private static double euclidean(List<Double> list1, List<Double> list2) {
        assert (list1.size() == list2.size());

        double result = 0;
        for (int i = 0; i < list1.size(); i++) {
            result += Math.pow(list1.get(i) - list2.get(i), 2);
        }
        return Math.sqrt(result);
    }

    /**
     * Normalization function
     * @param valuesToNormalize list of values to be normalized
     * @return list of normalized values
     */
    private static List<Double> normalize(List<Double> valuesToNormalize) {
        double length = 0;
        List<Double> resultList = new ArrayList<>();

        for (Double d : valuesToNormalize) {
            length += d*d;
        }
        final double denominator = Math.sqrt(length);

        for (Double d : valuesToNormalize) {
            resultList.add(d/denominator);
        }

        return resultList;
    }
}
