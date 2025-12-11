package com.softcomp.nn.activaitionfunction;

public class Sigmoid extends ActivationFunction {
    public double eulerNumber = Math.E;

    double applyFunction(double x) {
        return 1.0 / (1.0 + (Math.exp(-x)));
    }

    double derivative(double x) {
        return applyFunction(x) * (1 - applyFunction(x));
    }
}
