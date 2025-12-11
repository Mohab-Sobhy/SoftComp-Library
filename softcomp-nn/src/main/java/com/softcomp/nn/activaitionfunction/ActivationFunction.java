package com.softcomp.nn.activaitionfunction;

//make sure the derivative is used for all activation functions
//Sigmoid, ReLU, Tanh, Linear.
public interface ActivationFunction {
    double[] forward(double[] z);

    double[] backward(double[] z, double[] gradOutput);
}
