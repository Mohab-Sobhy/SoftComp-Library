package com.softcomp.ga.mutation;

import com.softcomp.ga.models.Chromosome;

public interface IMutation<T> {
    Chromosome<T> mutate(Chromosome<T> chromosome, double mutationRate);
    boolean checkRate(double mutationRate);
}
