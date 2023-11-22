package com.example.silverumbrella.controller;

import com.example.silverumbrella.HelloApplication;
import com.example.silverumbrella.model.GameModeType;
import com.example.silverumbrella.model.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

    private MazeController mazeController;
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

    public HelloController() {
    }

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
            mazeController = new MazeController();
            Player player1 = new Player(PlayerOne.getText(), 0);
            Player player2 = new Player(PlayerTwo.getText(), 0);
            mazeController.setPlayer1(player1);
            mazeController.setPlayer2(player2);
            MazeController gameController = obtainControllerWindow("maze-game.fxml", "maze").getController();
            Stage stage = (Stage) startButton.getScene().getWindow();
            stage.close();
            //HelloApplication.openWindow("maze-game.fxml", 900,700);
            gameController.addGamemode(gamemodeType);
            //mazeController.setGamemode(gamemodeType);
        }
    }

    public FXMLLoader obtainControllerWindow(String fxmlName, String stageTitle) {
        Parent rootNode;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxmlName));

        Stage newStage = new Stage();
        try {
            newStage.setTitle(stageTitle);
            newStage.getIcons().add(new Image("file:" + (HelloApplication.class.getResource("/images/Image 2.png")).getPath()));
            rootNode = fxmlLoader.load();
            newStage.setScene(new Scene(rootNode));
            newStage.show();
            return fxmlLoader;
        } catch (IOException e) {
            return null;
        }
    }

}