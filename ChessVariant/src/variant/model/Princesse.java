package variant.model;

import java.awt.*;
import java.util.ArrayList;

/**
 * Classe représentant une pièce d'échecs de type Princesse.
 * Elle étend la classe ChessPiece et redéfinit la méthode possiblesMoves() pour calculer les déplacements possibles
 * pour une Princesse.
 */
public class Princesse extends ChessPiece{

    /**
     * Constructeur pour créer une nouvelle pièce de type Princesse.
     *
     * @param color                 la couleur de la pièce
     * @param row                   la ligne sur laquelle la pièce se trouve
     * @param col                   la colonne sur laquelle la pièce se trouve
     * @param whitePiecesAtBottom   true si c'est au tour des blancs de jouer, false sinon
     */
    public Princesse(Color color, int row, int col,boolean whitePiecesAtBottom) {
        super("Princesse", color, row, col, whitePiecesAtBottom);
    }

    /**
     * Constructeur pour créer une nouvelle Princesse en copiant une autre pièce d'échecs.
     *
     * @param piece La pièce d'échecs à copier
     */
    public Princesse(ChessPiece piece) {
        super(piece.getPieceName(), piece.getColor(), piece.getRow(), piece.getCol(), piece.getWhitePiecesAtBottom());

    }

    /**
     * Retourne la liste de tous les déplacements possibles pour la Princesse à partir de sa position actuelle
     * sur l'échiquier.
     *
     * @param startYRow La ligne de la princesse sur l'échiquier (de 0 à 7)
     * @param startXCol La colonne de la princesse sur l'échiquier (de 0 à 9)
     * @param board     L'échiquier actuel représenté par une matrice de ChessPiece
     * @return Une liste d'entiers représentant les positions (ligne, colonne) des cases où la princesse peut se déplacer.
     */
    @Override
    public ArrayList<int[]> possiblesMoves(int startYRow, int startXCol, ChessPiece[][] board) {
        ArrayList<int[]> moves = new ArrayList<>();

        // generates moves like bishop

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
        for(int i=startYRow-1, j=startXCol+1; i>=0 && j<10 && canMove; i--, j++){
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
        for(int i=startYRow+1, j=startXCol+1; i<8 && j<10 && canMove; i++, j++){
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


        // generates moves like Knight
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
