package com.softcomp.ga.replacement;

import com.softcomp.ga.models.Population;

public class GenerationalReplacement<T> implements IReplacement<T> {

    @Override
    public Population<T> replace(Population<T> oldPopulation, Population<T> offspringPopulation) {
        if(offspringPopulation.getIndividuals().size()<2) return oldPopulation;
        return offspringPopulation;

    }

}