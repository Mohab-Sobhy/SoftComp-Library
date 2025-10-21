package com.softcomp.examples.graphcoloring;

import com.softcomp.examples.graphcoloring.models.Graph;
import com.softcomp.ga.fitness.IFitnessFunction;
import com.softcomp.ga.models.Chromosome;
import java.util.List;

public class GraphColoringFitnessFunction implements IFitnessFunction<Integer> {
    private final Graph graph;

    public GraphColoringFitnessFunction(Graph graph) {
        this.graph = graph;
    }

    @Override
    public double evaluate(Chromosome<Integer> chromosome) {
        int conflicts = 0;
        int totalEdges = 0;

        for (int i = 0; i < graph.getNumberOfVertices(); i++) {
            List<Integer> neighbors = graph.getNeighbors(i);

            for (int neighbor : neighbors) {
                if (neighbor > i) { // to prevent duplications
                    totalEdges++;

                    int color1 = chromosome.getGenes().get(i).get();
                    int color2 = chromosome.getGenes().get(neighbor).get();

                    if (color1 == color2) {
                        conflicts++;
                    }
                }
            }
        }

        double fitness;

        if (totalEdges == 0) {
            fitness = 1.0;
        } else {
            fitness = 1.0 - ((double) conflicts / totalEdges);
        }

        // for lower number of distinct colors
        fitness *= 100;
        fitness += (graph.getNumberOfVertices() - chromosome.getNumberOfDistinctValues());
        return Math.max(0, fitness);
    }
}
