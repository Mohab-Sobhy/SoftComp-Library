package com.softcomp.examples;

import com.softcomp.examples.graphcoloring.*;
import com.softcomp.ga.app.GAConfig;
import com.softcomp.ga.crossover.UniformCrossover;
import com.softcomp.ga.mutation.OptionsFlipMutation;
import com.softcomp.ga.replacement.ElitismReplacement;
import com.softcomp.ga.selection.TournamentSelection;

import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello from softcomp-examples module!");

        RandomGraphGenerator graphGenerator = RandomGraphGenerator.getInstance();
        RandomIndividualGenerator individualGenerator = RandomIndividualGenerator.getInstance();

        int numOfNodes = 10;
        double edgeProbability = 0.35;
        int numOfColors = 4;
        List<Integer> colorOptions = new ArrayList<>();

        for (int i = 0; i < numOfColors; i++) {
            colorOptions.add(i);
        }

        GAConfig<Integer> gaConfig = new GAConfig<>(
                100,
                200,
                0.8,
                0.2,
                new TournamentSelection<>(),
                new UniformCrossover<>(0.8),
                new OptionsFlipMutation<>(0.05, colorOptions),
                new ElitismReplacement<>(),
                null, //during runtime
                null
        );

        GraphColoringApp app = new GraphColoringApp(
                gaConfig,
                graphGenerator,
                individualGenerator,
                numOfNodes,
                edgeProbability,
                numOfColors
        );

        app.run();
    }
}
