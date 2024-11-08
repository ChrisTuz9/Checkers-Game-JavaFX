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

        if(rowDiff != colDiff)
            return false;

        if(from.getPiece().isKing())
            return true;
        return rowDiff == 1 && colDiff == 1;
    }

    public void checkPromotion(Tile toTile) {
        Piece piece = toTile.getPiece();
        if (piece != null) {
            if ((piece.getColor() == board.getPlayerColor() && toTile.getRow() == 0) ||
                    (piece.getColor() == board.getOpponentColor() && toTile.getRow() == board.getSIZE() - 1)) {
                piece.promoteToKing();
            }
        }
    }
}
