package com.softcomp.nn.initializers;

public interface WeightInitializer {

    double[][] initialize(int inputSize, int outputSize);
}