package com.diaby;

import javax.swing.*;
import java.awt.*;

public class Queen extends ChessPiece {
    public Queen(Color color, int row, int col) {
        super("Queen", color, row, col);
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
    public String getSymbol(){
        return (getColor() == Color.WHITE ? "B" : "N");
    }
}
