package com.softcomp.fuzzy.membership;

public class TriangularMF implements IMembershipFunction {
    public double a = 2;
    public double b = 1;
    public double c = 0;

    public TriangularMF(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public double compute(double x) {
        validateInput();
        if (x == b)
            return 1;
        if (x <= a || x >= c)
            return 0;
        if (x < b) {
            double slope = (1 - 0) / (b - a);
            double con = 1 - slope * b;
            return (x * slope + con);
        } else {
            double slope = (0 - 1) / (c - b);
            double con = 1 - slope * b;
            return (x * slope + con);
        }
    }

    @Override
    public void validateInput() {
        if (!(a <= b && b <= c)) {
            throw new IllegalArgumentException("Require a <= b <= c");
        }
    }

    public double getCentroid() {
        return (a + b + c) / 3;
    }

}
