package com.softcomp.fuzzy.models;

import com.softcomp.fuzzy.inference.operators.SNorm;
import com.softcomp.fuzzy.inference.operators.TNorm;

public class Rule {

    // Antecedents (inputs)
    private Triplet<String, String, Boolean> antecedent1;
    private Triplet<String, String, Boolean> antecedent2; // optional

    // Output (consequence)
    private Triplet<String, String, Boolean> consequent;

    private String operator; // AND / OR
    private double weight = 1.0;
    private boolean enabled = true;
    private Double crispValue = 0.0;

    // Constructor for 2-antecedent rule
    public Rule(
            Triplet<String, String, Boolean> antecedent1,
            Triplet<String, String, Boolean> antecedent2,
            Triplet<String, String, Boolean> consequent,
            String operator) {
        this.antecedent1 = antecedent1;
        this.antecedent2 = antecedent2;
        this.consequent = consequent;
        this.operator = operator;
    }

    // Constructor for single-antecedent rule
    public Rule(
            Triplet<String, String, Boolean> antecedent1,
            Triplet<String, String, Boolean> consequent,
            String operator) {
        this.antecedent1 = antecedent1;
        this.antecedent2 = null;
        this.consequent = consequent;
        this.operator = operator;
    }

    public Rule(
            Triplet<String, String, Boolean> antecedent1,
            Triplet<String, String, Boolean> antecedent2,
            Triplet<String, String, Boolean> consequent,
            String operator, Double crispValue) {
        this.antecedent1 = antecedent1;
        this.antecedent2 = antecedent2;
        this.consequent = consequent;
        this.operator = operator;
        this.crispValue = crispValue;
    }

    // Constructor for single-antecedent rule
    public Rule(
            Triplet<String, String, Boolean> antecedent1,
            Triplet<String, String, Boolean> consequent,
            String operator, Double crispValue) {
        this.antecedent1 = antecedent1;
        this.antecedent2 = null;
        this.consequent = consequent;
        this.operator = operator;
        this.crispValue = crispValue;
    }

    // Getters (optional but clean)
    public Triplet<String, String, Boolean> getAntecedent1() {
        return antecedent1;
    }

    public Triplet<String, String, Boolean> getAntecedent2() {
        return antecedent2;
    }

    public Triplet<String, String, Boolean> getConsequent() {
        return consequent;
    }

    public String getOperator() {
        return operator;
    }

    public double getWeight() {
        return weight;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Double getCrispValue() {
        return crispValue * weight;
    }

    public double evaluate(FuzzifiedInputs inputs, TNorm AND, SNorm OR) {
        double val1 = inputs.get(antecedent1.first).getMemberShipValue(antecedent1.second);
        val1 = antecedent1.third ? val1 : 1 - val1;

        double alpha = val1;

        if (antecedent2 != null) {
            double val2 = inputs.get(antecedent2.first).getMemberShipValue(antecedent2.second);
            val2 = antecedent2.third ? val2 : 1 - val2;

            alpha = operator.equals("AND") ? AND.apply(val1, val2) : OR.apply(val1, val2);
        }

        // firing strength * weight
        return alpha * weight;
    }
    public void setEnabled(boolean enable){
        this.enabled=enable;
    }

    @Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Rule)) return false;
    Rule r = (Rule) o;
    return operator.equals(r.operator)
        && weight == r.weight
        && crispValue.equals(r.crispValue)
        && antecedentEquals(antecedent1, r.antecedent1)
        && antecedentEquals(antecedent2, r.antecedent2)
        && antecedentEquals(consequent, r.consequent);
}
private boolean antecedentEquals(Triplet<String,String,Boolean> a, Triplet<String,String,Boolean> b) {
    if (a == null && b == null) return true;
    if (a == null || b == null) return false;
    return a.first.equals(b.first) &&
           a.second.equals(b.second) &&
           a.third.equals(b.third);
}
 private String formatTriplet(Triplet<String, String, Boolean> triplet) {
        if (triplet == null) {
            return null;
        }
        String variable = triplet.first;
        String fuzzySet = triplet.second;
        boolean isPositive = triplet.third; // true for IS, false for IS NOT (negation)

        StringBuilder sb = new StringBuilder();
        sb.append("(").append(variable).append(" is ");
        if (!isPositive) {
            sb.append("NOT ");
        }
        sb.append(fuzzySet).append(")");
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // --- 1. Antecedent (IF part) ---
        sb.append("IF ");
        sb.append(formatTriplet(antecedent1));

        // Append second antecedent if it exists
        if (antecedent2 != null) {
            sb.append(" ").append(operator).append(" ");
            sb.append(formatTriplet(antecedent2));
        }

        // --- 2. Consequent (THEN part) ---
        sb.append(" THEN ");
        sb.append(formatTriplet(consequent));

        // --- 3. Crisp Value and Weight ---
        // Assuming this is a Sugeno-type consequence (using crispValue)
        sb.append(" [Crisp Value: ").append(String.format("%.2f", crispValue));
        
        // Add weight if it's not the default 1.0
        if (weight != 1.0) {
            sb.append(" | Weight: ").append(String.format("%.2f", weight));
        }

        // Add enabled status if disabled
        if (!enabled) {
            sb.append(" | DISABLED");
        }
        sb.append("]");

        return sb.toString();
    }
}
