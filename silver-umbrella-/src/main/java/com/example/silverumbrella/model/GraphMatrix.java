package com.example.silverumbrella.model;

import java.util.*;

import java.util.stream.Collectors;
public class GraphMatrix <K extends Comparable<K>,V>  extends Graph<K,V>{
    private HashMap<K, Path<K,V>> vertexs;
    private ArrayList<Integer>[][] matrix;

    public GraphMatrix(int vertexNumber, EGraph type){
        super(type);
        vertexs = new HashMap<>();
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
        if(!vertexs.containsKey(key)){
            vertexs.put(key,new Path<>(key,value));
            vertexesPosition.put(key,numberVertexsCurrent++);
            return true;
        }
        return false;

    }

    @Override
    public void BFS(K keyVertex) {
        for (Path<K,V> vertex: vertexs.values()) {
            vertex.setColor(Color.WHITE);
            vertex.setDistance(infinite);
            vertex.setPredecessor(null);
        }
        Path<K,V> vertex = vertexs.get(keyVertex);
        vertex.setColor(Color.GRAY);
        vertex.setDistance(0);
        Queue<Path<K,V>> queue = new LinkedList<>();
        queue.offer(vertex);
        while (!queue.isEmpty()){
            Path<K,V> u = queue.poll();
            for(Path<K,V> v: vertexs.values()) {
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
        Path<K,V> vertex = vertexs.remove(key);
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
        Integer index = vertexesPosition.get(key);

        return index == null ? -1 : index;
    }
    @Override
    public Path<K, V> getVertex(K key) {
        return vertexs.get(key);
    }

    @Override
    public boolean addArista(K key1, K key2, int weight) {
        int vertex1 = indexVertex(key1);
        int vertex2 = indexVertex(key2);
        matrix[vertex1][vertex2].add(weight);
        Collections.sort(matrix[vertex1][vertex2]);
        aristas.add(new Arista<>(vertexs.get(key1),vertexs.get(key2),weight));
        if(!directed){

            matrix[vertex2][vertex1].add(weight);
            Collections.sort(matrix[vertex2][vertex1]);
            aristas.add(new Arista<>(vertexs.get(key2),vertexs.get(key1),weight));
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
                if (Arista.getStart().getKey().compareTo(key1) == 0 && Arista.getDestination().getKey().compareTo(key2) == 0) {
                    iterator.remove();
                }
            }
            if (!directed) {
                matrix[vertex2][vertex1].remove(0);
                for (Iterator<Arista<K, V>> iterator = aristas.iterator(); iterator.hasNext();) {
                    Arista Arista = iterator.next();
                    if (Arista.getStart().getKey().compareTo(key2) == 0 && Arista.getDestination().getKey().compareTo(key1) == 0) {
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
        for (Path<K,V> vertex: vertexs.values()) {
            vertex.setColor(Color.WHITE);
            vertex.setPredecessor(null);
        }
        time = 0;
        for (Path<K,V> vertex: vertexs.values()) {
            if(vertex.getColor() == Color.WHITE){
                DFS(vertex);
            }
        }
    }

    private void DFS(Path<K,V> vertex) {
        time++;
        vertex.setDiscoveryTime(time);
        vertex.setColor(Color.GRAY);
        for(Path<K,V> v: vertexs.values()) {
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
    public ArrayList<Integer> dijkstra(K keyVertexSource) {
        for (Path<K,V> vertex: vertexs.values()) {
            if(vertex.getKey().compareTo(keyVertexSource) != 0)
                vertex.setDistance(infinite);
            vertex.setPredecessor(null);
        }
        vertexs.get(keyVertexSource).setDistance(0);
        PriorityQueue<Path<K,V>> queue = new PriorityQueue<>(Comparator.comparingInt(Path::getDistance));
        for (Path<K,V> vertex: vertexs.values()) {
            queue.offer(vertex);
        }
        while (!queue.isEmpty()){
            Path<K,V> u = queue.poll();
            for(Path<K,V> v: vertexs.values()) {
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

        return vertexs.values().stream().map(Path::getDistance).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public ArrayList<Arista<K, V>> kruskal() {
        ArrayList<Arista<K,V>> AristasG=new ArrayList();
        Union findUnion=new Union(matrix.length);
        aristas.sort(Comparator.comparingInt(Arista::getWeight));

        for(Arista<K,V> Arista: aristas){
            int keyIndex1= indexVertex(Arista.getStart().getKey());
            int keyIndex2= indexVertex(Arista.getDestination().getKey());

            if(findUnion.find(keyIndex1) != findUnion.find(keyIndex2)){
                AristasG.add(Arista);
                findUnion.union(keyIndex1,keyIndex2);
            }
        }
        return AristasG;
    }
    public void floydWarshall() {
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
        K startVertex = vertexs.keySet().iterator().next();
        visited.add(startVertex);
        addAristasToMinHeap(startVertex, minHeap);
        while (visited.size() < vertexs.size()) {
            Arista<K, V> minArista = minHeap.poll();
            K fromKey = minArista.getStart().getKey();
            K toKey = minArista.getDestination().getKey();

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
                for (Map.Entry<K, Integer> entry : vertexesPosition.entrySet()) {
                    if (entry.getValue() == i) {
                        neighborKey = entry.getKey();
                        break;
                    }
                }
                int weight = matrix[index][i].get(0);
                if (!minHeap.contains(new Arista<>(vertexs.get(key), vertexs.get(neighborKey), weight))) {
                    minHeap.add(new Arista<>(vertexs.get(key), vertexs.get(neighborKey), weight));
                }
            }
        }
    }
}
