package com.softcomp.fuzzy.inference.operators;

public class TNormProduct implements TNorm {
    @Override
    public double apply(double a, double b) {
        return a * b;
    }
}
