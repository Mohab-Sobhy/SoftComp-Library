package com.softcomp.nn.activationfunction;

import java.io.Serializable;

public class ReLU extends ActivationFunction implements Serializable {
    public double eulerNumber = Math.E;

    public double applyFunction(double x) {
        return (x > 0) ? x : 0;
    }

    public double derivative(double x) {
        return (x > 0) ? 1 : 0;
    }
}
