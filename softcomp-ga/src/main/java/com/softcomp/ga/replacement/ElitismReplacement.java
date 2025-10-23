package com.softcomp.ga.replacement;

import com.softcomp.ga.models.Population;
import com.softcomp.ga.models.Individual;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Comparator;

public class ElitismReplacement<T> implements IReplacement<T> {
    private int elitismCount;

    public ElitismReplacement(int elitismCount) {
        this.elitismCount = elitismCount;
    }
    @Override
    public Population<T> replace(Population<T> oldPopulation, Population<T> offspringPopulation) {
        int populationSize = oldPopulation.getIndividuals().size();
        if(elitismCount>populationSize){
            elitismCount=populationSize;
        }
        Collections.
        sort(oldPopulation.getIndividuals()
        ,Comparator.comparingDouble(Individual<T>::getFitness)
        .reversed());
        List<Individual<T>> elites = oldPopulation.getIndividuals().subList(0,elitismCount);

        List<Individual<T>> newIndividuals = new ArrayList<>(offspringPopulation.getIndividuals());
        newIndividuals.addAll(elites);

        newIndividuals.sort(Comparator.comparingDouble(Individual<T>::getFitness).reversed());
        List<Individual<T>> finalIndividuals = newIndividuals.subList(0, Math.min(populationSize, newIndividuals.size()));

        Population<T> newPopulation = new Population<>(finalIndividuals);

        return newPopulation;
    }
}
