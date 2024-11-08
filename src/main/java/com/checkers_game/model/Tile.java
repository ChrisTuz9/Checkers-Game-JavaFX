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

    public boolean isEmpty() {
        return piece == null;
    }
}

/*
import com.checkers_game.model.enums.PieceColor;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.Getter;

public class Tile {
    private Rectangle tile;
    @Getter
    private StackPane stack;
    @Getter
    private Piece piece;

    public Tile(int row, int col) {
        stack = new StackPane();
        tile = new Rectangle(50, 50);
        tile.setFill((row + col) % 2 == 0 ? Color.web("#dfc6cf") : Color.web("#ab788b"));
        tile.setStroke(Color.BLACK);
        stack.getChildren().add(tile);
    }

    public void putPiece(PieceColor pieceColor) {
        piece = new Piece(pieceColor);
        stack.getChildren().add(piece.getPiece());
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
        if (piece != null) {
            stack.getChildren().add(piece.getPiece());
        }
    }

    public void removePiece() {
        if (piece != null) {
            stack.getChildren().remove(piece.getPiece());
            piece = null;
        }
    }
}*/