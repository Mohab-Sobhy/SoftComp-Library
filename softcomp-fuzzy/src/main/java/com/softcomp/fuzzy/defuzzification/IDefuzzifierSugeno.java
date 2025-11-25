package com.softcomp.fuzzy.defuzzification;

import java.util.List;
import java.util.Map;

import com.softcomp.fuzzy.inference.operators.SNorm;
import com.softcomp.fuzzy.inference.operators.TNorm;
import com.softcomp.fuzzy.models.FuzzifiedInputs;
import com.softcomp.fuzzy.models.LinguisticVariable;
import com.softcomp.fuzzy.models.Pair;
import com.softcomp.fuzzy.models.RuleBase;

public interface IDefuzzifierSugeno {
    /**
     * Converts aggregated fuzzy output values into a crisp number.
     * @param aggregatedOutput map of fuzzySetName → membershipDegree
     * @param ruleOutput
     * @return crisp output value
     */
    double defuzzify(List<Pair<Double,Double>> pairs);
}