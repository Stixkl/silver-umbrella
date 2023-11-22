package com.example.silverumbrella.model;

import java.util.*;
import java.util.stream.Collectors;
import java.util.Hashtable;

public class GraphList <K extends Comparable<K>,V> extends Graph<K,V> {

    private Hashtable<K, NewVertexList<K,V>> edges;

    public GraphList(EGraph type) {
        super(type);
        edges = new Hashtable<>();
    }

    @Override
    public boolean addVertex(K key, V value) {
        boolean flag = false;
        if(!edges.containsKey(key)){
            edges.put(key,new NewVertexList<>(key,value));
            vertexPosition.put(key,vertexNumber);
            vertexNumber++;

            flag = true;
        }
        return flag;
    }

    @Override
    public boolean removeVertex(K key) {
        boolean removed = false;
        NewVertexList<K,V> vertex = edges.remove(key);
        if(vertex != null){
            removed = true;

            for(K KeyVertex : edges.keySet()){
                NewVertexList<K,V> vertexList = edges.get(KeyVertex);
                LinkedList<Arista<K,V>> Aristas = vertexList.getAristas();
                for (Iterator<Arista<K, V>> iterator = Aristas.iterator(); iterator.hasNext();) {
                    Arista<K, V> Arista = iterator.next();
                    if (Arista.getfinalVertex().getKey().compareTo(key) == 0) {
                        iterator.remove();

                    }
                }
            }
        }
        return removed;
    }

    @Override
    public Vertex<K, V> getVertex(K key) {
        return edges.get(key);
    }

    public Hashtable<K,NewVertexList<K,V>> getvertex() {
        return edges;
    }


    @Override
    public boolean addArista(K key1, K key2, int weight) {
        boolean flag = false;
        NewVertexList<K, V> v1 = edges.get(key1);
        NewVertexList<K, V> v2 = edges.get(key2);
        Arista<K,V> Arista = new Arista<>(v1,v2,weight);
        v1.getAristas().add(Arista);
        aristas.add(Arista);
        flag = true;
        if(!directed){
            Arista<K,V> Arista2 = new Arista<>(v2,v1,weight);
            v2.getAristas().add(Arista2);
            aristas.add(Arista2);
        }
        return flag;
    }

    @Override
    public boolean removeArista(K key1, K key2) {
        boolean removed = false;
        NewVertexList<K,V> v1 = edges.get(key1);
        NewVertexList<K,V> v2 = edges.get(key2);
        List<Arista<K, V>> AristasV1 = v1.getAristas();
        for (Iterator<Arista<K, V>> iterator = AristasV1.iterator(); iterator.hasNext();) {
            Arista<K, V> Arista = iterator.next();
            if (Arista.getfinalVertex().getKey().compareTo(key2) == 0) {
                iterator.remove();
                removed = true;
            }
        }
        if (!directed) {
            List<Arista<K, V>> AristasV2 = v2.getAristas();
            for (Iterator<Arista<K, V>> iterator = AristasV2.iterator(); iterator.hasNext();) {
                Arista<K, V> Arista = iterator.next();
                if (Arista.getfinalVertex().getKey().compareTo(key1) == 0) {
                    iterator.remove();
                }
            }
        }
        return removed;
    }
    private int vertexIndex(K key){
        Integer index = vertexPosition.get(key);
        return index == null ? -1 : index;
    }

    public boolean adjacent(K keyVertex1, K keyVertex2) {
        boolean adjacent = false;
        NewVertexList<K,V> v1 = edges.get(keyVertex1);
        NewVertexList<K,V> v2 = edges.get(keyVertex2);
        if(v1!=null && v2!=null){
            LinkedList<Arista<K,V>> Aristas1 = v1.getAristas();
            for(Arista<K,V> Arista : Aristas1){
                if(Arista.getfinalVertex().getKey().compareTo(keyVertex2)==0){
                    adjacent = true;
                    break;
                }
            }
        }

        return adjacent;
    }


