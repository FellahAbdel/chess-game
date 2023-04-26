package com.diaby.model;

import java.awt.*;
import java.util.ArrayList;

public class Queen extends ChessPiece {

    private Rook rook ;
    private Bishop bishop;
    public Queen(String imageName,Color color, int row, int col) {
        super("Queen", imageName,color, row, col);
        rook = new Rook(imageName,color, row, col);
        bishop = new Bishop(imageName,color, row, col);
    }

    public Queen(ChessPiece piece) {
        super(piece.getPieceName(), piece.getImageName(), piece.getColor(), piece.getRow(), piece.getCol());
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
        // Vérifie si la case de destination est vide ou occupée par une pièce de la couleur opposée
        if (board[endYRow][endXCol] == null || !board[endYRow][endXCol].getColor().equals(getColor())) {
            return true;
        }
        return false;
    }

    public ArrayList<int[]> possiblesMoves(int startYRow, int startXCol, ChessPiece[][] board) {
        ArrayList<int[]> moves = new ArrayList<>();

        moves.addAll(rook.possiblesMoves(startYRow,startXCol,board));
        moves.addAll(bishop.possiblesMoves(startYRow,startXCol,board));

        return moves;
    }

    public String getSymbol(){
        return (getColor() == Color.WHITE ? "B" : "N");
    }
}
