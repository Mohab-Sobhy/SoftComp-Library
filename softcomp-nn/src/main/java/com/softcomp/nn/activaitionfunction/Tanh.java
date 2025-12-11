package com.softcomp.nn.activaitionfunction;

public class Tanh extends ActivationFunction {
    public double eulerNumber = Math.E;

    // 2×sigmoid(2x)−1
    double sigmoid(double x) {
        return 1.0 / (1.0 + (Math.exp(-x)));
    }

    double applyFunction(double x) {
        return 2 * sigmoid(2 * x) - 1;
    }

    double derivative(double x) {
        return 2 * applyFunction(x) * (1 - applyFunction(x));
    }
}
