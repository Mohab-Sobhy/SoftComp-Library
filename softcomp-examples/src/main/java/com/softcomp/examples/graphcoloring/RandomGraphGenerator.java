package com.softcomp.examples.graphcoloring;

import com.softcomp.examples.graphcoloring.models.Graph;
import com.softcomp.examples.graphcoloring.models.Node;

import java.util.Random;


public class RandomGraphGenerator {

    private static RandomGraphGenerator instance;

    private final Random random = new Random();

    private RandomGraphGenerator() {}

    public static synchronized RandomGraphGenerator getInstance() {
        if (instance == null) {
            instance = new RandomGraphGenerator();
        }
        return instance;
    }

    public Graph generateRandomGraph(int numVertices, double edgeProbability, int numColors) {
        Graph graph = new Graph();
        graph.setNumColors(numColors);

        Node[] nodes = new Node[numVertices];
        for (int i = 0; i < numVertices; i++) {
            nodes[i] = graph.addNode(i);
        }

        for (int i = 0; i < numVertices; i++) {
            for (int j = i + 1; j < numVertices; j++) {
                if (random.nextDouble() < edgeProbability) {
                    graph.addEdge(nodes[i], nodes[j]);
                }
            }
        }

        return graph;
    }

}
