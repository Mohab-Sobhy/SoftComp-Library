package com.softcomp.fuzzy.defuzzification;

import java.util.List;


import com.softcomp.fuzzy.models.Pair;


public class WeightedAverageSugeno implements IDefuzzifierSugeno {

    @Override
    public double defuzzify(List<Pair<Double,Double>> pairs) {

        double numerator = 0;
        double denominator = 0;

        for (Pair<Double,Double> pair :pairs) {

            
            numerator = numerator + (pair.first * pair.second);
            denominator = denominator + pair.second;
        }
        if(denominator == 0) return 0;
        return numerator / denominator;
    }

    
}
