package com.softcomp.fuzzy.defuzzification;

import java.util.Map;

import com.softcomp.fuzzy.models.LinguisticVariable;

public interface IDefuzzifierMamdani {

    double defuzzify(LinguisticVariable variable, Map<String, Double> aggregatedOutput);
}