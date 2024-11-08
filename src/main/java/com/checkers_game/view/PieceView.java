package com.checkers_game.view;

import com.checkers_game.model.Piece;
import com.checkers_game.model.enums.PieceColor;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import lombok.Getter;

@Getter
public class PieceView {
    private final Circle view;

    public PieceView(Piece piece) {
        view = new Circle(20);
        view.setFill(piece.getColor() == PieceColor.BLACK ? Color.BLACK : Color.WHITE);
        view.setStroke(Color.BLACK);
    }
}
