package com.diaby.model;
import java.awt.*;
import java.util.ArrayList;

public class Pawn extends ChessPiece {
    private boolean hasMoved; // indique si le pion a déjà été déplacé ou non
    private boolean justMovedDouble; // flag pour indiquer si le pion vient de faire un double pas
    public Pawn(String imageName,Color color, int row, int col) {
        super("Pawn",imageName ,color, row, col);
        hasMoved = false;
        justMovedDouble = false;
    }

    public boolean getHasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public boolean getJustMovedDouble() {
        return justMovedDouble;
    }

    public void setJustMovedDouble(boolean justMovedDouble) {
        this.justMovedDouble = justMovedDouble;
    }

    @Override
    public boolean isValidMove(int startYRow, int startXCol, int endYRow, int endXCol, ChessPiece[][] board) {
        // Vérifie si le déplacement est vertical
        int deltaX = endXCol - startXCol;
        int deltaY = endYRow- startYRow;
        if (deltaX != 0) {
            return false;
        }

        // Vérifie si le déplacement est d'une case vers l'avant en fonction de la couleur
        if (isWhite()) {
            // Vérifie si le pion n'a pas encore été déplacé et s'il se déplace de deux cases vers l'avant
            if (!hasMoved && deltaY == 2 && board[startYRow + 1][startXCol] == null) {
                setJustMovedDouble(true);
                return true;
            }
            // Vérifie si le pion se déplace d'une case vers l'avant
            if (deltaY == 1 && board[endYRow][endXCol] == null) {
                return true;
            }
        } else {
            // Vérifie si le pion n'a pas encore été déplacé et s'il se déplace de deux cases vers l'avant
            if (!hasMoved && deltaY == -2 && board[startYRow - 1][startXCol] == null) {
                setJustMovedDouble(true);
                return true;
            }
            // Vérifie si le pion se déplace d'une case vers l'avant
            if (deltaY == -1 && board[endYRow][endXCol] == null) {
                return true;
            }
        }

        // Si aucun des cas précédents n'est vérifié, le mouvement n'est pas valide
        return false;
    }

    public ArrayList<int[]> PossiblesMoves(int startYRow, int startXCol, ChessPiece[][] board) {
        ArrayList<int[]> moves = new ArrayList<>();

        if (this.isWhite()) {
            // Check one step forward
            if (startYRow < 6 && board[startYRow + 1][startXCol] == null) {
                moves.add(new int[]{startYRow + 1, startXCol});
            }
            // Check two steps forward
            if (startYRow == 1 && board[startYRow + 1][startXCol] == null && board[startYRow + 2][startXCol] == null) {
                moves.add(new int[]{startYRow + 2, startXCol});
            }
            // Check diagonal captures
            if (startYRow < 6 && startXCol > 0 && board[startYRow + 1][startXCol - 1] != null && !board[startYRow + 1][startXCol - 1].isWhite()) {
                moves.add(new int[]{startYRow + 1, startXCol - 1});
            }
            if (startYRow < 6 && startXCol < 7 && board[startYRow + 1][startXCol + 1] != null && !board[startYRow + 1][startXCol + 1].isWhite()) {
                moves.add(new int[]{startYRow + 1, startXCol + 1});
            }
        } else {
            // Check one step forward
            if (startYRow > 1 && board[startYRow - 1][startXCol] == null) {
                moves.add(new int[]{startYRow - 1, startXCol});
            }
            // Check two steps forward
            if (startYRow == 6 && board[startYRow - 1][startXCol] == null && board[startYRow - 2][startXCol] == null) {
                moves.add(new int[]{startYRow - 2, startXCol});
            }
            // Check diagonal captures
            if (startYRow > 1 && startXCol > 0 && board[startYRow - 1][startXCol - 1] != null && board[startYRow - 1][startXCol - 1].isWhite()) {
                moves.add(new int[]{startYRow - 1, startXCol - 1});
            }
            if (startYRow > 1 && startXCol < 7 && board[startYRow - 1][startXCol + 1] != null && board[startYRow - 1][startXCol + 1].isWhite()) {
                moves.add(new int[]{startYRow - 1, startXCol + 1});
            }
        }

        return moves;
    }



    public String getSymbol(){
        return (getColor() == Color.WHITE ? "B" : "N");
    }
}