package com.softcomp.ga.mutation;

import java.util.Random;
import com.softcomp.ga.models.Chromosome;
import com.softcomp.ga.models.Gene;

public class NonUniformMutation implements IMutation<Double> {

    private double rate;
    private int T;
    private int t;
    private double b;

    public NonUniformMutation(double mutationRate, int T, int t, double b) {
        this.rate = mutationRate;
        this.T = T;
        this.t = t;
        this.b = b;
    }

    @Override
    public Chromosome<Double> mutate(Chromosome<Double> chromosome) {

        if (checkRate(rate)) {
            for (int i = 0; i < chromosome.getGenes().size(); i++) {

                Gene<Double> gene = chromosome.getGenes().get(i);

                double distanceToLower = gene.get() - gene.getLowerBound();
                double distanceToUpper = gene.getUpperBound() - gene.get();

                Random random = new Random();
                double r1 = random.nextDouble();
                double delta;

                if (r1 <= 0.5) delta = distanceToLower;
                else delta = distanceToUpper;

                double r = random.nextDouble();
                double r2 = delta * (Math.pow(1 - r, Math.pow(1 - (double)t / T, b)));

                if (delta == distanceToLower) gene.set(gene.get() - r2);
                else gene.set(gene.get() + r2);
            }
        }

        return chromosome;
    }

    @Override
    public boolean checkRate(double mutationRate) {
        return true;
    }

    @Override
    public double getRate() {
        return rate;
    }

    @Override
    public void setRate(double rate) {
        this.rate = rate;
    }

    // 🔹 Getters and Setters for the other fields

    public int getT() {
        return T;
    }

    public void setT(int T) {
        this.T = T;
    }

    public int getTCurrent() {
        return t;
    }

    public void setTCurrent(int t) {
        this.t = t;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }
}
