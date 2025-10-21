package com.softcomp.ga.models;

import java.util.HashSet;
import java.util.List;

public class Chromosome<T> {
    private List<Gene<T>> genes;
    public Individual<T> individual;

    public Chromosome(List<Gene<T>> genes) {
        this.genes = genes;
    }

    public int getNumberOfDistinctValues() {
        HashSet<T> used = new HashSet<>();
        for (Gene<T> gene : genes) {
            used.add(gene.get());
        }
        return used.size();
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
