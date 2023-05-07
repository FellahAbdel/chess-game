package variant.model;

import java.awt.*;
import java.util.ArrayList;


public class Bishop extends ChessPiece {
    public Bishop(Color color, int row, int col,boolean isWhiteTurn) {
        super("Bishop", color, row, col, isWhiteTurn);
    }

    public Bishop(ChessPiece piece) {
        super(piece.getPieceName(), piece.getColor(), piece.getRow(), piece.getCol(), piece.getWhitePiecesAtBottom());

    }

    /**
     * Renvoie une liste de tous les mouvements possibles qu'un fou peut faire sur un échiquier donné à partir de sa
     * position actuelle.
     * Le fou peut se déplacer en diagonale dans n'importe quelle direction jusqu'à ce qu'il atteigne la fin de
     * l'échiquier ou une obstruction.
     * Si le fou rencontre une obstruction, il peut capturer la pièce obstruant si elle est de couleur opposée.
     *
     * @param startYRow La coordonnée y de départ (rangée) du fou.
     * @param startXCol La coordonnée x de départ (colonne) du fou.
     * @param board     L'échiquier sur lequel le fou est placé.
     * @return Une ArrayList de tableaux d'entiers, où chaque tableau d'entiers représente un mouvement possible pour
     * le fou.
     * Chaque tableau d'entiers contient deux entiers : la coordonnée y (rangée) et la coordonnée x (colonne) de la
     * case de destination.
     */
    @Override
    public ArrayList<int[]> possiblesMoves(int startYRow, int startXCol, ChessPiece[][] board) {
        ArrayList<int[]> moves = new ArrayList<>();
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

        return moves;
    }
}
