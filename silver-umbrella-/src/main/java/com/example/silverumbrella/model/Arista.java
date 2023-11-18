package com.example.silverumbrella.model;

public class Arista <K extends Comparable<K>,V> {
    private Path<K, V> destination;
    private Path<K, V> start;
    private int weight;

    public Arista(Path<K, V> start, Path<K, V> destination, int weight) {
        this.start = start;
        this.destination = destination;
        this.weight = weight;
    }
    public Path<K, V> getDestination() {
        return destination;
    }

    public void setDestination(Path<K, V> destination) {
        this.destination = destination;
    }

    public Path<K, V> getStart() {
        return start;
    }

    public void setStart(Path<K, V> start) {
        this.start = start;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
