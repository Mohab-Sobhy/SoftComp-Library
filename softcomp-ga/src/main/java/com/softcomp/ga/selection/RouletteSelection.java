package com.softcomp.ga.selection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.softcomp.ga.models.Chromosome;
import com.softcomp.ga.models.Population;

public class RouletteSelection<T> implements ISelection<T> {
    protected Random random = new Random();

    @Override
    public Chromosome<T> select(Population<T> population) {

        Chromosome<T> chosen = population.getIndividualOfIndex(0).getChromosome();
        int populationSize = population.getPopulationSize(), randomNumber, sum = 0;
        List<Integer> comulativeFitness = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            sum += population.getIndividualOfIndex(i).getFitness();
            comulativeFitness.set(i, sum);
        }
        randomNumber = random.nextInt(sum);
        for (int i = 0; i < comulativeFitness.size(); i++) {
            if (comulativeFitness.get(i) > randomNumber) {
                chosen = population.getIndividualOfIndex(i).getChromosome();
                break;
            }
        }
        return chosen;
    }

}