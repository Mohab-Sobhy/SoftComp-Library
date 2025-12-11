package com.softcomp.nn.activaitionfunction;

public class ReLU implements ActivationFunction {
    public double eulerNumber = Math.E;

    private double applyFunction(double x) {
        return (x > 0) ? x : 0;
    }

    private double derivative(double x) {
        return (x > 0) ? 1 : 0;
    }

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
