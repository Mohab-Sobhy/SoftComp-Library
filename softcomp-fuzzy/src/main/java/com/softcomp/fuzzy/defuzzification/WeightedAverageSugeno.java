package com.softcomp.fuzzy.defuzzification;

import java.util.Map;
import com.softcomp.fuzzy.models.FuzzySet;
import com.softcomp.fuzzy.models.LinguisticVariable;

public class WeightedAverageSugeno implements IDefuzzifierSugeno {

    @Override
    public double defuzzify(Map<String, Double> ruleOutput, Map<String, Double> aggregatedOutput) {

        double numerator = 0;
        double denominator = 0;

        for (Map.Entry<String, Double> entry : aggregatedOutput.entrySet()) {

            String name = entry.getKey();
            double membership = entry.getValue();

            double ruleCrispOutput = ruleOutput.get(name);

            numerator = numerator + (membership * ruleCrispOutput);
            denominator = denominator + membership;
        }
        if(denominator == 0) return 0;
        return numerator / denominator;
    }

    
}
