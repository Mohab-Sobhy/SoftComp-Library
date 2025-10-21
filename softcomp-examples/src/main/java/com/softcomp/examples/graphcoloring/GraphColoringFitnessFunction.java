package com.softcomp.examples.graphcoloring;

import com.softcomp.examples.graphcoloring.models.Graph;
import com.softcomp.ga.fitness.IFitnessFunction;
import com.softcomp.ga.models.Chromosome;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GraphColoringFitnessFunction implements IFitnessFunction<Integer> {
    private final Graph graph;
    private final double conflictWeight;
    private final double colorWeight;

    public GraphColoringFitnessFunction(Graph graph, double conflictWeight, double colorWeight) {
        this.graph = graph;
        this.conflictWeight = conflictWeight;
        this.colorWeight = colorWeight;
    }

    @Override
    public double evaluate(Chromosome<Integer> chromosome) {
        int conflicts = 0;
        int totalEdges = 0;

        for (int i = 0; i < graph.getNumberOfVertices(); i++) {
            List<Integer> neighbors = graph.getNeighbors(i);

            for (int neighbor : neighbors) {
                if (neighbor > i) { // prevent conflict
                    totalEdges++;

                    int color1 = chromosome.getGenes().get(i).get();
                    int color2 = chromosome.getGenes().get(neighbor).get();

                    if (color1 == color2) {
                        conflicts++;
                    }
                }
            }
        }

        Set<Integer> uniqueColors = new HashSet<>();
        for (int i = 0; i < chromosome.getGenes().size(); i++) {
            uniqueColors.add(chromosome.getGenes().get(i).get());
        }
        int numColors = uniqueColors.size();

        double conflictRatio = (totalEdges == 0) ? 0 : (double) conflicts / totalEdges;

        double fitness = 1.0 / (1.0 + conflictWeight * conflictRatio + colorWeight * (numColors - 1));

        return fitness;
    }
}
