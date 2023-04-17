package com.diaby.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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

    /*@Override*/
/*    public boolean isValidMove(int startYRow, int startXCol, int endYRow, int endXCol, ChessPiece[][] board) {
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
            if (startYRow > endYRow) {
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
    }*/
    @Override
    public boolean isValidMove(int startYRow, int startXCol, int endYRow, int endXCol, ChessPiece[][] board) {
        // Vérifier si le mouvement est vertical
        if (startXCol != endXCol) {
            return false;
        }

        // Vérifier que le mouvement ne se fait pas en arrière
        if (this.getColor().equals(new Color(255, 255, 255))) { // Pion blanc
            if (endYRow < startYRow) {
                return false;
            }
        } else { // Pion noir
            if (endYRow > startYRow) {
                return false;
            }
        }

        // Vérifier si le pion avance d'une seule case
        if (Math.abs(endYRow - startYRow) == 1) {
            // Vérifier si la case d'arrivée est vide
            if (board[endYRow][endXCol] != null) {
                return false;
            }
        } else if (Math.abs(endYRow - startYRow) == 2) { // Vérifier si le pion avance de deux cases
            // Vérifier si le pion a déjà bougé
            if (hasMoved) {
                return false;
            }

            // Vérifier si les cases intermédiaires sont vides
            int middleYRow = (startYRow + endYRow) / 2;
            if (board[middleYRow][endXCol] != null || board[endYRow][endXCol] != null) {
                return false;
            }
        } else { // Mouvement invalide
            return false;
        }

        // Le mouvement est valide
        return true;
    }
    @Override
    public List<int[]> getLegalMoves(ChessPiece[][] board, boolean isWhite) {
        List<int[]> legalMoves = new ArrayList<>();


        // Iterate over all the possible moves for this chess piece
        for (int[] move : getAllMoves()) {
            int row = this.row + move[0];
            int col = this.col + move[1];

            // Check if the move is within the bounds of the board
            if (row >= 0 && row < 8 && col >= 0 && col < 8) {
                ChessPiece destPiece = board[row][col];

                // Check if the destination tile is empty or has an opponent's piece
                if (destPiece == null || destPiece.isWhite() != isWhite) {
                    // Check if the move is valid for this piece
                    if (isValidMove(this.row, this.col, row, col, board)) {
                        // Add the move to the list of legal moves
                        legalMoves.add(new int[] {row, col});
                    }
                }
            }
        }

        return legalMoves;
    }

    @Override
    public List<int[]> getAllMoves() {
        List<int[]> allMoves = new ArrayList<>();
        boolean isWhite = this.isWhite();
        // Determine direction of movement based on piece color
        int direction = isWhite ? 1 : -1;

        // Generate all possible moves for a pawn
        allMoves.add(new int[] {direction, 0}); // Move forward
        allMoves.add(new int[] {direction, 1}); // Diagonal capture to the right
        allMoves.add(new int[] {direction, -1}); // Diagonal capture to the left

        // Add double move if pawn hasn't moved yet
        if ((isWhite && row == 1) || (!isWhite && row == 6)) {
            allMoves.add(new int[] {2 * direction, 0});
        }

        // Shift all moves by (1,0) to account for white tiles starting at (0,0)
/*        for (int i = 0; i < allMoves.size(); i++) {
            allMoves.get(i)[0] += 1;
        }*/

        return allMoves;
    }


    public String getSymbol(){
        return (getColor() == Color.WHITE ? "B" : "N");
    }
}