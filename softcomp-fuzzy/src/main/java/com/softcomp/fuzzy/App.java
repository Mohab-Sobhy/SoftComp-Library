package com.softcomp.fuzzy;

import com.softcomp.fuzzy.FuzzyProgram;
import com.softcomp.fuzzy.models.Rule;
import com.softcomp.fuzzy.models.Triplet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {
        private static final String RULES_FILE = "fire_risk_rules.txt";

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("please enter temperature from 0 to 100");
        Double temp = scanner.nextDouble();
        System.out.println("please enter smoke density from 0 to 100");
        Double smoke = scanner.nextDouble();
        System.out.println("please enter gas concentration from 0 to 100");
        Double gas = scanner.nextDouble();

        Map<String, Double> values = new HashMap<>();
        values.put("Temperature", temp);
        values.put("SmokeDensity", smoke);
        values.put("GasConcentration", gas);

        FuzzyProgram fuzzyProgram = new FuzzyProgram(
                List.of("Temperature", "SmokeDensity", "GasConcentration"),
                "FireRisk"
        );
        fuzzyProgram.loadRulesFromFile(RULES_FILE);
        Double crispValue = fuzzyProgram.runFuzzy(values);
        System.out.println("risk is " + crispValue);
    }
}
