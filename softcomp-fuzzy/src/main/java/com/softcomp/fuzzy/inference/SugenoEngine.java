package com.softcomp.fuzzy.inference;

import java.util.HashMap;
import java.util.Map;

import com.softcomp.fuzzy.inference.operators.SNorm;
import com.softcomp.fuzzy.inference.operators.TNorm;
import com.softcomp.fuzzy.models.FuzzifiedInputs;
import com.softcomp.fuzzy.models.Rule;
import com.softcomp.fuzzy.models.RuleBase;

public class SugenoEngine implements IInferenceEngine {
    private SNorm OR;
    private TNorm AND;

    public SugenoEngine(TNorm AND, SNorm OR) {
        this.AND = AND;
        this.OR = OR;
    }

    @Override
    public Map<String, Double> infer(RuleBase rules, FuzzifiedInputs inputs, String outputName) {
        Map<String, Double> aggregated = new HashMap<>();
        Double v1 = 0.0, v2 = 0.0;
        for (Rule rule : rules.getRules()) {
            if (!rule.isEnabled() || !rule.getConsequent().first.equals(outputName)) {
                continue;
            }
            Double val = rule.getCrispValue() * rule.evaluate(inputs, AND, OR);
            v1 += val;
            v2 += rule.evaluate(inputs, AND, OR);
        }
        if (v2 != 0)
            aggregated.put(outputName, v1 / v2);
        return aggregated;
    }
}
