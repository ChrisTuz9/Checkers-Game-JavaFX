package com.checkers_game.model;

import com.checkers_game.model.enums.PieceColor;
import lombok.Getter;

@Getter
public class Board {
    private final int SIZE = 8;
    private Tile[][] tiles = new Tile[SIZE][SIZE];
    private PieceColor playerColor;
    private PieceColor opponentColor;

    public Board(PieceColor playerColor) {
        this.playerColor = playerColor;
        opponentColor = playerColor == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE;

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                tiles[row][col] = new Tile(row, col);
            }
        }

        placePieces(0, 3, opponentColor);
        placePieces(SIZE - 3, SIZE, playerColor);
    }

    private void placePieces(int rowFirst, int rowLast, PieceColor pieceColor) {
        for (int row = rowFirst; row < rowLast; row++) {
            int colFirst = (row + 1) % 2;
            int colLast = colFirst + 7;
            for (int col = colFirst; col < colLast; col += 2) {
                tiles[row][col].putPiece(pieceColor);
            }
        }
    }

    public Tile getTile(int row, int col) {
        return tiles[row][col];
    }

}
