package com.softcomp.nn.activaitionfunction;

//make sure the derivative is used for all activation functions
//Sigmoid, ReLU, Tanh, Linear.
public abstract class ActivationFunction {
    abstract double applyFunction(double x);

    abstract double derivative(double x);

    public double[] forward(double[] z) {
        int arrLength = z.length;
        double[] values = new double[arrLength];
        for (int i = 0; i < arrLength; i++) {
            values[i] = applyFunction(z[i]);
        }
        return values;
    }

    public double[] backward(double[] z, double[] gradOutput) {
        int arrLength = z.length;
        double[] derivatives = new double[arrLength];
        for (int i = 0; i < arrLength; i++) {
            derivatives[i] = derivative(z[i]);
        }
        return derivatives;
    }
}
