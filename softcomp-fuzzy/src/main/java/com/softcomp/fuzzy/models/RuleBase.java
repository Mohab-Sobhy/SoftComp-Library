package com.softcomp.fuzzy.models;

import java.util.ArrayList;
import java.util.List;

public class RuleBase {
    private List<Rule> rules = new ArrayList<>();

    public void addRule(Rule r) { rules.add(r); }

    public List<Rule> getRules() { return rules; }

    public void setRules(List<Rule> rules){
        this.rules=rules;
    }

    public boolean removeRule(Rule r) {
        return rules.remove(r);
    }

    public Rule removeRuleAt(int index) {
        return rules.remove(index);
    }
}