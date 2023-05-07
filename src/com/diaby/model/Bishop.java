package com.diaby.model;

import java.awt.*;
import java.util.ArrayList;


public class Bishop extends ChessPiece {
    public Bishop(Color color, int row, int col, boolean isWhiteTurn) {
        super("Bishop", color, row, col, isWhiteTurn);
    }

    public Bishop(ChessPiece piece) {
        super(piece.getPieceName(), piece.getColor(), piece.getRow(), piece.getCol(), piece.getWhiteTurn());
    }

    //    @Override
    public ArrayList<int[]> possiblesMoves(int startYRow, int startXCol, ChessPiece[][] board) {
        ArrayList<int[]> moves = new ArrayList<>();
        boolean canMove = true;
        // Upper Left Diagonal
        for (int i = startYRow - 1, j = startXCol - 1; i >= 0 && j >= 0 && canMove; i--, j--) {
            if (board[i][j] == null) {
                moves.add(new int[]{i, j});
            } else if (board[i][j].isWhite() != this.isWhite()) {
                moves.add(new int[]{i, j});
                canMove = false;
            } else {
                canMove = false;
            }
        }

        canMove = true;
        // Upper Right Diagonal
        for (int i = startYRow - 1, j = startXCol + 1; i >= 0 && j < 8 && canMove; i--, j++) {
            if (board[i][j] == null) {
                moves.add(new int[]{i, j});
            } else if (board[i][j].isWhite() != this.isWhite()) {
                moves.add(new int[]{i, j});
                canMove = false;
            } else {
                canMove = false;
            }
        }

        canMove = true;
        // Lower Left Diagonal
        for (int i = startYRow + 1, j = startXCol - 1; i < 8 && j >= 0 && canMove; i++, j--) {
            if (board[i][j] == null) {
                moves.add(new int[]{i, j});
            } else if (board[i][j].isWhite() != this.isWhite()) {
                moves.add(new int[]{i, j});
                canMove = false;
            } else {
                canMove = false;
            }
        }

        canMove = true;
        // Lower Right Diagonal
        for (int i = startYRow + 1, j = startXCol + 1; i < 8 && j < 8 && canMove; i++, j++) {
            if (board[i][j] == null) {
                moves.add(new int[]{i, j});
            } else if (board[i][j].isWhite() != this.isWhite()) {
                moves.add(new int[]{i, j});
                canMove = false;
            } else {
                canMove = false;
            }
        }

        return moves;
    }

}
