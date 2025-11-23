package com.softcomp.fuzzy.defuzzification;

import java.util.Map;

import com.softcomp.fuzzy.models.LinguisticVariable;

public interface IDefuzzifierMamdani {
    /**
     * Converts aggregated fuzzy output values into a crisp number.
     * @param aggregatedOutput map of fuzzySetName → membershipDegree
     * @return crisp output value
     */
    double defuzzify(LinguisticVariable variable, Map<String, Double> aggregatedOutput);
}