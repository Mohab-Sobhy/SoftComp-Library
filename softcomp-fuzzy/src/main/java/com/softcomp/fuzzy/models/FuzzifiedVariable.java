package com.softcomp.fuzzy.models;

import java.util.HashMap;
import java.util.Map;

public class FuzzifiedVariable {

    private String variableName;
    private Map<String, Double> memberships = new HashMap<>();

    public FuzzifiedVariable(String variableName) {
        this.variableName = variableName;
    }

    public void addMembership(String fuzzySetName, double degree) {
        memberships.put(fuzzySetName, degree);
    }

    public String getVariableName() {
        return variableName;
    }

    public Map<String, Double> getMemberships() {
        return memberships;
    }
}
