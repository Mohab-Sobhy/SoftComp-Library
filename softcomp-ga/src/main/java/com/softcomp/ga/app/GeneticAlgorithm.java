package com.softcomp.ga.app;

import com.softcomp.ga.models.Individual;
import com.softcomp.ga.models.Population;

public class GeneticAlgorithm<T> {

    private GAConfig<T> config;
    private Population<T> population;
    private Individual<T> bestIndividual;

    public GeneticAlgorithm(GAConfig<T> config) {
        this.config = config;
    }

    public void initializePopulation() {

    }

    public void run() {
        initializePopulation();

        // selection
        // crossover
        // mutation
        // replacement
    }

}