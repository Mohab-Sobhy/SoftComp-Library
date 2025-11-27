package com.softcomp.fuzzy.defuzzification;

import java.util.Map;

import com.softcomp.fuzzy.models.FuzzySet;
import com.softcomp.fuzzy.models.LinguisticVariable;

public class MaxMembership implements IDefuzzifierMamdani {

    @Override
    public double defuzzify(LinguisticVariable variable, Map<String, Double> aggregatedOutput) {

        double maxMembership = 0.0;
        double maxCentroid = 0.0;
        int maxCount = 0;   
        FuzzySet maxFuzzySet = null;

        for (Map.Entry<String, Double> entry : aggregatedOutput.entrySet()) {
            double membership = entry.getValue();
            if (membership > maxMembership) {
                maxMembership = membership;
                maxFuzzySet = variable.getSets().get(entry.getKey());
                maxCentroid = maxFuzzySet.getCentroid();
            }
        }

        for (double membership : aggregatedOutput.values()) {
            if (membership == maxMembership) maxCount++;
        }

        if (maxCount > 1) {
            throw new IllegalArgumentException("Only one max peak is allowed");
        }

        if (maxFuzzySet == null) {
            throw new IllegalArgumentException("Fuzzy set not found");
        }

        if (!maxFuzzySet.isTriangular()) {
            throw new IllegalArgumentException("Max membership is not triangular. Only triangular sets allowed.");
        }

        if (maxMembership < 1.0) {
            throw new IllegalArgumentException("Can't have 1 point peak at a membership less than 1");
        }

        return maxCentroid;
    }
}

