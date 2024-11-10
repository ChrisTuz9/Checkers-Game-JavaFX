package com.checkers_game.model;

import com.checkers_game.model.enums.PieceColor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

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

    public boolean checkAllTiles(PieceColor color, Predicate<Tile> function) {
        for(int row = 0; row < board.getSIZE(); row++) {
            for(int col = 0; col < board.getSIZE(); col++) {
                Tile tile = board.getTile(row, col);
                Piece piece = tile.getPiece();
                if(piece != null && piece.getColor() == color) {
                    if(function.test(tile))
                        return true;
                }
            }
        }
        return false;
    }

    public boolean isMovePossibleForColor(PieceColor color) {
        return checkAllTiles(color, this::canMove);
    }

    public boolean canMove(Tile tile) {
        Piece piece = tile.getPiece();
        int row = tile.getRow();
        int col = tile.getCol();
        int[] deltaX = { -1, 1 };
        int[] deltaY;
        if (piece.isKing()) {
            deltaY = new int[] { 1, -1 };
        } else {
            deltaY = new int[] { piece.getColor() == board.getPlayerColor() ? -1 : 1 };
        }

        for(int i = 0; i < deltaX.length; i++) {
            for(int j = 0; j < deltaY.length; j++) {
                int checkRow = row + deltaY[j];
                int checkCol = col + deltaX[i];
                if(board.inBoardRange(checkRow, checkCol)) {
                    if(board.getTile(checkRow, checkCol).isEmpty()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean isCapturePossibleForColor(PieceColor color) {
        return checkAllTiles(color, this::canCapture);
    }

    public boolean canCapture(Tile tile) {
        int row = tile.getRow();
        int col = tile.getCol();
        int[] deltaX = {-1, 1, -1, 1};
        int[] deltaY = {1, 1, -1, -1};

        if(tile.getPiece().isKing()) {
            for(int i = 0; i < 4; i++) {
                for(int checkRow = row + 2 * deltaX[i], checkCol = col + 2 * deltaY[i]; board.inBoardRange(checkRow, checkCol); checkRow += deltaX[i], checkCol += deltaY[i]) {
                    if(canCaptureInDirection(tile, board.getTile(checkRow, checkCol)))
                        return true;
                }
            }
        } else {
            for(int i = 0; i < 4; i++) {
                int checkRow = row + 2 * deltaX[i];
                int checkCol = col + 2 * deltaY[i];
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

    public boolean hasAnyValidMove(PieceColor color) {
        return isMovePossibleForColor(color) || isCapturePossibleForColor(color);
    }
}
