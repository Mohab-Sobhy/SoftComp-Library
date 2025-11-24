package com.softcomp.fuzzy;

import com.softcomp.fuzzy.fuzzification.Fuzzifier;
import com.softcomp.fuzzy.inference.MamdaniEngine;
import com.softcomp.fuzzy.inference.SugenoEngine;
import com.softcomp.fuzzy.inference.operators.SNorm;
import com.softcomp.fuzzy.inference.operators.TNorm;
import com.softcomp.fuzzy.membership.TriangularMF;
import com.softcomp.fuzzy.membership.TrapezoidalMF;
import com.softcomp.fuzzy.models.*;

import java.util.Map;

public class App {

    public static void main(String[] args) {
        System.out.println("=== Fuzzy Logic System Test ===\n");

        // Test 1: Mamdani Inference - Tip Calculator
        System.out.println("TEST 1: MAMDANI INFERENCE - TIP CALCULATOR");
        System.out.println("-".repeat(50));
        testMamdaniTipCalculator();

        System.out.println("\n" + "=".repeat(50) + "\n");

        // Test 2: Sugeno Inference - Temperature Control
        System.out.println("TEST 2: SUGENO INFERENCE - TEMPERATURE CONTROL");
        System.out.println("-".repeat(50));
        testSugenoTemperatureControl();
    }

    /**
     * Test Mamdani inference with a tip calculator example
     * Inputs: service quality, food quality
     * Output: tip percentage
     */
    private static void testMamdaniTipCalculator() {
        // Define input variables
        LinguisticVariable service = new LinguisticVariable("service", 0, 10);
        service.addFuzzySet(new FuzzySet("poor", new TriangularMF(0, 0, 5)));
        service.addFuzzySet(new FuzzySet("good", new TriangularMF(0, 5, 10)));
        service.addFuzzySet(new FuzzySet("excellent", new TriangularMF(5, 10, 10)));

        LinguisticVariable food = new LinguisticVariable("food", 0, 10);
        food.addFuzzySet(new FuzzySet("bad", new TriangularMF(0, 0, 5)));
        food.addFuzzySet(new FuzzySet("decent", new TriangularMF(0, 5, 10)));
        food.addFuzzySet(new FuzzySet("delicious", new TriangularMF(5, 10, 10)));

        // Define output variable
        LinguisticVariable tip = new LinguisticVariable("tip", 0, 30);
        tip.addFuzzySet(new FuzzySet("low", new TriangularMF(0, 5, 10)));
        tip.addFuzzySet(new FuzzySet("medium", new TriangularMF(10, 15, 20)));
        tip.addFuzzySet(new FuzzySet("high", new TriangularMF(20, 25, 30)));

        // Create rule base
        RuleBase ruleBase = new RuleBase();

        // Rule 1: IF service is poor OR food is bad THEN tip is low
        ruleBase.addRule(new Rule(
                new Triplet<>("service", "poor", true),
                new Triplet<>("food", "bad", true),
                new Triplet<>("tip", "low", true),
                "OR"));

        // Rule 2: IF service is good AND food is decent THEN tip is medium
        ruleBase.addRule(new Rule(
                new Triplet<>("service", "good", true),
                new Triplet<>("food", "decent", true),
                new Triplet<>("tip", "medium", true),
                "AND"));

        // Rule 3: IF service is excellent AND food is delicious THEN tip is high
        ruleBase.addRule(new Rule(
                new Triplet<>("service", "excellent", true),
                new Triplet<>("food", "delicious", true),
                new Triplet<>("tip", "high", true),
                "AND"));

        // Rule 4: IF service is excellent THEN tip is high
        ruleBase.addRule(new Rule(
                new Triplet<>("service", "excellent", true),
                new Triplet<>("tip", "high", true),
                "AND"));

        // Test with crisp inputs
        double serviceValue = 7.5;
        double foodValue = 8.0;

        System.out.println("Input values:");
        System.out.println("  Service quality: " + serviceValue + "/10");
        System.out.println("  Food quality: " + foodValue + "/10");
        System.out.println();

        // Fuzzification
        Fuzzifier fuzzifier = new Fuzzifier();
        FuzzifiedInputs inputs = new FuzzifiedInputs();
        inputs.addVariable(fuzzifier.fuzzify(service, serviceValue));
        inputs.addVariable(fuzzifier.fuzzify(food, foodValue));

        // Display fuzzified values
        System.out.println("Fuzzified values:");
        displayFuzzifiedValues(inputs.get("service"));
        displayFuzzifiedValues(inputs.get("food"));
        System.out.println();

        // Inference with Mamdani
        TNorm andOp = (a, b) -> Math.min(a, b); // Min T-Norm
        SNorm orOp = (a, b) -> Math.max(a, b); // Max S-Norm
        MamdaniEngine engine = new MamdaniEngine(andOp, orOp);

        Map<String, Double> result = engine.infer(ruleBase, inputs, "tip");

        System.out.println("Inference results (firing strengths):");
        for (Map.Entry<String, Double> entry : result.entrySet()) {
            System.out.printf("  %s: %.4f%n", entry.getKey(), entry.getValue());
        }

        // Defuzzification (Centroid method)
        double defuzzified = defuzzifyCentroid(result, tip);
        System.out.println("\nDefuzzified tip: " + String.format("%.2f", defuzzified) + "%");
    }

