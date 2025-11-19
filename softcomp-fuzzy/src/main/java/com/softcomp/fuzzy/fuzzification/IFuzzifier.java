package com.softcomp.fuzzy.fuzzification;

import com.softcomp.fuzzy.models.LinguisticVariable;

import java.util.Map;


public interface IFuzzifier {
    /**
     * Fuzzifies a crisp numerical input based on a linguistic variable.
     * @param variable linguistic variable containing fuzzy sets
     * @param crispValue input value
     * @return map of fuzzySetName → membershipDegree
     */
    Map<String, Double> fuzzify(LinguisticVariable variable, double crispValue);
}