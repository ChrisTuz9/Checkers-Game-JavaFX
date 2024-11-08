package com.checkers_game.model;

import com.checkers_game.model.enums.PieceColor;
import javafx.scene.layout.GridPane;

public class GameLogic {
    private final Board board;

    public GameLogic(Board board) {
        this.board = board;
    }

    public boolean isValidMove(Tile from, Tile to) {
        if (to.getPiece() != null) {
            return false;
        }

        int rowDiff = Math.abs(from.getRow() - to.getRow());
        int colDiff = Math.abs(from.getCol() - to.getCol());

        return rowDiff == 1 && colDiff == 1;
    }
}

