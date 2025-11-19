package com.softcomp.fuzzy.models;

import java.util.HashMap;
import java.util.Map;

public class FuzzifiedInputs {

    private Map<String, FuzzifiedVariable> variables = new HashMap<>();

    public void addVariable(FuzzifiedVariable var) {
        variables.put(var.getVariableName(), var);
    }

    public FuzzifiedVariable get(String variableName) {
        return variables.get(variableName);
    }

    public Map<String, FuzzifiedVariable> getAll() {
        return variables;
    }
}
