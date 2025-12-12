package com.softcomp.nn.model;

public class SplitResult {
    public double[][] X_train, Y_train, X_test, Y_test;

    public SplitResult(double[][] X_train, double[][] Y_train, double[][] X_test, double[][] Y_test) {
        this.X_train = X_train;
        this.Y_train = Y_train;
        this.X_test = X_test;
        this.Y_test = Y_test;
    }
}
