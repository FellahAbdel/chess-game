package variant.model;

import java.awt.*;
import java.util.ArrayList;

/**
 * Classe représentant une pièce d'échecs de type Impératrice.
 * Elle étend la classe ChessPiece et redéfinit la méthode possiblesMoves() pour calculer les déplacements possibles
 * pour une Impératrice.
 */
public class Imperatrice extends ChessPiece {

    /**
     * Constructeur pour créer une nouvelle pièce de type Impératrice.
     *
     * @param color                 la couleur de la pièce
     * @param row                   la ligne sur laquelle la pièce se trouve
     * @param col                   la colonne sur laquelle la pièce se trouve
     * @param whitePiecesAtBottom   true si c'est au tour des blancs de jouer, false sinon
     */
    public Imperatrice(Color color, int row, int col,boolean whitePiecesAtBottom) {
        super("Imperatrice", color, row, col, whitePiecesAtBottom);
    }

    /**
     * Constructeur pour créer une nouvelle impératrice en copiant une autre pièce d'échecs.
     *
     * @param piece La pièce d'échecs à copier
     */
    public Imperatrice(ChessPiece piece) {
        super(piece.getPieceName(), piece.getColor(), piece.getRow(), piece.getCol(), piece.getWhitePiecesAtBottom());

    }

    /**
     * Retourne la liste de tous les déplacements possibles pour l'impératrice à partir de sa position actuelle
     * sur l'échiquier.
     *
     * @param startYRow La ligne de l'impératrice sur l'échiquier (de 0 à 7)
     * @param startXCol La colonne de l'impératrice sur l'échiquier (de 0 à 9)
     * @param board     L'échiquier actuel représenté par une matrice de ChessPiece
     * @return Une liste d'entiers représentant les positions (ligne, colonne) des cases où l'impératrice peut se déplacer.
     */
    @Override
    public ArrayList<int[]> possiblesMoves(int startYRow, int startXCol, ChessPiece[][] board) {
        ArrayList<int[]> moves = new ArrayList<>();

        // generate moves like rook
        boolean canMove = true;
        // Check moves to the right
        for (int i = startXCol + 1; i < 10 && canMove; i++) {
            if (board[startYRow][i] == null) {
                moves.add(new int[]{startYRow, i});
            } else if (board[startYRow][i].isWhite() != this.isWhite()) {
                moves.add(new int[]{startYRow, i});
                canMove = false;
            } else {
                canMove = false;
            }
        }

        // Check moves to the left
        canMove = true;
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

        // Check moves to the bottom
        canMove = true;
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

        // Check moves to the top
        canMove = true;
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


        // generate moves like Knight

        // Two steps up
        if(startYRow-2 >= 0){
            // One step to the left

            if(startXCol-1 >= 0 && (board[startYRow-2][startXCol-1] == null || board[startYRow-2][startXCol-1].isWhite() != this.isWhite())){
                moves.add(new int[]{startYRow-2, startXCol-1});
            }
            // One step to the right
            if(startXCol+1 < 10 && (board[startYRow-2][startXCol+1] == null || board[startYRow-2][startXCol+1].isWhite() != this.isWhite())){
                moves.add(new int[]{startYRow-2, startXCol+1});
            }
        }

        // Two steps down
        if(startYRow+2 < 8){
            // One step to the left
            if(startXCol-1 >= 0 && (board[startYRow+2][startXCol-1] == null || board[startYRow+2][startXCol-1].isWhite() != this.isWhite())){
                moves.add(new int[]{startYRow+2, startXCol-1});
            }
            // One step to the right
            if(startXCol+1 < 10 && (board[startYRow+2][startXCol+1] == null || board[startYRow+2][startXCol+1].isWhite() != this.isWhite())){
                moves.add(new int[]{startYRow+2, startXCol+1});
            }
        }

        // Two steps to the left
        if(startXCol-2 >= 0){
            // One step up
            if(startYRow-1 >= 0 && (board[startYRow-1][startXCol-2] == null || board[startYRow-1][startXCol-2].isWhite() != this.isWhite())){
                moves.add(new int[]{startYRow-1, startXCol-2});
            }
            // One step down
            if(startYRow+1 < 8 && (board[startYRow+1][startXCol-2] == null || board[startYRow+1][startXCol-2].isWhite() != this.isWhite())){
                moves.add(new int[]{startYRow+1, startXCol-2});
            }
        }

        // Two steps to the right
        if(startXCol+2 < 10){
            // One step up
            if(startYRow-1 >= 0 && (board[startYRow-1][startXCol+2] == null || board[startYRow-1][startXCol+2].isWhite() != this.isWhite())){
                moves.add(new int[]{startYRow-1, startXCol+2});
            }
            // One step down
            if(startYRow+1 < 8 && (board[startYRow+1][startXCol+2] == null || board[startYRow+1][startXCol+2].isWhite() != this.isWhite())){
                moves.add(new int[]{startYRow+1, startXCol+2});
            }
        }

        return moves;
    }
}

