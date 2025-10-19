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
        int populationSize = population.getPopulationSize();
        if (populationSize == 0) return null; // ✅ حماية من Population فاضي

        double sum = 0.0;
        List<Double> cumulativeFitness = new ArrayList<>();

        for (int i = 0; i < populationSize; i++) {
            double f = population.getIndividualOfIndex(i).getFitness();
            sum += f;
            cumulativeFitness.add(sum);
        }

        if (sum == 0) {
            int randomIndex = random.nextInt(populationSize);
            return population.getIndividualOfIndex(randomIndex).getChromosome();
        }

        double randomNumber = random.nextDouble() * sum;

        for (int i = 0; i < cumulativeFitness.size(); i++) {
            if (cumulativeFitness.get(i) >= randomNumber) {
                return population.getIndividualOfIndex(i).getChromosome();
            }
        }

        return population.getIndividualOfIndex(populationSize - 1).getChromosome();
    }
}
