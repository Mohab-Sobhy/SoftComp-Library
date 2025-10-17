package com.softcomp.examples.graphcoloring;

import com.softcomp.ga.models.Gene;
import com.softcomp.ga.models.Individual;
import com.softcomp.ga.models.Chromosome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class RandomIndividualGenerator {

    private static RandomIndividualGenerator instance;
    private final Random random = new Random();

    private RandomIndividualGenerator() {}

    public static synchronized RandomIndividualGenerator getInstance() {
        if (instance == null) {
            instance = new RandomIndividualGenerator();
        }
        return instance;
    }


    public Individual<Integer> generateRandomIndividual(int numVertices, int numColors) {

        // Generate random color for each vertex
        List< Gene<Integer> > genes = new ArrayList<>(numVertices);
        for (int i = 0; i < numVertices; i++) {
            int color = random.nextInt(numColors); // random color index from [0 .. numColors-1]
            Gene<Integer> gene = new Gene(color);
            gene.setLowerBound(0);
            gene.setUpperBound(numColors-1);

            genes.add(gene);
        }

        Chromosome<Integer> chromosome = new Chromosome<>(genes);

        Individual<Integer> individual = new Individual<>(chromosome);
        individual.setFitness(0.0); // will be computed later by fitness function

        return individual;
    }

}
