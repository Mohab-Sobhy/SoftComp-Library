package com.softcomp.ga.crossover;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import com.softcomp.ga.models.Chromosome;
import com.softcomp.ga.models.Gene;
import com.softcomp.ga.models.Individual;

public class OrderCrossover<T> implements ICrossover<T> {
    protected Random random = new Random();
    public Double rate;

    public OrderCrossover(double rate) {
        this.rate = rate;
    }

    public List<Gene<T>> CrossGenes(List<Gene<T>> g1, List<Gene<T>> g2, int point1, int point2) {
        List<Gene<T>> result = new ArrayList<>(g1.size());
        for (int i = 0; i < g1.size(); i++) result.add(null);
        HashSet<Gene<T>> used = new HashSet<>();
        for (int i = point1; i < point2; i++) {
            result.set(i, g1.get(i));
            used.add(g1.get(i));
        }
        int j = point2%g1.size();
        for (int i = point2%g1.size(); i !=point1; i = (i + 1) % g1.size()) {
            while (used.contains(g2.get(j)))
                j = (j + 1) % g1.size();
            result.set(i, g2.get(j));    
            used.add(g2.get(j));
        }
        return result;
    }

    @Override
    public double getRate() {
        return rate;
    }

    @Override
    public void setRate(double rate) {
        this.rate = rate;
    }

    @Override
    public List<Individual<T>> crossover(Individual<T> parent1, Individual<T> parent2) {

        List<Gene<T>> g1 = parent1.getChromosome().getGenes();
        List<Gene<T>> g2 = parent2.getChromosome().getGenes();
        int genesSize = g1.size();

        int minPoint = 1, maxPoint = genesSize;
        Integer point1 = random.nextInt(maxPoint - minPoint + 1) + minPoint;
        Integer point2 = random.nextInt(maxPoint - minPoint + 1) + minPoint;
        while (point1 == point2)
            point2 = random.nextInt(maxPoint - minPoint + 1) + minPoint;

        List<Individual<T>> results = new ArrayList<>();
        if (random.nextDouble() < rate) {
            results.add(new Individual<>(
                    new Chromosome<T>(CrossGenes(g1, g2, Math.min(point1, point2), Math.max(point1, point2)))));
            results.get(results.size() - 1).setParentId(parent1.getId());
        }
        if (random.nextDouble() < rate) {
            results.add(new Individual<>(
                    new Chromosome<T>(CrossGenes(g2, g1, Math.min(point1, point2), Math.max(point1, point2)))));
            results.get(results.size() - 1).setParentId(parent2.getId());
        }
        return results;
    }
}
