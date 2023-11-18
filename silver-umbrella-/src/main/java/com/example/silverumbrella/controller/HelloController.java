package com.example.silverumbrella.controller;

import com.example.silverumbrella.HelloApplication;
import com.example.silverumbrella.model.GameModeType;
import com.example.silverumbrella.model.Player;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class HelloController {

    @FXML
    private Label welcomeText;
    @FXML
    private ComboBox<String> Gamemode;
    @FXML
    private Button startButton;
    @FXML
    private TextField PlayerOne;
    @FXML
    private TextField PlayerTwo;

    @FXML
    protected void onButtonClick() {
        String gamemode = Gamemode.getValue();
        if (gamemode == null || gamemode.isEmpty() || PlayerOne.getText().isEmpty() || PlayerTwo.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error on initialize the game");
            alert.setContentText("Please, fill in all required fields to start the game");
            alert.showAndWait();
        } else {
            GameModeType gamemodeType = GameModeType.GRAPH_LIST;
            if (gamemode.equals("Graph matrix")) {
                gamemodeType = GameModeType.GRAPH_MATRIX;
            }
            MazeController.setGamemode(gamemodeType);
            Player player1 = new Player(PlayerOne.getText(), 0);
            Player player2 = new Player(PlayerTwo.getText(), 0);
            MazeController.setPlayer1(player1);
            MazeController.setPlayer2(player2);
            Stage stage = (Stage) startButton.getScene().getWindow();
            stage.close();
            HelloApplication.openWindow("maze-game.fxml", 900,700);
        }
    }

}