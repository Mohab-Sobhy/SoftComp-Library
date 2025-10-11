package com.softcomp.ga.fitness;

import com.softcomp.ga.models.Chromosome;

public interface IFitnessFunction<T> {
    double evaluate(Chromosome<T> chromosome);
}
