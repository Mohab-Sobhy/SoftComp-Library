package com.softcomp.ga;

import java.util.ArrayList;
import java.util.List;

import com.softcomp.ga.crossover.ICrossover;
import com.softcomp.ga.crossover.NPointCrossover;
import com.softcomp.ga.models.Chromosome;
import com.softcomp.ga.models.Gene;

public class App {
    public static void main(String[] args) {
        ICrossover<Integer> cross = new NPointCrossover<>(100);
        List<Gene<Integer>> genes = List.of(
                new Gene<Integer>(1),
                new Gene<Integer>(1),
                new Gene<Integer>(1),
                new Gene<Integer>(1),
                new Gene<Integer>(0),
                new Gene<Integer>(0),
                new Gene<Integer>(0),
                new Gene<Integer>(0));

        Chromosome<Integer> c1 = new Chromosome<>(genes);
        List<Gene<Integer>> genes2 = List.of(
                new Gene<Integer>(0),
                new Gene<Integer>(1),
                new Gene<Integer>(0),
                new Gene<Integer>(0),
                new Gene<Integer>(1),
                new Gene<Integer>(1),
                new Gene<Integer>(1),
                new Gene<Integer>(1));

        Chromosome<Integer> c2 = new Chromosome<>(genes2);
        List<Chromosome<Integer>> arr = cross.crossover(c1, c2);
        System.err.println(arr);
    }
}
