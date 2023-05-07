package variant.model;

import java.awt.*;
import java.util.ArrayList;

public class Queen extends ChessPiece {
    public Queen(Color color, int row, int col,boolean isWhiteTurn) {
        super("Queen", color, row, col, isWhiteTurn);
    }

    public Queen(ChessPiece piece) {
        super(piece.getPieceName(), piece.getColor(), piece.getRow(), piece.getCol(), piece.getWhiteTurn());

    }

    public ArrayList<int[]> possiblesMoves(int startYRow, int startXCol, ChessPiece[][] board) {
        ArrayList<int[]> moves = new ArrayList<>();

        // generate moves like bishop
        boolean canMove = true;
        // Upper Left Diagonal
        for(int i=startYRow-1, j=startXCol-1; i>=0 && j>=0 && canMove; i--, j--){
            if(board[i][j] == null) {
                moves.add(new int[]{i, j});
            }
            else if(board[i][j].isWhite() != this.isWhite()){
                moves.add(new int[]{i, j});
                canMove = false;
            }
            else {
                canMove = false;
            }
        }

        // Upper Right Diagonal
        canMove = true;
        for(int i=startYRow-1, j=startXCol+1; i>=0 && j<12 && canMove; i--, j++){
            if(board[i][j] == null) {
                moves.add(new int[]{i, j});
            }
            else if(board[i][j].isWhite() != this.isWhite()){
                moves.add(new int[]{i, j});
                canMove = false;
            }
            else {
                canMove = false;
            }
        }

        // Lower Left Diagonal
        canMove = true;
        for(int i=startYRow+1, j=startXCol-1; i<8 && j>=0 && canMove; i++, j--){
            if(board[i][j] == null) {
                moves.add(new int[]{i, j});
            }
            else if(board[i][j].isWhite() != this.isWhite()){
                moves.add(new int[]{i, j});
                canMove = false;
            }
            else {
                canMove = false;
            }
        }

        // Lower Right Diagonal
        canMove = true;
        for(int i=startYRow+1, j=startXCol+1; i<8 && j<12 && canMove; i++, j++){
            if(board[i][j] == null) {
                moves.add(new int[]{i, j});
            }
            else if(board[i][j].isWhite() != this.isWhite()){
                moves.add(new int[]{i, j});
                canMove = false;
            }
            else {
                canMove = false;
            }
        }

        // generate moves like rook
        // Check moves to the right
        for (int i = startXCol + 1; i < 12 && canMove; i++) {
            if (board[startYRow][i] == null) {
                moves.add(new int[]{startYRow, i});
            } else if (board[startYRow][i].isWhite() != this.isWhite()) {
                moves.add(new int[]{startYRow, i});
                canMove = false;
            } else {
                canMove = false;
            }
        }

        // Check moves to the left
        canMove = true;
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

        // Check moves to the bottom
        canMove = true;
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

        // Check moves to the top
        canMove = true;
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
