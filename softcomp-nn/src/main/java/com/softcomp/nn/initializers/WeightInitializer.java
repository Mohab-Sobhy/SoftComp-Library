package com.softcomp.nn.initializers;

import java.util.Random;

public abstract class WeightInitializer {
    Random rand = new Random();

    abstract double[][] initialize(int inputSize, int outputSize);
}