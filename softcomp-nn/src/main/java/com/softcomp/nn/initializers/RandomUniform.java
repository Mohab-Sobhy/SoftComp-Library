package com.softcomp.nn.initializers;

import java.util.Random;

public class RandomUniform {
    public static double[][] xavierUniform(int fanIn, int fanOut) {
        double limit = 1.0 / Math.sqrt(fanIn);
        double min = -limit;
        double max = limit;

        double[][] W = new double[fanOut][fanIn];
        Random rand = new Random();

        for (int i = 0; i < fanOut; i++) {
            for (int j = 0; j < fanIn; j++) {
                W[i][j] = min + (max - min) * rand.nextDouble();
            }
        }
        return W;
    }
}
