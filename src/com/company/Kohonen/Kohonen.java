package com.company.Kohonen;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Kohonen {

    /**
     * Static parameters
     */
    private static final int FRAME_SIZE = 3;
    private static final int NEURON_COUNT = 10;
    private static final double STEP = 0.01;
    private static final int FRAMES_NUMBER = 150;
    private static final String[] PICTURES = {
            "res/kohonen/boat.png",
            "res/kohonen/house.png",
            "res/kohonen/lena.png",
            "res/kohonen/mandrill.png",
            "res/kohonen/parrot.png"
    };

    private String fileName;
    private boolean enableNormalization;
    private List<List<Double>> weights;
    private boolean[] isUsed;
    private String imagePath;
    private BufferedImage image;
    private int[][] compressedFramesNeuronsId;
    private double[][] compressedFramesLength;
    private byte[] originalByteArray;

    /**
     * Constructor setting the image path
     * @param imagePathIndex index of the image path from PICTURES table
     */
    public Kohonen(int imagePathIndex, boolean enableNormalization) {
        imagePath = PICTURES[imagePathIndex];
        String[] splitName = PICTURES[imagePathIndex].split("/");
        this.fileName = splitName[2];
        this.enableNormalization = enableNormalization;
    }

    /**
     * Initializes the neural network, sets initial random weights
     */
    public void init() {
        try {
            image = ImageIO.read(new File(imagePath));

            originalByteArray = toByteArrayAutoClosable(image);

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
    public BufferedImage finish() {
        BufferedImage finalImage = new BufferedImage(image.getHeight(), image.getWidth(), image.getType());
        // Starts from coordinates of the central pixel
        for (int y = FRAME_SIZE/2, idY = 0; y < image.getHeight() - FRAME_SIZE/2; y += FRAME_SIZE, ++idY) {
            for(int x = FRAME_SIZE/2, idX = 0; x < image.getWidth() - FRAME_SIZE/2; x += FRAME_SIZE, ++idX) {

                try {
                    List<Double> weights = this.weights.get(compressedFramesNeuronsId[idX][idY]);

                    for (int i = -FRAME_SIZE/2; i <= FRAME_SIZE/2; ++i) {
                        for (int j = -FRAME_SIZE/2, k = 0; j <= FRAME_SIZE/2; ++j, ++k) {
                            finalImage.getRaster()
                                    .setPixel(
                                            x + i,
                                            y + j,
                                            new int[] {(int) (weights.get(k)*compressedFramesLength[idX][idY])});
                        }
                    }
                } catch (IndexOutOfBoundsException ignored) {}
            }
        }
        try {
            ImageIO.write(finalImage, "png", new File("out/" + fileName));
        } catch (IOException e) {
            System.out.println("Something went wrong during saving of the picture...");
        }
        return finalImage;
    }

    /**
     * Teaches Kohonen neural network
     */
    public void teach() {

        for (int i = 0; i < FRAMES_NUMBER; ++i) {
            int y = randomNumberInt(FRAME_SIZE/2, image.getHeight() - FRAME_SIZE/2);
            int x = randomNumberInt(FRAME_SIZE/2, image.getWidth() - FRAME_SIZE/2);

            List<Double> frameValues = getFrameValues(x, y);
            frameValues = normalize(frameValues);

            int bestId = getBestNeuronId(frameValues);
            adjustNeuron(bestId, frameValues);
        }
        resetUnusedNeurons();
    }

    /**
     * Resets weights in unused neurons
     */
    private void resetUnusedNeurons() {

        for (int i = 0; i < weights.size(); i++) {
            if (!isUsed[i]) {
                for (int j = 0; j < weights.get(i).size(); ++j){
                    weights.get(i).set(j, 0.0);
                }
            }
        }
    }

    /**
     * Adjusts neuron's weights
     * @param id id of the neuron
     * @param neuronWeights neuron weights (frame values)
     */
    private void adjustNeuron(int id, List<Double> neuronWeights) {

        List<Double> weights = this.weights.get(id);
        isUsed[id] = true;
        assert (neuronWeights.size() == weights.size());

        for (int i = 0; i < weights.size(); i++) {
            double adjusted = weights.get(i) + STEP*(neuronWeights.get(i) - weights.get(i));
            weights.set(i, adjusted);
        }

        if (enableNormalization) this.weights.set(id, normalize(weights));
        else this.weights.set(id, weights);
    }

    /**
     * Calculates compression ratio
     * @param compressedImage image to compare original with
     * @return compression ratio
     */
    public double cr(BufferedImage compressedImage) {
        assert this.image.getHeight() == image.getHeight();
        assert this.image.getWidth() == image.getWidth();

        try {
            byte[] byteArrayCompressed = toByteArrayAutoClosable(compressedImage);

            return (double) originalByteArray.length/byteArrayCompressed.length;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private static byte[] toByteArrayAutoClosable(BufferedImage image) throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()){
            ImageIO.write(image, "png", out);
            return out.toByteArray();
        }
    }

    /**
     * Calculates peak signal-to-noise ratio
     * @param image decompressed image
     * @return double representing peak signal-to-noise ratio
     */
    public double psnr(BufferedImage image) {
        assert this.image.getHeight() == image.getHeight();
        assert this.image.getWidth() == image.getWidth();

        double mse = 0;

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                mse += Math.pow(this.image.getRaster().getPixel(x, y, (int[]) null)[0] -
                                    image.getRaster().getPixel(x, y, (int[]) null)[0], 2);
            }
        }
        mse /= this.image.getWidth()*this.image.getHeight();

        return 10*Math.log10(255.0*255.0/mse);
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

        for (int i = -FRAME_SIZE/2; i <= FRAME_SIZE/2; ++i) {
            for (int j = -FRAME_SIZE/2; j <= FRAME_SIZE/2; ++j) {

                double[] pixels = image.getRaster().getPixel(x + i, y + j, (double[]) null);
                frameValues.add(pixels[0]);
            }
        }
        return frameValues;
    }

    /**
     * Helper method to get random integer in specified range
     * @param min lower range boundary
     * @param max upper range boundary
     * @return random integer
     */
    private static int randomNumberInt(int min, int max) {
        Random random = new Random();
        return (int) Math.floor(random.nextDouble()*(max - min) + min);
    }

    /**
     * Method to get vector's length
     * @param vector provided vector
     * @return double representing vector's length
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
