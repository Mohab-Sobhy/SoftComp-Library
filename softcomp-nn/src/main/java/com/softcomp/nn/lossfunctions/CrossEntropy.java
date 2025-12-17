package com.softcomp.nn.lossfunctions;

import java.io.Serializable;

public class CrossEntropy implements LossFunction, Serializable {

    private static final double EPSILON = 1e-15;

    @Override
    public double calculate(double[] pred, double[] actual) {
        validate(pred, actual);
        double loss = 0.0;

        for (int i = 0; i < pred.length; i++) {
            double p = Math.min(Math.max(pred[i], EPSILON), 1.0 - EPSILON);
            loss += actual[i] * Math.log(p);
        }
        return -loss;
    }

    @Override
    public double[] gradLoss(double[] pred, double[] actual) {
        validate(pred, actual);
        double[] grad = new double[pred.length];

        for (int i = 0; i < pred.length; i++) {
            double p = Math.min(Math.max(pred[i], EPSILON), 1.0 - EPSILON);

            grad[i] = -actual[i] / p;
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