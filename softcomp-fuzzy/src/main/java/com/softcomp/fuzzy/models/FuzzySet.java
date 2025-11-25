package com.softcomp.fuzzy.models;

import com.softcomp.fuzzy.membership.IMembershipFunction;
import com.softcomp.fuzzy.membership.TriangularMF;

public class FuzzySet {
    private String name;
    private IMembershipFunction mf;

    public FuzzySet(String name, IMembershipFunction mf) {
        this.name = name;
        this.mf = mf;
    }

    public String getName() {
        return name;
    }

    public double compute(double x) {
        return mf.compute(x);
    }

    public double getCentroid() {
        return mf.getCentroid();
    }

    public boolean isTriangular() {
        if (mf instanceof TriangularMF) {
            return true;
        } else
            return false;
    }

}
