package com.diaby;

import java.awt.*;

public abstract class ChessPiece {
    private String name; // nom de la pièce
    private Color color; // couleur de la pièce (blanc ou noir)
    //private ImageIcon image; // image de la pièce pour l'interface graphique
    private int row; // ligne actuelle de la pièce sur le plateau
    private int col; // colonne actuelle de la pièce sur le plateau

    public ChessPiece(String name, Color color, int row , int col) {
        this.name = name;
        this.color = color;
        this.row = row ;
        this.col = col ;
    }

    public String getName() {
        return name;
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

    /**
     * Vérifie si le mouvement d'une pièce est valide.
     *
     * @param startX la position en x de la pièce avant le mouvement.
     * @param startY la position en y de la pièce avant le mouvement.
     * @param endX la position en x de la pièce après le mouvement.
     * @param endY la position en y de la pièce après le mouvement.
     * @param board le tableau de pièces représentant l'état actuel du plateau.
     * @return true si le mouvement est valide, false sinon.
     */
    public abstract boolean isValidMove(int startX, int startY, int endX, int endY, ChessPiece[][] board);

    /**
     * Vérifie si une pièce peut atteindre une case donnée sur le plateau.
     *
     * @param x la position en x de la case.
     * @param y la position en y de la case.
     * @param board le tableau de pièces représentant l'état actuel du plateau.
     * @return true si la case peut être atteinte, false sinon.
     */
    public boolean canMoveTo(int x, int y, ChessPiece[][] board) {
        int startX = col;
        int startY = row;
        ChessPiece destPiece = board[y][x];

        // Vérifie si la case de destination est occupée par une pièce de la même couleur
        if (destPiece != null && destPiece.getColor().equals(color)) {
            return false;
        }

        // Vérifie si le mouvement est valide
        if (isValidMove(startX, startY, x, y, board)) {
            return true;
        }

        return false;
    }

    public abstract String getSymbol();
    @Override
    public String toString() {
        return color + " " + name;
    }
}
