package com.softcomp.examples.graphcoloring;

import com.softcomp.examples.graphcoloring.models.Graph;
import com.softcomp.ga.fitness.IFitnessFunction;
import com.softcomp.ga.models.Chromosome;
import java.util.List;

public class GraphColoringFitnessFunction implements IFitnessFunction<Integer> {
    Graph graph;

    public GraphColoringFitnessFunction(Graph graph) {
        this.graph = graph;
    }

    @Override
    public double evaluate(Chromosome<Integer> chromosome) {
        int conflicts = 0;

        for (int i = 0; i < graph.getNumberOfVertices(); i++) {
            List<Integer> neighbors = graph.getNeighbors(i);

            for (int neighbor : neighbors) {
                if (neighbor > i) { // (neighbor > i) to prevent duplicates
                    int color1 = chromosome.getGenes().get(i).get();
                    int color2 = chromosome.getGenes().get(neighbor).get();

                    if (color1 == color2) {
                        conflicts++;
                    }
                }
            }
        }

        double colorPenalty = graph.getNumColors() - 1;
        double conflictPenalty = conflicts * (graph.getNumberOfVertices() + 1);
        double score = conflictPenalty + colorPenalty;
        double fitness = 1.0 / (1.0 + score);

        return fitness;
    }
}
