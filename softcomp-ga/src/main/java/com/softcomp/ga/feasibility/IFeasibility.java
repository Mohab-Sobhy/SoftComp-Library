package com.softcomp.ga.feasibility;

import com.softcomp.ga.models.Chromosome;

public interface IFeasibility<T> {
    boolean isFeasible(Chromosome<T> chromosome);
}
