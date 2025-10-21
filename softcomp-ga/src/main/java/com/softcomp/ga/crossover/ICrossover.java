package com.softcomp.ga.crossover;

import java.util.List;

import com.softcomp.ga.models.Chromosome;
import com.softcomp.ga.models.Individual;

public interface ICrossover<T> {
    List<Individual<T>> crossover(Individual<T> parent1, Individual<T> parent2);

    double getRate();

    void setRate(double rate);
}