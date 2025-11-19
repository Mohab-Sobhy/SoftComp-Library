package com.softcomp.fuzzy.models;

import java.util.HashMap;
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

    public Map<String, FuzzySet> getSets() { return sets; }
}