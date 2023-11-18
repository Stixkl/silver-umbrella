package com.example.silverumbrella.model;

import java.util.*;
import java.util.stream.Collectors;

public class GraphList <K extends Comparable<K>,V> extends Graph<K,V> {

    private final HashMap<K,NewVertexList<K,V>> vertexs;

    public GraphList(EGraph type) {
        super(type);
        vertexs = new HashMap<>();
    }

    @Override
    public boolean addVertex(K key, V value) {
        boolean added = false;
        if(!vertexs.containsKey(key)){
            vertexs.put(key,new NewVertexList<>(key,value));
            vertexesPosition.put(key,numberVertexsCurrent);
            numberVertexsCurrent++;

            added = true;
        }
        return added;
    }

    @Override
    public boolean removeVertex(K key) {
        boolean removed = false;
        NewVertexList<K,V> vertex = vertexs.remove(key);
        if(vertex != null){
            removed = true;

            for(K KeyVertex : vertexs.keySet()){
                NewVertexList<K,V> vertexList = vertexs.get(KeyVertex);
                LinkedList<Arista<K,V>> Aristas = vertexList.getAristas();
                for (Iterator<Arista<K, V>> iterator = Aristas.iterator(); iterator.hasNext();) {
                    Arista<K, V> Arista = iterator.next();
                    if (Arista.getDestination().getKey().compareTo(key) == 0) {
                        iterator.remove();

                    }
                }
            }
        }
        return removed;
    }

    @Override
    public Path<K, V> getVertex(K key) {
        return vertexs.get(key);
    }

    public HashMap<K,NewVertexList<K,V>> getVertexs() {
        return vertexs;
    }


    @Override
    public boolean addArista(K key1, K key2, int weight) {
        boolean added = false;
        NewVertexList<K, V> v1 = vertexs.get(key1);
        NewVertexList<K, V> v2 = vertexs.get(key2);
        Arista<K,V> Arista = new Arista<>(v1,v2,weight);
        v1.getAristas().add(Arista);
        aristas.add(Arista);
        added = true;
        if(!directed){
            Arista<K,V> Arista2 = new Arista<>(v2,v1,weight);
            v2.getAristas().add(Arista2);
            aristas.add(Arista2);
        }
        return added;
    }

    @Override
    public boolean removeArista(K key1, K key2) {
        boolean removed = false;
        NewVertexList<K,V> v1 = vertexs.get(key1);
        NewVertexList<K,V> v2 = vertexs.get(key2);
        List<Arista<K, V>> AristasV1 = v1.getAristas();
        for (Iterator<Arista<K, V>> iterator = AristasV1.iterator(); iterator.hasNext();) {
            Arista<K, V> Arista = iterator.next();
            if (Arista.getDestination().getKey().compareTo(key2) == 0) {
                iterator.remove();
                removed = true;
            }
        }
        if (!directed) {
            List<Arista<K, V>> AristasV2 = v2.getAristas();
            for (Iterator<Arista<K, V>> iterator = AristasV2.iterator(); iterator.hasNext();) {
                Arista<K, V> Arista = iterator.next();
                if (Arista.getDestination().getKey().compareTo(key1) == 0) {
                    iterator.remove();
                }
            }
        }
        return removed;
    }


    public boolean adjacent(K keyVertex1, K keyVertex2) {
        boolean adjacent = false;
        NewVertexList<K,V> v1 = vertexs.get(keyVertex1);
        NewVertexList<K,V> v2 = vertexs.get(keyVertex2);
        if(v1!=null && v2!=null){
            LinkedList<Arista<K,V>> Aristas1 = v1.getAristas();
            for(Arista<K,V> Arista : Aristas1){
                if(Arista.getDestination().getKey().compareTo(keyVertex2)==0){
                    adjacent = true;
                    break;
                }
            }
        }

        return adjacent;
    }
    private int vertexsIndex(K key){
        Integer index = vertexesPosition.get(key);
        return index == null ? -1 : index;
    }


