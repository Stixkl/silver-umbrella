package com.example.silverumbrella.model;

import java.util.ArrayList;
import java.util.LinkedList;

public interface IGraph <K extends Comparable<K>,V>{

    void DFS();
    void BFS(K keyVertex);
    boolean removeVertex(K key);
    Vertex<K,V> getVertex(K key);
    boolean removeArista(K key1, K key2);
    boolean adjacent(K keyV1, K keyV2);
    ArrayList<Arista<K, V>> kruskal();
    ArrayList<Arista<K, V>> kruskal2();
    ArrayList<Integer> dijkstra(K keyVertex);
    LinkedList<Arista<K, V>> getArista();
    boolean addVertex(K key, V value);
    boolean addArista(K key1, K key2, int weight);
    ArrayList<Arista<K, V>> prim();
    public ArrayList<Integer> dijkstra2(K startNode, K endNode);
}
