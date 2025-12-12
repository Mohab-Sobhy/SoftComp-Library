package com.softcomp.nn.model;


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
        double[] gradZ = activationFunction.backward(outputCache, gradOutput);

        double[] gradInput = new double[inputSize];
        for (int i = 0; i < inputSize; i++) {
            double sum = 0.0;
            for (int j = 0; j < outputSize; j++) {
                sum += weights[i][j] * gradZ[j];
            }
            gradInput[i] = sum;
        }

        for (int i = 0; i < inputSize; i++) {
            for (int j = 0; j < outputSize; j++) {
                weights[i][j] -= learningRate * inputCache[i] * gradZ[j];
            }
        }

        for (int j = 0; j < outputSize; j++) {
            biases[j] -= learningRate * gradZ[j];
        }

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
