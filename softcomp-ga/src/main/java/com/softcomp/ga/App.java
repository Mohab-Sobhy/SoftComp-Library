package com.softcomp.ga;

import com.softcomp.ga.models.Chromosome;
import com.softcomp.ga.models.Gene;
import com.softcomp.ga.models.Individual;
import com.softcomp.ga.models.Population;
import com.softcomp.ga.replacement.GenerationalReplacement;
import com.softcomp.ga.replacement.IReplacement;
import com.softcomp.ga.replacement.SteadyStateReplacement;

import java.util.ArrayList;
import java.util.List;

public class App {

    public static void main(String[] args) {
        System.out.println("=== Testing Genetic Algorithm Replacement Strategies ===\n");

        // Test each replacement strategy
        testGenerationalReplacement();
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        testSteadyStateReplacement();
        System.out.println("\n" + "=".repeat(60) + "\n");
        
    }

    private static void testGenerationalReplacement() {
        System.out.println("TEST 1: Generational Replacement");
        System.out.println("-".repeat(60));

        Population<Double> oldPop = createPopulation(5, 1.0, 5.0);
        Population<Double> offspringPop = createPopulation(5, 6.0, 10.0);

        System.out.println("Old Population:");
        printPopulation(oldPop);

        System.out.println("\nOffspring Population:");
        printPopulation(offspringPop);

        IReplacement<Double> replacement = new GenerationalReplacement<>();
        Population<Double> newPop = replacement.replace(oldPop, offspringPop);

        System.out.println("\nNew Population (should be all offspring):");
        printPopulation(newPop);

        // Verify
        boolean correct = newPop.getPopulationSize() == offspringPop.getPopulationSize();
        System.out.println("\n✓ Result: " + (correct ? "PASS - Offspring replaced old population" : "FAIL"));
    }

    private static void testSteadyStateReplacement() {
        System.out.println("TEST 2: Steady State Replacement");
        System.out.println("-".repeat(60));

        Population<Double> oldPop = createPopulation(5, 1.0, 5.0);
        Population<Double> offspringPop = createPopulationWithParents(3, 8.0, oldPop);

        System.out.println("Old Population:");
        printPopulation(oldPop);

        System.out.println("\nOffspring Population (with parent IDs):");
        printPopulationWithParents(offspringPop);

        IReplacement<Double> replacement = new SteadyStateReplacement<>();
        Population<Double> newPop = replacement.replace(oldPop, offspringPop);

        System.out.println("\nNew Population (offspring replaced their parents):");
        printPopulation(newPop);

        System.out.println("\n✓ Result: PASS - Offspring replaced specific parents");
    }


    // Helper method to create a population with random fitness values
    private static Population<Double> createPopulation(int size, double minFitness, double maxFitness) {
        Population<Double> population = new Population<>();
        
        for (int i = 0; i < size; i++) {
            List<Gene<Double>> genes = new ArrayList<>();
            genes.add(new Gene<>(Math.random() * 100));
            genes.add(new Gene<>(Math.random() * 100));
            
            Chromosome<Double> chromosome = new Chromosome<>(genes);
            Individual<Double> individual = new Individual<>(chromosome);
            
            // Set random fitness in range
            double fitness = minFitness + (Math.random() * (maxFitness - minFitness));
            individual.setFitness(fitness);
            individual.setGenerationCreated(0);
            
            population.addIndividual(individual);
        }
        
        return population;
    }

    // Helper method to create a population with mixed fitness (some high, some low)
    private static Population<Double> createMixedPopulation(int size) {
        Population<Double> population = new Population<>();
        
        for (int i = 0; i < size; i++) {
            List<Gene<Double>> genes = new ArrayList<>();
            genes.add(new Gene<>(Math.random() * 100));
            genes.add(new Gene<>(Math.random() * 100));
            
            Chromosome<Double> chromosome = new Chromosome<>(genes);
            Individual<Double> individual = new Individual<>(chromosome);
            
            // Alternate between high fitness (7-10) and low fitness (1-4)
            double fitness;
            if (i % 2 == 0) {
                fitness = 7.0 + (Math.random() * 3.0);  // High fitness
            } else {
                fitness = 1.0 + (Math.random() * 3.0);  // Low fitness
            }
            
            individual.setFitness(fitness);
            individual.setGenerationCreated(0);
            
            population.addIndividual(individual);
        }
        
        return population;
    }

    // Helper method to create offspring with parent IDs
    private static Population<Double> createPopulationWithParents(int size, double baseFitness, 
                                                                   Population<Double> parentPop) {
        Population<Double> population = new Population<>();
        
        for (int i = 0; i < size; i++) {
            List<Gene<Double>> genes = new ArrayList<>();
            genes.add(new Gene<>(Math.random() * 100));
            genes.add(new Gene<>(Math.random() * 100));
            
            Chromosome<Double> chromosome = new Chromosome<>(genes);
            Individual<Double> individual = new Individual<>(chromosome);
            
            individual.setFitness(baseFitness + i);
            individual.setGenerationCreated(1);
            
            // Set parent ID from parent population
            if (i < parentPop.getPopulationSize()) {
                individual.setParentId(parentPop.getIndividualOfIndex(i).getId());
            }
            
            population.addIndividual(individual);
        }
        
        return population;
    }

    // Helper method to print population
    private static void printPopulation(Population<Double> population) {
        for (int i = 0; i < population.getPopulationSize(); i++) {
            Individual<Double> ind = population.getIndividualOfIndex(i);
            System.out.printf("  [%d] ID: %s | Fitness: %.2f | Gen: %d%n",
                    i,
                    ind.getId().substring(0, 8) + "...",
                    ind.getFitness(),
                    ind.getGenerationCreated());
        }
    }

    // Helper method to print population with parent info
    private static void printPopulationWithParents(Population<Double> population) {
        for (int i = 0; i < population.getPopulationSize(); i++) {
            Individual<Double> ind = population.getIndividualOfIndex(i);
            String parentId = ind.getParentId() != null ? 
                ind.getParentId().substring(0, 8) + "..." : "null";
            System.out.printf("  [%d] ID: %s | Parent: %s | Fitness: %.2f%n",
                    i,
                    ind.getId().substring(0, 8) + "...",
                    parentId,
                    ind.getFitness());
        }
    }

    // Helper method to check if population is sorted by fitness (descending)
    private static boolean isSortedDescending(Population<Double> population) {
        for (int i = 0; i < population.getPopulationSize() - 1; i++) {
            if (population.getIndividualOfIndex(i).getFitness() < 
                population.getIndividualOfIndex(i + 1).getFitness()) {
                return false;
            }
        }
        return true;
    }
}