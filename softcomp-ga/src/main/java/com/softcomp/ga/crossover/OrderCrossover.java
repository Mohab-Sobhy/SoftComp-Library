package com.softcomp.ga.crossover;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import com.softcomp.ga.models.Chromosome;
import com.softcomp.ga.models.Gene;

public class OrderCrossover<T> implements ICrossover<T> {
    protected Random random = new Random();
    public Double rate;

    public OrderCrossover(double rate) {
        this.rate = rate;
    }

    public List<Gene<T>> CrossGenes(List<Gene<T>> g1, List<Gene<T>> g2, int point1, int point2) {
        List<Gene<T>> result = new ArrayList<>();
        HashSet<Gene<T>> used = new HashSet<>();
        for (int i = point1; i < point2; i++) {
            result.set(i, g1.get(i));
            used.add(g1.get(i));
        }
        int j = point2;
        for (int i = point2; i < (point2 - point1) + 1; i = (i + 1) % g1.size()) {
            while (used.contains(g2.get(j)))
                j = (j + 1) % g1.size();
            used.add(g2.get(j));
            result.set(i, g1.get(j));
        }
        return result;
    }

    @Override
    public double getRate(){
        return rate;
    }

    @Override
    public void setRate(double rate){
        this.rate = rate;
    }

    @Override
    public List<Chromosome<T>> crossover(Chromosome<T> parent1, Chromosome<T> parent2) {

        List<Gene<T>> g1 = parent1.getGenes();
        List<Gene<T>> g2 = parent2.getGenes();
        int genesSize = g1.size();

        // random list of random number of integers for points
        int minPoint = 1, maxPoint = genesSize;
        Integer point1 = random.nextInt(maxPoint - minPoint + 1) + minPoint;
        Integer point2 = random.nextInt(maxPoint - minPoint + 1) + minPoint;
        while (point1 == point2)
            point2 = random.nextInt(maxPoint - minPoint + 1) + minPoint;

        // crossover for 2 children
        List<Chromosome<T>> results = new ArrayList<>();
        if (random.nextDouble() < rate) {
            results.add(new Chromosome<T>(CrossGenes(g1, g2, Math.min(point1, point2), Math.max(point1, point2))));
        }
        if (random.nextDouble() < rate) {
            results.add(new Chromosome<T>(CrossGenes(g2, g1, Math.min(point1, point2), Math.max(point1, point2))));
        }
        return results;
    }
}
