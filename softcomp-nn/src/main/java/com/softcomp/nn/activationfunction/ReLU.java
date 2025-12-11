package com.softcomp.nn.activationfunction;

public class ReLU extends ActivationFunction {
    public double eulerNumber = Math.E;

    double applyFunction(double x) {
        return (x > 0) ? x : 0;
    }

    double derivative(double x) {
        return (x > 0) ? 1 : 0;
    }
}
