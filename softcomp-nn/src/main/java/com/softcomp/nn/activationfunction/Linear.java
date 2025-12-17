package com.softcomp.nn.activationfunction;

import java.io.Serializable;

public class Linear extends ActivationFunction implements Serializable {
    public double applyFunction(double x) {
        return x;
    }

    public double derivative(double x) {
        return 1;
    }
}
