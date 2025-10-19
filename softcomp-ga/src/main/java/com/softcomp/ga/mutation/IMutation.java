package com.softcomp.ga.mutation;

import com.softcomp.ga.models.Chromosome;

public interface IMutation<T> {
    Chromosome<T> mutate(Chromosome<T> chromosome);
    boolean checkRate(double mutationRate);

    double getRate();
    void setRate(double rate);
}
