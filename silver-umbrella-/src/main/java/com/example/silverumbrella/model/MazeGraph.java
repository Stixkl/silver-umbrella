package com.example.silverumbrella.model;

import javafx.scene.Cursor;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;

public class MazeGraph {
    private Hashtable<String,RadioButton> radioButtons;
    private IGraph<Integer,RadioButton> mazeGraph;
    private Hashtable<Integer, Line> lines;

    public MazeGraph(GameModeType gameModeType) {
        if (gameModeType == GameModeType.GRAPH_MATRIX)
            mazeGraph = new GraphMatrix<>(110, EGraph.SIMPLE);
        else {
            mazeGraph = new GraphList<>(EGraph.SIMPLE);
        }
        radioButtons = new Hashtable<>();
        lines = new Hashtable<>();
        createRadioButtons();
        addAristas();

    }

    public void createRadioButtons(){
        int[] positionsX1 = { 13, 219, 255, 360, 393,  533, 672,  325,638,    14, 116, 219, 255, 360, 428,   80, 150, 219, 287, 393, 463, 638,   45, 116, 186, 360, 464, 500, 533, 603, 672,   13, 186, 255, 290, 428, 500, 638,   13, 116, 255, 399, 603, 672,   80, 186, 287, 428, 500, 567, 638,   45, 219, 290, 325, 464, 533,  116, 219, 464, 533, 603};
        int[] positionsY1 = {670, 670, 670, 670, 670,  670, 670,  635,635,   603, 603, 603, 603, 603, 603,  566, 566, 566, 566, 566, 566, 566,  534, 534, 534, 534, 534, 534, 534, 534, 534,  498, 498, 498, 498, 498, 498, 498,  463, 463, 463, 463, 463, 463,  429, 429, 429, 429, 429, 429, 429,  395, 395, 395, 395, 395, 395,  363, 363, 363, 363, 363};
        int[] positionsX2 = { 13, 186, 255, 500, 340, 603,   13,  80, 116, 186, 255, 393, 464, 533,  116, 428, 603, 672,  45,  255, 325, 360, 464, 500, 638,   13, 150, 325,   80, 186, 360, 567, 672,  638,  13, 116, 255,   47, 290, 428, 500,  219, 603,  150, 255, 393, 464, 672,   13, 325, 428, 567};
        int[] positionsY2 = {321, 321, 321, 321, 337, 321,  285, 285, 285, 285, 285, 285, 285, 285,  256, 256, 256, 256,  220, 220, 220, 220, 220, 220, 220,  182, 182, 182,  152, 152, 152, 152, 152,  114, 114, 114, 114,  463, 463, 463, 463,   83,  83,   45,  45,  45,  45,  45,   11,  11,  11,  11};


        for (int i = 0; i < positionsX1.length; i++) {
            RadioButton radioButton = new RadioButton();
            radioButton.setLayoutX(positionsX1[i]);
            radioButton.setLayoutY(positionsY1[i]);
            radioButton.setCursor(Cursor.HAND);
            mazeGraph.addVertex(i, radioButton);
            radioButtons.put("radioButton" + i, radioButton);
        }

        for (int i = 0; i < positionsX2.length; i++) {
            RadioButton radioButton = new RadioButton();
            radioButton.setLayoutX(positionsX2[i]);
            radioButton.setLayoutY(positionsY2[i]);
            radioButton.setCursor(Cursor.HAND);
            mazeGraph.addVertex(i + 62, radioButton);
            radioButtons.put("radioButton" + (i + 62), radioButton);

        }

    }

