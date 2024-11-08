package com.checkers_game.view;

import com.checkers_game.model.Board;
import com.checkers_game.model.Tile;
import javafx.scene.layout.GridPane;
import lombok.Getter;

@Getter
public class BoardView {
    private final GridPane gridPane = new GridPane();
    private final TileView[][] tileViews;

    public BoardView(Board board) {
        tileViews = new TileView[board.getSIZE()][board.getSIZE()];

        for (int row = 0; row < board.getSIZE(); row++) {
            for (int col = 0; col < board.getSIZE(); col++) {
                Tile tile = board.getTile(row, col);
                TileView tileView = new TileView(tile);
                tileViews[row][col] = tileView;

                if (!tile.isEmpty()) {
                    PieceView pieceView = new PieceView(tile.getPiece());
                    tileView.setPieceView(pieceView);
                }

                gridPane.add(tileView.getStackPane(), col, row);
            }
        }
    }

    public TileView getTileView(int row, int col) {
        return tileViews[row][col];
    }
}
