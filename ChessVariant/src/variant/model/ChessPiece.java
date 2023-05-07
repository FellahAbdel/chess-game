package variant.model;

import java.awt.*;
import java.util.ArrayList;

public abstract class ChessPiece {
    private String pieceName; // nom de la pièce

    private boolean isWhiteTurn ;

    protected Color color; // couleur de la pièce (blanc ou noir)
    private int row; // ligne actuelle de la pièce sur le plateau
    private int col; // colonne actuelle de la pièce sur le plateau
    private boolean captured;

    public ChessPiece(String name, Color color, int row , int col, boolean isWhiteTurn) {
        this.pieceName = name;
        this.color = color;
        this.row = row ;
        this.col = col ;
        captured = false;
        this.isWhiteTurn = isWhiteTurn;
    }

    public boolean getWhiteTurn() {
        return isWhiteTurn;
    }

    public String getPieceName (){
        return pieceName;
    }

    public Color getColor() {
        return color;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setCaptured() {
        captured = true;
    }
    public boolean isWhite() {
        return this.getColor() == Color.WHITE;
    }

    public abstract ArrayList<int[]> possiblesMoves(int startYRow, int startXCol, ChessPiece[][] board);
    /**
     * Vérifie si une pièce peut atteindre une case donnée sur le plateau.
     *
     * @param colX la position en x de la case.
     * @param rowY la position en y de la case.
     * @param board le tableau de pièces représentant l'état actuel du plateau.
     * @return true si la case peut être atteinte, false sinon.
     */
    public boolean canMove(int rowY, int colX, ChessPiece[][] board) {

        ArrayList<int[]> moves = possiblesMoves(rowY,colX,board);
        // Vérifie si il a des mouvements valides
        return moves.size() != 0;
    }

}
