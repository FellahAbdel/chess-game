package com.diaby.model;

import java.awt.*;

public class Pawn extends ChessPiece {
    private boolean hasMoved; // indique si le pion a déjà été déplacé ou non
    private boolean justMovedDouble; // flag pour indiquer si le pion vient de faire un double pas
    public Pawn(String imageName,Color color, int row, int col) {
        super("Pawn",imageName ,color, row, col);
        hasMoved = false;
        justMovedDouble = false;
    }

    public boolean getHasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public boolean getJustMovedDouble() {
        return justMovedDouble;
    }

    public void setJustMovedDouble(boolean justMovedDouble) {
        this.justMovedDouble = justMovedDouble;
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
        // faut régler le problème avec equals
        int forwardDirection = getColor().equals("white") ? -1 : 1;

        if (deltaY != forwardDirection) {
            // Vérifie si le pion n'a pas encore été déplacé et s'il se déplace de deux cases vers l'avant
            if (!hasMoved && deltaY == 2 * forwardDirection && board[startY + forwardDirection][startX] == null) {
                setJustMovedDouble(true);
                setHasMoved(true);
                return true;
            }
            return false;
        }

        // Vérifie si la case de destination est vide
//        if (board[endY][endX] == null) {
//            return true;
//        }

        // Vérifie si la case de destination est occupée par une pièce de la couleur opposée
//        if (!board[endY][endX].getColor().equals(getColor())) {
//            return true;
//        }
        return true;
    }
    public String getSymbol(){
        return (getColor() == Color.WHITE ? "B" : "N");
    }
}