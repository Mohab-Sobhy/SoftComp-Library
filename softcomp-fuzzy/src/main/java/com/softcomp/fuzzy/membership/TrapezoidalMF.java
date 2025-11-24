package com.softcomp.fuzzy.membership;

public class TrapezoidalMF implements IMembershipFunction {
    public double a = 2;
    public double b = 1;
    public double c = 0;
    public double d = -1;

    public TrapezoidalMF(double a, double b, double c, double d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    @Override
    public double compute(double x) {
        validateInput();
        if (x >= b && x <= c) {
            return 1;

        } else if (x >= d || x <= a) {
            return 0;
        } else if (x < b) {
            double slope = (1 - 0) / (b - a);
            double con = 1 - slope * b;
            return (x * slope + con);
        } else {
            double slope = (0 - 1) / (d - c);
            double con = 1 - slope * c;
            return (x * slope + con);
        }
    }

    @Override
    public void validateInput() {
        if (!(a <= b && b <= c && c <= d)) {
            throw new IllegalArgumentException("Require a <= b <= c <= d");
        }
    }

    public double getCentroid() {
        return (a + b + c + d) / 4;
    }

}
