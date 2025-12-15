package com.softcomp.nn.model;

import java.util.ArrayList;
import java.util.List;

import com.softcomp.nn.activationfunction.ActivationFunction;
import com.softcomp.nn.initializers.WeightInitializer;

public class Layer {

    private int inputSize;
    private int outputSize;

    private double[][] weights;
    private double[] biases;

    private ActivationFunction activationFunction;

    private List<double[]> inputCaches = new ArrayList<>();
    private List<double[]> outputCaches = new ArrayList<>();
    private List<double[]> outputACaches = new ArrayList<>();

    public Layer(int inputSize, int outputSize,
            ActivationFunction activationFunction,
            WeightInitializer initializer) {

        if (inputSize <= 0 || outputSize <= 0) {
            throw new IllegalArgumentException("Layer sizes must be positive");
        }
        if (activationFunction == null || initializer == null) {
            throw new NullPointerException("Activation and initializer cannot be null");
        }

        this.inputSize = inputSize;
        this.outputSize = outputSize;
        this.activationFunction = activationFunction;
        this.weights = initializer.initialize(inputSize, outputSize);
        this.biases = new double[outputSize];
    }

    public Layer(int inputSize, int outputSize,
            ActivationFunction activationFunction,
            WeightInitializer initializer,
            double[] biases) {

        this(inputSize, outputSize, activationFunction, initializer);

        if (biases == null) {
            throw new NullPointerException("Biases cannot be null");
        }
        if (biases.length != outputSize) {
            throw new IllegalArgumentException(
                    "Bias length must match output size");
        }
        this.biases = biases;
    }

    public double[] forward(double[] input) {
        if (input == null) {
            throw new NullPointerException("Input cannot be null");
        }
        if (input.length != inputSize) {
            throw new IllegalArgumentException(
                    "Expected input size " + inputSize + ", got " + input.length);
        }

        double[] z = new double[outputSize];

        for (int i = 0; i < outputSize; i++) {
            for (int j = 0; j < inputSize; j++) {
                z[i] += weights[j][i] * input[j];
            }
            z[i] += biases[i];
        }

        inputCaches.add(input);
        outputCaches.add(z);
        outputACaches.add(activationFunction.forward(z));
        return activationFunction.forward(z);
    }

    public double[] backward(double[] gradOutput, double learningRate) {
        if (gradOutput == null) {
            throw new NullPointerException("Gradient output cannot be null");
        }
        if (gradOutput.length != outputSize) {
            throw new IllegalArgumentException(
                    "Gradient size must match output size");
        }
        if (learningRate <= 0) {
            throw new IllegalArgumentException("Learning rate must be > 0");
        }

        // 1️⃣ Compute gradient w.r.t pre-activation z
        double[] z = outputCaches.get(outputCaches.size() - 1); // last cached output (pre-activation)
        double[] x = inputCaches.get(inputCaches.size() - 1); // last cached input
        double[] gradZ = activationFunction.backward(z, gradOutput);

        // 2️⃣ Compute gradient w.r.t input to propagate to previous layer
        double[] gradInput = new double[inputSize];
        for (int i = 0; i < inputSize; i++) {
            double sum = 0.0;
            for (int j = 0; j < outputSize; j++) {
                sum += weights[i][j] * gradZ[j];
            }
            gradInput[i] = sum;
        }

        // 3️⃣ Update weights and biases
        for (int i = 0; i < inputSize; i++) {
            for (int j = 0; j < outputSize; j++) {
                weights[i][j] -= learningRate * x[i] * gradZ[j];
            }
        }
        for (int j = 0; j < outputSize; j++) {
            biases[j] -= learningRate * gradZ[j];
        }

        // 4️⃣ Clear caches for this forward/backward pass
        inputCaches.remove(inputCaches.size() - 1);
        outputCaches.remove(outputCaches.size() - 1);
        outputACaches.remove(outputACaches.size() - 1);

        // 5️⃣ Return gradInput to propagate backward
        return gradInput;
    }

    public int getInputSize() {
        return inputSize;
    }

    public int getOutputSize() {
        return outputSize;
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

    public List<double[]> getInputCaches() {
        return inputCaches;
    }

    public List<double[]> getOutputCaches() {
        return outputCaches;
    }

    public void setWeights(double[][] weights) {
        if (weights == null) {
            throw new NullPointerException("Weights cannot be null");
        }
        if (weights.length != inputSize || weights[0].length != outputSize) {
            throw new IllegalArgumentException(
                    "Weights shape must be [" + inputSize + "][" + outputSize + "]");
        }
        this.weights = weights;
    }

    public void setBiases(double[] biases) {
        if (biases == null) {
            throw new NullPointerException("Biases cannot be null");
        }
        if (biases.length != outputSize) {
            throw new IllegalArgumentException(
                    "Bias length must match output size");
        }
        this.biases = biases;
    }

    public void setActivationFunction(ActivationFunction activationFunction) {
        if (activationFunction == null) {
            throw new NullPointerException("Activation function cannot be null");
        }
        this.activationFunction = activationFunction;
    }
}
