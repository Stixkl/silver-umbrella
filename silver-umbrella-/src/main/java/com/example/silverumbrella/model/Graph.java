package com.example.silverumbrella.model;

import java.util.HashMap;
import java.util.LinkedList;

public abstract class Graph <K extends Comparable<K>,V> implements IGraph<K,V> {
    protected final HashMap<K, Integer> vertexPosition;
    protected int time;
    protected int vertexNumber;
    protected boolean loops;
    protected boolean multiple;
    protected boolean directed;
    protected final Integer infinite;
    protected LinkedList<Arista<K, V>> aristas;
    protected Graph(EGraph type) {
        aristas = new LinkedList<>();
        time = 0;
        vertexPosition = new HashMap<>();
        vertexNumber = 0;
        infinite = Integer.MAX_VALUE;
        selectType(type);
    }

    public void selectType(EGraph type){
        directed=(type==EGraph.DIRECTED || type==EGraph.MULTIGRAPH_DIRECTED);
        multiple=(type==EGraph.MULTIGRAPH || type==EGraph.MULTIGRAPH_DIRECTED || type==EGraph.PSEUDOGRAPH);
        loops=(type==EGraph.PSEUDOGRAPH || type==EGraph.DIRECTED || type==EGraph.MULTIGRAPH_DIRECTED);
    }
}
