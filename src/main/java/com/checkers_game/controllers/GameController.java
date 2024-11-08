package com.checkers_game.controllers;

import com.checkers_game.model.Board;
import com.checkers_game.model.Piece;
import com.checkers_game.model.Tile;
import com.checkers_game.model.enums.PieceColor;
import com.checkers_game.view.BoardView;
import com.checkers_game.view.PieceView;
import com.checkers_game.view.TileView;

public class GameController {
    private final Board board;
    private final BoardView boardView;
    private boolean isWhiteTurn = true;
    private Tile selectedTile = null;

    public GameController(Board board, BoardView boardView) {
        this.board = board;
        this.boardView = boardView;
        setupInteractions();
    }

    private void setupInteractions() {
        for (int row = 0; row < board.getSIZE(); row++) {
            for (int col = 0; col < board.getSIZE(); col++) {
                Tile tile = board.getTile(row, col);
                TileView tileView = boardView.getTileView(row, col);
                tileView.getStackPane().setOnMouseClicked(event -> handleTileClick(tile, tileView));
            }
        }
    }

    private void handleTileClick(Tile clickedTile, TileView clickedTileView) {
        if (clickedTile.getPiece() != null && isPieceColorCorrect(clickedTile.getPiece())) {
            selectedTile = clickedTile;
        } else if (selectedTile != null && board.getGameLogic().isValidMove(selectedTile, clickedTile)) {
            movePiece(selectedTile, clickedTile);
            updateViewAfterMove(selectedTile, clickedTileView);
            endTurn();
        }
    }

    private boolean isPieceColorCorrect(Piece piece) {
        return (isWhiteTurn && piece.getColor() == PieceColor.WHITE) || (!isWhiteTurn && piece.getColor() == PieceColor.BLACK);
    }

    private void movePiece(Tile fromTile, Tile toTile) {
        toTile.setPiece(fromTile.getPiece());
        fromTile.setPiece(null);
    }

    private void updateViewAfterMove(Tile fromTile, TileView toTileView) {
        TileView fromTileView = boardView.getTileView(fromTile.getRow(), fromTile.getCol());

        PieceView pieceView = fromTileView.getPieceView();
        toTileView.setPieceView(pieceView);
        fromTileView.removePieceView();
    }

    private void endTurn() {
        isWhiteTurn = !isWhiteTurn;
        selectedTile = null;
    }
}
