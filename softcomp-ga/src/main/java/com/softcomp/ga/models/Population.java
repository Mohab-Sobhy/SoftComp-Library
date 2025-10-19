package com.softcomp.ga.models;

import java.util.ArrayList;
import java.util.List;

public class Population<T> {
    private List<Individual<T>> individuals = new ArrayList<>();

    public Population() {}

    public Population(List<Individual<T>> individuals) {
        this.individuals = individuals;
    }

    public Individual<T> getIndividualOfIndex(int index) {
        return individuals.get(index);
    }

    public int getPopulationSize() {
        return individuals.size();
    }

    public List<Individual<T>> getIndividuals() {
        return individuals;
    }

    public void setIndividuals(List<Individual<T>> individuals) {
        this.individuals = individuals;
    }

    public void addIndividual(Individual<T> individual) {
        individuals.add(individual);
    }

    public Individual<T> getBestIndividual() {
        if (individuals.isEmpty()) {
            return null;
        }

        Individual<T> best = individuals.get(0);
        for (Individual<T> ind : individuals) {
            if (ind.getFitness() > best.getFitness()) {
                best = ind;
            }
        }
        return best;
    }
}
