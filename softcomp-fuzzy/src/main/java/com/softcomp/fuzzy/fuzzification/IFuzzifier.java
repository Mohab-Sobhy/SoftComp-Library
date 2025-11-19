package com.softcomp.fuzzy.fuzzification;

import com.softcomp.fuzzy.models.FuzzifiedVariable;
import com.softcomp.fuzzy.models.LinguisticVariable;
import java.util.Map;

public interface IFuzzifier {
    /**
     * Fuzzifies a crisp input based on the variable's fuzzy sets.
     */
    FuzzifiedVariable fuzzify(LinguisticVariable variable, double crispValue);
}