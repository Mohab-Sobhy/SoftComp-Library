package com.softcomp.fuzzy.defuzzification;

import java.util.List;

import com.softcomp.fuzzy.models.Pair;

public interface IDefuzzifierSugeno {

    double defuzzify(List<Pair<Double, Double>> pairs);
}