    @Override
    public void BFS(K keyVertex) {

        for(K key:vertexs.keySet()){
            Path<K,V> vertex = vertexs.get(key);
            vertex.setColor(Color.WHITE);
            vertex.setDistance(infinite);
            vertex.setPredecessor(null);
        }

        NewVertexList<K,V> vertexL = vertexs.get(keyVertex);

        vertexL.setColor(Color.GRAY);
        vertexL.setDistance(0);
        Queue<NewVertexList<K,V>> queue = new LinkedList<>();
        queue.offer(vertexL);
        while(!queue.isEmpty()){
            NewVertexList<K,V> vertex = queue.poll();
            LinkedList<Arista<K,V>> Aristas = vertex.getAristas();
            for(Arista<K,V> Arista : Aristas){

                NewVertexList<K,V> vertex2 = (NewVertexList<K, V>) Arista.getDestination();
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

    public void DFS(){
        if(vertexs.size()>0){
            for(NewVertexList<K,V> vertex : vertexs.values()){
                vertex.setColor(Color.WHITE);
                vertex.setPredecessor(null);
            }
            time = 0;
            for(NewVertexList<K,V> vertex : vertexs.values()){
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
            NewVertexList<K,V> vertex2 = (NewVertexList<K, V>) Arista.getDestination();
            if(vertex2.getColor()==Color.WHITE){
                vertex2.setPredecessor(vertex);
                DFSVisit(vertex2,time);
            }
        }
        vertex.setColor(Color.BLACK);
        time+=1;
        vertex.setFinishTime(time);
    }


    @Override
    public ArrayList<Integer> dijkstra(K keyVertexSource) {
        for(NewVertexList<K,V> vertex : vertexs.values()){
            if(vertex.getKey().compareTo(keyVertexSource)!=0)
                vertex.setDistance(infinite);
            vertex.setPredecessor(null);
        }

        PriorityQueue<NewVertexList<K,V>> priority = new PriorityQueue<>(Comparator.comparingInt(Path::getDistance));
        for(NewVertexList<K,V> vertex : vertexs.values()){
            priority.offer(vertex);
        }
        while(!priority.isEmpty()){
            NewVertexList<K,V> vertex = priority.poll();
            LinkedList<Arista<K,V>> Aristas = vertex.getAristas();
            for(Arista<K,V> Arista : Aristas){
                NewVertexList<K,V> vertex2 = (NewVertexList<K, V>) Arista.getDestination();
                int weight = Arista.getWeight()+vertex.getDistance();
                if(weight<vertex2.getDistance()){
                    priority.remove(vertex2);
                    vertex2.setDistance(weight);
                    vertex2.setPredecessor(vertex);
                    priority.offer(vertex2);
                }
            }
        }
        return vertexs.values().stream().map(Path::getDistance).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public ArrayList<Arista<K, V>> kruskal() {
        if(!directed){
            ArrayList<Arista<K,V>> mst = new ArrayList<>();
            Union unionFind = new Union(vertexs.size());
            aristas.sort(Comparator.comparingInt(Arista::getWeight));
            for(Arista<K,V> Arista : aristas){
                NewVertexList<K,V> vertex1 = (NewVertexList<K, V>) Arista.getStart();
                NewVertexList<K,V> vertex2 = (NewVertexList<K, V>) Arista.getDestination();
                if(unionFind.find(vertexsIndex(vertex1.getKey()))!=unionFind.find(vertexsIndex(vertex2.getKey()))){
                    mst.add(Arista);
                    unionFind.union(vertexsIndex(vertex1.getKey()),vertexsIndex(vertex2.getKey()));
                }
            }
            return mst;
        }
        return null;
    }

    public ArrayList<Arista<K, V>> prim() {
        if (!directed) {
            HashSet<K> visited = new HashSet<>();
            PriorityQueue<Arista<K, V>> minHeap = new PriorityQueue<>(Comparator.comparingInt(Arista::getWeight));
            ArrayList<Arista<K, V>> minimumSpanningTree = new ArrayList<>();
            K startVertexKey = vertexs.keySet().iterator().next();
            visited.add(startVertexKey);
            addAristasToHeap(startVertexKey, minHeap);
            while (visited.size() < vertexs.size()) {
                Arista<K, V> minArista = minHeap.poll();
                K fromKey = minArista.getStart().getKey();
                K toKey = minArista.getDestination().getKey();

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
        NewVertexList<K, V> vertex = vertexs.get(key);
        for (Arista<K, V> Arista : vertex.getAristas()) {
            K neighborKey = Arista.getDestination().getKey();
            int weight = Arista.getWeight();
            if (!minHeap.contains(new Arista<>(vertex, vertexs.get(neighborKey), weight))) {
                minHeap.add(new Arista<>(vertex, vertexs.get(neighborKey), weight));
            }
        }
    }

    public ArrayList<ArrayList<Integer>> floyd_warshall() {
        int size = numberVertexsCurrent;
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
            int fromIndex = vertexsIndex(Arista.getStart().getKey());
            int toIndex = vertexsIndex(Arista.getDestination().getKey());
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
