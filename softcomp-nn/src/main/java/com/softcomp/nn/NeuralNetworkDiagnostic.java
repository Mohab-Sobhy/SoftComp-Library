package com.softcomp.nn;

import com.softcomp.nn.activationfunction.Sigmoid;
import com.softcomp.nn.activationfunction.ReLU;
import com.softcomp.nn.initializers.XavierNormal;
import com.softcomp.nn.lossfunctions.MeanSquaredError;
import com.softcomp.nn.model.Layer;

public class NeuralNetworkDiagnostic {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("Neural Network Diagnostic Tool");
        System.out.println("========================================\n");

        // Test 1: Layer Forward Pass
        testLayerForward();

        // Test 2: Layer Backward Pass
        testLayerBackward();

        // Test 3: Activation Functions
        testActivationFunctions();

        // Test 4: Weight Updates
        testWeightUpdates();

        // Test 5: Simple XOR with debugging
        testXORWithDebugging();

        System.out.println("\n========================================");
        System.out.println("Diagnostics Complete!");
        System.out.println("========================================");
    }

    private static void testLayerForward() {
        System.out.println("TEST 1: Layer Forward Pass");
        System.out.println("--------------------------");

        Layer layer = new Layer(2, 2, new Sigmoid(), new XavierNormal());

        // Set known weights for testing
        double[][] weights = { { 0.5, -0.5 }, { 0.5, -0.5 } };
        double[] biases = { 0.0, 0.0 };
        layer.setWeights(weights);
        layer.setBiases(biases);

        double[] input = { 1.0, 1.0 };
        double[] output = layer.forward(input);

        System.out.println("Input: [" + input[0] + ", " + input[1] + "]");
        System.out.println("Weights:");
        for (int i = 0; i < 2; i++) {
            System.out.println("  [" + weights[i][0] + ", " + weights[i][1] + "]");
        }
        System.out.println("Output: [" + output[0] + ", " + output[1] + "]");
        System.out.println("Expected: [0.73105..., 0.5] (sigmoid(1.0), sigmoid(0.0))");
        System.out.println();
    }

    private static void testLayerBackward() {
        System.out.println("TEST 2: Layer Backward Pass");
        System.out.println("---------------------------");

        Layer layer = new Layer(2, 2, new Sigmoid(), new XavierNormal());

        double[][] weights = { { 0.5, -0.5 }, { 0.5, -0.5 } };
        double[] biases = { 0.0, 0.0 };
        layer.setWeights(weights);
        layer.setBiases(biases);

        System.out.println("Initial Weights:");
        printWeights(layer.getWeights());

        // Forward pass
        double[] input = { 1.0, 1.0 };
        double[] output = layer.forward(input);

        // Backward pass
        double[] gradOutput = { 0.1, -0.1 };
        double learningRate = 0.5;
        double[] gradInput = layer.backward(gradOutput, learningRate);

        System.out.println("\nAfter Backward Pass:");
        System.out.println("Gradient Input: [" + gradInput[0] + ", " + gradInput[1] + "]");
        System.out.println("Updated Weights:");
        printWeights(layer.getWeights());
        System.out.println("Updated Biases: [" + layer.getBiases()[0] + ", " + layer.getBiases()[1] + "]");
        System.out.println();
    }

    private static void testActivationFunctions() {
        System.out.println("TEST 3: Activation Functions");
        System.out.println("----------------------------");

        Sigmoid sigmoid = new Sigmoid();
        ReLU relu = new ReLU();

        double[] testValues = { -2.0, -1.0, 0.0, 1.0, 2.0 };

        System.out.println("Sigmoid:");
        for (double x : testValues) {
            double fx = sigmoid.applyFunction(x);
            double dfx = sigmoid.derivative(x);
            System.out.printf("  f(%.1f) = %.4f, f'(%.1f) = %.4f\n", x, fx, x, dfx);
        }

        System.out.println("\nReLU:");
        for (double x : testValues) {
            double fx = relu.applyFunction(x);
            double dfx = relu.derivative(x);
            System.out.printf("  f(%.1f) = %.4f, f'(%.1f) = %.4f\n", x, fx, x, dfx);
        }
        System.out.println();
    }

    private static void testWeightUpdates() {
        System.out.println("TEST 4: Weight Update Mechanics");
        System.out.println("--------------------------------");

        // Create simple network for XOR
        Model model = new Model(2, new int[] { 2, 4 }, new int[] { 4, 1 });
        model.setActivationFunction(new Sigmoid());
        model.setWeightInitializer(new XavierNormal());
        model.setLossFunction(new MeanSquaredError());
        model.setEpoches(1);
        model.setLearningRate(0.5);
        model.setBatchSize(1);

        // Get initial weights from first layer
        Layer firstLayer = model.getNetwork().getLayers()[0];
        double[][] initialWeights = copyWeights(firstLayer.getWeights());

        System.out.println("Initial weights (first layer, sample):");
        System.out.printf("  w[0][0] = %.6f\n", initialWeights[0][0]);
        System.out.printf("  w[0][1] = %.6f\n", initialWeights[0][1]);

        // Train on single sample
        double[][] X = { { 0, 1 } };
        double[][] Y = { { 1 } };
        model.train(X, Y);

        double[][] updatedWeights = firstLayer.getWeights();
        System.out.println("\nAfter 1 epoch on [0,1]->1:");
        System.out.printf("  w[0][0] = %.6f (change: %.6f)\n",
                updatedWeights[0][0], updatedWeights[0][0] - initialWeights[0][0]);
        System.out.printf("  w[0][1] = %.6f (change: %.6f)\n",
                updatedWeights[0][1], updatedWeights[0][1] - initialWeights[0][1]);

        if (Math.abs(updatedWeights[0][0] - initialWeights[0][0]) < 1e-10) {
            System.out.println("\n⚠ WARNING: Weights are not updating!");
        } else {
            System.out.println("\n✓ Weights are updating correctly");
        }
        System.out.println();
    }

    private static void testXORWithDebugging() {
        System.out.println("TEST 5: XOR Training with Debugging");
        System.out.println("-----------------------------------");

        double[][] X = { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } };
        double[][] Y = { { 0 }, { 1 }, { 1 }, { 0 } };

        // Create network with better configuration
        Model model = new Model(2, new int[] { 2, 8 }, new int[] { 8, 1 });
        model.setActivationFunction(new Sigmoid());
        model.setWeightInitializer(new XavierNormal());
        model.setLossFunction(new MeanSquaredError());
        model.setEpoches(5000);
        model.setLearningRate(0.1); // Higher learning rate
        model.setBatchSize(4);

        System.out.println("Configuration:");
        System.out.println("  Architecture: 2 -> 8 -> 1");
        System.out.println("  Learning Rate: " + model.getLearningRate());
        System.out.println("  Epochs: " + model.getEpoches());
        System.out.println("  Batch Size: " + model.getBatchSize());

        // Get initial prediction
        System.out.println("\nBefore Training:");
        for (int i = 0; i < X.length; i++) {
            double[] pred = model.predict(X[i]);
            System.out.printf("  [%.0f,%.0f] -> %.4f (target: %.0f)\n",
                    X[i][0], X[i][1], pred[0], Y[i][0]);
        }

        model.train(X, Y);

        System.out.println("\nAfter Training:");
        double totalError = 0;
        for (int i = 0; i < X.length; i++) {
            double[] pred = model.predict(X[i]);
            double error = Math.abs(pred[0] - Y[i][0]);
            totalError += error;
            System.out.printf("  [%.0f,%.0f] -> %.4f (target: %.0f, error: %.4f)\n",
                    X[i][0], X[i][1], pred[0], Y[i][0], error);
        }

        System.out.println("\nLoss progression:");
        var losses = model.getLoses();
        int[] checkpoints = { 0, 24, 49, 74, 99 };
        for (int i : checkpoints) {
            if (i < losses.size()) {
                System.out.printf("  Epoch %d: %.6f\n", i + 1, losses.get(i));
            }
        }

        double avgError = totalError / X.length;
        System.out.printf("\nAverage Prediction Error: %.4f\n", avgError);

        if (avgError < 0.1) {
            System.out.println("✓ Network learned XOR successfully!");
        } else if (avgError < 0.3) {
            System.out.println("⚠ Network is learning but needs more training");
        } else {
            System.out.println("✗ Network is not learning effectively");
            System.out.println("  Possible issues:");
            System.out.println("  - Check gradient calculation in backward pass");
            System.out.println("  - Verify activation function derivatives");
            System.out.println("  - Check weight update formula");
            System.out.println("  - Try different learning rates or architectures");
        }
    }

    private static void printWeights(double[][] weights) {
        for (int i = 0; i < weights.length; i++) {
            System.out.print("  [");
            for (int j = 0; j < weights[i].length; j++) {
                System.out.printf("%.4f", weights[i][j]);
                if (j < weights[i].length - 1)
                    System.out.print(", ");
            }
            System.out.println("]");
        }
    }

    private static double[][] copyWeights(double[][] weights) {
        double[][] copy = new double[weights.length][weights[0].length];
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].length; j++) {
                copy[i][j] = weights[i][j];
            }
        }
        return copy;
    }
}