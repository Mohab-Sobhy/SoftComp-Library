package com.softcomp.nn.model;

public class Network {

    private Layer[] layers;

    public Network(Layer[] layers) {
        if (layers == null) {
            throw new NullPointerException("layers cannot be null");
        }
        this.layers = layers;
    }

    public double[] forward(double[] inputs) {
        double[] output = inputs;
        for (int i = 0; i < layers.length; i++) {
            if (layers[i] == null) {
                throw new NullPointerException("this layer is null no layer can be null");
            }
            double[] inp = layers[i].forward(output);
            output = inp;
        }
        return output;
    }

    public double[] backward(double[] gradOutput, double learningRate) {
        double[] grad = gradOutput;
        for (int i = layers.length - 1; i >= 0; i--) {
            if (layers[i] == null) {
                throw new NullPointerException("this layer is null no layer can be null");
            }
            grad = layers[i].backward(grad, learningRate);
        }
        return grad;
    }

    public Layer[] getLayers() {
        return layers;
    }

    public void setLayers(Layer[] layers) {
        if (layers == null) {
            throw new NullPointerException("layers cannot be null");
        }
        this.layers = layers;
    }

}
