package com.diaby;

import javax.swing.*;
import java.awt.*;

public class Pawn extends ChessPiece {
    private boolean hasMoved; // indique si le pion a déjà été déplacé ou non

    public Pawn(Color color, int row, int col) {
        super("Pawn", color, row, col);
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
        // Vérifie si le déplacement est vertical
        int deltaX = endX - startX;
        int deltaY = endY - startY;
        if (deltaX != 0) {
            return false;
        }

        // Vérifie si le déplacement est d'une case vers l'avant
        int forwardDirection = getColor().equals("white") ? -1 : 1;
        if (deltaY != forwardDirection) {
            // Vérifie si le pion n'a pas encore été déplacé et s'il se déplace de deux cases vers l'avant
            if (!hasMoved && deltaY == 2 * forwardDirection && board[startY + forwardDirection][startX] == null) {
                return true;
            }
            return false;
        }

        // Vérifie si la case de destination est vide
        if (board[endY][endX] == null) {
            return true;
        }

        // Vérifie si la case de destination est occupée par une pièce de la couleur opposée
        if (!board[endY][endX].getColor().equals(getColor())) {
            return true;
        }

        return false;
    }
    public String getSymbol(){
        return (getColor() == Color.WHITE ? "B" : "N");
    }
}