package com.softcomp.ga.selection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.softcomp.ga.models.Chromosome;
import com.softcomp.ga.models.Individual;
import com.softcomp.ga.models.Population;

public class TournamentSelection<T> implements ISelection<T> {

    private int tournamentSize;
    private Random random = new Random();

    public TournamentSelection(int tournamentSize) {
        this.tournamentSize = tournamentSize;
    }

    @Override
    public Chromosome<T> select(Population<T> population) {
        int populationSize = population.getPopulationSize();
        if (populationSize == 0) {
            return null; // just in case empty population
        }

        List<Individual<T>> participants = new ArrayList<>();

        for (int i = 0; i < tournamentSize; i++) {
            int randomIndex = random.nextInt(populationSize);
            participants.add(population.getIndividualOfIndex(randomIndex));
        }

        Individual<T> winner = participants.get(0);

        for (int i = 1; i < tournamentSize; i++) {
            if (participants.get(i).getFitness() > winner.getFitness()) {
                winner = participants.get(i);
            }
        }

        return winner.getChromosome();
    }

}