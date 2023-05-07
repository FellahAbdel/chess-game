package variant.model;

import java.awt.*;
import java.util.ArrayList;

public abstract class ChessPiece {
    private String pieceName; // nom de la pièce

    private boolean whitePiecesAtBottom;

    protected Color color; // couleur de la pièce (blanc ou noir)
    private int row; // ligne actuelle de la pièce sur le plateau
    private int col; // colonne actuelle de la pièce sur le plateau
    protected boolean captured;

    public ChessPiece(String name, Color color, int row , int col, boolean whitePiecesAtBottom) {
        this.pieceName = name;
        this.color = color;
        this.row = row ;
        this.col = col ;
        captured = false;
        this.whitePiecesAtBottom = whitePiecesAtBottom;
    }

    /**
     * Renvoie le tour en cours (true si c'est le tour des blancs, false si c'est le tour des noirs).
     *
     * @return boolean true si c'est le tour des blancs, false si c'est le tour des noirs.
     */
    public boolean getWhitePiecesAtBottom() {
        return whitePiecesAtBottom;
    }

    /**
     * Renvoie le nom de la pièce.
     *
     * @return String le nom de la pièce.
     */
    public String getPieceName (){
        return pieceName;
    }

    /**
     * Renvoie la couleur de la pièce.
     *
     * @return Color la couleur de la pièce.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Renvoie le numéro de ligne de la pièce.
     *
     * @return int le numéro de ligne de la pièce.
     */
    public int getRow() {
        return row;
    }

    /**
     * Renvoie le numéro de colonne de la pièce.
     *
     * @return int le numéro de colonne de la pièce.
     */
    public int getCol() {
        return col;
    }

    /**
     * Met à jour le numéro de ligne de la pièce.
     *
     * @param row le nouveau numéro de ligne de la pièce.
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Met à jour le numéro de colonne de la pièce.
     *
     * @param col le nouveau numéro de colonne de la pièce.
     */
    public void setCol(int col) {
        this.col = col;
    }

    /**
     * Met la pièce en tant que capturée.
     */
    public void setCaptured() {
        captured = true;
    }

    /**
     * Vérifie si la pièce est blanche.
     *
     * @return boolean true si la pièce est blanche, false sinon.
     */
    public boolean isWhite() {
        return this.getColor() == Color.WHITE;
    }

    /**
     * Renvoie une liste des coordonnées des cases où la pièce peut se déplacer.
     *
     * @param startYRow le numéro de ligne de départ de la pièce.
     * @param startXCol le numéro de colonne de départ de la pièce.
     * @param board     le plateau de jeu.
     * @return ArrayList<int [ ]> une liste des coordonnées des cases où la pièce peut se déplacer.
     */
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
        // Vérifie s'il a des mouvements valides
        return moves.size() != 0;
    }

}
