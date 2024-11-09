package com.checkers_game.model;

import com.checkers_game.model.enums.PieceColor;

public class GameLogic {
    private final Board board;

    public GameLogic(Board board) {
        this.board = board;
    }

    public boolean isValidMove(Tile from, Tile to, Tile tileWithPieceToCapture) {
        if(to.getPiece() != null)
            return false;

        int rowDiff = Math.abs(from.getRow() - to.getRow());
        int colDiff = Math.abs(from.getCol() - to.getCol());

        if(rowDiff != colDiff)
            return false;

        if(from.getPiece().isKing())
            return true;

        if(tileWithPieceToCapture != null)
            return rowDiff == 2;
        return rowDiff == 1;
    }

    public Tile getTileWithPieceToCapture(Tile from, Tile to) {
        int fromRow = from.getRow();
        int fromCol = from.getCol();
        int toRow = to.getRow();
        int toCol = to.getCol();

        int directionX = (int)Math.signum(toRow - fromRow);
        int directionY = (int)Math.signum(toCol - fromCol);
        int distance = Math.abs(fromRow - toRow);
        Tile tileWithPieceToCapture = null;

        if(from.getPiece().isKing()) {
            for(int i = 1; i < distance; i++) {
                Tile tile = board.getTile(fromRow + directionX * i, fromCol + directionY * i);
                Piece piece = tile.getPiece();
                if(piece != null) {
                    if(piece.getColor() != from.getPiece().getColor()) {
                        if (tileWithPieceToCapture == null)
                            tileWithPieceToCapture = tile;
                        else
                            return null;
                    }
                }
            }
        }
        else if (distance == 2) {
            Tile tile = board.getTile(fromRow + directionX, fromCol + directionY);
            Piece piece = tile.getPiece();
            if(piece != null && piece.getColor() != from.getPiece().getColor())
                return tile;
        }

        return tileWithPieceToCapture;
    }

    public void checkPromotion(Tile toTile) {
        Piece piece = toTile.getPiece();
        if (piece != null) {
            int row = toTile.getRow();
            PieceColor pieceColor = piece.getColor();
            if ((pieceColor == board.getPlayerColor() && row == 0) ||
                    (pieceColor == board.getOpponentColor() && row == board.getSIZE() - 1)) {
                piece.promoteToKing();
            }
        }
    }
}
