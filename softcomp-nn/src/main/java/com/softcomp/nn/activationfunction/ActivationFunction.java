package com.softcomp.nn.activationfunction;

public interface ActivationFunction {
    double[] forward(double[] z);

    double[] backward(double[] z, double[] gradOutput);
}
