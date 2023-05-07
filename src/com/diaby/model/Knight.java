package com.diaby.model;

import java.util.ArrayList;

import java.awt.*;

/**
 * Classe représentant une pièce d'échecs de type Knight (Cavalier).
 * Elle étend la classe ChessPiece et redéfinit la méthode possiblesMoves() pour calculer les déplacements possibles
 * pour un Cavalier.
 */
public class Knight extends ChessPiece {

    /**
     * Constructeur pour créer une nouvelle pièce de type Knight.
     *
     * @param color                 la couleur de la pièce
     * @param row                   la ligne sur laquelle la pièce se trouve
     * @param col                   la colonne sur laquelle la pièce se trouve
     * @param whitePiecesAtBottom   true si c'est au tour des blancs de jouer, false sinon
     */
    public Knight(Color color, int row, int col, boolean whitePiecesAtBottom) {
        super("Knight", color, row, col, whitePiecesAtBottom);
    }

    /**
     * Constructeur pour créer un nouveau cavalier en copiant une autre pièce d'échecs.
     *
     * @param piece La pièce d'échecs à copier
     */
    public Knight(ChessPiece piece) {
        super(piece.getPieceName(), piece.getColor(), piece.getRow(), piece.getCol(), piece.getWhitePiecesAtBottom());
    }

    /**
     * Retourne la liste de tous les déplacements possibles pour le cavalier à partir de sa position actuelle
     * sur l'échiquier.
     *
     * @param startYRow La ligne du cavalier sur l'échiquier (de 0 à 7)
     * @param startXCol La colonne du cavalier sur l'échiquier (de 0 à 7)
     * @param board     L'échiquier actuel représenté par une matrice de ChessPiece
     * @return Une liste d'entiers représentant les positions (ligne, colonne) des cases où le cavalier peut se déplacer.
     */
    public ArrayList<int[]> possiblesMoves(int startYRow, int startXCol, ChessPiece[][] board) {
        ArrayList<int[]> moves = new ArrayList<>();
        // Two steps up
        if (startYRow - 2 >= 0) {
            // One step to the left

            if (startXCol - 1 >= 0 &&
                    (board[startYRow - 2][startXCol - 1] == null ||
                            board[startYRow - 2][startXCol - 1].isWhite() != this.isWhite())) {
                moves.add(new int[]{startYRow - 2, startXCol - 1});
            }
            // One step to the right
            if (startXCol + 1 < 8 &&
                    (board[startYRow - 2][startXCol + 1] == null ||
                            board[startYRow - 2][startXCol + 1].isWhite() != this.isWhite())) {
                moves.add(new int[]{startYRow - 2, startXCol + 1});
            }
        }

        // Two steps down
        if (startYRow + 2 < 8) {
            // One step to the left
            if (startXCol - 1 >= 0 &&
                    (board[startYRow + 2][startXCol - 1] == null ||
                            board[startYRow + 2][startXCol - 1].isWhite() != this.isWhite())) {
                moves.add(new int[]{startYRow + 2, startXCol - 1});
            }
            // One step to the right
            if (startXCol + 1 < 8 &&
                    (board[startYRow + 2][startXCol + 1] == null ||
                            board[startYRow + 2][startXCol + 1].isWhite() != this.isWhite())) {
                moves.add(new int[]{startYRow + 2, startXCol + 1});
            }
        }

        // Two steps to the left
        if (startXCol - 2 >= 0) {
            // One step up
            if (startYRow - 1 >= 0 &&
                    (board[startYRow - 1][startXCol - 2] == null ||
                            board[startYRow - 1][startXCol - 2].isWhite() != this.isWhite())) {
                moves.add(new int[]{startYRow - 1, startXCol - 2});
            }
            // One step down
            if (startYRow + 1 < 8 &&
                    (board[startYRow + 1][startXCol - 2] == null ||
                            board[startYRow + 1][startXCol - 2].isWhite() != this.isWhite())) {
                moves.add(new int[]{startYRow + 1, startXCol - 2});
            }
        }

        // Two steps to the right
        if (startXCol + 2 < 8) {
            // One step up
            if (startYRow - 1 >= 0 &&
                    (board[startYRow - 1][startXCol + 2] == null ||
                            board[startYRow - 1][startXCol + 2].isWhite() != this.isWhite())) {
                moves.add(new int[]{startYRow - 1, startXCol + 2});
            }
            // One step down
            if (startYRow + 1 < 8 &&
                    (board[startYRow + 1][startXCol + 2] == null ||
                            board[startYRow + 1][startXCol + 2].isWhite() != this.isWhite())) {
                moves.add(new int[]{startYRow + 1, startXCol + 2});
            }
        }

        return moves;
    }

}