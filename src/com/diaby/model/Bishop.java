package com.diaby.model;

import java.awt.*;
import java.util.ArrayList;

/**
 * Classe représentant une pièce d'échecs de type Bishop (bishop).
 * Elle étend la classe ChessPiece et redéfinit la méthode possiblesMoves() pour calculer les déplacements possibles
 * pour un Fou.
 */
public class Bishop extends ChessPiece {

    /**
     * Constructeur pour créer une nouvelle pièce de type Bishop.
     *
     * @param color                 la couleur de la pièce
     * @param row                   la ligne sur laquelle la pièce se trouve
     * @param col                   la colonne sur laquelle la pièce se trouve
     * @param whitePiecesAtBottom   true si c'est au tour des blancs de jouer, false sinon
     */
    public Bishop(Color color, int row, int col, boolean whitePiecesAtBottom) {
        super("Bishop", color, row, col, whitePiecesAtBottom);
    }

    /**
     * Constructeur pour créer un nouveau fou en copiant une autre pièce d'échecs.
     *
     * @param piece La pièce d'échecs à copier
     */
    public Bishop(ChessPiece piece) {
        super(piece.getPieceName(), piece.getColor(), piece.getRow(), piece.getCol(), piece.getWhitePiecesAtBottom());
    }

    /**
     * Renvoie une liste de tous les mouvements possibles qu'un fou peut faire sur un échiquier donné à partir de sa
     * position actuelle.
     *
     * @param startYRow La coordonnée y de départ (ligne) du fou.
     * @param startXCol La coordonnée x de départ (colonne) du fou.
     * @param board     L'échiquier sur lequel le fou est placé.
     * @return Une liste d'entiers représentant les positions (ligne, colonne) des cases où le fou peut se déplacer.
     */
    @Override
    public ArrayList<int[]> possiblesMoves(int startYRow, int startXCol, ChessPiece[][] board) {
        ArrayList<int[]> moves = new ArrayList<>();
        boolean canMove = true;
        // Upper Left Diagonal
        for (int i = startYRow - 1, j = startXCol - 1; i >= 0 && j >= 0 && canMove; i--, j--) {
            if (board[i][j] == null) {
                moves.add(new int[]{i, j});
            } else if (board[i][j].isWhite() != this.isWhite()) {
                moves.add(new int[]{i, j});
                canMove = false;
            } else {
                canMove = false;
            }
        }

        canMove = true;
        // Upper Right Diagonal
        for (int i = startYRow - 1, j = startXCol + 1; i >= 0 && j < 8 && canMove; i--, j++) {
            if (board[i][j] == null) {
                moves.add(new int[]{i, j});
            } else if (board[i][j].isWhite() != this.isWhite()) {
                moves.add(new int[]{i, j});
                canMove = false;
            } else {
                canMove = false;
            }
        }

        canMove = true;
        // Lower Left Diagonal
        for (int i = startYRow + 1, j = startXCol - 1; i < 8 && j >= 0 && canMove; i++, j--) {
            if (board[i][j] == null) {
                moves.add(new int[]{i, j});
            } else if (board[i][j].isWhite() != this.isWhite()) {
                moves.add(new int[]{i, j});
                canMove = false;
            } else {
                canMove = false;
            }
        }

        canMove = true;
        // Lower Right Diagonal
        for (int i = startYRow + 1, j = startXCol + 1; i < 8 && j < 8 && canMove; i++, j++) {
            if (board[i][j] == null) {
                moves.add(new int[]{i, j});
            } else if (board[i][j].isWhite() != this.isWhite()) {
                moves.add(new int[]{i, j});
                canMove = false;
            } else {
                canMove = false;
            }
        }

        return moves;
    }

}
