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


    public void run(Population<T> population) {

        // selection
        // crossover
        // mutation
        // replacement
    }

}