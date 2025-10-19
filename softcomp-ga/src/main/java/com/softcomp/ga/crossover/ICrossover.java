package com.softcomp.ga.crossover;

import java.util.List;

import com.softcomp.ga.models.Chromosome;

public interface ICrossover<T> {
    List<Chromosome<T>> crossover(Chromosome<T> parent1, Chromosome<T> parent2);
    double getRate();
    void setRate(double rate);
}