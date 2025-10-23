package com.softcomp.examples.graphcoloring.models;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private final List<Node> nodes;
    private int numColors = 3; 

    public Graph() {
        nodes = new ArrayList<>();
    }

    public Node addNode(int id) {
        Node node = new Node(id);
        nodes.add(node);
        return node;
    }

    public void addEdge(Node a, Node b) {
        a.addNeighbor(b);
        b.addNeighbor(a);
    }

    public void printColors() {
        for (Node node : nodes) {
            System.out.println("Node " + node.getId() + " -> Color " + node.getColor());
        }
    }

    public int getNumberOfVertices() {
        return nodes.size();
    }

    public int getNumColors() {
        return numColors;
    }

    public void setNumColors(int numColors) {
        this.numColors = numColors;
    }

    public List<Integer> getNeighbors(int nodeIndex) {
        if (nodeIndex < 0 || nodeIndex >= nodes.size()) {
            throw new IndexOutOfBoundsException("Invalid node index: " + nodeIndex);
        }

        Node node = nodes.get(nodeIndex);
        List<Integer> neighborIds = new ArrayList<>();

        for (Node neighbor : node.getNeighbors()) {
            neighborIds.add(neighbor.getId());
        }

        return neighborIds;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public String getGraphStructure() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Graph Structure ===\n");

        for (Node node : nodes) {
            sb.append("Node ").append(node.getId())
                    .append(" (Color: ").append(node.getColor()).append(") -> ");

            List<Node> neighbors = node.getNeighbors();
            if (neighbors.isEmpty()) {
                sb.append("No neighbors\n");
            } else {
                for (int i = 0; i < neighbors.size(); i++) {
                    sb.append(neighbors.get(i).getId());
                    if (i < neighbors.size() - 1) {
                        sb.append(", ");
                    }
                }
                sb.append("\n");
            }
        }

        return sb.toString();
    }

}
