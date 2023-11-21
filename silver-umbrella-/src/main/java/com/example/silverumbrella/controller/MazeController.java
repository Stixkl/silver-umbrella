package com.example.silverumbrella.controller;

import com.example.silverumbrella.model.Arista;
import com.example.silverumbrella.model.GameModeType;
import com.example.silverumbrella.model.MazeGraph;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.net.URL;
import com.example.silverumbrella.model.Player;

import java.security.Key;
import java.util.*;

public class MazeController implements Initializable {

    private static GameModeType gamemode;
    private static Player player1;
    private static Player player2;
    private Hashtable<String,RadioButton> radioButtons;
    @FXML
    private Label playerOneTag;
    @FXML
    private Label playerTwoTag;
    @FXML
    private Label playerOnePoints;
    @FXML
    private Label playerTwoPoints;
    @FXML
    private Label round1;
    @FXML
    private Label round2;
    @FXML
    private Label ponderacion1;
    @FXML
    private Label ponderacion2;
    @FXML
    private Button goButtonPlayer1;
    @FXML
    private Button goButtonPlayer2;
    private int round = 0;
    private MazeGraph mazeGraph;

    @FXML
    private AnchorPane maze;

    private LinkedList<Arista<Integer,RadioButton>> steps;

    public GameModeType getGamemode() {
        return gamemode;
    }

    public static void setGamemode(GameModeType gamemode) {
        MazeController.gamemode = gamemode;
    }

    public static void setPlayer1(Player player) {
        player1 = player;
    }

    public static void setPlayer2(Player player) {
        player2 = player;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playerOneTag.setText("Player: " + player1.getName());
        playerTwoTag.setText("Player: " + player2.getName());
        playerOnePoints.setText("Points: " + String.valueOf(player1.getPoints()));
        playerTwoPoints.setText("Points: " + String.valueOf(player2.getPoints()));
        if (round % 2 == 0) {
            goButtonPlayer2.setDisable(true);
            goButtonPlayer1.setDisable(false);
            playerOneTag.setTextFill(Color.RED);
            playerTwoTag.setTextFill(Color.BLACK);
        } else {
            goButtonPlayer1.setDisable(true);
            goButtonPlayer2.setDisable(false);
            playerOneTag.setTextFill(Color.BLACK);
            playerTwoTag.setTextFill(Color.RED);
        }
    }

    public void onButtonPressed() {
        if (round % 2 == 0) {
            goButtonPlayer1.setDisable(true);
            goButtonPlayer2.setDisable(false);
            playerOneTag.setTextFill(Color.BLACK);
            playerTwoTag.setTextFill(Color.RED);
            round++;
        } else {
            goButtonPlayer2.setDisable(true);
            goButtonPlayer1.setDisable(false);
            playerOneTag.setTextFill(Color.RED);
            playerTwoTag.setTextFill(Color.BLACK);
            round++;
        }
        playerOnePoints.setText("Points: " + String.valueOf(player1.getPoints()));
        playerTwoPoints.setText("Points: " + String.valueOf(player2.getPoints()));
        round1.setText("Round: " + String.valueOf(round));
        round2.setText("Round: " + String.valueOf(round));
    }

    public void onRadioButtonPressed() {

    }

    public void addRadioButtons() {
        /*
        radioButtons = new Hashtable<>();
        radioButtons.put("radioButton1", radioButton1);
        radioButtons.put("radioButton2", radioButton2);
        radioButtons.put("radioButton3", radioButton3);
        radioButtons.put("radioButton4", radioButton4);
        radioButtons.put("radioButton5", radioButton5);
        for (int i = 0; i < 110; i++) {
        }
        */

    }

    public void addGamemode(GameModeType  gameMode) {
        gamemode = gameMode;
        mazeGraph = new MazeGraph(gameMode);
        maze.getChildren().addAll(mazeGraph.returnVertex().values());
        maze.getChildren().addAll(mazeGraph.getAristasLine().values());
        radioButtons = mazeGraph.returnVertex();
        steps = mazeGraph.getGraph().getArista();
    }


}
