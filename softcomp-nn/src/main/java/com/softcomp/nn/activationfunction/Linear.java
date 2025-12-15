package com.softcomp.nn.activationfunction;

public class Linear extends ActivationFunction {
    public double applyFunction(double x) {
        return x;
    }

    public double derivative(double x) {
        return 1;
    }
}
