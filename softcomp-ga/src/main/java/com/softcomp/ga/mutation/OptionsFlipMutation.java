package com.softcomp.ga.mutation;

import java.util.List;
import java.util.Random;

import com.softcomp.ga.models.Chromosome;
import com.softcomp.ga.models.Gene;

public class OptionsFlipMutation<T> implements IMutation<T> {

    private double rate;
    private final List<T> flipOptions;
    private final Random random = new Random();

    public OptionsFlipMutation(double mutationRate, List<T> flipOptions) {
        this.rate = mutationRate;
        this.flipOptions = flipOptions;
    }

    @Override
    public Chromosome<T> mutate(Chromosome<T> chromosome) {

        for (int i = 0; i < chromosome.getGenes().size(); i++) {
            if (checkRate(rate)) {
                Gene<T> gene = chromosome.getGenes().get(i);
                T currentValue = gene.get();

                T newValue = getDifferentRandomValue(currentValue);

                Gene<T> newGene = new Gene<>(newValue);
                chromosome.getGenes().set(i, newGene);
            }
        }

        return chromosome;
    }

    private T getDifferentRandomValue(T currentValue) {
        if (flipOptions == null || flipOptions.isEmpty()) return currentValue;

        T newValue = currentValue;
        while (newValue.equals(currentValue) && flipOptions.size() > 1) {
            newValue = flipOptions.get(random.nextInt(flipOptions.size()));
        }
        return newValue;
    }

    @Override
    public boolean checkRate(double mutationRate) {
        return random.nextDouble() < mutationRate;
    }

    @Override
    public double getRate(){
        return rate;
    }

    @Override
    public void setRate(double rate){
        this.rate = rate;
    }
}