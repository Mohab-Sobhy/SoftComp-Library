package com.softcomp.fuzzy.defuzzification;

import java.util.Map;

public interface IDefuzzifier {
    /**
     * Converts aggregated fuzzy output values into a crisp number.
     * @param aggregatedOutput map of fuzzySetName → membershipDegree
     * @return crisp output value
     */
    double defuzzify(Map<String, Double> aggregatedOutput);
}