package com.softcomp.ga.models;

import java.util.UUID;

public class Individual<T> {

    private Chromosome<T> chromosome;
    private double fitness;
    private boolean feasible;
    private int generationCreated;
    private String id;
    private String parentId = null;

    public Individual(Chromosome<T> chromosome) {
        this.chromosome = chromosome;
        chromosome.individual = this;
        this.fitness = 0.0;
        this.feasible = true;
        this.generationCreated = 0;
        this.id = UUID.randomUUID().toString();
    }

    public Chromosome<T> getChromosome() {
        return chromosome;
    }

    public void setChromosome(Chromosome<T> chromosome) {
        this.chromosome = chromosome;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public boolean isFeasible() {
        return feasible;
    }

    public void setFeasible(boolean feasible) {
        this.feasible = feasible;
    }

    public int getGenerationCreated() {
        return generationCreated;
    }

    public void setGenerationCreated(int generationCreated) {
        this.generationCreated = generationCreated;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return String.format(
                "Individual{chromosome=%s, fitness=%.4f, feasible=%b, generation=%d}",
                chromosome.toString(), fitness, feasible, generationCreated);
    }

}
