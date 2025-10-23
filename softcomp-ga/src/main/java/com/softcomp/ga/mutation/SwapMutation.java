package com.softcomp.ga.mutation;

import java.util.Random;

import com.softcomp.ga.models.Chromosome;
import com.softcomp.ga.models.Gene;


public class SwapMutation<T> implements IMutation<T> {

    private double rate;

    public SwapMutation(double mutationRate) {
        this.rate = mutationRate;
    }

    @Override
    public Chromosome<T> mutate(Chromosome<T> chromosome){

        if(checkRate(rate))
        {
            int size = chromosome.getGenes().size();
            Random random = new Random();

            int i = random.nextInt(size);
            int j = random.nextInt(size);
            while(j == i) j = random.nextInt(size);

            Gene<T> temp = chromosome.getGenes().get(i);
            chromosome.getGenes().set(i, chromosome.getGenes().get(j));
            chromosome.getGenes().set(j, temp);
        }

        return chromosome;
    }

    @Override
    public boolean checkRate(double mutationRate)
    {
        Random random = new Random();
        if(random.nextDouble() < mutationRate) return true;
        return false;
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
