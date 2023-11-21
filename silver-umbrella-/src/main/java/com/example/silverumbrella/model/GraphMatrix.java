package com.example.silverumbrella.model;

import java.util.*;
import java.util.stream.Collectors;
import java.util.Hashtable;
public class GraphMatrix <K extends Comparable<K>,V>  extends Graph<K,V>{

    private Hashtable<K, Vertex<K,V>> edges;
    private ArrayList<Integer>[][] matrix;

    public GraphMatrix(int vertexNumber, EGraph type){
        super(type);
        edges = new Hashtable<>();
        matrix = new ArrayList[vertexNumber][vertexNumber];
        int i=0;
        do{
            int j=0;

            do{
                matrix[i][j] = new ArrayList<>();j++;
            }while(j<vertexNumber);
            i++;
        }while(i<vertexNumber);
    }


    @Override
    public boolean addVertex(K key, V value)  {
        if(!edges.containsKey(key)){
            edges.put(key,new Vertex<>(key, value));
            vertexPosition.put(key,vertexNumber++);
            return true;
        }
        return false;

    }

    @Override
    public void BFS(K keyVertex) {
        for (Vertex<K,V> vertex: edges.values()) {
            vertex.setColor(Color.WHITE);
            vertex.setDistance(infinite);
            vertex.setPredecessor(null);
        }
        Vertex<K,V> vertex = edges.get(keyVertex);
        vertex.setColor(Color.GRAY);
        vertex.setDistance(0);
        Queue<Vertex<K,V>> queue = new LinkedList<>();
        queue.offer(vertex);
        while (!queue.isEmpty()){
            Vertex<K,V> u = queue.poll();
            for(Vertex<K,V> v: edges.values()) {
                if(adjacent(u.getKey(),v.getKey()) && v.getColor() == Color.WHITE){
                    v.setColor(Color.GRAY);
                    v.setDistance(u.getDistance()+1);
                    v.setPredecessor(u);
                    queue.offer(v);

                }
            }
            u.setColor(Color.BLACK);
        }

    }

    @Override
    public boolean removeVertex(K key) {
        Vertex<K,V> vertex = edges.remove(key);
        if(vertex != null){
            int index= indexVertex(key);

            int i=0;
            do{
                matrix[index][i].clear();
                matrix[i][index].clear();
                i++;
            }while(i<matrix.length);
            return true;
        }
        return false;
    }

    private int indexVertex(K key){
        Integer index = vertexPosition.get(key);
        return index == null ? -1 : index;
    }
    @Override
    public Vertex<K, V> getVertex(K key) {
        return edges.get(key);
    }

    @Override
    public boolean addArista(K key1, K key2, int weight) {
        int vertex1 = indexVertex(key1);
        int vertex2 = indexVertex(key2);
        if (vertex1 != -1 && vertex2 != -1) {

            matrix[vertex1][vertex2].add(weight);
            Collections.sort(matrix[vertex1][vertex2]);
            aristas.add(new Arista<>(edges.get(key1), edges.get(key2), weight));
            if (!directed) {

                matrix[vertex2][vertex1].add(weight);
                Collections.sort(matrix[vertex2][vertex1]);
                aristas.add(new Arista<>(edges.get(key2), edges.get(key1), weight));
            }
        }
        return true;
    }

