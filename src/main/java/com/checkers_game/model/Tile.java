package com.checkers_game.model;

import com.checkers_game.model.enums.PieceColor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Tile {
    private final int row;
    private final int col;
    private Piece piece;

    public Tile(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void putPiece(PieceColor pieceColor) {
        piece = new Piece(pieceColor);
    }

    public void clearPiece() {
        piece = null;
    }

    public boolean isEmpty() {
        return piece == null;
    }
}
