package com.softcomp.nn.activationfunction;

public class Linear extends ActivationFunction {
    double applyFunction(double x) {
        return x;
    }

    double derivative(double x) {
        return 1;
    }
}
