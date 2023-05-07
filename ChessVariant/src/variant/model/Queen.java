package variant.model;

import java.awt.*;
import java.util.ArrayList;

/**
 * Classe représentant une pièce d'échecs de type Queen (Reine).
 * Elle étend la classe ChessPiece et redéfinit la méthode possiblesMoves() pour calculer les déplacements possibles
 * pour une Reine.
 */
public class Queen extends ChessPiece {

    /**
     * Constructeur pour créer une nouvelle pièce de type Queen.
     *
     * @param color                 la couleur de la pièce
     * @param row                   la ligne sur laquelle la pièce se trouve
     * @param col                   la colonne sur laquelle la pièce se trouve
     * @param whitePiecesAtBottom   true si c'est au tour des blancs de jouer, false sinon
     */
    public Queen(Color color, int row, int col,boolean whitePiecesAtBottom ) {
        super("Queen", color, row, col, whitePiecesAtBottom );
    }

    /**
     * Constructeur pour créer une nouvelle reine en copiant une autre pièce d'échecs.
     *
     * @param piece La pièce d'échecs à copier
     */
    public Queen(ChessPiece piece) {
        super(piece.getPieceName(), piece.getColor(), piece.getRow(), piece.getCol(), piece.getWhitePiecesAtBottom());

    }

    /**
     * Retourne la liste de tous les déplacements possibles pour la reine à partir de sa position actuelle
     * sur l'échiquier.
     *
     * @param startYRow La ligne de la reine sur l'échiquier (de 0 à 7)
     * @param startXCol La colonne de la reine sur l'échiquier (de 0 à 7)
     * @param board     L'échiquier actuel représenté par une matrice de ChessPiece
     * @return Une liste d'entiers représentant les positions (ligne, colonne) des cases où la tour peut se déplacer.
     */
    public ArrayList<int[]> possiblesMoves(int startYRow, int startXCol, ChessPiece[][] board) {
        ArrayList<int[]> moves = new ArrayList<>();

        // generate moves like bishop
        boolean canMove = true;
        // Upper Left Diagonal
        for(int i=startYRow-1, j=startXCol-1; i>=0 && j>=0 && canMove; i--, j--){
            if(board[i][j] == null) {
                moves.add(new int[]{i, j});
            }
            else if(board[i][j].isWhite() != this.isWhite()){
                moves.add(new int[]{i, j});
                canMove = false;
            }
            else {
                canMove = false;
            }
        }

        // Upper Right Diagonal
        canMove = true;
        for(int i=startYRow-1, j=startXCol+1; i>=0 && j<12 && canMove; i--, j++){
            if(board[i][j] == null) {
                moves.add(new int[]{i, j});
            }
            else if(board[i][j].isWhite() != this.isWhite()){
                moves.add(new int[]{i, j});
                canMove = false;
            }
            else {
                canMove = false;
            }
        }

        // Lower Left Diagonal
        canMove = true;
        for(int i=startYRow+1, j=startXCol-1; i<8 && j>=0 && canMove; i++, j--){
            if(board[i][j] == null) {
                moves.add(new int[]{i, j});
            }
            else if(board[i][j].isWhite() != this.isWhite()){
                moves.add(new int[]{i, j});
                canMove = false;
            }
            else {
                canMove = false;
            }
        }

        // Lower Right Diagonal
        canMove = true;
        for(int i=startYRow+1, j=startXCol+1; i<8 && j<12 && canMove; i++, j++){
            if(board[i][j] == null) {
                moves.add(new int[]{i, j});
            }
            else if(board[i][j].isWhite() != this.isWhite()){
                moves.add(new int[]{i, j});
                canMove = false;
            }
            else {
                canMove = false;
            }
        }

        // generate moves like rook
        // Check moves to the right
        for (int i = startXCol + 1; i < 12 && canMove; i++) {
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

        return moves;
    }

}
