package com.softcomp.ga.selection;

import com.softcomp.ga.models.Chromosome;
import com.softcomp.ga.models.Population;

public interface ISelection<T> {
    Chromosome<T> select(Population<T> population);
}
