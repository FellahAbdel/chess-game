package com.diaby.model;

//import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Bishop extends ChessPiece {
    public Bishop(String imageName, Color color, int row, int col) {
        super("Bishop",imageName, color, row, col);
    }

    @Override
    public boolean isValidMove(int startX, int startY, int endX, int endY, ChessPiece[][] board) {
        // Vérifie si le déplacement est diagonal
        int deltaX = endX - startX;
        int deltaY = endY - startY;
        if (Math.abs(deltaX) != Math.abs(deltaY)) {
            return false;
        }

        // Vérifie si la trajectoire ne rencontre pas d'obstacle
        int xIncrement = deltaX > 0 ? 1 : -1;
        int yIncrement = deltaY > 0 ? 1 : -1;
        int x = startX + xIncrement;
        int y = startY + yIncrement;
        while (x != endX && y != endY) {
            if (board[y][x] != null) {
                return false;
            }
            x += xIncrement;
            y += yIncrement;
        }


        // Vérifie si la case de destination est vide ou occupée par une pièce de la couleur opposée
        return board[endY][endX] == null || !board[endY][endX].getColor().equals(getColor());
    }

//    @Override
    public ArrayList<int[]> PossiblesMoves(int startYRow, int startXCol, ChessPiece[][] board) {
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



    @Override
    public String getSymbol(){
        return (getColor() == Color.WHITE ? "B" : "N");
    }
}
