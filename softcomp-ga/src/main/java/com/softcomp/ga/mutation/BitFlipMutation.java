package com.softcomp.ga.mutation;

import java.util.Random;

//FOR Binary Chromosome

import com.softcomp.ga.models.Chromosome;
import com.softcomp.ga.models.Gene;

public class BitFlipMutation implements IMutation<Boolean> {

    @Override
    public Chromosome<Boolean> mutate(Chromosome<Boolean> chromosome, double mutatationRate){

        for(int i = 0; i < chromosome.getGenes().size(); i++)
        {
            if(checkRate(mutatationRate)) {
                Gene<Boolean> gene = chromosome.getGenes().get(i);
                boolean bitFlip = !gene.get();
                Gene<Boolean> newGene = new Gene<Boolean>(bitFlip);
                chromosome.getGenes().set(i, newGene);
            }
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
;