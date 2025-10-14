package com.softcomp.ga.replacement;

import com.softcomp.ga.models.Population;

public class SteadyStateReplacement<T> implements IReplacement<T> {

    @Override
    public Population<T> replace(Population<T> oldPopulation, Population<T> offspringPopulation) {
      for(int i=0;i<offspringPopulation.getPopulationSize();i++){
           String offspringId=offspringPopulation.getIndividualOfIndex(i).getParentId();
           for(int j=0;j<oldPopulation.getPopulationSize();j++){
                if(oldPopulation.getIndividualOfIndex(j).getId().equals(offspringId)){
                     oldPopulation.getIndividuals().set(j, offspringPopulation.getIndividualOfIndex(i));
                     break;
                }
           }
      }
        return oldPopulation;
    }

}
