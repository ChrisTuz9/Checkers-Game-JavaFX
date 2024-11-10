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

        int directionY = from.getRow() - to.getRow();
        int rowDiff = Math.abs(directionY);
        int colDiff = Math.abs(from.getCol() - to.getCol());

        if(rowDiff != colDiff)
            return false;

        PieceColor pieceColor = from.getPiece().getColor();

        if(tilesWithPieces.size() == 1) {
            if (tilesWithPieces.getFirst().getPiece().getColor() == pieceColor)
                return false;
        }

        if(from.getPiece().isKing())
            return true;

        if(tilesWithPieces.size() == 1)
            return rowDiff == 2;

        return directionY == (pieceColor == board.getPlayerColor() ? 1 : -1);
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
        if(piece != null) {
            int row = toTile.getRow();
            PieceColor pieceColor = piece.getColor();
            if((pieceColor == board.getPlayerColor() && row == 0) ||
                    (pieceColor == board.getOpponentColor() && row == board.getSIZE() - 1)) {
                piece.promoteToKing();
            }
        }
    }

    public boolean isCapturePossibleForColor(PieceColor color) {
        for(int row = 0; row < board.getSIZE(); row++) {
            for(int col = 0; col < board.getSIZE(); col++) {
                Tile tile = board.getTile(row, col);
                Piece piece = tile.getPiece();
                if(piece != null && piece.getColor() == color) {
                    if(canCapturePiece(tile))
                        return true;
                }
            }
        }
        return false;
    }

    public boolean canCapturePiece(Tile tile) {
        int row = tile.getRow();
        int col = tile.getCol();
        int[] deltaX = {-2, 2, -2, 2};
        int[] deltaY = {2, 2, -2, -2};

        if(tile.getPiece().isKing()) {
            for(int i = 0; i < 4; i++) {
                for(int checkRow = row + deltaX[i], checkCol = col + deltaY[i]; board.inBoardRange(checkRow, checkCol); checkRow += deltaX[i], checkCol += deltaY[i]) {
                    if(canCaptureInDirection(tile, board.getTile(checkRow, checkCol)))
                        return true;
                }
            }
        } else {
            for(int i = 0; i < 4; i++) {
                int checkRow = row + deltaX[i];
                int checkCol = col + deltaY[i];
                if(!board.inBoardRange(checkRow, checkCol))
                    continue;
                if(canCaptureInDirection(tile, board.getTile(checkRow, checkCol)))
                    return true;
            }
        }
        return false;
    }

    private boolean canCaptureInDirection(Tile from, Tile to) {
        if(to.getPiece() != null)
            return false;

        List<Tile> tilesWithPieces = getTilesWithPiece(from, to);

        if(tilesWithPieces.size() != 1)
            return false;

        if(tilesWithPieces.getFirst().getPiece().getColor() == from.getPiece().getColor())
            return false;

        if(from.getPiece().isKing())
            return true;

        return Math.abs(from.getRow() - to.getRow()) == 2;
    }
}
