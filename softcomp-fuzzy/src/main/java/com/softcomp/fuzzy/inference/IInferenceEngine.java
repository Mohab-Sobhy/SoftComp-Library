package com.softcomp.fuzzy.inference;

import com.softcomp.fuzzy.models.FuzzifiedInputs;
import com.softcomp.fuzzy.models.RuleBase;
import java.util.Map;

public interface IInferenceEngine {

    public Map<String, Double> infer(RuleBase rules, FuzzifiedInputs inputs, String outputName);
}