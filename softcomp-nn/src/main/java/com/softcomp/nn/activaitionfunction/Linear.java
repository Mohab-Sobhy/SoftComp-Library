package com.softcomp.nn.activaitionfunction;

public class Linear extends ActivationFunction {
    double applyFunction(double x) {
        return x;
    }

    double derivative(double x) {
        return 1;
    }
}
