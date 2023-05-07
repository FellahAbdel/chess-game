package com.diaby.model;

import java.awt.*;
import java.util.ArrayList;

public class Rook extends ChessPiece {
    private boolean hasMoved; // indique si le pion a déjà été déplacé ou non

    public Rook(Color color, int row, int col, boolean isWhiteTurn) {
        super("Rook", color, row, col, isWhiteTurn);
        hasMoved = false;
    }

    public Rook(ChessPiece piece) {
        super(piece.getPieceName(), piece.getColor(), piece.getRow(), piece.getCol(), piece.getWhiteTurn());
    }

    public boolean getHasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public ArrayList<int[]> possiblesMoves(int startYRow, int startXCol, ChessPiece[][] board) {
        ArrayList<int[]> moves = new ArrayList<>();
        boolean canMove = true;
        // Check moves to the right
        for (int i = startXCol + 1; i < 8 && canMove; i++) {
            if (board[startYRow][i] == null) {
                moves.add(new int[]{startYRow, i});
            } else if (board[startYRow][i].isWhite() != this.isWhite()) {
                moves.add(new int[]{startYRow, i});
                canMove = false;
            } else {
                canMove = false;
            }
        }

        canMove = true;
        // Check moves to the left
        for (int i = startXCol - 1; i >= 0 && canMove; i--) {
            if (board[startYRow][i] == null) {
                moves.add(new int[]{startYRow, i});
            } else if (board[startYRow][i].isWhite() != this.isWhite()) {
                moves.add(new int[]{startYRow, i});
                canMove = false;
            } else {
                canMove = false;
            }
        }

        canMove = true;
        // Check moves to the bottom
        for (int i = startYRow + 1; i < 8 && canMove; i++) {
            if (board[i][startXCol] == null) {
                moves.add(new int[]{i, startXCol});
            } else if (board[i][startXCol].isWhite() != this.isWhite()) {
                moves.add(new int[]{i, startXCol});
                canMove = false;
            } else {
                canMove = false;
            }
        }

        canMove = true;
        // Check moves to the top
        for (int i = startYRow - 1; i >= 0 && canMove; i--) {
            if (board[i][startXCol] == null) {
                moves.add(new int[]{i, startXCol});
            } else if (board[i][startXCol].isWhite() != this.isWhite()) {
                moves.add(new int[]{i, startXCol});
                canMove = false;
            } else {
                canMove = false;
            }
        }

        return moves;
    }
}
