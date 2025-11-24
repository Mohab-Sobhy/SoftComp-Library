package com.softcomp.fuzzy;

import java.util.ArrayList;
import java.util.List;

import com.softcomp.fuzzy.defuzzification.IDefuzzifierMamdani;
import com.softcomp.fuzzy.defuzzification.IDefuzzifierSugeno;
import com.softcomp.fuzzy.defuzzification.WeightedAverageMamdani;
import com.softcomp.fuzzy.defuzzification.WeightedAverageSugeno;
import com.softcomp.fuzzy.fuzzification.Fuzzifier;
import com.softcomp.fuzzy.inference.IInferenceEngine;
import com.softcomp.fuzzy.inference.MamdaniEngine;
import com.softcomp.fuzzy.inference.operators.SNormMax;
import com.softcomp.fuzzy.inference.operators.TNormMin;
import com.softcomp.fuzzy.membership.TriangularMF;
import com.softcomp.fuzzy.models.FuzzySet;
import com.softcomp.fuzzy.models.LinguisticVariable;

public class FuzzyProgram {

    private List<LinguisticVariable> linguisticInputs;
    private LinguisticVariable linguisticOutput;

    private Fuzzifier fuzzifier = new Fuzzifier();
    private IInferenceEngine engine = new MamdaniEngine(new TNormMin(), new SNormMax());

    private IDefuzzifierMamdani defuzzifierMamdani = new WeightedAverageMamdani();
    private IDefuzzifierSugeno defuzzifierSugeno = new WeightedAverageSugeno();

    // true = Mamdani, false = Sugeno
    private boolean defuzzifierChoice = true;

    // ----------------------------------------------------
    // Constructors
    // ----------------------------------------------------

    /** Minimal constructor: only input + output variables */
    public FuzzyProgram(List<LinguisticVariable> linguisticInputs,
            LinguisticVariable linguisticOutput) {
        this.linguisticInputs = linguisticInputs;
        this.linguisticOutput = linguisticOutput;
    }

    public FuzzyProgram(List<String> inputs, String outputName) {
        linguisticInputs = new ArrayList<>();

        for (String name : inputs) {
            LinguisticVariable lv = new LinguisticVariable(name, 0, 100);

            lv.addFuzzySet(new FuzzySet("very low", new TriangularMF(0, 0, 25)));
            lv.addFuzzySet(new FuzzySet("low", new TriangularMF(0, 25, 50)));
            lv.addFuzzySet(new FuzzySet("medium", new TriangularMF(25, 50, 75)));
            lv.addFuzzySet(new FuzzySet("high", new TriangularMF(50, 75, 100)));
            lv.addFuzzySet(new FuzzySet("very high", new TriangularMF(75, 100, 100)));

            linguisticInputs.add(lv);
        }

        linguisticOutput = new LinguisticVariable(outputName, 0, 100);
        linguisticOutput.addFuzzySet(new FuzzySet("very low", new TriangularMF(0, 0, 25)));
        linguisticOutput.addFuzzySet(new FuzzySet("low", new TriangularMF(0, 25, 50)));
        linguisticOutput.addFuzzySet(new FuzzySet("medium", new TriangularMF(25, 50, 75)));
        linguisticOutput.addFuzzySet(new FuzzySet("high", new TriangularMF(50, 75, 100)));
        linguisticOutput.addFuzzySet(new FuzzySet("very high", new TriangularMF(75, 100, 100)));
    }

    /** Full constructor: set everything in one shot */
    public FuzzyProgram(
            List<LinguisticVariable> linguisticInputs,
            LinguisticVariable linguisticOutput,
            Fuzzifier fuzzifier,
            IInferenceEngine engine,
            IDefuzzifierMamdani defuzzifierMamdani,
            IDefuzzifierSugeno defuzzifierSugeno,
            boolean defuzzifierChoice) {

        this.linguisticInputs = linguisticInputs;
        this.linguisticOutput = linguisticOutput;
        this.fuzzifier = fuzzifier;
        this.engine = engine;
        this.defuzzifierMamdani = defuzzifierMamdani;
        this.defuzzifierSugeno = defuzzifierSugeno;
        this.defuzzifierChoice = defuzzifierChoice;
    }

    // ----------------------------------------------------
    // Getters & Setters
    // ----------------------------------------------------

    public List<LinguisticVariable> getLinguisticInputs() {
        return linguisticInputs;
    }

    public void setLinguisticInputs(List<LinguisticVariable> linguisticInputs) {
        this.linguisticInputs = linguisticInputs;
    }

    public LinguisticVariable getLinguisticOutput() {
        return linguisticOutput;
    }

    public void setLinguisticOutput(LinguisticVariable linguisticOutput) {
        this.linguisticOutput = linguisticOutput;
    }

    public Fuzzifier getFuzzifier() {
        return fuzzifier;
    }

    public void setFuzzifier(Fuzzifier fuzzifier) {
        this.fuzzifier = fuzzifier;
    }

    public IInferenceEngine getEngine() {
        return engine;
    }

    public void setEngine(IInferenceEngine engine) {
        this.engine = engine;
    }

    public IDefuzzifierMamdani getDefuzzifierMamdani() {
        return defuzzifierMamdani;
    }

    public void setDefuzzifierMamdani(IDefuzzifierMamdani defuzzifierMamdani) {
        this.defuzzifierMamdani = defuzzifierMamdani;
    }

    public IDefuzzifierSugeno getDefuzzifierSugeno() {
        return defuzzifierSugeno;
    }

    public void setDefuzzifierSugeno(IDefuzzifierSugeno defuzzifierSugeno) {
        this.defuzzifierSugeno = defuzzifierSugeno;
    }

    public boolean getDefuzzifierChoice() {
        return defuzzifierChoice;
    }

    public void setDefuzzifierChoice(boolean defuzzifierChoice) {
        this.defuzzifierChoice = defuzzifierChoice;
    }

    public Double runFuzzy() {
        return 0.0;
    }
}
