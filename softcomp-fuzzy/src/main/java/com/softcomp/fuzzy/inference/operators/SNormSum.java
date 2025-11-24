package com.softcomp.fuzzy.inference.operators;

public class SNormSum implements SNorm {
    @Override
    public double apply(double a, double b) {
        return a + b;
    }
}
