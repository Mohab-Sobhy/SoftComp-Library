package com.softcomp.examples.graphcoloring;

import com.softcomp.examples.graphcoloring.models.Graph;
import com.softcomp.ga.LoggerService;
import com.softcomp.ga.app.GAConfig;
import com.softcomp.ga.app.GeneticAlgorithm;
import com.softcomp.ga.models.Individual;
import com.softcomp.ga.models.Population;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GraphColoringApp {
    private final GAConfig<Integer> gaConfig;
    private final RandomGraphGenerator graphGenerator;
    private final RandomIndividualGenerator individualGenerator;
    private final LoggerService logger;
    private final int numOfNodes;
    private final double edgeProbability;
    private final int numOfColors;


    public GraphColoringApp(
            GAConfig<Integer> gaConfig,
            RandomGraphGenerator graphGenerator,
            RandomIndividualGenerator individualGenerator,
            LoggerService loggerService,
            int numOfNodes,
            double edgeProbability,
            int numOfColors
    )
    {
        this.gaConfig = gaConfig;
        this.graphGenerator = graphGenerator;
        this.individualGenerator = individualGenerator;
        this.logger = loggerService;
        this.numOfNodes = numOfNodes;
        this.edgeProbability = edgeProbability;
        this.numOfColors = numOfColors;
    }

    public Population<Integer> initializePopulation(int numberOfIndividuals, int numberOfVertices, int numberOfColors) {
        Population<Integer> population = new Population<>();

        for (int i = 0; i < numberOfIndividuals; i++) {
            Individual<Integer> individual = individualGenerator.generateRandomIndividual(numberOfVertices, numberOfColors);
            population.addIndividual( individual );
        }

        return population;
    }

    public void run() {
        Graph graph = graphGenerator.generateRandomGraph(numOfNodes, edgeProbability, numOfColors);
        logger.log( graph.getGraphStructure() );

        Population<Integer> population = initializePopulation(gaConfig.getPopulationSize(), graph.getNumberOfVertices(), graph.getNumColors());

        gaConfig.setFitnessFunction(new GraphColoringFitnessFunction(graph , 10.0 , 0.05));

        GeneticAlgorithm<Integer> geneticAlgorithm = new GeneticAlgorithm<>(gaConfig , logger);

        geneticAlgorithm.run(population);
    }

    public static void openVisualizer(String filePath) {
        try {
            File htmlFile = new File(filePath);
            if (!htmlFile.exists()) {
                System.err.println("visualizer.html not found at: " + filePath);
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