    public void addAristas(){

        mazeGraph.addArista(20,19,3);
        mazeGraph.addArista(19,13,2);
        mazeGraph.addArista(13,25,2);
        mazeGraph.addArista(13,12,3);
        mazeGraph.addArista(12,33,3);
        mazeGraph.addArista(0,9,2);
        mazeGraph.addArista(9,0,2);
        mazeGraph.addArista(0,11,2);
        mazeGraph.addArista(2,7,3);
        mazeGraph.addArista(3,14,4);
        mazeGraph.addArista(4,5,4);
        mazeGraph.addArista(5,6,4);
        mazeGraph.addArista(5,28,4);
        mazeGraph.addArista(5,14,5);
        mazeGraph.addArista(6,8,2);
        mazeGraph.addArista(7,12,3);
        mazeGraph.addArista(7,2,3);
        mazeGraph.addArista(8,21,2);
        mazeGraph.addArista(9,31,3);
        mazeGraph.addArista(9,15,3);
        mazeGraph.addArista(10,15,2);
        mazeGraph.addArista(10,16,2);
        mazeGraph.addArista(10,11,3);
        mazeGraph.addArista(11,24,3);
        mazeGraph.addArista(14,20,2);
        mazeGraph.addArista(14,27,6);
        mazeGraph.addArista(14,28,5);
        mazeGraph.addArista(14,5,5);
        mazeGraph.addArista(15,16,2);
        mazeGraph.addArista(17,10,4);
        mazeGraph.addArista(17,33,2);
        mazeGraph.addArista(17,24,2);
        mazeGraph.addArista(18,25,3);
        mazeGraph.addArista(20,14,2);
        mazeGraph.addArista(22,23,2);
        mazeGraph.addArista(22,9,3);
        mazeGraph.addArista(22,31,4);
        mazeGraph.addArista(23,39,2);
        mazeGraph.addArista(25,26,3);
        mazeGraph.addArista(25,13,2);
        mazeGraph.addArista(26,55,4);
        mazeGraph.addArista(27,28,3);
        mazeGraph.addArista(27,5,4);
        mazeGraph.addArista(28,29,8);
        mazeGraph.addArista(29,30,2);
        mazeGraph.addArista(29,36,4);
        mazeGraph.addArista(29,37,2);
        mazeGraph.addArista(30,43,2);
        mazeGraph.addArista(31,51,3);
        mazeGraph.addArista(32,16,4);
        mazeGraph.addArista(34,35,4);
        mazeGraph.addArista(34,46,2);
        mazeGraph.addArista(35,47,2);
        mazeGraph.addArista(36,48,2);
        mazeGraph.addArista(36,38,4);
        mazeGraph.addArista(37,50,2);
        mazeGraph.addArista(38,62,4);
        mazeGraph.addArista(39,40,4);
        mazeGraph.addArista(40,46,2);
        mazeGraph.addArista(40,52,3);
        mazeGraph.addArista(41,47,2);
        mazeGraph.addArista(42,61,3);
        mazeGraph.addArista(42,49,2);
        mazeGraph.addArista(44,45,3);
        mazeGraph.addArista(44,22,4);
        mazeGraph.addArista(44,23,4);
        mazeGraph.addArista(45,63,3);
        mazeGraph.addArista(46,52,3);
        mazeGraph.addArista(48,56,2);
        mazeGraph.addArista(49,56,2);
        mazeGraph.addArista(51,62,3);
        mazeGraph.addArista(51,57,3);
        mazeGraph.addArista(52,53,2);
        mazeGraph.addArista(52,58,3);
        mazeGraph.addArista(52,64,3);
        mazeGraph.addArista(53,58,3);
        mazeGraph.addArista(53,64,3);
        mazeGraph.addArista(54,55,4);
        mazeGraph.addArista(55,66,3);
        mazeGraph.addArista(56,60,3);
        mazeGraph.addArista(57,70,3);
        mazeGraph.addArista(57,69,3);
        mazeGraph.addArista(58,64,2);
        mazeGraph.addArista(58,71,3);
        mazeGraph.addArista(59,74,2);
        mazeGraph.addArista(60,66,2);
        mazeGraph.addArista(60,75,2);
        mazeGraph.addArista(64,65,5);
        mazeGraph.addArista(65,59,5);
        mazeGraph.addArista(66,75,2);
        mazeGraph.addArista(67,78,2);
        mazeGraph.addArista(78,67,2);
        mazeGraph.addArista(68,69,2);


        mazeGraph.addArista(109,105,4);
        mazeGraph.addArista(109,104,4);
        mazeGraph.addArista(109,100,3);
        mazeGraph.addArista(108,107,3);
        mazeGraph.addArista(108,97,3);
        mazeGraph.addArista(108,98,5);
        mazeGraph.addArista(108,103,2);
        mazeGraph.addArista(107,103,3);
        mazeGraph.addArista(106,95,4);
        mazeGraph.addArista(106,87,5);
        mazeGraph.addArista(105,100,5);
        mazeGraph.addArista(104,98,3);
        mazeGraph.addArista(103,89,6);
        mazeGraph.addArista(102,101,3);
        mazeGraph.addArista(102,99,2);
        mazeGraph.addArista(102,96,3);
        mazeGraph.addArista(102,81,5);
        mazeGraph.addArista(101,88,6);
        mazeGraph.addArista(100,99,2);
        mazeGraph.addArista(100,93,3);
        mazeGraph.addArista(99,96,3);
        mazeGraph.addArista(99,91,3);
        mazeGraph.addArista(99,81,5);
        mazeGraph.addArista(98,97,4);
        mazeGraph.addArista(98,85,3);
        mazeGraph.addArista(98,83,7);
        mazeGraph.addArista(97,92,3);
        mazeGraph.addArista(96,89,3);
        mazeGraph.addArista(96,81,4);
        mazeGraph.addArista(95,87,7);
        mazeGraph.addArista(94,93,5);
        mazeGraph.addArista(94,86,3);
        mazeGraph.addArista(91,71,4);
        mazeGraph.addArista(91,76,5);
        mazeGraph.addArista(90,88,3);
        mazeGraph.addArista(90,87,3);
        mazeGraph.addArista(87,80,2);
        mazeGraph.addArista(86,79,2);
        mazeGraph.addArista(85,83,6);
        mazeGraph.addArista(84,75,4);
        mazeGraph.addArista(84,74,4);
        mazeGraph.addArista(83,77,3);
        mazeGraph.addArista(82,77,4);
        mazeGraph.addArista(82,81,2);
        mazeGraph.addArista(80,69,3);
        mazeGraph.addArista(79,78,2);
        mazeGraph.addArista(77,73,2);
        mazeGraph.addArista(76,71,5);
        mazeGraph.addArista(76,70,3);
        mazeGraph.addArista(75,74,2);
        mazeGraph.addArista(73,72,4);

        mazeGraph.getArista().get(1).getinitialVertex().getLayoutX();
        mazeGraph.getArista().get(1).getinitialVertex().getLayoutY();

        for (int i = 0; i < mazeGraph.getArista().size(); i++) {
            RadioButton button1 = mazeGraph.getArista().get(i).getinitialVertex().getValue();
            RadioButton button2 = mazeGraph.getArista().get(i).getfinalVertex().getValue();

            Line line = new Line(button1.getLayoutX() + 8, button1.getLayoutY() + 8, button2.getLayoutX() + 8, button2.getLayoutY() + 8);

            line.setCursor(Cursor.HAND);
            String tooltipText = String.valueOf(mazeGraph.getArista().get(i).getWeight());
            Tooltip tooltip = new Tooltip(tooltipText);
            Tooltip.install(line, tooltip);
            line.setStroke(Color.BLACK);

            line.setStrokeWidth(1.5);
            lines.put(mazeGraph.getArista().get(i).getinitialVertex().getKey() + mazeGraph.getArista().get(i).getfinalVertex().getKey(), line);
        }

    }

    public Hashtable<String,RadioButton> returnVertex() {
        return radioButtons;
    }


    public IGraph<Integer, RadioButton> getGraph() {
        return mazeGraph;
    }

    public Hashtable<Integer,Line> getAristasLine() {
        return lines;
    }
}
