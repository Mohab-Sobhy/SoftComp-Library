package com.softcomp.ga.mutation;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.softcomp.ga.models.Chromosome;
import com.softcomp.ga.models.Gene;

public class InverseMutation<T> implements IMutation<T> {

    private double rate;

    public InverseMutation(double mutationRate) {
        this.rate = mutationRate;
    }

    @Override
    public Chromosome<T> mutate(Chromosome<T> chromosome){

        if(checkRate(rate))
        {
            int size = chromosome.getGenes().size();
            List<Gene<T>> genes = chromosome.getGenes();
            Random random = new Random();
            
            int i = random.nextInt(size);
            int j = random.nextInt(size);
            while(j == i) j = random.nextInt(size);

            if(i > j) {
                int temp = i;
                i = j;
                j = temp;
            }

            Collections.reverse(genes.subList(i, j+1));
            
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
