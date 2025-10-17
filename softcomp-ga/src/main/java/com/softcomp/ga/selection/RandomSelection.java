package com.softcomp.ga.selection;

import java.util.Random;
import com.softcomp.ga.models.Chromosome;
import com.softcomp.ga.models.Population;

public class RandomSelection<T> implements ISelection<T> {

    private Random random = new Random();

    @Override
    public Chromosome<T> select(Population<T> population) {
        int populationSize = population.getPopulationSize();
        if (populationSize == 0) {
            return null;
        }

        int randomIndex = random.nextInt(populationSize);
        return population.getIndividualOfIndex(randomIndex).getChromosome();
    }
}
