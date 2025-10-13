package com.softcomp.ga.models;

public class Gene<T> {
    private T value;
    private double lowerBound;
    private double upperBound;

    public Gene(T gene) {
        this.value = gene;
    }

    public T get() {
        return value;
    }

    public void set(T gene) {
        this.value = gene;
    }

    public double getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(double LB) {
        lowerBound = LB;
    }

    public double getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(double UB) {
        upperBound = UB;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
