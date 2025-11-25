package com.softcomp.fuzzy.fuzzification;

import com.softcomp.fuzzy.models.FuzzifiedVariable;
import com.softcomp.fuzzy.models.FuzzySet;
import com.softcomp.fuzzy.models.LinguisticVariable;

import java.util.List;

public class Fuzzifier {

    public FuzzifiedVariable fuzzify(LinguisticVariable variable, double crispValue) {
        FuzzifiedVariable fuzzifiedVariable = new FuzzifiedVariable(variable.getName());
        List<FuzzySet> fuzzysets = variable.getAllFuzzysets();
        for (FuzzySet fuzzySet : fuzzysets) {
            fuzzifiedVariable.addMembership(fuzzySet.getName(), fuzzySet.compute(crispValue));
        }
        return fuzzifiedVariable;
    }
}