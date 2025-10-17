package com.softcomp.examples.graphcoloring;

import com.softcomp.examples.graphcoloring.models.Graph;
import com.softcomp.ga.app.GAConfig;
import com.softcomp.ga.app.GeneticAlgorithm;
import com.softcomp.ga.models.Individual;
import com.softcomp.ga.models.Population;

public class GraphColoringApp {
    private final GAConfig<Integer> gaConfig;
    private final RandomGraphGenerator graphGenerator;
    private final RandomIndividualGenerator individualGenerator;
    private final int numOfNodes;
    private final double edgeProbability;
    private final int numOfColors;


    public GraphColoringApp(
            GAConfig<Integer> gaConfig,
            RandomGraphGenerator graphGenerator,
            RandomIndividualGenerator individualGenerator,
            int numOfNodes,
            double edgeProbability,
            int numOfColors
    )
    {
        this.gaConfig = gaConfig;
        this.graphGenerator = graphGenerator;
        this.individualGenerator = individualGenerator;
        this.numOfNodes = numOfNodes;
        this.edgeProbability = edgeProbability;
        this.numOfColors = numOfColors;
    }

    public Population<Integer> initializePopulation(int numberOfIndividuals, int numberOfVertices, int numberOfColors) {
        Population<Integer> population = new Population<>();

        for (int i = 0; i < numberOfIndividuals; i++) {
            Individual<Integer> individual = individualGenerator.generateRandomIndividual(numberOfVertices, numberOfColors);
            System.out.println( individual ); // remember to remove it
            population.addIndividual( individual );
        }

        return population;
    }

    public void run() {
        Graph graph = graphGenerator.generateRandomGraph(numOfNodes, edgeProbability, numOfColors);

        Population<Integer> population = initializePopulation(gaConfig.getPopulationSize(), graph.getNumberOfVertices(), graph.getNumColors());

        gaConfig.setFitnessFunction(new GraphColoringFitnessFunction(graph));

        GeneticAlgorithm<Integer> geneticAlgorithm = new GeneticAlgorithm<>(gaConfig);
        geneticAlgorithm.run(population);
    }
}
