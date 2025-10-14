package com.softcomp.ga.models;

import java.util.List;

public class Population<T> {
    private List<Individual<T>> individuals;

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

}
