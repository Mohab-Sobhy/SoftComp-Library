package com.softcomp.fuzzy.defuzzification;

import java.util.Map;

import com.softcomp.fuzzy.models.LinguisticVariable;

public interface IDefuzzifierSugeno {
    /**
     * Converts aggregated fuzzy output values into a crisp number.
     * @param aggregatedOutput map of fuzzySetName → membershipDegree
     * @param ruleOutput
     * @return crisp output value
     */
    double defuzzify(Map<String, Double>ruleOutput, Map<String, Double> aggregatedOutput);
}