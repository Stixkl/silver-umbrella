package com.example.silverumbrella.model;

public class Arista <K extends Comparable<K>,V> {
    private Vertex<K, V> finalVertex;
    private Vertex<K, V> initialVertex;
    private int weight;

    public Arista(Vertex<K, V> initialVertex, Vertex<K, V> finalVertex, int weight) {
        this.initialVertex = initialVertex;
        this.finalVertex = finalVertex;
        this.weight = weight;
    }
    public Vertex<K, V> getfinalVertex() {
        return finalVertex;
    }

    public void setfinalVertex(Vertex<K, V> finalVertex) {
        this.finalVertex = finalVertex;
    }

    public Vertex<K, V> getinitialVertex() {
        return initialVertex;
    }

    public void setinitialVertex(Vertex<K, V> initialVertex) {
        this.initialVertex = initialVertex;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
