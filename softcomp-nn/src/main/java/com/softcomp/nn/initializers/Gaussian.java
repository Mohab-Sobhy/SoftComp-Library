package com.softcomp.nn.initializers;

public class Gaussian {
    public double mean;
    public double standardDeviation;
    private double pi = Math.PI;

    public Gaussian(double mean, double standardDeviation) {
        this.mean = mean;
        this.standardDeviation = standardDeviation;
    }

    public double compute(double x) {
        return 1 / (standardDeviation * Math.sqrt(2 * pi))
                * Math.exp(Math.pow(x - mean, 2) / (2 * Math.pow(standardDeviation, 2)));
    }
}