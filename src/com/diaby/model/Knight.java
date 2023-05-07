package com.diaby.model;

import java.util.ArrayList;

import java.awt.*;

public class Knight extends ChessPiece {
    public Knight(Color color, int row, int col, boolean isWhiteTurn) {
        super("Knight", color, row, col, isWhiteTurn);
    }

    public Knight(ChessPiece piece) {
        super(piece.getPieceName(), piece.getColor(), piece.getRow(), piece.getCol(), piece.getWhitePiecesAtBottom());
    }

    public ArrayList<int[]> possiblesMoves(int startYRow, int startXCol, ChessPiece[][] board) {
        ArrayList<int[]> moves = new ArrayList<>();
        // Two steps up
        if (startYRow - 2 >= 0) {
            // One step to the left

            if (startXCol - 1 >= 0 &&
                    (board[startYRow - 2][startXCol - 1] == null ||
                            board[startYRow - 2][startXCol - 1].isWhite() != this.isWhite())) {
                moves.add(new int[]{startYRow - 2, startXCol - 1});
            }
            // One step to the right
            if (startXCol + 1 < 8 &&
                    (board[startYRow - 2][startXCol + 1] == null ||
                            board[startYRow - 2][startXCol + 1].isWhite() != this.isWhite())) {
                moves.add(new int[]{startYRow - 2, startXCol + 1});
            }
        }

        // Two steps down
        if (startYRow + 2 < 8) {
            // One step to the left
            if (startXCol - 1 >= 0 &&
                    (board[startYRow + 2][startXCol - 1] == null ||
                            board[startYRow + 2][startXCol - 1].isWhite() != this.isWhite())) {
                moves.add(new int[]{startYRow + 2, startXCol - 1});
            }
            // One step to the right
            if (startXCol + 1 < 8 &&
                    (board[startYRow + 2][startXCol + 1] == null ||
                            board[startYRow + 2][startXCol + 1].isWhite() != this.isWhite())) {
                moves.add(new int[]{startYRow + 2, startXCol + 1});
            }
        }

        // Two steps to the left
        if (startXCol - 2 >= 0) {
            // One step up
            if (startYRow - 1 >= 0 &&
                    (board[startYRow - 1][startXCol - 2] == null ||
                            board[startYRow - 1][startXCol - 2].isWhite() != this.isWhite())) {
                moves.add(new int[]{startYRow - 1, startXCol - 2});
            }
            // One step down
            if (startYRow + 1 < 8 &&
                    (board[startYRow + 1][startXCol - 2] == null ||
                            board[startYRow + 1][startXCol - 2].isWhite() != this.isWhite())) {
                moves.add(new int[]{startYRow + 1, startXCol - 2});
            }
        }

        // Two steps to the right
        if (startXCol + 2 < 8) {
            // One step up
            if (startYRow - 1 >= 0 &&
                    (board[startYRow - 1][startXCol + 2] == null ||
                            board[startYRow - 1][startXCol + 2].isWhite() != this.isWhite())) {
                moves.add(new int[]{startYRow - 1, startXCol + 2});
            }
            // One step down
            if (startYRow + 1 < 8 &&
                    (board[startYRow + 1][startXCol + 2] == null ||
                            board[startYRow + 1][startXCol + 2].isWhite() != this.isWhite())) {
                moves.add(new int[]{startYRow + 1, startXCol + 2});
            }
        }

        return moves;
    }

}