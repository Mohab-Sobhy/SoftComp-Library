package com.softcomp.ga.replacement;

import com.softcomp.ga.models.Individual;
import com.softcomp.ga.models.Population;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class ElitismReplacement<T> implements IReplacement<T> {
    private int populationSize = -1;

    @Override
    public Population<T> replace(Population<T> oldPopulation, Population<T> offspringPopulation) {
        if (populationSize == -1) populationSize = oldPopulation.getIndividuals().size();
        List<Individual<T>> oldIndividuals = oldPopulation.getIndividuals();
        List<Individual<T>> offIndividuals = offspringPopulation.getIndividuals();
        List<Individual<T>> newIndividuals = new ArrayList<>(Stream.concat(oldIndividuals.stream(), offIndividuals.stream()).toList());
        newIndividuals.sort(Comparator.comparingDouble(Individual<T>::getFitness).reversed());
        if (newIndividuals.size() > populationSize) {
            newIndividuals.subList(populationSize, newIndividuals.size()).clear();
        }
        Population<T> newPopulation = new Population<>();
        newPopulation.setIndividuals(newIndividuals);
        return newPopulation;
    }
}