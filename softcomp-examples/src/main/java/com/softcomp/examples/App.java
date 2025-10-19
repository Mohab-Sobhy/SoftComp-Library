package com.softcomp.examples;

import com.softcomp.examples.graphcoloring.*;
import com.softcomp.ga.LoggerService;
import com.softcomp.ga.app.GAConfig;
import com.softcomp.ga.crossover.UniformCrossover;
import com.softcomp.ga.mutation.OptionsFlipMutation;
import com.softcomp.ga.replacement.ElitismReplacement;
import com.softcomp.ga.replacement.GenerationalReplacement;
import com.softcomp.ga.replacement.RandomReplacement;
import com.softcomp.ga.replacement.SteadyStateReplacement;
import com.softcomp.ga.selection.TournamentSelection;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        System.out.println("softcomp-examples module started!");

        RandomGraphGenerator graphGenerator = RandomGraphGenerator.getInstance();
        RandomIndividualGenerator individualGenerator = RandomIndividualGenerator.getInstance();
        LoggerService loggerService = LoggerService.getInstance();

        int numOfNodes = 3;
        double edgeProbability = 0.5;
        int numOfColors = 3;
        List<Integer> colorOptions = new ArrayList<>();

        for (int i = 0; i < numOfColors; i++) {
            colorOptions.add(i);
        }

        GAConfig<Integer> gaConfig = new GAConfig<>(
                2,
                30,
                new TournamentSelection<>(2),
                new UniformCrossover<>(0.9),
                new OptionsFlipMutation<>(0.9, colorOptions),
                new RandomReplacement<>(),
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

        openVisualizer("visualizer.html");
    }

    private static void openVisualizer(String filePath) {
        try {
            File htmlFile = new File(filePath);
            if (!htmlFile.exists()) {
                System.err.println("❌ visualizer.html not found at: " + filePath);
                return;
            }

            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(htmlFile.toURI());
                System.out.println("Opened visualizer in default browser.");
            } else {
                System.err.println("Desktop browsing not supported on this platform.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
