package com.softcomp.fuzzy.inference.operators;

public class SNormMax implements SNorm {
    @Override
    public double apply(double a, double b) {
        return Math.max(a, b);
    }
}
