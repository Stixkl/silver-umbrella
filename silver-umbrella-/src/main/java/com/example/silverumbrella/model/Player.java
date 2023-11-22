package com.example.silverumbrella.model;

public class Player {
    private String name;
    private int points;
    private int dijkstra;
    private int playerActualNode;
    public Player(String name, int points) {
        this.name = name;
        this.points = points;
        dijkstra = 0;
    }

    public int getPlayerActualNode() {
        return playerActualNode;
    }
    public void setPlayerActualNode(int playerActualNode) {
        this.playerActualNode = playerActualNode;
    }
    public void setDijkstra(int dijkstra) {
        this.dijkstra = dijkstra;
    }
    public int getDijkstra() {
        return dijkstra;
    }
    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPoints(int points) {
        this.points = points;
    }

}
