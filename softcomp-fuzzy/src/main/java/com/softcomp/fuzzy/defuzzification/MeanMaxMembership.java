package com.softcomp.fuzzy.defuzzification;

import java.util.Map;

import com.softcomp.fuzzy.models.LinguisticVariable;

public class MeanMaxMembership implements IDefuzzifierMamdani {

    @Override
    public double defuzzify(LinguisticVariable variable, Map<String, Double> aggregatedOutput) {

        return 0.0;
    }

    
    
}
