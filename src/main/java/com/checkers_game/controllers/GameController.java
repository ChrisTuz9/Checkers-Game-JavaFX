package com.checkers_game.controllers;

import com.checkers_game.model.Board;
import com.checkers_game.model.GameLogic;
import com.checkers_game.model.Piece;
import com.checkers_game.model.Tile;
import com.checkers_game.model.enums.PieceColor;
import com.checkers_game.view.BoardView;
import com.checkers_game.view.PieceView;
import com.checkers_game.view.TileView;
import javafx.scene.control.Alert;

import java.util.List;

public class GameController {
    private final Board board;
    private final BoardView boardView;
    private final GameLogic gameLogic;
    private boolean isWhiteTurn = true;
    private Tile selectedTile = null;
    private String gameOverMessage = null;

    public GameController(Board board, BoardView boardView) {
        this.board = board;
        this.boardView = boardView;
        this.gameLogic = new GameLogic(board);
        setupInteractions();
    }

    private void setupInteractions() {
        for (int row = 0; row < board.getSIZE(); row++) {
            for (int col = 0; col < board.getSIZE(); col++) {
                Tile tile = board.getTile(row, col);
                TileView tileView = boardView.getTileView(row, col);
                tileView.getStackPane().setOnMouseClicked(event -> handleTileClick(tile));
            }
        }
    }

    private void handleTileClick(Tile clickedTile) {
        if (clickedTile.getPiece() != null && isPieceColorCorrect(clickedTile.getPiece())) {
            selectedTile = clickedTile;
        } else if (selectedTile != null) {
            List<Tile> tilesWithPiece = gameLogic.getTilesWithPiece(selectedTile, clickedTile);
            if (gameLogic.isValidMove(selectedTile, clickedTile, tilesWithPiece))
            {
                if(tilesWithPiece.size() == 1) {
                    Tile tileWithPieceToCapture = tilesWithPiece.getFirst();
                    tileWithPieceToCapture.setPiece(null);
                    boardView.getTileView(tileWithPieceToCapture.getRow(), tileWithPieceToCapture.getCol()).removePieceView();
                    movePiece(selectedTile, clickedTile);
                    if(gameLogic.canCapture(clickedTile)) {
                        selectedTile = clickedTile;
                    } else {
                        endTurn();
                    }
                }
                else if(!gameLogic.isCapturePossibleForColor(isWhiteTurn ? PieceColor.WHITE : PieceColor.BLACK)) {
                    movePiece(selectedTile, clickedTile);
                    endTurn();
                }
            }
        }
    }

    private boolean isPieceColorCorrect(Piece piece) {
        return (isWhiteTurn && piece.getColor() == PieceColor.WHITE) || (!isWhiteTurn && piece.getColor() == PieceColor.BLACK);
    }

    private void movePiece(Tile fromTile, Tile toTile) {
        toTile.setPiece(fromTile.getPiece());
        fromTile.setPiece(null);
        gameLogic.checkPromotion(toTile);
        updateViewAfterMove(fromTile, toTile);
    }

    private void updateViewAfterMove(Tile fromTile, Tile toTile) {
        TileView fromTileView = boardView.getTileView(fromTile.getRow(), fromTile.getCol());
        TileView toTileView = boardView.getTileView(toTile.getRow(), toTile.getCol());

        PieceView pieceView = fromTileView.getPieceView();
        if(toTile.getPiece().isKing()) {
            pieceView.promoteToKing();
        }

        toTileView.setPieceView(pieceView);
        fromTileView.removePieceView();
    }

    private void endTurn() {
        isWhiteTurn = !isWhiteTurn;
        selectedTile = null;
        checkGameOver();
    }

    private void checkGameOver() {
        if(isWhiteTurn) {
            if (!gameLogic.hasAnyValidMove(PieceColor.WHITE)) {
                gameOverMessage = "Black win!";
            }
        } else {
            if(!gameLogic.hasAnyValidMove(PieceColor.BLACK)) {
                gameOverMessage = "White win!";
            }
        }

        if(gameOverMessage != null) {
            showGameOverDialog(gameOverMessage);
        }
    }

    private void showGameOverDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
        System.exit(0);
    }
}
