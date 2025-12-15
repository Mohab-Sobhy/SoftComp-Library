package com.softcomp.nn.lossfunctions;

public class BinaryCrossEntropy implements LossFunction {

    private static final double EPSILON = 1e-15; // to avoid log(0)

    @Override
    public double calculate(double[] pred, double[] actual) {
        validate(pred, actual);
        double sum = 0.0;
        int n = pred.length;

        for (int i = 0; i < n; i++) {
            double p = Math.min(Math.max(pred[i], EPSILON), 1 - EPSILON); // clamp to avoid NaN
            sum += -(actual[i] * Math.log(p) + (1 - actual[i]) * Math.log(1 - p));
        }

        return sum / n;
    }

    @Override
    public double[] gradLoss(double[] pred, double[] actual) {
        validate(pred, actual);
        int n = pred.length;
        double[] grad = new double[n];

        for (int i = 0; i < n; i++) {
            double p = Math.min(Math.max(pred[i], EPSILON), 1 - EPSILON); // clamp
            grad[i] = (p - actual[i]) / n; // derivative of BCE wrt sigmoid output
        }

        return grad;
    }

    private void validate(double[] pred, double[] actual) {
        if (pred == null || actual == null) {
            throw new IllegalArgumentException("Prediction or actual array is null");
        }
        if (pred.length != actual.length) {
            throw new IllegalArgumentException("Prediction and actual sizes do not match");
        }
    }
}
