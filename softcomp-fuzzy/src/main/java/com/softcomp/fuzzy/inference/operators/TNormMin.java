package com.softcomp.fuzzy.inference.operators;

public class TNormMin implements TNorm {
    @Override
    public double apply(double a, double b) {
        return Math.min(a, b);
    }
}
