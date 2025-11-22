package com.softcomp.fuzzy.membership;

public interface IMembershipFunction {
    double compute(double x);
    public void validateInput();
}