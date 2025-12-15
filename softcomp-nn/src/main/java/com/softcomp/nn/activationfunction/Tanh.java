package com.softcomp.nn.activationfunction;

public class Tanh extends ActivationFunction {
    public double eulerNumber = Math.E;

    // 2×sigmoid(2x)−1
    public double sigmoid(double x) {
        return 1.0 / (1.0 + (Math.exp(-x)));
    }

    public double applyFunction(double x) {
        return 2 * sigmoid(2 * x) - 1;
    }

    public double derivative(double x) {
        return 2 * applyFunction(x) * (1 - applyFunction(x));
    }
}
