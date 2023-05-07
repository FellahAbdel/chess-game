package com.diaby.model;

import java.awt.*;
import java.util.ArrayList;

/**
 * Classe représentant une pièce d'échecs de type Rook (Tour).
 * Elle étend la classe ChessPiece et redéfinit la méthode possiblesMoves() pour calculer les déplacements possibles
 * pour une Tour.
 */
public class Rook extends ChessPiece {
    private boolean hasMoved; // indique si le pion a déjà été déplacé ou non

    /**
     * Constructeur pour créer une nouvelle pièce de type Rook.
     *
     * @param color       la couleur de la pièce
     * @param row         la ligne sur laquelle la pièce se trouve
     * @param col         la colonne sur laquelle la pièce se trouve
     * @param isWhiteTurn true si c'est au tour des blancs de jouer, false sinon
     */
    public Rook(Color color, int row, int col, boolean isWhiteTurn) {
        super("Rook", color, row, col, isWhiteTurn);
        hasMoved = false;
    }

    /**
     * Constructeur pour créer une nouvelle tour en copiant une autre pièce d'échecs.
     *
     * @param piece La pièce d'échecs à copier
     */
    public Rook(ChessPiece piece) {
        super(piece.getPieceName(), piece.getColor(), piece.getRow(), piece.getCol(), piece.getWhitePiecesAtBottom());
    }

    /**
     * Retourne un booléen indiquant si la tour a déjà bougé depuis le début de la partie.
     *
     * @return True si la tour a déjà bougé, False sinon.
     */
    public boolean getHasMoved() {
        return hasMoved;
    }

    /**
     * Modifie le booléen qui indique si la tour a déjà bougé depuis le début de la partie.
     *
     * @param hasMoved True si la tour a déjà bougé, False sinon.
     */
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    /**
     * Retourne la liste de tous les déplacements possibles pour la tour à partir de sa position actuelle
     * sur l'échiquier.
     *
     * @param startYRow La rangée de la tour sur l'échiquier (de 0 à 7)
     * @param startXCol La colonne de la tour sur l'échiquier (de 0 à 7)
     * @param board     L'échiquier actuel représenté par une matrice de ChessPiece
     * @return Une liste d'entiers représentant les positions (rangée, colonne) des cases où la tour peut se déplacer.
     */
    public ArrayList<int[]> possiblesMoves(int startYRow, int startXCol, ChessPiece[][] board) {
        ArrayList<int[]> moves = new ArrayList<>();
        boolean canMove = true;
        // Check moves to the right
        for (int i = startXCol + 1; i < 8 && canMove; i++) {
            if (board[startYRow][i] == null) {
                moves.add(new int[]{startYRow, i});
            } else if (board[startYRow][i].isWhite() != this.isWhite()) {
                moves.add(new int[]{startYRow, i});
                canMove = false;
            } else {
                canMove = false;
            }
        }

        canMove = true;
        // Check moves to the left
        for (int i = startXCol - 1; i >= 0 && canMove; i--) {
            if (board[startYRow][i] == null) {
                moves.add(new int[]{startYRow, i});
            } else if (board[startYRow][i].isWhite() != this.isWhite()) {
                moves.add(new int[]{startYRow, i});
                canMove = false;
            } else {
                canMove = false;
            }
        }

        canMove = true;
        // Check moves to the bottom
        for (int i = startYRow + 1; i < 8 && canMove; i++) {
            if (board[i][startXCol] == null) {
                moves.add(new int[]{i, startXCol});
            } else if (board[i][startXCol].isWhite() != this.isWhite()) {
                moves.add(new int[]{i, startXCol});
                canMove = false;
            } else {
                canMove = false;
            }
        }

        canMove = true;
        // Check moves to the top
        for (int i = startYRow - 1; i >= 0 && canMove; i--) {
            if (board[i][startXCol] == null) {
                moves.add(new int[]{i, startXCol});
            } else if (board[i][startXCol].isWhite() != this.isWhite()) {
                moves.add(new int[]{i, startXCol});
                canMove = false;
            } else {
                canMove = false;
            }
        }

        return moves;
    }
}
