package com.softcomp.fuzzy.inference;

import java.util.HashMap;
import java.util.Map;

import com.softcomp.fuzzy.inference.operators.SNorm;
import com.softcomp.fuzzy.inference.operators.TNorm;
import com.softcomp.fuzzy.models.FuzzifiedInputs;
import com.softcomp.fuzzy.models.Rule;
import com.softcomp.fuzzy.models.RuleBase;

public class MamdaniEngine implements IInferenceEngine {
    private SNorm OR;
    private TNorm AND;

    public MamdaniEngine(TNorm AND, SNorm OR) {
        this.AND = AND;
        this.OR = OR;
    }

    @Override
    public Map<String, Double> infer(RuleBase rules, FuzzifiedInputs inputs, String outputName) {
        Map<String, Double> aggregated = new HashMap<>();
        for (Rule rule : rules.getRules()) {
            if (!rule.isEnabled() || !rule.getConsequent().first.equals(outputName)) {
                continue;
            }
            Double val = rule.evaluate(inputs, AND, OR);
            String key = rule.getConsequent().second;
            if (aggregated.get(key) == null) {
                aggregated.put(key, val);
            } else {
                Double orignial = aggregated.get(key);
                aggregated.put(key, Math.max(val, orignial));
            }

        }
        return aggregated;
    }
}
