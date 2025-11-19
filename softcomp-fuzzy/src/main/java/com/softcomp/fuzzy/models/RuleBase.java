package com.softcomp.fuzzy.models;

public class RuleBase {
    private java.util.List<Rule> rules = new java.util.ArrayList<>();


    public void addRule(Rule r) { rules.add(r); }
    public java.util.List<Rule> getRules() { return rules; }
}