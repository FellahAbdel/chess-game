package com.diaby.model;

import java.awt.*;
import java.util.ArrayList;


public class Bishop extends ChessPiece {
    public Bishop(Color color, int row, int col, boolean isWhiteTurn) {
        super("Bishop",color, row, col, isWhiteTurn);
    }

    public Bishop(ChessPiece piece) {
        super(piece.getPieceName(),piece.getColor(), piece.getRow(), piece.getCol(),piece.getWhiteTurn());
    }

    @Override
    public boolean isValidMove(int startYRow, int startXCol, int endYRow, int endXCol, ChessPiece[][] board) {
        ArrayList<int[]> moves = possiblesMoves(startYRow,startXCol,board);
        // Vérifie si la case de destination est vide ou occupée par une pièce de la couleur opposée
        for(int[] move : moves)
        {
            if(endYRow == move[0] && endXCol == move[1])
            {
                return true;
            }
        }
        if(board[endYRow][endXCol] == null || !board[endYRow][endXCol].getColor().equals(getColor()))
        {
            return true;
        }

        return false;
    }


//    @Override
    public ArrayList<int[]> possiblesMoves(int startYRow, int startXCol, ChessPiece[][] board) {
        ArrayList<int[]>  moves = new ArrayList<>();

        // Upper Left Diagonal
        for(int i=startYRow-1, j=startXCol-1; i>=0 && j>=0; i--, j--){
            if(board[i][j] == null) {
                moves.add(new int[]{i, j});
            }
            else if(board[i][j].isWhite() != this.isWhite()){
                moves.add(new int[]{i, j});
                break;
            }
            else {
                break;
            }
        }

        // Upper Right Diagonal
        for(int i=startYRow-1, j=startXCol+1; i>=0 && j<8; i--, j++){
            if(board[i][j] == null) {
                moves.add(new int[]{i, j});
            }
            else if(board[i][j].isWhite() != this.isWhite()){
                moves.add(new int[]{i, j});
                break;
            }
            else {
                break;
            }
        }

        // Lower Left Diagonal
        for(int i=startYRow+1, j=startXCol-1; i<8 && j>=0; i++, j--){
            if(board[i][j] == null) {
                moves.add(new int[]{i, j});
            }
            else if(board[i][j].isWhite() != this.isWhite()){
                moves.add(new int[]{i, j});
                break;
            }
            else {
                break;
            }
        }

        // Lower Right Diagonal
        for(int i=startYRow+1, j=startXCol+1; i<8 && j<8; i++, j++){
            if(board[i][j] == null) {
                moves.add(new int[]{i, j});
            }
            else if(board[i][j].isWhite() != this.isWhite()){
                moves.add(new int[]{i, j});
                break;
            }
            else {
                break;
            }
        }

        return moves;
    }

}