    @Override
    public void BFS(K keyVertex) {

        for(K key:edges.keySet()){
            Vertex<K,V> vertex = edges.get(key);
            vertex.setColor(Color.WHITE);
            vertex.setDistance(infinite);
            vertex.setPredecessor(null);
        }

        NewVertexList<K,V> vertexL = edges.get(keyVertex);

        vertexL.setColor(Color.GRAY);
        vertexL.setDistance(0);
        Queue<NewVertexList<K,V>> queue = new LinkedList<>();
        queue.offer(vertexL);
        while(!queue.isEmpty()){
            NewVertexList<K,V> vertex = queue.poll();
            LinkedList<Arista<K,V>> Aristas = vertex.getAristas();
            for(Arista<K,V> Arista : Aristas){

                NewVertexList<K,V> vertex2 = (NewVertexList<K, V>) Arista.getfinalVertex();
                if(vertex2.getColor()==Color.WHITE){
                    vertex2.setColor(Color.GRAY);
                    vertex2.setDistance(vertex.getDistance()+1);
                    vertex2.setPredecessor(vertex);
                    queue.offer(vertex2);
                }
            }
            vertex.setColor(Color.BLACK);
        }
    }

    @Override
    public void DFS(){
        if(edges.size()>0){
            for(NewVertexList<K,V> vertex : edges.values()){
                vertex.setColor(Color.WHITE);
                vertex.setPredecessor(null);
            }
            time = 0;
            for(NewVertexList<K,V> vertex : edges.values()){
                if(vertex.getColor()==Color.WHITE){
                    DFSVisit(vertex, time);
                }
            }
        }
    }

    private void DFSVisit(NewVertexList<K,V> vertex, int t){
        time+=1;
        vertex.setDistance(time);

        vertex.setColor(Color.GRAY);
        LinkedList<Arista<K,V>> Aristas = vertex.getAristas();
        for(Arista<K,V> Arista : Aristas){
            NewVertexList<K,V> vertex2 = (NewVertexList<K, V>) Arista.getfinalVertex();
            if(vertex2.getColor()==Color.WHITE){
                vertex2.setPredecessor(vertex);
                DFSVisit(vertex2,time);
            }
        }
        vertex.setColor(Color.BLACK);
        time+=1;
        vertex.setFinishTime(time);
    }

    public ArrayList<Integer> dijkstra2(K startNode, K endNode) {
        for (NewVertexList<K, V> vertex : edges.values()) {
            if (vertex.getKey().compareTo(startNode) != 0)
                vertex.setDistance(infinite);
            vertex.setPredecessor(null);
        }

        PriorityQueue<NewVertexList<K, V>> priority = new PriorityQueue<>(Comparator.comparingInt(Vertex::getDistance));
        for (NewVertexList<K, V> vertex : edges.values()) {
            priority.offer(vertex);
        }

        while (!priority.isEmpty()) {
            NewVertexList<K, V> vertex = priority.poll();
            LinkedList<Arista<K, V>> edges = vertex.getAristas();
            for (Arista<K, V> edge : edges) {
                NewVertexList<K, V> vertex2 = (NewVertexList<K, V>) edge.getfinalVertex();
                int weight = edge.getWeight() + vertex.getDistance();
                if (weight < vertex2.getDistance()) {
                    priority.remove(vertex2);
                    vertex2.setDistance(weight);
                    vertex2.setPredecessor(vertex);
                    priority.offer(vertex2);
                }
            }
        }

        ArrayList<Integer> shortestPath = new ArrayList<>();
        NewVertexList<K, V> currentNode = edges.get(endNode);
        while (currentNode != null && !currentNode.getKey().equals(startNode)) {
            shortestPath.add((Integer) currentNode.getKey());
            currentNode = (NewVertexList<K, V>) currentNode.getPredecessor();
        }
        shortestPath.add((Integer) startNode);
        Collections.reverse(shortestPath);

        return shortestPath;
    }


