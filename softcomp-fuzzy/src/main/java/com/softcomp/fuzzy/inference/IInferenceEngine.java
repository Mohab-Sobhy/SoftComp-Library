package com.softcomp.fuzzy.inference;

import com.softcomp.fuzzy.models.FuzzifiedInputs;
import com.softcomp.fuzzy.models.RuleBase;
import java.util.Map;

public interface IInferenceEngine {
    /**
     * Performs fuzzy inference and returns aggregated fuzzy outputs.
     */
    public Map<String, Double> infer(RuleBase rules, FuzzifiedInputs inputs);
}