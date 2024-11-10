package com.checkers_game;

import com.checkers_game.controllers.GameController;
import com.checkers_game.model.Board;
import com.checkers_game.model.enums.PieceColor;
import com.checkers_game.view.BoardView;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    private PieceColor selectedColor = PieceColor.WHITE;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        showStartScreen(primaryStage);
    }

    private void showStartScreen(Stage stage) {
        VBox startLayout = new VBox(20);
        startLayout.setPadding(new Insets(20));
        startLayout.setAlignment(Pos.CENTER);

        Text titleText = new Text("Checkers");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        Label instructionLabel = new Label("Choose a color");
        instructionLabel.setFont(Font.font("Arial", 16));

        ChoiceBox<PieceColor> colorChoiceBox = new ChoiceBox<>();
        colorChoiceBox.getItems().addAll(PieceColor.WHITE, PieceColor.BLACK);
        colorChoiceBox.setValue(PieceColor.WHITE);

        Button startButton = new Button("Start");
        startButton.setOnAction(e -> {
            selectedColor = colorChoiceBox.getValue();
            showGameScreen(stage);
        });

        startLayout.getChildren().addAll(titleText, instructionLabel, colorChoiceBox, startButton);
        Scene startScene = new Scene(startLayout, 409, 409);
        stage.setTitle("Checkers");
        stage.setScene(startScene);
        stage.show();
    }

    private void showGameScreen(Stage stage) {
        Board board = new Board(selectedColor);
        BoardView boardView = new BoardView(board);
        GameController gameController = new GameController(board, boardView);

        Scene gameScene = new Scene(boardView.getGridPane(), 409, 409);
        stage.setScene(gameScene);
        stage.show();
    }
}
