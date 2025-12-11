package com.softcomp.nn.activaitionfunction;

public class Sigmoid implements ActivationFunction {
    public double eulerNumber = Math.E;

    private double applyFunction(double x) {
        return 1.0 / (1.0 + (Math.exp(-x)));
    }

    private double derivative(double x) {
        return applyFunction(x) * (1 - applyFunction(x));
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
        double[] values = new double[arrLength];
        for (int i = 0; i < arrLength; i++) {
            values[i] = derivative(values[i]);
        }
        return values;
    }
}