    /**
     * Test Sugeno inference with temperature control example
     * Inputs: temperature error, rate of change
     * Output: heater power (crisp)
     */
    private static void testSugenoTemperatureControl() {
        // Define input variables
        LinguisticVariable tempError = new LinguisticVariable("error", -10, 10);
        tempError.addFuzzySet(new FuzzySet("negative", new TrapezoidalMF(-10, -10, -5, 0)));
        tempError.addFuzzySet(new FuzzySet("zero", new TriangularMF(-5, 0, 5)));
        tempError.addFuzzySet(new FuzzySet("positive", new TrapezoidalMF(0, 5, 10, 10)));

        LinguisticVariable changeRate = new LinguisticVariable("rate", -5, 5);
        changeRate.addFuzzySet(new FuzzySet("decreasing", new TrapezoidalMF(-5, -5, -2, 0)));
        changeRate.addFuzzySet(new FuzzySet("stable", new TriangularMF(-2, 0, 2)));
        changeRate.addFuzzySet(new FuzzySet("increasing", new TrapezoidalMF(0, 2, 5, 5)));

        // Create Sugeno rule base (with crisp consequents)
        RuleBase sugenoRules = new RuleBase();

        // Rule 1: IF error is negative AND rate is decreasing THEN power = 0
        sugenoRules.addRule(new Rule(
                new Triplet<>("error", "negative", true),
                new Triplet<>("rate", "decreasing", true),
                new Triplet<>("power", "output", true),
                "AND",
                0.0));

        // Rule 2: IF error is negative AND rate is stable THEN power = 20
        sugenoRules.addRule(new Rule(
                new Triplet<>("error", "negative", true),
                new Triplet<>("rate", "stable", true),
                new Triplet<>("power", "output", true),
                "AND",
                20.0));

        // Rule 3: IF error is zero THEN power = 50
        sugenoRules.addRule(new Rule(
                new Triplet<>("error", "zero", true),
                new Triplet<>("power", "output", true),
                "AND",
                50.0));

        // Rule 4: IF error is positive AND rate is stable THEN power = 80
        sugenoRules.addRule(new Rule(
                new Triplet<>("error", "positive", true),
                new Triplet<>("rate", "stable", true),
                new Triplet<>("power", "output", true),
                "AND",
                80.0));

        // Rule 5: IF error is positive AND rate is increasing THEN power = 100
        sugenoRules.addRule(new Rule(
                new Triplet<>("error", "positive", true),
                new Triplet<>("rate", "increasing", true),
                new Triplet<>("power", "output", true),
                "AND",
                100.0));

        // Test with crisp inputs
        double errorValue = 3.0; // Positive error (room is cold)
        double rateValue = 1.0; // Slightly increasing

        System.out.println("Input values:");
        System.out.println("  Temperature error: " + errorValue + "°C");
        System.out.println("  Rate of change: " + rateValue + "°C/min");
        System.out.println();

        // Fuzzification
        Fuzzifier fuzzifier = new Fuzzifier();
        FuzzifiedInputs inputs = new FuzzifiedInputs();
        inputs.addVariable(fuzzifier.fuzzify(tempError, errorValue));
        inputs.addVariable(fuzzifier.fuzzify(changeRate, rateValue));

        // Display fuzzified values
        System.out.println("Fuzzified values:");
        displayFuzzifiedValues(inputs.get("error"));
        displayFuzzifiedValues(inputs.get("rate"));
        System.out.println();

        // Inference with Sugeno
        TNorm andOp = (a, b) -> Math.min(a, b);
        SNorm orOp = (a, b) -> Math.max(a, b);
        SugenoEngine sugenoEngine = new SugenoEngine(andOp, orOp);

        Map<String, Double> sugenoResult = sugenoEngine.infer(sugenoRules, inputs, "power");

        System.out.println("Sugeno output (weighted average):");
        for (Map.Entry<String, Double> entry : sugenoResult.entrySet()) {
            System.out.printf("  Heater power: %.2f%%%n", entry.getValue());
        }
    }

    /**
     * Display fuzzified membership values
     */
    private static void displayFuzzifiedValues(FuzzifiedVariable var) {
        System.out.println("  " + var.getVariableName() + ":");
        for (Map.Entry<String, Double> entry : var.getMemberships().entrySet()) {
            System.out.printf("    %s: %.4f%n", entry.getKey(), entry.getValue());
        }
    }

    /**
     * Centroid defuzzification method for Mamdani
     */
    private static double defuzzifyCentroid(Map<String, Double> aggregated, LinguisticVariable outputVar) {
        double numerator = 0.0;
        double denominator = 0.0;

        for (Map.Entry<String, Double> entry : aggregated.entrySet()) {
            String setName = entry.getKey();
            double strength = entry.getValue();

            FuzzySet fuzzySet = outputVar.getSets().get(setName);
            if (fuzzySet != null) {
                double centroid = fuzzySet.getCentroid();
                numerator += strength * centroid;
                denominator += strength;
            }
        }

        return denominator != 0 ? numerator / denominator : 0.0;
    }
}