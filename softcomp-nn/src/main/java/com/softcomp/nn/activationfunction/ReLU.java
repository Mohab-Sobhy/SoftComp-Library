package com.softcomp.nn.activationfunction;

public class ReLU extends ActivationFunction {
    public double eulerNumber = Math.E;

    public double applyFunction(double x) {
        return (x > 0) ? x : 0;
    }

    public double derivative(double x) {
        return (x > 0) ? 1 : 0;
    }
}
