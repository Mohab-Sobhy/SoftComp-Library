package com.softcomp.ga.replacement;

import com.softcomp.ga.models.Individual;
import com.softcomp.ga.models.Population;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class RandomReplacement<T> implements IReplacement<T> {
    private int populationSize = -1;
    private final Random random = new Random();

    @Override
    public Population<T> replace(Population<T> oldPopulation, Population<T> offspringPopulation) {
        if (populationSize == -1)
            populationSize = oldPopulation.getIndividuals().size();

        List<Individual<T>> oldIndividuals = oldPopulation.getIndividuals();
        List<Individual<T>> offIndividuals = offspringPopulation.getIndividuals();

        List<Individual<T>> combined = new ArrayList<>(
                Stream.concat(oldIndividuals.stream(), offIndividuals.stream()).toList()
        );

        Collections.shuffle(combined, random);

        List<Individual<T>> selected = combined.subList(0, Math.min(populationSize, combined.size()));

        Population<T> newPopulation = new Population<>();
        newPopulation.setIndividuals(new ArrayList<>(selected));

        return newPopulation;
    }
}
