package com.diaby.model;

import java.awt.*;
import java.util.ArrayList;

public class Queen extends ChessPiece {
    public Queen(String imageName,Color color, int row, int col) {
        super("Queen", imageName,color, row, col);
    }

    @Override
    public boolean isValidMove(int startX, int startY, int endX, int endY, ChessPiece[][] board) {
        // Vérifie si le déplacement se fait horizontalement, verticalement ou en diagonale
        int deltaX = Math.abs(endX - startX);
        int deltaY = Math.abs(endY - startY);
        if (deltaX == deltaY)
        {  // diagonale
            // Vérifie si la trajectoire ne rencontre pas d'obstacle
            int incrementX = endX > startX ? 1 : -1;
            int incrementY = endY > startY ? 1 : -1;
            int x = startX + incrementX;
            int y = startY + incrementY;
            while (x != endX && y != endY)
            {
                if (board[y][x] != null)
                {
                    return false;
                }
                x += incrementX;
                y += incrementY;
            }
        }
        else if (startX == endX || startY == endY)
        {  // horizontal ou vertical
            // Vérifie si la trajectoire ne rencontre pas d'obstacle
            int increment = startX == endX ? (endY - startY > 0 ? 1 : -1) : (endX - startX > 0 ? 1 : -1);
            int x = startX;
            int y = startY;
            while ((startX == endX && y != endY) || (startY == endY && x != endX))
            {
                x += startX == endX ? 0 : increment;
                y += startY == endY ? 0 : increment;
                if (board[y][x] != null)
                {
                    return false;
                }
            }
        }

//        // Vérifie si la case de destination est vide ou occupée par une pièce de la couleur opposée
        if (board[endY][endX] == null || !board[endY][endX].getColor().equals(getColor())) {
            return true;
        }
        return false;
    }

    public ArrayList<int[]> PossiblesMoves(int startYRow, int startXCol, ChessPiece[][] board) {
        ArrayList<int[]> moves = new ArrayList<>();

        // generate moves like bishop
        // Upper Left Diagonal
        for (int i = startYRow - 1, j = startXCol - 1; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == null) {
                moves.add(new int[]{i, j});
            } else if (board[i][j].isWhite() != this.isWhite()) {
                moves.add(new int[]{i, j});
                break;
            } else {
                break;
            }
        }

        // Upper Right Diagonal
        for (int i = startYRow - 1, j = startXCol + 1; i >= 0 && j < 8; i--, j++) {
            if (board[i][j] == null) {
                moves.add(new int[]{i, j});
            } else if (board[i][j].isWhite() != this.isWhite()) {
                moves.add(new int[]{i, j});
                break;
            } else {
                break;
            }
        }

        // Lower Left Diagonal
        for (int i = startYRow + 1, j = startXCol - 1; i < 8 && j >= 0; i++, j--) {
            if (board[i][j] == null) {
                moves.add(new int[]{i, j});
            } else if (board[i][j].isWhite() != this.isWhite()) {
                moves.add(new int[]{i, j});
                break;
            } else {
                break;
            }
        }

        // Lower Right Diagonal
        for (int i = startYRow + 1, j = startXCol + 1; i < 8 && j < 8; i++, j++) {
            if (board[i][j] == null) {
                moves.add(new int[]{i, j});
            } else if (board[i][j].isWhite() != this.isWhite()) {
                moves.add(new int[]{i, j});
                break;
            } else {
                break;
            }
        }

        // generate moves like rook

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
