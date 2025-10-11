package com.softcomp.ga.crossover;

import com.softcomp.ga.models.Chromosome;

public interface ICrossover<T> {
    Chromosome<T>[] crossover(Chromosome<T> parent1, Chromosome<T> parent2);
}