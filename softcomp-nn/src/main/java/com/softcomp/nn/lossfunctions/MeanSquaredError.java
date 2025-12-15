package com.softcomp.nn.lossfunctions;

public class MeanSquaredError implements LossFunction {

    @Override
    public double calculate(double[] pred, double[] actual) {
        validate(pred, actual);

        double sum = 0.0;
        int n = pred.length;
        for (int i = 0; i < n; i++) {
            double diff = pred[i] - actual[i];
            sum += diff * diff;
        }
        return sum / n;
    }

    @Override
    public double[] gradLoss(double[] pred, double[] actual) {
        validate(pred, actual);
        int n = pred.length;
        double[] grad = new double[n];
        for (int i = 0; i < n; i++) {
            grad[i] = 2.0 * (pred[i] - actual[i]) / n;
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
