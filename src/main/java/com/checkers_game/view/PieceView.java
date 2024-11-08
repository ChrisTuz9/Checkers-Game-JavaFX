package com.checkers_game.view;

import com.checkers_game.model.Piece;
import com.checkers_game.model.enums.PieceColor;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import lombok.Getter;

@Getter
public class PieceView {
    private final Circle view;

    public PieceView(Piece piece) {
        view = new Circle(20);
        view.setStroke(Color.BLACK);
        view.setFill(piece.getColor() == PieceColor.BLACK ? Color.BLACK : Color.WHITE);
    }

    public void promoteToKing() {
        view.setRadius(15);
        view.setStrokeWidth(6);
        view.setStroke(Color.GOLD);
        view.setStrokeType(StrokeType.OUTSIDE);
    }
}
