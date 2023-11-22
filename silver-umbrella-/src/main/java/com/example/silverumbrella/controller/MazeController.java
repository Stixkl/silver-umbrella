package com.example.silverumbrella.controller;

import com.example.silverumbrella.model.Arista;
import com.example.silverumbrella.model.GameModeType;
import com.example.silverumbrella.model.MazeGraph;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.net.URL;
import com.example.silverumbrella.model.Player;
import javafx.scene.shape.Rectangle;

import java.security.Key;
import java.util.*;

public class MazeController implements Initializable {
    private int dijkstraPowerPlayer1;
    private int dijkstraPowerPlayer2;
    private static GameModeType gamemode;
    private static Player player1;
    private static Player player2;
    private Hashtable<Integer,RadioButton> radioButtons;
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
    @FXML
    private Button dijkstraPower1;
    @FXML
    private Button dijkstraPower2;
    @FXML
    private Button jugadorPoder1;
    @FXML
    private Button jugadorPoder2;
    @FXML
    private Rectangle player1Rectangle;
    @FXML
    private Rectangle player2Rectangle;

    private LinkedList<Arista<Integer,RadioButton>> steps;
    public MazeController() {
    }
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
        player1Rectangle.setFill(Color.GREEN);
        player1Rectangle.setStroke(Color.GREEN);
        player2Rectangle.setFill(Color.RED);
        player2Rectangle.setStroke(Color.RED);
        dijkstraPower1.setDisable(true);
        dijkstraPower2.setDisable(true);
        player1.setPlayerActualNode(3);
        player2.setPlayerActualNode(107);
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

    public void setActionOnRadioButtons() {
        for (Map.Entry<Integer, RadioButton> entry : radioButtons.entrySet()) {
            entry.getValue().setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    onRadioButtonPressed(entry.getKey());
                }
            });
        }
    }

    public void onButtonPressed() {
        if (round % 2 == 0) {
            if (player2.getDijkstra()>0) {
                dijkstraPower2.setDisable(false);
                dijkstraPower1.setDisable(true);
            } else {
                dijkstraPower2.setDisable(true);
                dijkstraPower1.setDisable(true);
            }
            radioButtons.get(player2.getPlayerActualNode()).setStyle("-fx-background-color: #ff0000");
            goButtonPlayer1.setDisable(true);
            goButtonPlayer2.setDisable(false);
            playerOneTag.setTextFill(Color.BLACK);
            playerTwoTag.setTextFill(Color.RED);
            round++;
        } else {
            if (player1.getDijkstra()>0) {
                dijkstraPower1.setDisable(false);
                dijkstraPower2.setDisable(true);
            } else {
                dijkstraPower1.setDisable(true);
                dijkstraPower2.setDisable(true);
            }
            radioButtons.get(player1.getPlayerActualNode()).setStyle("-fx-background-color: #00ff00");
            goButtonPlayer2.setDisable(true);
            goButtonPlayer1.setDisable(false);
            playerOneTag.setTextFill(Color.RED);
            playerTwoTag.setTextFill(Color.BLACK);
            round++;
        }
        if (round % 10 == 0) {
            player1.setDijkstra(player1.getDijkstra() + 1);
            player2.setDijkstra(player2.getDijkstra() + 1);
        }
        playerOnePoints.setText("Points: " + String.valueOf(player1.getPoints()));
        playerTwoPoints.setText("Points: " + String.valueOf(player2.getPoints()));
        round1.setText("Round: " + String.valueOf(round));
        round2.setText("Round: " + String.valueOf(round));
    }

    public void onRadioButtonPressed(int key) {
        if (round % 2 == 0) {
            player1.setPlayerActualNode(key);
            ponderacion1.setText("Ponderacion: " + String.valueOf(steps.get(key).getWeight()));
        } else {
            player2.setPlayerActualNode(key);
            ponderacion2.setText("Ponderacion: " + String.valueOf(steps.get(key).getWeight()));
        }
    }


    public void addGamemode(GameModeType  gameMode) {
        gamemode = gameMode;
        mazeGraph = new MazeGraph(gameMode);
        maze.getChildren().addAll(mazeGraph.returnVertex().values());
        maze.getChildren().addAll(mazeGraph.getAristasLine().values());
        radioButtons = mazeGraph.returnVertex();
        steps = mazeGraph.getGraph().getArista();
        setActionOnRadioButtons();
    }


    public void onDijkstraButtonClick() {
        if (round % 2 == 0) {
            player1.setDijkstra(player1.getDijkstra() - 1);
            boolean flag1 = true;
            int player1Node = player1.getPlayerActualNode();
            int goal = 66;
            try {
                ArrayList<Integer> path2 = mazeGraph.getGraph().dijkstra2(player1Node, goal);
                for (int i = 0; i < path2.size(); i++) {
                    radioButtons.get(path2.get(i)).setStyle("-fx-background-color: #00ff00");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (player1.getDijkstra() == 0) {
                dijkstraPower1.setDisable(true);
            }
        } else {
            player2.setDijkstra(player2.getDijkstra() - 1);
            boolean flag2 = true;
            int player2Node = player2.getPlayerActualNode();
            int goal = 66;
            try {
                ArrayList<Integer> path = mazeGraph.getGraph().dijkstra2(player2Node, goal);
                for (int i = 0; i < path.size(); i++) {
                    radioButtons.get(path.get(i)).setStyle("-fx-background-color: #ff0000");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (player2.getDijkstra() == 0) {
                dijkstraPower2.setDisable(true);
            }
        }
    }

    public void onPoder2Pressed() {

    }

}
