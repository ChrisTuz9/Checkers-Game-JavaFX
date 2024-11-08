package com.checkers_game.model;

import com.checkers_game.model.enums.PieceColor;
import lombok.Getter;

@Getter
public class Piece {
    private PieceColor color;

    public Piece(PieceColor color) {
        this.color = color;
    }
}