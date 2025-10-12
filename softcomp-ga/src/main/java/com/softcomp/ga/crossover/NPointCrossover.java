package com.softcomp.ga.crossover;

import java.util.Random;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import com.softcomp.ga.models.Chromosome;
import com.softcomp.ga.models.Gene;

public class NPointCrossover<T> implements ICrossover<T> {
    private Random random = new Random();
    public Double rate;

    public NPointCrossover(double rate) {
        this.rate = rate;
    }

    public List<Integer> getRandomPoints(int genesSize, int numOfPoints, int minPoint, int maxPoint) {
        int pointsNum = random.nextInt(numOfPoints) + 1;
        HashSet<Integer> used = new HashSet<>();
        while (used.size() < pointsNum) {
            used.add(random.nextInt(maxPoint - minPoint + 1) + minPoint);
        }
        List<Integer> points = new ArrayList<>(used);
        points.sort((n1, n2) -> n1.compareTo(n2));
        points.add(genesSize - 1);
        return points;
    }

    @Override
    public List<Chromosome<T>> crossover(Chromosome<T> parent1, Chromosome<T> parent2) {

        List<Gene<T>> g1 = parent1.getGenes();
        List<Gene<T>> g2 = parent2.getGenes();
        int genesSize = g1.size();

        // random list of random number of integers for points
        int numOfPoints = genesSize / 2, minPoint = 1, maxPoint = genesSize - 2;
        List<Integer> points = getRandomPoints(genesSize, numOfPoints, minPoint, maxPoint);

        // crossover for 2 children
        List<Chromosome<T>> results = new ArrayList<>();
        List<Gene<T>> newg = new ArrayList<>();
        if (random.nextDouble() < rate) {
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
            results.add(new Chromosome<T>(newg));
        }
        newg = new ArrayList<>();
        if (random.nextDouble() < rate) {
            boolean fromFirst = true;
            int pointIndex = 0;
            for (int i = 0; i < g1.size(); i++) {
                if (pointIndex < points.size() && i == points.get(pointIndex)) {
                    fromFirst = !fromFirst;
                    pointIndex++;
                }
                if (fromFirst) {
                    newg.add(g2.get(i));
                } else {
                    newg.add(g1.get(i));
                }
            }
            results.add(new Chromosome<T>(newg));
        }
        return results;
    }

}