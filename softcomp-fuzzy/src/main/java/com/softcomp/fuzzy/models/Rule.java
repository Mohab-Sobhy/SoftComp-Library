package com.softcomp.fuzzy.models;

import java.util.Map;


public class Rule {
    private String description;
    private double weight = 1.0;
    private boolean enabled = true;


    public Rule(String description) {
        this.description = description;
    }


    public double evaluate(Map<String, Double> inputs) {
        return 0.0; // Firing strength
    }
}