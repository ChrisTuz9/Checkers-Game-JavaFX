package com.checkers_game;

import com.checkers_game.controllers.GameController;
import com.checkers_game.model.Board;
import com.checkers_game.model.enums.PieceColor;
import com.checkers_game.view.BoardView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Board board = new Board(PieceColor.WHITE);
        BoardView boardView = new BoardView(board);
        GameController gameController = new GameController(board, boardView);

        Scene scene = new Scene(boardView.getGridPane(), 409, 409);
        stage.setTitle("Checkers");
        stage.setScene(scene);
        stage.show();
    }
}
