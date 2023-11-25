package com.example.silverumbrella.Tests;

import com.example.silverumbrella.model.GraphList;
import com.example.silverumbrella.model.GraphMatrix;
import com.example.silverumbrella.model.EGraph;
import com.example.silverumbrella.model.GameModeType;
import com.example.silverumbrella.model.GraphList;
import com.example.silverumbrella.model.GraphMatrix;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GraphTest {
    private GraphMatrix<Integer, Integer> matrix;
    private GraphList<Integer, Integer> list;

    public void setUp1() { //list
        list = new GraphList<>(EGraph.SIMPLE);
        list.addVertex(1, 1);
        list.addVertex(2, 2);
        list.addVertex(3, 3);
        list.addVertex(4, 4);
        list.addArista(1, 2, 1);
        list.addArista(1, 3, 2);
        list.addArista(2, 4, 3);
        list.addArista(2, 3, 1);
        list.addArista(3, 1, 5);
        list.addArista(4, 3, 7);
    }

    public void setUp2() {
        list = new GraphList<>(EGraph.SIMPLE);
        list.addVertex(1, 1);
        list.addVertex(2, 2);
        list.addVertex(3, 3);
        list.addVertex(4, 4);
    }

    public void setUp3() {
        matrix = new GraphMatrix<>(6,EGraph.SIMPLE);
        matrix.addVertex(1, 1);
        matrix.addVertex(2, 2);
        matrix.addVertex(3, 3);
        matrix.addVertex(4, 4);
        matrix.addArista(1, 2, 1);
        matrix.addArista(1, 3, 2);
        matrix.addArista(2, 4, 3);
        matrix.addArista(2, 3, 1);
        matrix.addArista(3, 1, 5);
        matrix.addArista(4, 3, 7);
    }

    public void setUp4() {
        matrix = new GraphMatrix<>(6,EGraph.SIMPLE);
        matrix.addVertex(1, 1);
        matrix.addVertex(2, 2);
        matrix.addVertex(3, 3);
        matrix.addVertex(4, 4);
    }

    @Test
    public void AddVertexNormal(){
        setUp1();
        setUp3();

        boolean expected = list.addVertex(1,1);
        Assertions.assertEquals(true, expected);
    }

    @Test
    public void AddEdgeNormal(){
        setUp4();
        setUp2();

    }
    @Test
    public void AddVertexAnormal(){
        setUp2();
        setUp4();

    }

    @Test
    public void AddAristaAnormal(){
        setUp1();
        setUp3();

    }

    @Test
    public void AddAristaNoExiteVertex(){
        setUp1();
        setUp3();

    }

    @Test
    public void RemoveEdgeNormal(){
        setUp2();
        setUp4();

    }

    @Test void RemoveEdgeAnormal(){
        setUp1();
        setUp3();

    }

    @Test
    public void RemoveVertexNormal(){
        setUp2();
        setUp4();
    }

    @Test
    public void RemoveVertexAnormal(){
        setUp1();
        setUp3();
        boolean result;
        result = list.removeArista(1,7);
        Assertions.assertFalse(result);
    }


    @Test
    public void BFS(){
        setUp1();
        setUp3();
    }

    @Test
    public void DFS(){
        setUp1();
        setUp3();
    }

    @Test
    public void dijkstra(){
        setUp1();
        setUp3();
    }

    @Test
    public void kruskal(){
        setUp1();
        setUp3();
    }

    @Test
    public void prim(){
        setUp1();
        setUp3();
    }

    @Test
    public void floydWarshall() {
        setUp1();
        setUp3();
    }

}