    @Override
    public boolean removeArista(K key1, K key2) {
        int vertex1 = indexVertex(key1);
        int vertex2 = indexVertex(key2);

        if(matrix[vertex1][vertex2].size() > 0){
            matrix[vertex1][vertex2].remove(0);
            for (Iterator<Arista<K, V>> iterator = aristas.iterator(); iterator.hasNext();) {
                Arista Arista = iterator.next();
                if (Arista.getinitialVertex().getKey().compareTo(key1) == 0 && Arista.getfinalVertex().getKey().compareTo(key2) == 0) {
                    iterator.remove();
                }
            }
            if (!directed) {
                matrix[vertex2][vertex1].remove(0);
                for (Iterator<Arista<K, V>> iterator = aristas.iterator(); iterator.hasNext();) {
                    Arista Arista = iterator.next();
                    if (Arista.getinitialVertex().getKey().compareTo(key2) == 0 && Arista.getfinalVertex().getKey().compareTo(key1) == 0) {
                        iterator.remove();
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean adjacent(K keyVertex1, K keyVertex2){
        return matrix[indexVertex(keyVertex1)][indexVertex(keyVertex2)].size() > 0;

    }
    @Override
    public void DFS(){
        for (Vertex<K,V> vertex: edges.values()) {
            vertex.setColor(Color.WHITE);
            vertex.setPredecessor(null);
        }
        time = 0;
        for (Vertex<K,V> vertex: edges.values()) {
            if(vertex.getColor() == Color.WHITE){
                DFS(vertex);
            }
        }
    }

    private void DFS(Vertex<K,V> vertex) {
        time++;
        vertex.setDiscoveryTime(time);
        vertex.setColor(Color.GRAY);
        for(Vertex<K,V> v: edges.values()) {
            if(adjacent(vertex.getKey(),v.getKey()) && v.getColor() == Color.WHITE){
                v.setPredecessor(vertex);
                DFS(v);
            }
        }
        vertex.setColor(Color.BLACK);
        time++;
        vertex.setFinishTime(time);
    }


    @Override
    public ArrayList<Integer> dijkstra(K keyedgesource) {
        for (Vertex<K,V> vertex: edges.values()) {
            if(vertex.getKey().compareTo(keyedgesource) != 0)
                vertex.setDistance(infinite);
            vertex.setPredecessor(null);
        }
        edges.get(keyedgesource).setDistance(0);
        PriorityQueue<Vertex<K,V>> queue = new PriorityQueue<>(Comparator.comparingInt(Vertex::getDistance));
        for (Vertex<K,V> vertex: edges.values()) {
            queue.offer(vertex);
        }
        while (!queue.isEmpty()){
            Vertex<K,V> u = queue.poll();
            for(Vertex<K,V> v: edges.values()) {
                if(adjacent(u.getKey(),v.getKey())) {
                    int weight = matrix[indexVertex(u.getKey())][indexVertex(v.getKey())].get(0)+u.getDistance();
                    if(weight < v.getDistance()){
                        v.setDistance(weight);
                        v.setPredecessor(u);
                        queue.offer(v);
                    }

                }
            }
        }

        return edges.values().stream().map(Vertex::getDistance).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public ArrayList<Arista<K, V>> kruskal() {
        ArrayList<Arista<K,V>> AristasG=new ArrayList();
        Union findUnion=new Union(matrix.length);
        aristas.sort(Comparator.comparingInt(Arista::getWeight));

        for(Arista<K,V> Arista: aristas){
            int keyIndex1= indexVertex(Arista.getinitialVertex().getKey());
            int keyIndex2= indexVertex(Arista.getfinalVertex().getKey());

            if(findUnion.find(keyIndex1) != findUnion.find(keyIndex2)){
                AristasG.add(Arista);
                findUnion.union(keyIndex1,keyIndex2);
            }
        }
        return AristasG;
    }

    public void floydMayweather() {
        int n = matrix.length;
        int[][] distance = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    distance[i][j] = 0;
                } else if (matrix[i][j].size() > 0) {
                    distance[i][j] = matrix[i][j].get(0);
                } else {
                    distance[i][j] = Integer.MAX_VALUE;
                }
            }
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (distance[i][k] != Integer.MAX_VALUE && distance[k][j] != Integer.MAX_VALUE) {
                        int newDistance = distance[i][k] + distance[k][j];
                        if (newDistance < distance[i][j]) {
                            distance[i][j] = newDistance;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j && distance[i][j] != Integer.MAX_VALUE) {
                    matrix[i][j].clear();
                    matrix[i][j].add(distance[i][j]);
                }
            }
        }
    }

    @Override
    public LinkedList<Arista<K, V>> getArista() {
        return aristas;
    }

    public ArrayList<Arista<K, V>> prim() {
        HashSet<K> visited = new HashSet<>();
        PriorityQueue<Arista<K, V>> minHeap = new PriorityQueue<>(Comparator.comparingInt(Arista::getWeight));
        ArrayList<Arista<K, V>> minimumSpanningTree = new ArrayList<>();
        K startVertex = edges.keySet().iterator().next();
        visited.add(startVertex);
        addAristasToMinHeap(startVertex, minHeap);
        while (visited.size() < edges.size()) {
            Arista<K, V> minArista = minHeap.poll();
            K fromKey = minArista.getinitialVertex().getKey();
            K toKey = minArista.getfinalVertex().getKey();

            if (!visited.contains(toKey)) {
                visited.add(toKey);
                minimumSpanningTree.add(minArista);
                addAristasToMinHeap(toKey, minHeap);
            }
        }

        return minimumSpanningTree;
    }

    private void addAristasToMinHeap(K key, PriorityQueue<Arista<K, V>> minHeap) {
        int index = indexVertex(key);
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[index][i].size() > 0) {
                K neighborKey = null;
                for (Map.Entry<K, Integer> entry : vertexPosition.entrySet()) {
                    if (entry.getValue() == i) {
                        neighborKey = entry.getKey();
                        break;
                    }
                }
                int weight = matrix[index][i].get(0);
                if (!minHeap.contains(new Arista<>(edges.get(key), edges.get(neighborKey), weight))) {
                    minHeap.add(new Arista<>(edges.get(key), edges.get(neighborKey), weight));
                }
            }
        }
    }
}
