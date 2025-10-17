package com.softcomp.examples.graphcoloring.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an undirected graph for the Graph Coloring problem.
 */
public class Graph {
    private final List<Node> nodes;
    private int numColors = 3; // default number of colors (can be changed)

    public Graph() {
        nodes = new ArrayList<>();
    }

    // إضافة عقدة جديدة
    public Node addNode(int id) {
        Node node = new Node(id);
        nodes.add(node);
        return node;
    }

    // إضافة ضلع بين عقدتين
    public void addEdge(Node a, Node b) {
        a.addNeighbor(b);
        b.addNeighbor(a);
    }

    // عرض نتيجة التلوين
    public void printColors() {
        for (Node node : nodes) {
            System.out.println("Node " + node.getId() + " -> Color " + node.getColor());
        }
    }

    // 🔹 عدد العقد (vertices)
    public int getNumberOfVertices() {
        return nodes.size();
    }

    // 🔹 عدد الألوان المتاحة (يمكن تعديله)
    public int getNumColors() {
        return numColors;
    }

    // 🔹 تغيير عدد الألوان المتاحة (لو محتاج)
    public void setNumColors(int numColors) {
        this.numColors = numColors;
    }

    // 🔹 الحصول على الجيران لعقدة معينة (بناءً على index أو id)
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

    // 🔹 الوصول إلى قائمة كل العقد
    public List<Node> getNodes() {
        return nodes;
    }
}
