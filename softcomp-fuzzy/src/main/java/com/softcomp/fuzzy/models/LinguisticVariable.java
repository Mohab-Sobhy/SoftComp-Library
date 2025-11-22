package com.softcomp.fuzzy.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LinguisticVariable {
    private String name;
    private double min, max;
    private Map<String, FuzzySet> sets = new HashMap<>();

    public LinguisticVariable(String name, double min, double max) {
        this.name = name;
        this.min = min;
        this.max = max;
    }

    public void addFuzzySet(FuzzySet set) {
        sets.put(set.getName(), set);
    }

    public List<FuzzySet> getAllFuzzysets(){
        return new ArrayList<>(sets.values());
    }

    // Getters
    public String getName() {
        return name;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public Map<String, FuzzySet> getSets() {
        return sets;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public void setSets(Map<String, FuzzySet> sets) {
        this.sets = sets;
    }
}