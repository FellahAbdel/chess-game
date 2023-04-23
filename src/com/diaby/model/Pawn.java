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
                return true;
            }
            // Vérifie si le pion se déplace d'une case vers l'avant
            if (deltaY == 1 && board[endYRow][endXCol] == null) {
                return true;
            }
        } else {
            // Vérifie si le pion n'a pas encore été déplacé et s'il se déplace de deux cases vers l'avant
            if (!hasMoved && deltaY == -2 && board[startYRow - 1][startXCol] == null) {
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

    public void promotePawn(Pawn pawn, int row, int col, String pieceType,ChessPiece[][] board) {
        Color color = pawn.getColor();
        ChessPiece newPiece;
        switch (pieceType) {
            case "Queen":
                if (pawn.isWhite()) {
                    newPiece = new Queen("reine_b.png", color, row, col);
                } else {
                    newPiece = new Queen("reine_n.png", color, row, col);
                }
                break;
            case "Rook":
                if (pawn.isWhite()) {
                    newPiece = new Rook("tour_b.png", color, row, col);
                } else {
                    newPiece = new Rook("tour_n.png", color, row, col);
                }
                break;
            case "Bishop":
                if (pawn.isWhite()) {
                    newPiece = new Bishop("fou_b.png", color, row, col);
                } else {
                    newPiece = new Bishop("fou_n.png", color, row, col);
                }
                break;
            case "Knight":
                if (pawn.isWhite()) {
                    newPiece = new Knight("cavalier_b.png", color, row, col);
                } else {
                    newPiece = new Knight("cavalier_n.png", color, row, col);
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid piece type");
        }
        board[row][col] = newPiece;
    }

    public ArrayList<int[]> PossiblesMoves(int startXCol, int startYRow, ChessPiece[][] board) {
        ArrayList<int[]> moves = new ArrayList<>();

        if (this.isWhite()) {

            // Check one step forward
            if (startXCol < 7 && board[startXCol + 1][startYRow] == null) {
                moves.add(new int[]{startXCol + 1, startYRow});
            }
            // Check two steps forward
            if (startXCol == 1 && board[startXCol + 1][startYRow] == null && board[startXCol + 2][startYRow] == null) {
                moves.add(new int[]{startXCol + 2, startYRow});
            }
            // Check diagonal captures
            if (startXCol < 6 && startYRow > 0 && board[startXCol + 1][startYRow - 1] != null && !board[startXCol + 1][startYRow - 1].isWhite()) {
                moves.add(new int[]{startXCol + 1, startYRow - 1});
            }
            if (startXCol < 6 && startYRow < 7 && board[startXCol + 1][startYRow + 1] != null && !board[startXCol + 1][startYRow + 1].isWhite()) {
                moves.add(new int[]{startXCol + 1, startYRow + 1});
            }
            // Check en passant capture
            if (startXCol == 4 && startYRow > 0 && board[startXCol][startYRow - 1] != null && !board[startXCol][startYRow - 1].isWhite()
                    && board[startXCol + 1][startYRow - 1] == null && board[startXCol][startYRow - 1] instanceof Pawn) {
                moves.add(new int[]{startXCol + 1, startYRow - 1});
            }
            if (startXCol == 4 && startYRow < 7 && board[startXCol][startYRow + 1] != null && !board[startXCol][startYRow + 1].isWhite()
                    && board[startXCol + 1][startYRow + 1] == null && board[startXCol][startYRow + 1] instanceof Pawn) {
                moves.add(new int[]{startXCol + 1, startYRow + 1});
            }
        } else {
            // Check one step forward
            if (startXCol > 0 && board[startXCol - 1][startYRow] == null) {
                moves.add(new int[]{startXCol - 1, startYRow});
            }
            // Check two steps forward
            if (startXCol == 6 && board[startXCol - 1][startYRow] == null && board[startXCol - 2][startYRow] == null) {
                moves.add(new int[]{startXCol - 2, startYRow});
            }
            // Check diagonal captures
            if (startXCol > 1 && startYRow > 0 && board[startXCol - 1][startYRow - 1] != null && board[startXCol - 1][startYRow - 1].isWhite()) {
                moves.add(new int[]{startXCol - 1, startYRow - 1});
            }
            if (startXCol > 1 && startYRow < 7 && board[startXCol - 1][startYRow + 1] != null && board[startXCol - 1][startYRow + 1].isWhite()) {
                moves.add(new int[]{startXCol - 1, startYRow + 1});
            }
            // Check en passant capture
            if (startXCol == 3 && startYRow > 0 && board[startXCol][startYRow - 1] != null && board[startXCol][startYRow - 1].isWhite()
                    && board[startXCol - 1][startYRow - 1] == null && board[startXCol][startYRow - 1] instanceof Pawn) {
                moves.add(new int[]{startXCol - 1, startYRow - 1});
            }
            if (startXCol == 3 && startYRow < 7 && board[startXCol][startYRow + 1] != null && board[startXCol][startYRow + 1].isWhite()
                    && board[startXCol - 1][startYRow + 1] == null && board[startXCol][startYRow + 1] instanceof Pawn) {
                moves.add(new int[]{startXCol - 1, startYRow + 1});
            }
        }
        return moves;
    }



    public String getSymbol(){
        return (getColor() == Color.WHITE ? "B" : "N");
    }
}