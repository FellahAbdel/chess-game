package com.diaby;

//import javax.swing.*;
import java.awt.*;

public class Knight extends ChessPiece {
    public Knight(Color color, int row, int col) {
        super("Knight", color, row, col);
    }

    @Override
    public boolean isValidMove(int startX, int startY, int endX, int endY, ChessPiece[][] board) {
        // Vérifie si le déplacement forme un L
        int deltaX = Math.abs(endX - startX);
        int deltaY = Math.abs(endY - startY);
        if ((deltaX == 1 && deltaY == 2) || (deltaX == 2 && deltaY == 1)) {
            // Vérifie si la case de destination est vide ou occupée par une pièce de la couleur opposée
            if (board[endY][endX] == null || !board[endY][endX].getColor().equals(getColor())) {
                return true;
            }
        }

        return false;
    }

    public String getSymbol(){
        return (getColor() == Color.WHITE ? "B" : "N");
    }
}