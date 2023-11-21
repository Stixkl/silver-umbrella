package com.example.silverumbrella.model;

import javafx.scene.control.RadioButton;

import java.util.ArrayList;

public class Vertex<K extends Comparable<K>,V> extends RadioButton {

    private int time;

    private int distance;

    private Color color;

    private Vertex<K,V> predecessor;

    private final V value;

    private final K key;

    private int finishTime;

    public Vertex(K key, V value) {
        time = 0;
        this.key = key;
        this.value = value;
        distance = 0;
        this.color = Color.WHITE;
        finishTime = 0;
    }

    public V getValue() {
        return value;
    }

    public int getDistance() {
        return distance;
    }

    public Color getColor() {
        return color;
    }

    public void setDiscoveryTime(int discoveryTime) {
        this.time = discoveryTime;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public K getKey() {
        return key;
    }

    public void setPredecessor(Vertex<K, V> predecessor) {
        this.predecessor = predecessor;

    }

    public int getDiscoveryTime() {
        return time;
    }
    public Vertex<K, V> getPredecessor() {
        return predecessor;

    }

    public int getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }
}
