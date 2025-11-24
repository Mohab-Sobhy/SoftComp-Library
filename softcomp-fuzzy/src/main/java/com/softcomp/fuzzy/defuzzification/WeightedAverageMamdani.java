package com.softcomp.fuzzy.defuzzification;

import java.util.Map;

import com.softcomp.fuzzy.models.FuzzySet;
import com.softcomp.fuzzy.models.LinguisticVariable;

public class WeightedAverageMamdani implements IDefuzzifierMamdani {

    @Override
    public double defuzzify(LinguisticVariable variable, Map<String, Double> aggregatedOutput) {

        double numerator = 0;
        double denominator = 0;

        for (Map.Entry<String, Double> entry : aggregatedOutput.entrySet()) {

            String name = entry.getKey();
            double membership = entry.getValue();

            FuzzySet fuzzySet = variable.getSets().get(name);
            double centroid = fuzzySet.getCentroid();

            numerator = numerator + (membership * centroid);
            denominator = denominator + membership;
        }
        if(denominator == 0) return 0;
        return numerator / denominator;
    }

    
    
}
