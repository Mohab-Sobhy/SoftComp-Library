package com.softcomp.ga.app;

import com.softcomp.ga.selection.ISelection;
import com.softcomp.ga.crossover.ICrossover;
import com.softcomp.ga.mutation.IMutation;
import com.softcomp.ga.replacement.IReplacement;
import com.softcomp.ga.fitness.IFitnessFunction;
import com.softcomp.ga.feasibility.IFeasibility;

public class GAConfig<T> {

    // Core parameters
    private int populationSize = 50;
    private int numGenerations = 100;
    private double crossoverRate = 0.7;
    private double mutationRate = 0.05;

    // Strategy objects
    private ISelection<T> selectionStrategy;
    private ICrossover<T> crossoverStrategy;
    private IMutation<T> mutationStrategy;
    private IReplacement<T> replacementStrategy;

    // Problem-specific functions
    private IFitnessFunction<T> fitnessFunction;
    private IFeasibility<T> feasibilityHandler;

    public GAConfig() {}

    public GAConfig(int populationSize, int numGenerations,
                    double crossoverRate, double mutationRate,
                    ISelection<T> selectionStrategy,
                    ICrossover<T> crossoverStrategy,
                    IMutation<T> mutationStrategy,
                    IReplacement<T> replacementStrategy,
                    IFitnessFunction<T> fitnessFunction,
                    IFeasibility<T> feasibilityHandler) {

        this.populationSize = populationSize;
        this.numGenerations = numGenerations;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.selectionStrategy = selectionStrategy;
        this.crossoverStrategy = crossoverStrategy;
        this.mutationStrategy = mutationStrategy;
        this.replacementStrategy = replacementStrategy;
        this.fitnessFunction = fitnessFunction;
        this.feasibilityHandler = feasibilityHandler;
    }

    public int getPopulationSize() { return populationSize; }
    public void setPopulationSize(int populationSize) { this.populationSize = populationSize; }

    public int getNumGenerations() { return numGenerations; }
    public void setNumGenerations(int numGenerations) { this.numGenerations = numGenerations; }

    public double getCrossoverRate() { return crossoverRate; }
    public void setCrossoverRate(double crossoverRate) { this.crossoverRate = crossoverRate; }

    public double getMutationRate() { return mutationRate; }
    public void setMutationRate(double mutationRate) { this.mutationRate = mutationRate; }

    public ISelection<T> getSelectionStrategy() { return selectionStrategy; }
    public void setSelectionStrategy(ISelection<T> selectionStrategy) { this.selectionStrategy = selectionStrategy; }

    public ICrossover<T> getCrossoverStrategy() { return crossoverStrategy; }
    public void setCrossoverStrategy(ICrossover<T> crossoverStrategy) { this.crossoverStrategy = crossoverStrategy; }

    public IMutation<T> getMutationStrategy() { return mutationStrategy; }
    public void setMutationStrategy(IMutation<T> mutationStrategy) { this.mutationStrategy = mutationStrategy; }

    public IReplacement<T> getReplacementStrategy() { return replacementStrategy; }
    public void setReplacementStrategy(IReplacement<T> replacementStrategy) { this.replacementStrategy = replacementStrategy; }

    public IFitnessFunction<T> getFitnessFunction() { return fitnessFunction; }
    public void setFitnessFunction(IFitnessFunction<T> fitnessFunction) { this.fitnessFunction = fitnessFunction; }

    public IFeasibility<T> getFeasibilityHandler() { return feasibilityHandler; }
    public void setFeasibilityHandler(IFeasibility<T> feasibilityHandler) { this.feasibilityHandler = feasibilityHandler; }

    @Override
    public String toString() {
        return "GAConfig {" +
                "populationSize=" + populationSize +
                ", numGenerations=" + numGenerations +
                ", crossoverRate=" + crossoverRate +
                ", mutationRate=" + mutationRate +
                ", selection=" + (selectionStrategy != null ? selectionStrategy.getClass().getSimpleName() : "null") +
                ", crossover=" + (crossoverStrategy != null ? crossoverStrategy.getClass().getSimpleName() : "null") +
                ", mutation=" + (mutationStrategy != null ? mutationStrategy.getClass().getSimpleName() : "null") +
                ", replacement=" + (replacementStrategy != null ? replacementStrategy.getClass().getSimpleName() : "null") +
                '}';
    }
}
