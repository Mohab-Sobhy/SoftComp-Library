package com.softcomp.nn.model;

public class Network {

    private Layer[] layers;

    public Network(Layer[] layers) {
        this.layers = layers;
    }

    public double[] forward(double[] inputs) {
        double[] output = inputs;
        for (int i = 0; i < layers.length; i++) {
            double[] inp = layers[i].forward(output);
            output = inp;
        }
        return output;
    }

    public double[] backward(double[] gradOutput, double learningRate) {
        double[] grad = gradOutput;
        for (int i = layers.length - 1; i >= 0; i--) {
            grad = layers[i].backward(grad, learningRate);
        }
        return grad;
    }

    public Layer[] getLayers() {
        return layers;
    }

    public void setLayers(Layer[] layers) {
        this.layers = layers;
    }

}
