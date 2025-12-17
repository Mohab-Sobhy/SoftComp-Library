package com.softcomp.nn.initializers;

import java.io.Serializable;
import java.util.Random;

public class RandomUniform implements WeightInitializer, Serializable {
    public double[][] initialize(int fanIn, int fanOut) {
        double limit = 1.0 / Math.sqrt(fanIn);
        double min = -limit;
        double max = limit;

        double[][] W = new double[fanIn][fanOut];
        Random rand = new Random();

        for (int i = 0; i < fanIn; i++) {
            for (int j = 0; j < fanOut; j++) {
                W[i][j] = min + (max - min) * rand.nextDouble();
            }
        }
        return W;
    }
}
