package com.softcomp.nn.model;

import java.io.EOFException;

import com.softcomp.nn.activationfunction.ActivationFunction;
import com.softcomp.nn.initializers.WeightInitializer;

public class Layer {

    private int inputSize;
    private int outputSize;

    private double[][] weights;
    private double[] biases;

    private ActivationFunction activationFunction;

    private double[] inputCache;
    private double[] outputCache;

    public Layer(int inputSize, int outputSize,
            ActivationFunction activationFunction,
            WeightInitializer initializer) {
        this.inputSize = inputSize;
        this.outputSize = outputSize;
        this.activationFunction = activationFunction;

        this.weights = initializer.initialize(inputSize, outputSize);

        this.biases = new double[outputSize];
    }

    public Layer(int inputSize, int outputSize,
            ActivationFunction activationFunction,
            WeightInitializer initializer, double[] biases) {
        this.inputSize = inputSize;
        this.outputSize = outputSize;
        this.activationFunction = activationFunction;

        this.weights = initializer.initialize(inputSize, outputSize);

        if (biases.length != outputSize)
            this.biases = new double[outputSize];
        else
            this.biases = biases;
    }

    // TODO: add forward and backward methods here
    public double[] forward(double[] input) {
        double[] output = new double[outputSize];
        try {
            if (input.length != inputSize) {
                throw new IllegalArgumentException("Input size does not match layer input size");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        for (int i = 0; i < outputSize; i++) {
            for (int j = 0; j < inputSize; j++) {
                output[i] += weights[j][i] * input[j];
            }
            output[i] += biases[i];
        }
        inputCache = input;
        outputCache = output;
        return activationFunction.forward(output);
    }

    public double[] backward(double[] gradOutput, double learningRate) {
        // Step 1: Compute dL/dZ using activation derivative
        double[] gradZ = activationFunction.backward(outputCache, gradOutput);

        // Step 2: Compute gradient w.r.t weights and biases
        double[][] gradW = new double[inputSize][outputSize];
        double[] gradB = new double[outputSize];

        for (int i = 0; i < inputSize; i++) {
            for (int j = 0; j < outputSize; j++) {
                gradW[i][j] = inputCache[i] * gradZ[j];
            }
        }

        for (int j = 0; j < outputSize; j++) {
            gradB[j] = gradZ[j];
        }

        // Step 3: Compute gradient w.r.t input (to pass to previous layer)
        double[] gradInput = new double[inputSize];
        for (int i = 0; i < inputSize; i++) {
            double sum = 0.0;
            for (int j = 0; j < outputSize; j++) {
                sum += weights[i][j] * gradZ[j];
            }
            gradInput[i] = sum;
        }

        // Step 4: Update weights and biases
        for (int i = 0; i < inputSize; i++) {
            for (int j = 0; j < outputSize; j++) {
                weights[i][j] -= learningRate * gradW[i][j];
            }
        }

        for (int j = 0; j < outputSize; j++) {
            biases[j] -= learningRate * gradB[j];
        }

        // Step 5: Return gradient to pass to previous layer
        return gradInput;
    }

    public double[][] getWeights() {
        return weights;
    }

    public double[] getBiases() {
        return biases;
    }

    public ActivationFunction getActivationFunction() {
        return activationFunction;
    }
}
