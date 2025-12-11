package com.softcomp.nn.model;

import com.softcomp.nn.activaitionfunction.ActivationFunction;

public class Layer {
    private int inputSize;
    private int outputSize;
    private double weights[][];
    private double[] biases;
    private ActivationFunction activationFunction;
}
