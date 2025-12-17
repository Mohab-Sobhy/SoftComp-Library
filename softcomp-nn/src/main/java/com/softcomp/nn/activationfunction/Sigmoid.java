package com.softcomp.nn.activationfunction;

import java.io.Serializable;

public class Sigmoid extends ActivationFunction implements Serializable {

    @Override
    public double applyFunction(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }

    @Override
    public double derivative(double x) {
        double sigmoid = applyFunction(x);
        return sigmoid * (1 - sigmoid);
    }
}
