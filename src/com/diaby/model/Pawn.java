package com.diaby.model;

import java.awt.*;
import java.util.ArrayList;

/**
 * Classe représentant une pièce d'échecs de type Pawn (Pion).
 * Elle étend la classe ChessPiece et redéfinit la méthode possiblesMoves() pour calculer les déplacements possibles
 * pour un Pion.
 */
public class Pawn extends ChessPiece {

    private boolean hasJustMoveDouble;

    /**
     * Constructeur pour créer une nouvelle pièce de type Rook.
     *
     * @param color                 la couleur de la pièce
     * @param row                   la ligne sur laquelle la pièce se trouve
     * @param col                   la colonne sur laquelle la pièce se trouve
     * @param whitePiecesAtBottom   true si c'est au tour des blancs de jouer, false sinon
     */
    public Pawn(Color color, int row, int col, boolean whitePiecesAtBottom) {
        super("Pawn", color, row, col, whitePiecesAtBottom);
        hasJustMoveDouble = false;
    }

    /**
     * Constructeur pour créer un nouveau pion en copiant une autre pièce d'échecs.
     *
     * @param piece La pièce d'échecs à copier
     */
    public Pawn(ChessPiece piece) {
        super(piece.getPieceName(), piece.getColor(), piece.getRow(), piece.getCol(), piece.getWhitePiecesAtBottom());
    }

    /**
     * Retourne un booléen indiquant si le pion a déjà bougé depuis le début de la partie de deux cases.
     *
     * @return True si le pion a déjà bougé de deux cases, False sinon.
     */
    public boolean getHasJustMoveDouble() {
        return hasJustMoveDouble;
    }

    /**
     * Modifie le booléen qui indique si la tour a déjà bougé depuis le début de la partie de deux cases.
     *
     * @param hasJustMoveDouble True si le pion a bougé de deux cases, False sinon.
     */
    public void setHasJustMoveDouble(boolean hasJustMoveDouble) {
        this.hasJustMoveDouble = hasJustMoveDouble;
    }


    public void promotePawn(Pawn pawn, int row, int col, String pieceType, ChessPiece[][] board) {
        Color color = pawn.getColor();
        ChessPiece newPiece;
        switch (pieceType) {
            case "Queen":
                newPiece = new Queen(color, row, col, pawn.getWhitePiecesAtBottom());
                break;
            case "Rook":
                newPiece = new Rook(color, row, col, pawn.getWhitePiecesAtBottom());
                break;
            case "Bishop":
                newPiece = new Bishop(color, row, col, pawn.getWhitePiecesAtBottom());
                break;
            case "Knight":
                newPiece = new Knight(color, row, col, pawn.getWhitePiecesAtBottom());
                break;
            default:
                throw new IllegalArgumentException("Invalid piece type");
        }
        board[row][col] = newPiece;
    }


    private void descent(int startXCol, int startYRow, ChessPiece[][] board, ArrayList<int[]> moves, Boolean
            isWhite) {


        if (startXCol < 7 && board[startXCol + 1][startYRow] == null) {
            moves.add(new int[]{startXCol + 1, startYRow});
        }
        // Check two steps forward
        if (startXCol == 1 &&
                board[startXCol + 1][startYRow] == null &&
                board[startXCol + 2][startYRow] == null) {
            moves.add(new int[]{startXCol + 2, startYRow});
        }
        // Check diagonal captures
        if (startXCol < 8 && startYRow > 0 &&
                board[startXCol + 1][startYRow - 1] != null &&
                board[startXCol + 1][startYRow - 1].isWhite() == isWhite) {
            moves.add(new int[]{startXCol + 1, startYRow - 1});
        }
        if (startXCol < 8 && startYRow < 7 &&
                board[startXCol + 1][startYRow + 1] != null &&
                board[startXCol + 1][startYRow + 1].isWhite() == isWhite) {
            moves.add(new int[]{startXCol + 1, startYRow + 1});
        }
        // Check en passant capture
        if (startXCol == 4 &&
                startYRow > 0 &&
                board[startXCol][startYRow - 1] != null &&
                board[startXCol][startYRow - 1].isWhite() == isWhite &&
                ((Pawn)board[startXCol][startYRow + 1]).getHasJustMoveDouble() &&
                board[startXCol + 1][startYRow - 1] == null &&
                board[startXCol][startYRow - 1] instanceof Pawn) {
            moves.add(new int[]{startXCol + 1, startYRow - 1});
        }
        if (startXCol == 4 &&
                startYRow < 7 &&
                board[startXCol][startYRow + 1] != null &&
                board[startXCol][startYRow + 1].isWhite() == isWhite &&
                ((Pawn)board[startXCol][startYRow + 1]).getHasJustMoveDouble() &&
                board[startXCol + 1][startYRow + 1] == null &&
                board[startXCol][startYRow + 1] instanceof Pawn) {
            moves.add(new int[]{startXCol + 1, startYRow + 1});
        }
    }

