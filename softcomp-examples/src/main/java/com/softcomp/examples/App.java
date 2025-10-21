package com.softcomp.examples;

import com.softcomp.examples.graphcoloring.*;
import com.softcomp.ga.LoggerService;
import com.softcomp.ga.app.GAConfig;
import com.softcomp.ga.crossover.OrderCrossover;
import com.softcomp.ga.crossover.UniformCrossover;
import com.softcomp.ga.mutation.OptionsFlipMutation;
import com.softcomp.ga.replacement.ElitismReplacement;
import com.softcomp.ga.replacement.RandomReplacement;
import com.softcomp.ga.selection.TournamentSelection;

import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        System.out.println("softcomp-examples module started!");

        RandomGraphGenerator graphGenerator = RandomGraphGenerator.getInstance();
        RandomIndividualGenerator individualGenerator = RandomIndividualGenerator.getInstance();
        LoggerService loggerService = LoggerService.getInstance();

        int numOfNodes = 10;
        double edgeProbability = 0.6;
        int numOfColors = 8;
        List<Integer> colorOptions = new ArrayList<>();

        for (int i = 0; i < numOfColors; i++) {
            colorOptions.add(i);
        }

        GAConfig<Integer> gaConfig = new GAConfig<>(
                4,
                200,
                new TournamentSelection<>(2),
                new UniformCrossover<>(0.9),
                new OptionsFlipMutation<>(0.1, colorOptions),
                new ElitismReplacement<>(1),
                null, // fitnessFunction (set during runtime)
                null // no need for feasibility function
        );

        GraphColoringApp app = new GraphColoringApp(
                gaConfig,
                graphGenerator,
                individualGenerator,
                loggerService,
                numOfNodes,
                edgeProbability,
                numOfColors
        );

        app.run();

        app.openVisualizer("visualizer.html");
    }

}
