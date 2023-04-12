package com.diaby;

//import javax.swing.*;
import java.awt.*;

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

    @Override
    public String getSymbol(){
        return (getColor() == Color.WHITE ? "B" : "N");
    }
}
