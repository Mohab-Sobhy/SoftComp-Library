package com.softcomp.nn.model;



import com.softcomp.nn.activationfunction.ActivationFunction;
import com.softcomp.nn.initializers.WeightInitializer;

public class Layer {

    private int inputSize;
    private int outputSize;

    private double[][] weights;
    private double[] biases;

    private ActivationFunction activationFunction;

    private double[] inputCache ;
    private double[] outputCache ;
    private double[] outputACache ;
    private double[][] dW;
    private double[] db;

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
        this.dW = new double[inputSize][outputSize];
        this.db = new double[outputSize];
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

        inputCache=input;
        outputCache=z;
        outputACache=activationFunction.forward(z);
        return outputACache;
    }

    public double[] backward(double[] gradOutput) {
        if (gradOutput == null) {
            throw new NullPointerException("Gradient output cannot be null");
        }
        if (gradOutput.length != outputSize) {
            throw new IllegalArgumentException(
                    "Gradient size must match output size");
        }

        double[] z = outputCache;
        double[] x = inputCache; 
        double[] gradZ = activationFunction.backward(z, gradOutput);

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
                    dW[i][j] += x[i] * gradZ[j];
                }
            }

            for (int j = 0; j < outputSize; j++) {
                db[j] += gradZ[j];
            }


        
        return gradInput;
    }

    public void updateWeights(double learningRate, int batchSize) {
    if (learningRate <= 0) {
            throw new IllegalArgumentException("Learning rate must be > 0");
        }
    for (int i = 0; i < inputSize; i++) {
        for (int j = 0; j < outputSize; j++) {
            weights[i][j] -= learningRate * (dW[i][j] / batchSize);
        }
    }

    for (int j = 0; j < outputSize; j++) {
        biases[j] -= learningRate * (db[j] / batchSize);
    }
    zeroGradients();
}
   public void zeroGradients() {
    for (int i = 0; i < inputSize; i++) {
        for (int j = 0; j < outputSize; j++) {
            dW[i][j] = 0.0;
        }
    }
    for (int j = 0; j < outputSize; j++) {
        db[j] = 0.0;
    }
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

    public double[] getInputCache() {
        return inputCache;
    }

    public double[] getOutputCache() {
        return outputCache;
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
