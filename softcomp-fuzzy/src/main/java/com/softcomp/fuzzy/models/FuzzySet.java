package com.softcomp.fuzzy.models;

import com.softcomp.fuzzy.membership.MembershipFunction;

public class FuzzySet {
    private String name;
    private MembershipFunction mf;


    public FuzzySet(String name, MembershipFunction mf) {
        this.name = name;
        this.mf = mf;
    }


    public String getName() { return name; }
    public double compute(double x) { return mf.compute(x); }
}