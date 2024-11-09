package com.checkers_game.model;

import com.checkers_game.model.enums.PieceColor;

import java.util.ArrayList;
import java.util.List;

public class GameLogic {
    private final Board board;

    public GameLogic(Board board) {
        this.board = board;
    }

    public boolean isValidMove(Tile from, Tile to, List <Tile> tilesWithPieces) {
        if(to.getPiece() != null || tilesWithPieces.size() > 1)
            return false;

        int rowDiff = Math.abs(from.getRow() - to.getRow());
        int colDiff = Math.abs(from.getCol() - to.getCol());

        if(rowDiff != colDiff)
            return false;

        if(from.getPiece().isKing())
            return true;

        if(tilesWithPieces.size() == 1)
            return rowDiff == 2;
        return rowDiff == 1;
    }

    public List<Tile> getTilesWithPiece(Tile from, Tile to) {
        int fromRow = from.getRow();
        int fromCol = from.getCol();
        int toRow = to.getRow();
        int toCol = to.getCol();

        int directionX = (int) Math.signum(toRow - fromRow);
        int directionY = (int) Math.signum(toCol - fromCol);
        int distance = Math.abs(fromRow - toRow);
        List<Tile> tilesWithPiece = new ArrayList<>();

        for (int i = 1; i < distance; i++) {
            Tile tile = board.getTile(fromRow + directionX * i, fromCol + directionY * i);
            if (tile.getPiece() != null) {
                tilesWithPiece.add(tile);
            }
        }

        return tilesWithPiece;
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
