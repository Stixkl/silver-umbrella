package com.example.silverumbrella.model;

public class Player {
    private String name;
    private int points;
    private int dijkstra;
    private int playerActualNode;
    private boolean playing = false;
    private boolean finished = false;
    private int kruskal;
    private int prim;

    private int playerViewNode;

    public Player(String name, int points) {
        this.name = name;
        this.points = points;
        dijkstra = 0;
        kruskal = 0;
        prim = 0;
    }

    public int getPlayerActualNode() {
        return playerActualNode;
    }
    public int getPlayerViewNode() {
        return playerViewNode;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished() {
        this.finished = true;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void playerChange() {
        this.playing = !this.playing;
    }

    public void setPlayerViewNode(int playerViewNode) {
        this.playerViewNode = playerViewNode;
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

    public int getKruskal() {
        return kruskal;
    }

    public void setKruskal(int kruskal) {
        this.kruskal = kruskal;
    }

    public int getPrim() {
        return prim;
    }

    public void setPrim(int prim) {
        this.prim = prim;
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
