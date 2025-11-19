package com.softcomp.fuzzy.inference;

import com.softcomp.fuzzy.models.RuleBase;
import java.util.Map;


public interface IInferenceEngine {
    /**
     * Performs fuzzy inference using a rule base and fuzzified inputs.
     * @param rules rule base
     * @param fuzzifiedInputs map of variableName → (fuzzySetName → degree)
     * @return aggregated fuzzy output set
     */
    Map<String, Double> infer(RuleBase rules, Map<String, Map<String, Double>> fuzzifiedInputs);
}