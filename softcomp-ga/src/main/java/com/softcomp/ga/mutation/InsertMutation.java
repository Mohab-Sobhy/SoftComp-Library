package com.softcomp.ga.mutation;

import java.util.Random;

import com.softcomp.ga.models.Chromosome;
import com.softcomp.ga.models.Gene;

public class InsertMutation<T> implements IMutation<T> {

    private double mutationRate;

    public InsertMutation(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    @Override
    public Chromosome<T> mutate(Chromosome<T> chromosome){

        if(checkRate(mutationRate))
        {
            int size = chromosome.getGenes().size();
            Random random = new Random();
            
            int i = random.nextInt(size);
            int j = random.nextInt(size);
            while(j == i) j = random.nextInt(size);

            Gene<T> gene = chromosome.getGenes().get(i);
            chromosome.getGenes().remove(i);
            chromosome.getGenes().add(j, gene);
        }

        return chromosome;
    }

    //Checks mutation rate against every gene
    @Override
    public boolean checkRate(double mutationRate)
    {
        Random random = new Random();
        if(random.nextDouble() < mutationRate) return true;
        return false;
    }
}
