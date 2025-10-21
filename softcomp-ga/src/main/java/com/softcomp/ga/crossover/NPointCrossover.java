package com.softcomp.ga.crossover;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import com.softcomp.ga.models.Chromosome;
import com.softcomp.ga.models.Gene;
import com.softcomp.ga.models.Individual;

public class NPointCrossover<T> implements ICrossover<T> {
    protected Random random = new Random();
    public Double rate;

    public NPointCrossover(double rate) {
        this.rate = rate;
    }

    public List<Integer> getRandomPoints(int genesSize, int maxNumOfPoints, int minPoint, int maxPoint) {
        int pointsNum = random.nextInt(maxNumOfPoints) + 1;
        HashSet<Integer> used = new HashSet<>();
        while (used.size() < pointsNum) {
            used.add(random.nextInt(maxPoint - minPoint + 1) + minPoint);
        }
        List<Integer> points = new ArrayList<>(used);
        points.sort((n1, n2) -> n1.compareTo(n2));
        points.add(genesSize - 1);
        return points;
    }

    public List<Gene<T>> CrossGenes(List<Gene<T>> g1, List<Gene<T>> g2, List<Integer> points) {
        List<Gene<T>> newg = new ArrayList<>();
        boolean fromFirst = true;
        int pointIndex = 0;
        for (int i = 0; i < g1.size(); i++) {
            if (pointIndex < points.size() && i == points.get(pointIndex)) {
                fromFirst = !fromFirst;
                pointIndex++;
            }
            if (fromFirst) {
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

        // random list of random number of integers for points
        int maxNumOfPoints = genesSize / 2, minPoint = 1, maxPoint = genesSize;
        List<Integer> points = getRandomPoints(genesSize, maxNumOfPoints, minPoint, maxPoint);

        // crossover for 2 children
        List<Individual<T>> results = new ArrayList<>();
        if (random.nextDouble() < rate) {
            results.add(new Individual<>(new Chromosome<T>(CrossGenes(g1, g2, points))));
            results.get(results.size() - 1).setParentId(parent1.getParentId());
        }
        if (random.nextDouble() < rate) {
            results.add(new Individual<>(new Chromosome<T>(CrossGenes(g2, g1, points))));
            results.get(results.size() - 1).setParentId(parent2.getParentId());

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

    public List<Chromosome<T>> crossover(Chromosome<T> parent1, Chromosome<T> parent2, List<Integer> points) {
        List<Gene<T>> g1 = parent1.getGenes();
        List<Gene<T>> g2 = parent2.getGenes();
        // crossover for 2 children
        List<Chromosome<T>> results = new ArrayList<>();
        if (random.nextDouble() < rate) {
            results.add(new Chromosome<T>(CrossGenes(g1, g2, points)));
        }
        if (random.nextDouble() < rate) {
            results.add(new Chromosome<T>(CrossGenes(g2, g1, points)));
        }
        return results;
    }

}