    private void climb(int startXCol, int startYRow, ChessPiece[][] board, ArrayList<int[]> moves, Boolean isWhite) {
        if (startXCol > 0 && board[startXCol - 1][startYRow] == null) {
            moves.add(new int[]{startXCol - 1, startYRow});
        }
        // Check two steps forward
        if (startXCol == 6 && board[startXCol - 1][startYRow] == null &&
                board[startXCol - 2][startYRow] == null) {
            moves.add(new int[]{startXCol - 2, startYRow});
        }
        // Check diagonal captures
        if (startXCol > 0 && startYRow > 0 &&
                board[startXCol - 1][startYRow - 1] != null &&
                board[startXCol - 1][startYRow - 1].isWhite() == isWhite) {
            moves.add(new int[]{startXCol - 1, startYRow - 1});
        }
        if (startXCol > 0 && startYRow < 7 &&
                board[startXCol - 1][startYRow + 1] != null &&
                board[startXCol - 1][startYRow + 1].isWhite() == isWhite) {
            moves.add(new int[]{startXCol - 1, startYRow + 1});
        }
        // Check en passant capture
        if (startXCol == 3 && startYRow > 0 &&
                board[startXCol][startYRow - 1] != null &&
                board[startXCol][startYRow - 1].isWhite() == isWhite &&
                ((Pawn)board[startXCol][startYRow - 1]).getHasJustMoveDouble() &&
                board[startXCol - 1][startYRow - 1] == null &&
                board[startXCol][startYRow - 1] instanceof Pawn) {
            moves.add(new int[]{startXCol - 1, startYRow - 1});
        }
        if (startXCol == 3 && startYRow < 7 &&
                board[startXCol][startYRow + 1] != null &&
                board[startXCol][startYRow + 1].isWhite() == isWhite &&
                ((Pawn)board[startXCol][startYRow + 1]).getHasJustMoveDouble() &&
                board[startXCol - 1][startYRow + 1] == null &&
                board[startXCol][startYRow + 1] instanceof Pawn) {
            moves.add(new int[]{startXCol - 1, startYRow + 1});
        }
    }

    private ArrayList<int[]> possiblesMovesTurn(int startXCol, int startYRow, ChessPiece[][] board,
                                               ArrayList<int[]> moves, Boolean whiteTurn) {
        if (whiteTurn) {
            if (this.isWhite()) {  // Descente blanche.
                descent(startXCol, startYRow, board, moves, false);
            } else { // Monté Noir.
                climb(startXCol, startYRow, board, moves, true);
            }
        } else {
            if (!this.isWhite()) {  // Descente Noire
                descent(startXCol, startYRow, board, moves, true);
            } else {  // Montee blanche.
                climb(startXCol, startYRow, board, moves, false);
            }
        }

        return moves;
    }

    /**
     * Retourne la liste de tous les déplacements possibles pour le pion à partir de sa position actuelle
     * sur l'échiquier.
     *
     * @param startYRow La ligne du pion sur l'échiquier (de 0 à 7)
     * @param startXCol La colonne du pion sur l'échiquier (de 0 à 7)
     * @param board     L'échiquier actuel représenté par une matrice de ChessPiece
     * @return Une liste d'entiers représentant les positions (ligne, colonne) des cases où la tour peut se déplacer.
     */
    @Override
    public ArrayList<int[]> possiblesMoves(int startXCol, int startYRow, ChessPiece[][] board) {
        ArrayList<int[]> moves = new ArrayList<>();

       return possiblesMovesTurn(startXCol, startYRow, board, moves, !this.getWhitePiecesAtBottom());

    }


}