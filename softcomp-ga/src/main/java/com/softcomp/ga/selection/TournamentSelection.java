package com.softcomp.ga.selection;

import com.softcomp.ga.models.Chromosome;
import com.softcomp.ga.models.Individual;
import com.softcomp.ga.models.Population;

import java.util.Random;

public class TournamentSelection<T> implements ISelection<T> {

    private final int tournamentSize;
    private final Random random;

    public TournamentSelection(int tournamentSize) {
        this.tournamentSize = tournamentSize;
        this.random = new Random();
    }

    @Override
    public Chromosome<T> select(Population<T> population) {
        if (population == null || population.getIndividuals().isEmpty()) {
            throw new IllegalArgumentException("Population cannot be null or empty.");
        }

        Individual<T> best = null;

        // Pick random individuals and select the best one
        for (int i = 0; i < tournamentSize; i++) {
            int randomIndex = random.nextInt(population.getIndividuals().size());
            Individual<T> candidate = population.getIndividuals().get(randomIndex);

            if (best == null || candidate.getFitness() > best.getFitness()) {
                best = candidate;
            }
        }

        return best.getChromosome();
    }
}
