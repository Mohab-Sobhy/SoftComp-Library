package com.softcomp.ga.models;

import java.util.List;

public class Chromosome<T> {
    private List<Gene<T>> genes;

    public Chromosome(List<Gene<T>> genes) {
        this.genes = genes;
    }

    public List<Gene<T>> getGenes() {
        return genes;
    }

    public void setGenes(List<Gene<T>> genes) {
        this.genes = genes;
    }

    public void addGene(Gene<T> gene) {
        genes.add(gene);
    }

    @Override
    public String toString() {
        return genes.toString();
    }
}
