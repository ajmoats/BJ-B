import java.util.Scanner;
import java.io.*;

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
            loadData("validate.txt", validationX, validationY, N_VAL);

            // Train the perceptron
            PrintWriter weightWriter = new PrintWriter(new File("weights.txt"));

            for (int i = 0; i < N_TRAIN; i++) {
                int prediction = predictStatus(weights, trainingX[i]);

                // Update the weights if the prediction is incorrect
                if (prediction != trainingY[i]) {
                    double[] update = scalarProduct(trainingY[i], trainingX[i]);
                    weights = arraySum(weights, update);
                }
                writeWeightsToFile(weightWriter, weights);
            }
            weightWriter.close();


            // Validate the model
            PrintWriter predictionWriter = new PrintWriter(new File("predictions.txt"));
            int correctCount = 0;

            for (int i = 0; i < N_VAL; i++) {
                int prediction = predictStatus(weights, validationX[i]);
                recordPrediction(predictionWriter, validationX[i], prediction);

                if (prediction == validationY[i]) {
                    correctCount++;
                }

                if (prediction == -1){
                    displayForgery(validationX[i]);
                }
            }

            predictionWriter.close();

            // Output the statistics
            displayModelStats(correctCount, N_VAL);

        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found. Ensure the training and validation files are in the directory.");
            e.printStackTrace();
        }
    }


    // Helper methods from the Warm-Up
    // File Processing Helper Functions:
    // 1. Loading Data from a File, X-features and Y-labels
    public static void loadData(String filename, double[][] X, int[] Y, int n){
        File inputFile = new File(filename);
        Scanner fileIn = new Scanner(new File(filename));

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < M; j++) {
                X[i][j] = fileIn.nextDouble();
            }

            String status = fileIn.next();

            if (status.equalsIgnoreCase("authentic")){
                Y[i] = 1; // Real bills represented as 1
            } else{
                Y[i] = -1; // Fake bills represented as -1
            }
        }
        fileIn.close();
    }

    // 2. Weight Writing
    public static void writeWeightsToFile(PrintWriter writer, double[] weights){
        for (int i = 0; i < M; i++) {
            writer.printf("%10.5f", weights[i]); // Required formatting of 5 digits after the decimal point
        }
        writer.println(); // Move to the next line after writing all weights
    }


    // 3. Recording Predictions
    public static void recordPrediction(PrintWriter writer, double[] features, int prediction){
        for (int i = 0; i < M; i++) {
            writer.printf("%10.5f", features[i]); // Required formatting of 5 digits after the decimal point
        }
        writer.printf("%10.5f\n", prediction); // Write the prediction at the end of the line and newline
    }

    // 4. Forgery Features Display
    public static void displayForgery(double[] features){
        System.out.println("Forgery Features:");
        for (int i = 0; i < M; i++) {
            System.out.printf("%10.4f", features[i]); // Required formatting of 4 digits after the decimal point
        }
        System.out.println(); // Move to the next line after displaying all features
    }


    // 5. Display of Model Stats
    public static void displayModelStats(int correct, int total){
        int incorrect = total - correct;
        double accuracy = (double) correct / total * 100.0;
        System.out.println("Model Performance:");
        System.out.printf("Correct Predictions: %d\n", correct);
        System.out.printf("Incorrect Predictions: %d\n", incorrect);
        System.out.printf("Accuracy: %.1f%%\n", accuracy);
    }


    // Linear Algebra Helper Functions:
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
