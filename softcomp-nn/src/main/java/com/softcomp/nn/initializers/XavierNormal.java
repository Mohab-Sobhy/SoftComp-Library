package com.softcomp.nn.initializers;

import java.io.Serializable;
import java.util.Random;

public class XavierNormal implements WeightInitializer, Serializable {
    private final Random rand = new Random();

    @Override
    public double[][] initialize(int fanIn, int fanOut) {
        double std = Math.sqrt(2.0 / (fanIn + fanOut));
        double[][] W = new double[fanIn][fanOut];

        for (int i = 0; i < fanIn; i++) {
            for (int j = 0; j < fanOut; j++) {
                W[i][j] = rand.nextGaussian() * std;
            }
        }
        return W;
    }
}
