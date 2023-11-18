package com.example.silverumbrella.model;

import java.util.LinkedList;

public class NewVertexList <K extends Comparable<K>,V> extends Path<K,V> {
    private final LinkedList<Arista<K, V>> edges;
    public NewVertexList(K key, V value) {
        super(key, value);
        edges = new LinkedList<>();
    }
    public LinkedList<Arista<K, V>> getAristas() {
        return edges;
    }
}
