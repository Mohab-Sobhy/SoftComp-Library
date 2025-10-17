package com.softcomp.examples.graphcoloring.models;

import java.util.ArrayList;
import java.util.List;

public class Node {
    int id;
    int color;
    private final List<Node> neighbors;

    public Node(int id) {
        this.id = id;
        this.color = -1; // No Color Initially
        this.neighbors = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void addNeighbor(Node neighbor) {
        if (!neighbors.contains(neighbor)) {
            neighbors.add(neighbor);
        }
    }

    public List<Node> getNeighbors() {
        return neighbors;
    }
}
