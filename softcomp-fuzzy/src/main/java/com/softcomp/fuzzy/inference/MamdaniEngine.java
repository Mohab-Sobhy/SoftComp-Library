package com.softcomp.fuzzy.inference;

import java.util.HashMap;
import java.util.Map;

import com.softcomp.fuzzy.models.FuzzifiedInputs;
import com.softcomp.fuzzy.models.Rule;
import com.softcomp.fuzzy.models.RuleBase;

public class MamdaniEngine implements IInferenceEngine {
    @Override
    public Map<String, Double> infer(RuleBase rules, FuzzifiedInputs inputs) {
        Map<String, Double> aggregated = new HashMap<>();
        for (Rule rule : rules.getRules()) {
            Double val = rule.evaluate(inputs);
            String key = ""; //
            if (aggregated.get(key) == null) {

            } else {
                Double orignial = aggregated.get(key);
                aggregated.put(key, Math.max(val, orignial));
            }

        }
        return aggregated;
    }
}
