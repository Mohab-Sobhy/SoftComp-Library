package com.softcomp.ga.app;

import java.util.ArrayList;
import java.util.List;

import com.softcomp.ga.LoggerService;
import com.softcomp.ga.models.Chromosome;
import com.softcomp.ga.models.Individual;
import com.softcomp.ga.models.Population;
import com.softcomp.ga.selection.ISelection;
import com.softcomp.ga.mutation.IMutation;
import com.softcomp.ga.replacement.IReplacement;
import com.softcomp.ga.fitness.IFitnessFunction;
import com.softcomp.ga.crossover.ICrossover;

public class GeneticAlgorithm<T> {

    private GAConfig<T> config;
    private Population<T> population;
    private LoggerService logger;

    public GeneticAlgorithm(GAConfig<T> config, LoggerService logger) {
        this.config = config;
        this.logger = logger;
    }

    public void run(Population<T> initialPopulation) {
        this.population = initialPopulation;
        IFitnessFunction<T> fitnessFunction = config.getFitnessFunction();
        ISelection<T> selection = config.getSelectionStrategy();
        ICrossover<T> crossover = config.getCrossoverStrategy();
        IMutation<T> mutation = config.getMutationStrategy();
        IReplacement<T> replacement = config.getReplacementStrategy();

        evaluatePopulation(population, fitnessFunction);

        logger.log("=== Initial Population ===");
        logPopulation(population);

        Individual<T> currentBest = population.getBestIndividual();
        logger.log(String.format("Best solution: %s | Fitness = %.3f\n",
                currentBest.getChromosome(), currentBest.getFitness()));

        int generation = 1;
        while (generation <= config.getNumGenerations()) {

            logger.log("=== Generation " + generation + " ===");

            List<Chromosome<T>> parents = new ArrayList<>();
            for (int i = 0; i < population.getPopulationSize(); i++) {
                Chromosome<T> parent = selection.select(population);
                parents.add(parent);
            }

            List<Individual<T>> offspring = new ArrayList<>();
            for (int i = 0; i < parents.size(); i += 2) {
                Chromosome<T> parent1 = parents.get(i);
                Chromosome<T> parent2 = parents.get((i + 1) % parents.size());
                if (parent1 == null || parent2 == null)
                    continue;

                List<Individual<T>> children = crossover.crossover(parent1.individual, parent2.individual);
                offspring.addAll(children);
            }

            List<Individual<T>> mutatedOffspring = new ArrayList<>();
            for (Individual<T> child : offspring) {
                Chromosome<T> mutated = mutation.mutate(child.getChromosome());
                mutatedOffspring.add(mutated.individual);
            }

            Population<T> offspringPopulation = new Population<>(mutatedOffspring);
            population = replacement.replace(population, offspringPopulation);

            evaluatePopulation(population, fitnessFunction);

            currentBest = population.getBestIndividual();

            logPopulation(population);
            logger.log(String.format("Best solution: %s | Fitness = %.3f\n",
                    currentBest.getChromosome(), currentBest.getFitness()));

            generation++;
        }
    }

    private void evaluatePopulation(Population<T> population, IFitnessFunction<T> fitnessFunction) {
        for (Individual<T> individual : population.getIndividuals()) {
            double fitness = fitnessFunction.evaluate(individual.getChromosome());
            individual.setFitness(fitness);
        }
    }

    private void logPopulation(Population<T> population) {
        for (int i = 0; i < population.getPopulationSize(); i++) {
            Individual<T> ind = population.getIndividualOfIndex(i);
            logger.log(String.format("  Color: %s | Fitness = %.3f",
                    ind.getChromosome().toString(), ind.getFitness()));
        }
    }
}
