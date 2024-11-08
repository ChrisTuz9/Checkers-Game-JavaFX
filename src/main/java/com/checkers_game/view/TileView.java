package com.checkers_game.view;

import com.checkers_game.model.Tile;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.Getter;

@Getter
public class TileView {
    private final StackPane stackPane;
    private final Rectangle tile;
    private PieceView pieceView;

    public TileView(Tile tileModel) {
        stackPane = new StackPane();
        tile = new Rectangle(50, 50);
        tile.setFill((tileModel.getRow() + tileModel.getCol()) % 2 == 0 ? Color.web("#dfc6cf") : Color.web("#ab788b"));
        tile.setStroke(Color.BLACK);
        stackPane.getChildren().add(tile);
    }

    public void setPieceView(PieceView pieceView) {
        this.pieceView = pieceView;
        stackPane.getChildren().add(pieceView.getView());
    }

    public void removePieceView() {
        if (pieceView != null) {
            stackPane.getChildren().remove(pieceView.getView());
            pieceView = null;
        }
    }
}
