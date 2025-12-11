package com.softcomp.nn.activaitionfunction;

public interface ActivationFunction {
    double[] forward(double[] z);

    double[] backward(double[] z, double[] gradOutput);
}
