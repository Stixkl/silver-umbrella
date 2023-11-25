package com.example.silverumbrella.controller;

import com.example.silverumbrella.model.Arista;
import com.example.silverumbrella.model.GameModeType;
import com.example.silverumbrella.model.MazeGraph;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.net.URL;
import com.example.silverumbrella.model.Player;
import javafx.scene.shape.Line;
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
    private int round = 1;
    private MazeGraph mazeGraph;
    @FXML
    private AnchorPane maze;
    @FXML
    private Button dijkstraPower1;
    @FXML
    private Button dijkstraPower2;
    @FXML
    private Button kruskal1;
    @FXML
    private Button kruskal2;
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
        player1.playerChange();
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
        kruskal1.setDisable(true);
        kruskal2.setDisable(true);
        player1.setPlayerActualNode(3);
        player2.setPlayerActualNode(107);
        playerOneTag.setText("Player: " + player1.getName());
        playerTwoTag.setText("Player: " + player2.getName());
        playerOnePoints.setText("Points: " + String.valueOf(player1.getPoints()));
        playerTwoPoints.setText("Points: " + String.valueOf(player2.getPoints()));
        if (round % 2 == 0) {
            goButtonPlayer1.setDisable(true);
            goButtonPlayer2.setDisable(false);
            playerOneTag.setTextFill(Color.BLACK);
            playerTwoTag.setTextFill(Color.RED);
        } else {
            goButtonPlayer2.setDisable(true);
            goButtonPlayer1.setDisable(false);
            playerOneTag.setTextFill(Color.RED);
            playerTwoTag.setTextFill(Color.BLACK);
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

        if (player2.isFinished() && player1.isFinished()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Congratulations");
            alert.setHeaderText("Congratulations the game is over");
            alert.setContentText("You have finished the game");
            alert.showAndWait();
            return;
        }

        if (player1.isPlaying() && !player1.isFinished()) {
            if (player2.getDijkstra() > 0) {
                dijkstraPower2.setDisable(false);
                dijkstraPower1.setDisable(true);
            } else {
                dijkstraPower2.setDisable(true);
                dijkstraPower1.setDisable(false);
            }

            if (player1.getKruskal()>0) {
                kruskal1.setDisable(false);
                kruskal2.setDisable(true);
            } else {
                kruskal1.setDisable(true);
                kruskal2.setDisable(false);
            }

            for (int i = 0; i < radioButtons.size(); i++) {

                if (radioButtons.get(i).isSelected()) {

                    if (mazeGraph.getGraph().adjacent(player1.getPlayerActualNode(), i)) {

                        if (i == 66 && !player1.isFinished()) {
                            player1.setFinished();
                            radioButtons.get(player1.getPlayerActualNode()).setStyle("-fx-background-color: trasparent");
                            player1.setPlayerActualNode(i);
                            radioButtons.get(player1.getPlayerActualNode()).setStyle("-fx-background-color: #ff0000");
                            player1.playerChange();
                            player2.playerChange();
                            goButtonPlayer1.setDisable(true);
                            goButtonPlayer2.setDisable(false);
                            player1.setFinished();
                            playerOneTag.setTextFill(Color.RED);
                            playerTwoTag.setTextFill(Color.BLACK);
                            round++;
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Congratulations");
                            alert.setHeaderText("Congratulations " + player2.getName() + " you finished");
                            alert.setContentText("You have won the game");
                            alert.showAndWait();
                            return;
                        }
                        if (i == 66 && !player1.isFinished()) {
                            return;
                        }

                        radioButtons.get(player1.getPlayerActualNode()).setStyle("-fx-background-color: trasparent");
                        player1.setPlayerActualNode(i);
                        radioButtons.get(player1.getPlayerActualNode()).setStyle("-fx-background-color: #ff0000");
                        playerOneTag.setTextFill(Color.RED);
                        playerTwoTag.setTextFill(Color.BLACK);
                        round++;

                        player1.playerChange();
                        player2.playerChange();
                        goButtonPlayer1.setDisable(true);
                        goButtonPlayer2.setDisable(false);


                    }
                    else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Error This node is not adjacent to the current node");
                        alert.setContentText("Please, select a node adjacent to the current node");
                        alert.showAndWait();
                    }

                }

            }


        }

        else {

            if (player1.getDijkstra()>0) {
                dijkstraPower1.setDisable(false);
                dijkstraPower2.setDisable(true);
            } else {
                dijkstraPower1.setDisable(true);
                dijkstraPower2.setDisable(false);
            }

            if (player1.getKruskal()>0) {
                kruskal1.setDisable(true);
                kruskal2.setDisable(false);
            } else {
                kruskal1.setDisable(false);
                kruskal2.setDisable(true);
            }

            for (int i = 0; i < radioButtons.size(); i++) {

                if (radioButtons.get(i).isSelected()){

                    if (mazeGraph.getGraph().adjacent(player2.getPlayerActualNode(), i)) {

                        if ( i == 66 && !player2.isFinished()){
                            player2.isFinished();
                            radioButtons.get(player2.getPlayerActualNode()).setStyle("-fx-background-color: trasparent");
                            player2.setPlayerActualNode(i);
                            radioButtons.get(player2.getPlayerActualNode()).setStyle("-fx-background-color: #00ff00");
                            player1.playerChange();
                            player2.playerChange();
                            goButtonPlayer1.setDisable(false);
                            goButtonPlayer2.setDisable(true);
                            player2.setFinished();
                            playerOneTag.setTextFill(Color.BLACK);
                            playerTwoTag.setTextFill(Color.RED);
                            round++;
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Congratulations");
                            alert.setHeaderText("Congratulations " + player1.getName() + " you finished");
                            alert.setContentText("You finished");
                            alert.showAndWait();
                            return;
                        }
                        if (i == 66 && !player2.isFinished()){
                            return;
                        }

                        radioButtons.get(player2.getPlayerActualNode()).setStyle("-fx-background-color: trasparent");
                        player2.setPlayerActualNode(i);
                        radioButtons.get(player2.getPlayerActualNode()).setStyle("-fx-background-color: #00ff00");
                        playerOneTag.setTextFill(Color.BLACK);
                        playerTwoTag.setTextFill(Color.RED);
                        round++;

                        player1.playerChange();
                        player2.playerChange();
                        goButtonPlayer1.setDisable(false);
                        goButtonPlayer2.setDisable(true);


                    }

                    else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Error This node is not adjacent to the current node");
                        alert.setContentText("Please, select a node adjacent to the current node");
                        alert.showAndWait();
                    }

                }


            }

        }
        if (round % 10 == 0) {
            player1.setDijkstra(player1.getDijkstra() + 1);
            player2.setDijkstra(player2.getDijkstra() + 1);
        }
        if (round % 5 == 0) {
            player1.setKruskal(player1.getKruskal() + 1);
            player2.setKruskal(player2.getKruskal() + 1);
        }
        playerOnePoints.setText("Points: " + String.valueOf(player1.getPoints()));
        playerTwoPoints.setText("Points: " + String.valueOf(player2.getPoints()));
        round1.setText("Round: " + String.valueOf(round));
        round2.setText("Round: " + String.valueOf(round));



    }

    public void onRadioButtonPressed(int key) {
        if (round % 2 == 0) {
            player1.setPlayerViewNode(key);
            ponderacion1.setText("Ponderacion: " + String.valueOf(steps.get(key).getWeight()));
        } else {
            player2.setPlayerViewNode(key);
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

        radioButtons.get(3).setStyle("-fx-background-color: #ff0000");
        radioButtons.get(107).setStyle("-fx-background-color: #00ff00");
    }


    public void onDijkstraButtonClick() {
        if (round % 2 == 0) {
            player2.setDijkstra(player2.getDijkstra() - 1);
            int player2Node = player2.getPlayerActualNode();
            int goal = 66;
            try {
                ArrayList<Integer> path = mazeGraph.getGraph().dijkstra2(player2Node, goal);
                for (int i = 1; i < path.size(); i++) {
                    radioButtons.get(path.get(i)).setStyle("-fx-background-color: blue");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (player2.getDijkstra() == 0) {
                dijkstraPower2.setDisable(true);
            }
        } else {
            player1.setDijkstra(player1.getDijkstra() - 1);
            int player1Node = player1.getPlayerActualNode();
            int goal = 66;
            try {
                ArrayList<Integer> path2 = mazeGraph.getGraph().dijkstra2(player1Node, goal);
                for (int i = 1; i < path2.size(); i++) {
                    radioButtons.get(path2.get(i)).setStyle("-fx-background-color: orange");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (player1.getDijkstra() == 0) {
                dijkstraPower1.setDisable(true);
            }
        }
    }

    public void onKruskalPressed() {

        if (round % 2 == 0) {
            player1.setKruskal(player1.getKruskal() - 1);

            try {
                ArrayList<Arista<Integer, RadioButton>> minimumSpanningTree = mazeGraph.getGraph().kruskal2();
                Hashtable<Integer,Line> lines = mazeGraph.getAristasLine();

                int count1 = 0;
                int count = 0;
                for (Arista<Integer, RadioButton> aristas : minimumSpanningTree) {
                    if (count >= 5) break;

                    int startNode = aristas.getinitialVertex().getKey();
                    int endNode = aristas.getfinalVertex().getKey();

                    Line line = lines.get(startNode + endNode);
                    line.setStroke(Color.BLUE);
                    line.setStrokeWidth(3);

                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (player1.getKruskal() == 0) {
                kruskal1.setDisable(true);
            }

        } else {
            player2.setKruskal(player2.getKruskal() - 1);

            try {
                ArrayList<Arista<Integer, RadioButton>> minimumSpanningTree = mazeGraph.getGraph().kruskal();
                Hashtable<Integer,Line> lines = mazeGraph.getAristasLine();

                int count = 0;
                for (Arista<Integer, RadioButton> aristas : minimumSpanningTree) {
                    if (count >= 5) break;

                    int startNode = aristas.getinitialVertex().getKey();
                    int endNode = aristas.getfinalVertex().getKey();

                    Line line = lines.get(startNode + endNode);
                    line.setStroke(Color.ORANGE);
                    line.setStrokeWidth(3);

                    if (player2.getKruskal() == 0) {
                        kruskal2.setDisable(true);
                    }

                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }

}
