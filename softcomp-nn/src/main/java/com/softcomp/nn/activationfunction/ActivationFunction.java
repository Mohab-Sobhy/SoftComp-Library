package com.softcomp.nn.activationfunction;

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
        double[] derivativeByGrad = new double[arrLength];
        for (int i = 0; i < arrLength; i++) {
            derivativeByGrad[i] = derivative(z[i]) * gradOutput[i];
        }
        return derivativeByGrad;
    }
}
