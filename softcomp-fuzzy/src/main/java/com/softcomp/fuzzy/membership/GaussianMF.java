package com.softcomp.fuzzy.membership;

public class GaussianMF implements IMembershipFunction {
    public double mean;
    public double standardDeviation;
    public double eulerNumber = Math.E;
    public double pi = Math.PI;

    public GaussianMF(double mean, double standardDeviation) {
        this.mean = mean;
        this.standardDeviation = standardDeviation;
    }

    @Override
    public double compute(double x) {
        return 1 / (standardDeviation * Math.sqrt(2 * pi))
                * Math.exp(Math.pow(x - mean, 2) / (2 * Math.pow(standardDeviation, 2)));
    }

    @Override
    public void validateInput() {
    }

    @Override
    public double getCentroid() {
        return mean;
    }
}
