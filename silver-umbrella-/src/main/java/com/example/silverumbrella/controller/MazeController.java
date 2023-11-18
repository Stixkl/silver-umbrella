package com.example.silverumbrella.controller;

import com.example.silverumbrella.model.GameModeType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.paint.Color;

import java.net.URL;
import com.example.silverumbrella.model.Player;

import java.util.ResourceBundle;

public class MazeController implements Initializable {
    private static GameModeType gamemode;
    private static Player player1;
    private static Player player2;
    @FXML
    private Label playerOneTag;
    @FXML
    private Label playerTwoTag;
    @FXML
    private Label playerOnePoints;
    @FXML
    private Label playerTwoPoints;
    @FXML
    private RadioButton radioButton1;
    @FXML
    private RadioButton radioButton2;
    @FXML
    private RadioButton radioButton3;
    @FXML
    private RadioButton radioButton4;
    @FXML
    private RadioButton radioButton5;
    @FXML
    private Label round1;
    @FXML
    private Label round2;
    private int round = 0;

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
            playerOneTag.setTextFill(Color.RED);
            playerTwoTag.setTextFill(Color.BLACK);
        } else {
            playerOneTag.setTextFill(Color.BLACK);
            playerTwoTag.setTextFill(Color.RED);
        }
    }

    public void onRadioButtonPressed() {
        if (round % 2 == 0) {
            playerOneTag.setTextFill(Color.BLACK);
            playerTwoTag.setTextFill(Color.RED);
            round++;
        } else {
            playerOneTag.setTextFill(Color.RED);
            playerTwoTag.setTextFill(Color.BLACK);
            round++;
        }
        round1.setText("Round: " + String.valueOf(round));
        round2.setText("Round: " + String.valueOf(round));
    }

}
