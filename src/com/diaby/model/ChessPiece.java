package com.diaby.model;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public abstract class ChessPiece {
    private String pieceName; // nom de la pièce
    private String imageName;
    private Color color; // couleur de la pièce (blanc ou noir)
    //private ImageIcon image; // image de la pièce pour l'interface graphique
    protected int row; // ligne actuelle de la pièce sur le plateau
    protected int col; // colonne actuelle de la pièce sur le plateau

    private boolean captured = false;
    public ChessPiece(String name,String imageName, Color color, int row , int col) {
        this.pieceName = name;
        this.color = color;
        this.row = row ;
        this.col = col ;
        this.imageName = imageName;
        captured = false;
    }

    public String getPieceName (){
        return pieceName;
    }
    public String getImageName() {
        return imageName;
    }

    public Color getColor() {
        return color;
    }

/*    public ImageIcon getImage() {
        return image;
    }*/

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
    /**
     * Vérifie si le mouvement d'une pièce est valide.
     *
     * @param startYRow la position en x de la pièce avant le mouvement.
     * @param startXCol la position en y de la pièce avant le mouvement.
     * @param endXCol la position en x de la pièce après le mouvement.
     * @param endYRow la position en y de la pièce après le mouvement.
     * @param board le tableau de pièces représentant l'état actuel du plateau.
     * @return true si le mouvement est valide, false sinon.
     */
    public abstract boolean isValidMove(int startYRow, int startXCol, int endYRow, int endXCol, ChessPiece[][] board);

    public abstract List<int[]> getLegalMoves(ChessPiece[][] board, boolean isWhite);
    public abstract List<int[]> getAllMoves();
    /**
     * Vérifie si une pièce peut atteindre une case donnée sur le plateau.
     *
     * @param xCol la position en x de la case.
     * @param yRow la position en y de la case.
     * @param board le tableau de pièces représentant l'état actuel du plateau.
     * @return true si la case peut être atteinte, false sinon.
     */
    public boolean canMoveTo(int yRow, int xCol, ChessPiece[][] board) {
        int startXCol = col;
        int startYRow = row;
        ChessPiece destPiece = board[yRow][xCol];

        // Vérifie si le mouvement est valide
        return isValidMove(startYRow, startXCol, yRow, xCol, board);
    }

/*    public List<int[]> getLegalMoves(ChessPiece[][] board) {
        List<int[]> legalMoves = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (canMoveTo(i, j, board)) {
                    legalMoves.add(new int[] {i, j});
                }
            }
        }
        return legalMoves;
    }*/

    public abstract String getSymbol();


    @Override
    public String toString() {
        return color + " " + pieceName;
    }
}
