package com.softcomp.ga.crossover;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.softcomp.ga.models.Chromosome;
import com.softcomp.ga.models.Gene;
import com.softcomp.ga.models.Individual;

public class UniformCrossover<T> implements ICrossover<T> {

    protected Random random = new Random();
    public Double rate;

    public UniformCrossover(double crossoverRate) {
        this.rate = crossoverRate;
    }

    public List<Gene<T>> CrossGenes(List<Gene<T>> g1, List<Gene<T>> g2, List<Boolean> flips) {

        List<Gene<T>> newg = new ArrayList<>();
        for (int i = 0; i < g1.size(); i++) {
            if (flips.get(i)) {
                newg.add(g1.get(i));
            } else {
                newg.add(g2.get(i));
            }
        }
        return newg;
    }

    @Override
    public List<Individual<T>> crossover(Individual<T> parent1, Individual<T> parent2) {

        List<Gene<T>> g1 = parent1.getChromosome().getGenes();
        List<Gene<T>> g2 = parent2.getChromosome().getGenes();
        int genesSize = g1.size();
        List<Boolean> flips = new ArrayList<>();
        for (int i = 0; i < genesSize; i++) {
            flips.add(random.nextBoolean());
        }
        List<Individual<T>> results = new ArrayList<>();
        if (random.nextDouble() < rate) {
            results.add(new Individual<>(new Chromosome<T>(CrossGenes(g1, g2, flips))));
            results.get(results.size() - 1).setParentId(parent1.getId());
        }
        if (random.nextDouble() < rate) {
            results.add(new Individual<>(new Chromosome<T>(CrossGenes(g2, g1, flips))));
            results.get(results.size() - 1).setParentId(parent2.getId());
        }
        return results;
    }

    @Override
    public double getRate() {
        return rate;
    }

    @Override
    public void setRate(double rate) {
        this.rate = rate;
    }
}
