package com.softcomp.nn.initializers;

import java.util.Random;

public class XavierNormal extends WeightInitializer {

    public double[][] initialize(int fanIn, int fanOut) {
        double std = Math.sqrt(2.0 / (fanIn + fanOut));
        double[][] W = new double[fanOut][fanIn];
        Random rand = new Random();

        for (int i = 0; i < fanOut; i++) {
            for (int j = 0; j < fanIn; j++) {
                W[i][j] = rand.nextGaussian() * std;
            }
        }
        return W;
    }
}
