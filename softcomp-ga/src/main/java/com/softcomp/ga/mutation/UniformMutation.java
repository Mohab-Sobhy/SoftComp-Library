package com.softcomp.ga.mutation;

import java.util.Random;

import com.softcomp.ga.models.Chromosome;
import com.softcomp.ga.models.Gene;

public class UniformMutation implements IMutation<Double>{

    private double rate;

    public UniformMutation(double mutationRate) {
        this.rate = mutationRate;
    }

    @Override
    public Chromosome<Double> mutate(Chromosome<Double> chromosome){

        if(checkRate(rate))
        {
            for(int i = 0; i<chromosome.getGenes().size(); i++)
            {

                Gene<Double> gene = chromosome.getGenes().get(i);

                double distanceToLower = gene.get() - gene.getLowerBound();
                double distanceToUpper = gene.getUpperBound() - gene.get();

                Random random = new Random();
                double r1 = random.nextDouble();
                double delta;

                if(r1 <= 0.5) delta = distanceToLower;
                else delta = distanceToUpper;

                double r2 = random.nextDouble() * delta;

                if(delta == distanceToLower) gene.set(gene.get() - r2);
                else gene.set(gene.get() + r2);

            }
        }

        return chromosome;
    }

    @Override
    public boolean checkRate(double mutationRate)
    {
        return true;
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
