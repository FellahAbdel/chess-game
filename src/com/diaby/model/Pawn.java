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
    public boolean isValidMove(int startYRow, int startXCol, int endYRow, int endXCol, ChessPiece[][] board) {
        // Vérifier que le mouvement se fait dans la même colonne
        if (startXCol != endXCol) {
            return false;
        }

        // Vérifier que le mouvement ne dépasse pas deux cases
        int rowDiff = Math.abs(endYRow - startYRow);
        if (rowDiff > 2) {
            return false;
        }

        // Vérifier que le mouvement ne se fait pas en arrière pour :
        if(this.getColor().equals(new Color(255, 255, 255))){ // Pion blanc
            if (startYRow < endYRow) {
                return false;
            }
        }else { // Pion noir
            if(startYRow < endYRow){
                return false ;
            }
        }


        // Vérifier que le mouvement ne dépasse pas une case si le pion a déjà bougé
        if (hasMoved && rowDiff > 1) {
            return false;
        }

        // Vérifier que le pion ne capture pas une pièce de la même couleur
        if (board[endYRow][endXCol] != null && board[endYRow][endXCol].getColor() == this.getColor()) {
            return false;
        }

        // Vérifier que le mouvement est valide pour un premier coup
        if (!hasMoved && rowDiff == 2) {
            // Vérifier qu'il n'y a pas de pièce sur la trajectoire
            int direction = (endYRow - startYRow) / rowDiff;
            int middleRow = startYRow + direction;
            if (board[middleRow][startXCol] != null) {
                return false;
            }
        }

        // Le mouvement est valide
        return true;
    }

    public String getSymbol(){
        return (getColor() == Color.WHITE ? "B" : "N");
    }
}