import java.util.Scanner;
import java.util.Random;

/** 
 * Project A, Perceptron for EN.500.132 Bootcamp: Java.
 * @author Alexis Moats; JHED: amoats3
 * The purpose of this program is to simulate how a perceptron can
 * learn to classify money into two categories: "real" and "fake". 
 */

public class ProjectB {
    
    private static final int M = 4; // num of features
    private static final int N_TRAIN = 1029; // num of training examples
    private static final int N_VAL = 343; // num of validation examples

    public static void main(String[] args) {
        // Read in training and validation data
        double[][] trainingX = new double[N_TRAIN][M];
        int[] trainingY = new int[N_TRAIN];
        double[][] validationX = new double[N_VAL][M];
        int[] validationY = new int[N_VAL];
        double[] weights = new double[M];

        // Use try/catch to handle file read/write exceptions
        try {
            // Load in the data
            loadData("training.txt", trainingX, trainingY, N_TRAIN);
            loadData("validation.txt", validationX, validationY, N_VAL);

            // Train the perceptron


            // Validate the model


            // Output the statistics
            displayModelStats(correctCount, N_VAL);

        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found. Ensure the training and validation files are in the directory.");
            e.printStackTrace();
        }
    }


    // Helper methods from the Warm-Up
    // File Processing Helper Functions
    // 1. Loading Data from a File
    public static void loadData(String filename, double[][] X, int[] Y, int n)

    // 2. Weight Writing
    public static void writeWeightsToFile(PrintWriter writer, double[] weights)


    // 3. Recording Predictions
    public static void recordPrediction(PrintWriter writer, double[] features, int prediction)

    // 4. Forgery Features Display
    public static void displayForgery(double[] features)


    // 5. Display of Model Stats
    public static void displayModelStats(int correct, int total)

    // Linear Algebra Helper Functions
    // 1. Dot Product
    public static double dotProduct(double[] a, double[] b){
        double result = 0;
        for (int i = 0; i < M; i++) {
            result += a[i] * b[i];
        }
        return result;
    }

    // 2. Scalar Product
    public static double[] scalarProduct(double k, double[] a){
        double[] result = new double[M];
        for (int i = 0; i < M; i++) {
            result[i] = k * a[i];
        }
        return result;
    }

    // 3. Array Summation
    public static double[] arraySum(double[] a, double[] b){
        double[] result = new double[M];
        for (int i = 0; i < M; i++) {
            result[i] = a[i] + b[i];
        }
        return result;
    }

    // 4. Array Assignment Prediction
    public static int predictStatus(double[] weights, double[] features){
        double score = dotProduct(weights, features);
        return (score >= 0) ? 1 : -1; // Return 1 for "real", -1 for "fake"
    }
