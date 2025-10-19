package com.softcomp.ga.replacement;

import com.softcomp.ga.models.Individual;
import com.softcomp.ga.models.Population;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ElitismReplacement<T> implements IReplacement<T> {
    public int PopulationSize=-1;
    @Override
    public Population<T> replace(Population<T> oldPopulation, Population<T> offspringPopulation) {
     if(PopulationSize==-1)
        PopulationSize=oldPopulation.getIndividuals().size();
        List<Individual<T>> oldIndividuals=oldPopulation.getIndividuals();
        List<Individual<T>> offIndividuals=offspringPopulation.getIndividuals();
        List<Individual<T>> newIndividuals=Stream.
        concat(oldIndividuals.stream(), offIndividuals.stream()).collect(Collectors.toList());
        newIndividuals.sort(Comparator.comparingDouble(Individual<T>::getFitness).reversed());   
        if (newIndividuals.size() > PopulationSize) {
           newIndividuals.subList(PopulationSize, newIndividuals.size()).clear();
        }
        Population<T> newPopulation = new Population<T>();
        newPopulation.setIndividuals(newIndividuals);
        return newPopulation;
    }

}
