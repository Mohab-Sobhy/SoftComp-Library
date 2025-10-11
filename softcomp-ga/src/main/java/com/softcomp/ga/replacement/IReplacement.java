package com.softcomp.ga.replacement;

import com.softcomp.ga.models.Population;

public interface IReplacement<T> {
    Population<T> replace(Population<T> oldPopulation, Population<T> offspringPopulation);
}
