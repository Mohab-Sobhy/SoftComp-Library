package com.softcomp.fuzzy.inference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.softcomp.fuzzy.defuzzification.IDefuzzifierSugeno;
import com.softcomp.fuzzy.defuzzification.WeightedAverageSugeno;
import com.softcomp.fuzzy.inference.operators.SNorm;
import com.softcomp.fuzzy.inference.operators.TNorm;
import com.softcomp.fuzzy.models.FuzzifiedInputs;
import com.softcomp.fuzzy.models.Pair;
import com.softcomp.fuzzy.models.Rule;
import com.softcomp.fuzzy.models.RuleBase;

public class SugenoEngine implements IInferenceEngine {
    private SNorm OR;
    private TNorm AND;
    private IDefuzzifierSugeno defuzzifierSugeno = new WeightedAverageSugeno();

    public SugenoEngine(TNorm AND, SNorm OR) {
        this.AND = AND;
        this.OR = OR;
    }

    public SugenoEngine(TNorm AND, SNorm OR,IDefuzzifierSugeno defuzzifierSugeno) {
        this.AND = AND;
        this.OR = OR;
        this.defuzzifierSugeno=defuzzifierSugeno;
    }


    @Override
    public Map<String, Double> infer(RuleBase rules, FuzzifiedInputs inputs, String outputName) {
        Map<String, Double> aggregated = new HashMap<>();
        List<Pair<Double, Double>> pairs = new ArrayList<>();

        for (Rule rule : rules.getRules()) {
            if (!rule.isEnabled() || !rule.getConsequent().first.equals(outputName)) {
                continue;
            }
            pairs.add(new Pair<>(rule.getCrispValue(), rule.evaluate(inputs, AND, OR)));
        }

        aggregated.put(outputName, defuzzifierSugeno.defuzzify(pairs));
        return aggregated;
    }

    public SNorm getOR() {
        return OR;
    }

    public void setOR(SNorm OR) {
        this.OR = OR;
    }

    public TNorm getAND() {
        return AND;
    }

    public void setAND(TNorm AND) {
        this.AND = AND;
    }

    public IDefuzzifierSugeno getDefuzzifierSugeno() {
        return defuzzifierSugeno;
    }

    public void setDefuzzifierSugeno(IDefuzzifierSugeno defuzzifierSugeno) {
        this.defuzzifierSugeno = defuzzifierSugeno;
    }
}
