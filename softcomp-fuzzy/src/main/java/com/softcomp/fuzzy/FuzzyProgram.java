package com.softcomp.fuzzy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.softcomp.fuzzy.defuzzification.IDefuzzifierMamdani;
import com.softcomp.fuzzy.defuzzification.IDefuzzifierSugeno;
import com.softcomp.fuzzy.defuzzification.WeightedAverageMamdani;
import com.softcomp.fuzzy.defuzzification.WeightedAverageSugeno;
import com.softcomp.fuzzy.fuzzification.Fuzzifier;
import com.softcomp.fuzzy.inference.IInferenceEngine;
import com.softcomp.fuzzy.inference.MamdaniEngine;
import com.softcomp.fuzzy.inference.SugenoEngine;
import com.softcomp.fuzzy.inference.operators.SNormMax;
import com.softcomp.fuzzy.inference.operators.TNormMin;
import com.softcomp.fuzzy.membership.TriangularMF;
import com.softcomp.fuzzy.models.FuzzifiedInputs;
import com.softcomp.fuzzy.models.FuzzySet;
import com.softcomp.fuzzy.models.LinguisticVariable;
import com.softcomp.fuzzy.models.Rule;
import com.softcomp.fuzzy.models.RuleBase;

public class FuzzyProgram {
    private static final Gson gson = new GsonBuilder().create();

    private List<LinguisticVariable> linguisticInputs;
    private LinguisticVariable linguisticOutput;
    private RuleBase ruleBase = new RuleBase();

    private Fuzzifier fuzzifier = new Fuzzifier();
    private IInferenceEngine engine = new SugenoEngine(new TNormMin(), new SNormMax());

    private IDefuzzifierMamdani defuzzifierMamdani = new WeightedAverageMamdani();
    private IDefuzzifierSugeno defuzzifierSugeno = new WeightedAverageSugeno();

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

    public FuzzyProgram(
            List<LinguisticVariable> linguisticInputs,
            LinguisticVariable linguisticOutput,
            Fuzzifier fuzzifier,
            IInferenceEngine engine,
            IDefuzzifierMamdani defuzzifierMamdani,
            IDefuzzifierSugeno defuzzifierSugeno) {

        this.linguisticInputs = linguisticInputs;
        this.linguisticOutput = linguisticOutput;
        this.fuzzifier = fuzzifier;
        this.engine = engine;
        this.defuzzifierMamdani = defuzzifierMamdani;
        this.defuzzifierSugeno = defuzzifierSugeno;
    }

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

    public void setRuleBase(RuleBase ruleBase) {
        this.ruleBase = ruleBase;
    }

    public RuleBase getRuleBase() {
        return ruleBase;
    }

    public void AddRule(Rule rule) {
        ruleBase.addRule(rule);
    }

    public void removeRule(Rule rule) {
        ruleBase.removeRule(rule);
    }

    public void removeRuleAt(int index) {
        ruleBase.removeRuleAt(index);
    }

    public void editRule(Rule oldRule, Rule newRule) {
        ruleBase.removeRule(oldRule);
        ruleBase.addRule(newRule);
    }

    public void enableRule(Rule rule) {
        Rule newRule = new Rule(rule.getAntecedent1(), rule.getAntecedent2(), rule.getOperator());
        ruleBase.removeRule(rule);
        ruleBase.addRule(newRule);
    }

    public void DisableRule(Rule rule) {
        Rule newRule = new Rule(rule.getAntecedent1(), rule.getAntecedent2(), rule.getOperator());
        newRule.setEnabled(false);
        ruleBase.removeRule(rule);
        ruleBase.addRule(newRule);
    }

    public void enableRuleIntoFile(Rule rule, String path) throws IOException {
        Rule newRule = new Rule(rule.getAntecedent1(), rule.getAntecedent2(), rule.getOperator());
        removeRuleFromFile(rule, path);
        addRuleAtFile(newRule, path);
    }

    public void DisableRuleIntoFile(Rule rule, String path) throws IOException {
        Rule newRule = new Rule(rule.getAntecedent1(), rule.getAntecedent2(), rule.getOperator());
        newRule.setEnabled(false);
        removeRuleFromFile(rule, path);
        addRuleAtFile(newRule, path);
    }

    public boolean checkIfRuleExistsInFile(Rule ruleToCheck, String path) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String rule;
            while ((rule = br.readLine()) != null) {
                Rule aRule = gson.fromJson(rule, Rule.class);
                if (ruleToCheck.equals(aRule)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void addRuleAtFile(Rule rule, String path) throws IOException {
        try (FileWriter writer = new FileWriter(path, true)) {
            if (!checkIfRuleExistsInFile(rule, path)) {
                gson.toJson(rule, writer);
                writer.write("\n");
            }
        }
    }

    public void loadRulesFromFile(String path) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String rule;
            while ((rule = br.readLine()) != null) {
                Rule aRule = gson.fromJson(rule, Rule.class);
                if (!ruleBase.getRules().contains(aRule))
                    ruleBase.addRule(aRule);
            }
        }
    }

    public void removeRuleFromFile(Rule ruleToBeDeleted, String path) throws IOException {
        List<String> rules = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String rule;
            while ((rule = br.readLine()) != null) {
                Rule aRule = gson.fromJson(rule, Rule.class);
                if (!ruleToBeDeleted.equals(aRule))
                    rules.add(rule);
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, false))) {
            for (String l : rules) {
                writer.write(l);
                writer.newLine();
            }
        }
    }

    public void editRuleFromFile(Rule oldRule, Rule newRule, String path) throws IOException {
        List<String> rules = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String rule;
            while ((rule = br.readLine()) != null) {
                Rule aRule = gson.fromJson(rule, Rule.class);
                if (oldRule.equals(aRule)) {
                    rules.add(gson.toJson(newRule));
                } else {
                    rules.add(rule);
                }
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, false))) {
            for (String l : rules) {
                writer.write(l);
                writer.newLine();
            }
        }
    }

    public LinguisticVariable getLinguisticInputByName(String name) {
        for (LinguisticVariable linguisticInput : linguisticInputs) {
            if (linguisticInput.getName().equals(name)) {
                return linguisticInput;
            }
        }
        return null;
    }

    public Double runFuzzy(Map<String, Double> values) {
        try {
            FuzzifiedInputs fuzzifiedInputs = new FuzzifiedInputs();
            for (Map.Entry<String, Double> entry : values.entrySet()) {
                if (entry.getKey() == null || entry.getValue() == null) {
                    throw new IllegalArgumentException("null values are not allowed");
                }
                LinguisticVariable variable = getLinguisticInputByName(entry.getKey());
                if (variable == null) {
                    throw new NoSuchElementException(entry.getKey() + " not found");
                }
                if (entry.getValue() < variable.getMin() || entry.getValue() > variable.getMax()) {
                    throw new IllegalArgumentException("the value of " + entry.getKey() + " is out of bounds");
                }
                fuzzifiedInputs.addVariable(fuzzifier.fuzzify(variable, entry.getValue()));
            }
            Map<String, Double> map = engine.infer(ruleBase, fuzzifiedInputs, linguisticOutput.getName());
            if (engine instanceof SugenoEngine) {
                return map.get(linguisticOutput.getName());
            }
            return defuzzifierMamdani.defuzzify(linguisticOutput, map);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
