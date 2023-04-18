package com.diaby.model;

//import javax.swing.*;
//import javax.swing.text.Position;

import java.awt.*;
import java.util.ArrayList;

public class Rook extends ChessPiece {
    private boolean hasMoved; // indique si le pion a déjà été déplacé ou non

    public Rook(String imageName,Color color, int row, int col)
    {
        super("Rook", imageName,color, row, col);
        hasMoved = false;
    }

    public boolean getHasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
    @Override
    public boolean isValidMove(int startX, int startY, int endX, int endY, ChessPiece[][] board) {
        // Vérifie si le déplacement est horizontal ou vertical
        if (startX != endX && startY != endY) {
            return false;
        }

        // Vérifie si la trajectoire ne rencontre pas d'obstacle
        int increment;
        if (startX == endX) {
            increment = (endY - startY > 0 ? 1 : -1);
        } else {
            increment = (endX - startX > 0 ? 1 : -1);
        }
        int x = startX;
        int y = startY;

        while ((startX == endX && y != endY) || (startY == endY && x != endX)) {
            x += startX == endX ? 0 : increment;
            y += startY == endY ? 0 : increment;
            if (board[y][x] != null) {
                return false;
            }
        }
        setHasMoved(true);

        // Vérifie si la case de destination est vide ou occupée par une pièce de la couleur opposée
        return board[endY][endX] == null || !board[endY][endX].getColor().equals(getColor());
    }

    public ArrayList<int[]> PossiblesMoves(int startYRow, int startXCol, ChessPiece[][] board) {
        ArrayList<int[]> moves = new ArrayList<>();

        // Check moves to the right
        for (int i = startXCol + 1; i < 8; i++) {
            if (board[startYRow][i] == null) {
                moves.add(new int[]{startYRow, i});
            } else if (board[startYRow][i].isWhite() != this.isWhite()) {
                moves.add(new int[]{startYRow, i});
                break;
            } else {
                break;
            }
        }

        // Check moves to the left
        for (int i = startXCol - 1; i >= 0; i--) {
            if (board[startYRow][i] == null) {
                moves.add(new int[]{startYRow, i});
            } else if (board[startYRow][i].isWhite() != this.isWhite()) {
                moves.add(new int[]{startYRow, i});
                break;
            } else {
                break;
            }
        }

        // Check moves to the bottom
        for (int i = startYRow + 1; i < 8; i++) {
            if (board[i][startXCol] == null) {
                moves.add(new int[]{i, startXCol});
            } else if (board[i][startXCol].isWhite() != this.isWhite()) {
                moves.add(new int[]{i, startXCol});
                break;
            } else {
                break;
            }
        }

        // Check moves to the top
        for (int i = startYRow - 1; i >= 0; i--) {
            if (board[i][startXCol] == null) {
                moves.add(new int[]{i, startXCol});
            } else if (board[i][startXCol].isWhite() != this.isWhite()) {
                moves.add(new int[]{i, startXCol});
                break;
            } else {
                break;
            }
        }

        return moves;
    }


    public String getSymbol(){
        return (getColor() == Color.WHITE ? "B" : "N");
    }
}
