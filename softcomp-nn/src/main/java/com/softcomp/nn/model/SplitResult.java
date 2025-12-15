package com.softcomp.nn.model;

public class SplitResult {

    private final double[][] X_train;
    private final double[][] Y_train;
    private final double[][] X_test;
    private final double[][] Y_test;

    public SplitResult(double[][] X_train, double[][] Y_train, double[][] X_test, double[][] Y_test) {
        this.X_train = X_train;
        this.Y_train = Y_train;
        this.X_test = X_test;
        this.Y_test = Y_test;
    }

    public double[][] getX_train() {
        return X_train;
    }

    public double[][] getY_train() {
        return Y_train;
    }

    public double[][] getX_test() {
        return X_test;
    }

    public double[][] getY_test() {
        return Y_test;
    }
}
