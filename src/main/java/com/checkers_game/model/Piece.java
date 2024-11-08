package com.checkers_game.model;

import com.checkers_game.model.enums.PieceColor;
import lombok.Getter;

@Getter
public class Piece {
    private PieceColor color;
    private boolean isKing;

    public Piece(PieceColor color) {
        this.color = color;
        isKing = false;
    }

    public void promoteToKing() {
        this.isKing = true;
    }
}