    @Override
    public ArrayList<Integer> dijkstra(K keyvertexource) {
        for(NewVertexList<K,V> vertex : edges.values()){
            if(vertex.getKey().compareTo(keyvertexource)!=0)
                vertex.setDistance(infinite);
            vertex.setPredecessor(null);
        }

        PriorityQueue<NewVertexList<K,V>> priority = new PriorityQueue<>(Comparator.comparingInt(Vertex::getDistance));
        for(NewVertexList<K,V> vertex : edges.values()){
            priority.offer(vertex);
        }
        while(!priority.isEmpty()){
            NewVertexList<K,V> vertex = priority.poll();
            LinkedList<Arista<K,V>> Aristas = vertex.getAristas();
            for(Arista<K,V> Arista : Aristas){
                NewVertexList<K,V> vertex2 = (NewVertexList<K, V>) Arista.getfinalVertex();
                int weight = Arista.getWeight()+vertex.getDistance();
                if(weight<vertex2.getDistance()){
                    priority.remove(vertex2);
                    vertex2.setDistance(weight);
                    vertex2.setPredecessor(vertex);
                    priority.offer(vertex2);
                }
            }
        }
        return edges.values().stream().map(Vertex::getDistance).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public ArrayList<Arista<K, V>> kruskal() {
        if(!directed){
            ArrayList<Arista<K,V>> mst = new ArrayList<>();
            Union unionFind = new Union(edges.size());
            aristas.sort(Comparator.comparingInt(Arista::getWeight));
            for(Arista<K,V> Arista : aristas){
                NewVertexList<K,V> vertex1 = (NewVertexList<K, V>) Arista.getinitialVertex();
                NewVertexList<K,V> vertex2 = (NewVertexList<K, V>) Arista.getfinalVertex();
                if(unionFind.find(vertexIndex(vertex1.getKey()))!=unionFind.find(vertexIndex(vertex2.getKey()))){
                    mst.add(Arista);
                    unionFind.union(vertexIndex(vertex1.getKey()),vertexIndex(vertex2.getKey()));
                }
            }
            return mst;
        }
        return null;
    }

    @Override
    public ArrayList<Arista<K, V>> prim() {
        if (!directed) {
            HashSet<K> visited = new HashSet<>();
            PriorityQueue<Arista<K, V>> minHeap = new PriorityQueue<>(Comparator.comparingInt(Arista::getWeight));
            ArrayList<Arista<K, V>> minimumSpanningTree = new ArrayList<>();
            K startVertexKey = edges.keySet().iterator().next();
            visited.add(startVertexKey);
            addAristasToHeap(startVertexKey, minHeap);
            while (visited.size() < edges.size()) {
                Arista<K, V> minArista = minHeap.poll();
                K fromKey = minArista.getinitialVertex().getKey();
                K toKey = minArista.getfinalVertex().getKey();

                if (!visited.contains(toKey)) {
                    visited.add(toKey);
                    minimumSpanningTree.add(minArista);
                    addAristasToHeap(toKey, minHeap);
                }
            }

            return minimumSpanningTree;
        }
        return null;
    }

    private void addAristasToHeap(K key, PriorityQueue<Arista<K, V>> minHeap) {
        NewVertexList<K, V> vertex = edges.get(key);
        for (Arista<K, V> Arista : vertex.getAristas()) {
            K neighborKey = Arista.getfinalVertex().getKey();
            int weight = Arista.getWeight();
            if (!minHeap.contains(new Arista<>(vertex, edges.get(neighborKey), weight))) {
                minHeap.add(new Arista<>(vertex, edges.get(neighborKey), weight));
            }
        }
    }

    public ArrayList<ArrayList<Integer>> floyd_Mayweather() {
        int size = vertexNumber;
        ArrayList<ArrayList<Integer>> dist = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                if (i == j) {
                    row.add(0);
                } else {
                    row.add(infinite);
                }
            }
            dist.add(row);
        }

        for (Arista<K, V> Arista : aristas) {
            int fromIndex = vertexIndex(Arista.getinitialVertex().getKey());
            int toIndex = vertexIndex(Arista.getfinalVertex().getKey());
            dist.get(fromIndex).set(toIndex, Arista.getWeight());
        }

        for (int k = 0; k < size; k++) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    int directPath = dist.get(i).get(j);
                    int throughK = dist.get(i).get(k) + dist.get(k).get(j);
                    if (throughK < directPath) {
                        dist.get(i).set(j, throughK);
                    }
                }
            }
        }

        return dist;
    }

    public LinkedList<Arista<K, V>> getArista() {
        return aristas;
    }
}
