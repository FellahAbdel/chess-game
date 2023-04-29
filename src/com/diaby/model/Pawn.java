package com.diaby.model;

import java.awt.*;
import java.util.ArrayList;

public class Pawn extends ChessPiece {
    private boolean hasMoved; // indique si le pion a déjà été déplacé ou non

    public Pawn(Color color, int row, int col,boolean isWhiteTurn) {
        super("Pawn",color, row, col,isWhiteTurn);
        hasMoved = false;
    }

    public Pawn(ChessPiece piece) {
        super(piece.getPieceName(), piece.getColor(), piece.getRow(), piece.getCol(), piece.getWhiteTurn());
    }

    public boolean getHasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
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
                newPiece = new Queen(color, row, col, pawn.getWhiteTurn());
                break;
            case "Rook":
                newPiece = new Rook(color, row, col, pawn.getWhiteTurn());
                break;
            case "Bishop":
                newPiece = new Bishop(color, row, col, pawn.getWhiteTurn());
                break;
            case "Knight":
                newPiece = new Knight(color, row, col, pawn.getWhiteTurn());
                break;
            default:
                throw new IllegalArgumentException("Invalid piece type");
        }
        board[row][col] = newPiece;
    }
    public ArrayList<int[]> possiblesMoves(int startXCol, int startYRow, ChessPiece[][] board) {
        ArrayList<int[]> moves = new ArrayList<>();

        if(this.getWhiteTurn())
        {
            moves = possiblesMovesWhiteTurnTrue(startXCol,startYRow,board,moves);
        }else{
            moves = possiblesMovesWhiteTurnFalse(startXCol,startYRow,board,moves);
        }

        return moves;
    }

    public ArrayList<int[]> possiblesMovesWhiteTurnFalse(int startXCol, int startYRow, ChessPiece[][] board, ArrayList<int[]> moves) {

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

    public ArrayList<int[]> possiblesMovesWhiteTurnTrue(int startXCol, int startYRow, ChessPiece[][] board, ArrayList<int[]> moves) {

        if (!this.isWhite()) {

            // Check one step forward
            if (startXCol < 7 && board[startXCol + 1][startYRow] == null) {
                moves.add(new int[]{startXCol + 1, startYRow});
            }
            // Check two steps forward
            if (startXCol == 1 && board[startXCol + 1][startYRow] == null && board[startXCol + 2][startYRow] == null) {
                moves.add(new int[]{startXCol + 2, startYRow});
            }
            // Check diagonal captures
            if (startXCol < 6 && startYRow > 0 && board[startXCol + 1][startYRow - 1] != null && board[startXCol + 1][startYRow - 1].isWhite()) {
                moves.add(new int[]{startXCol + 1, startYRow - 1});
            }
            if (startXCol < 6 && startYRow < 7 && board[startXCol + 1][startYRow + 1] != null && board[startXCol + 1][startYRow + 1].isWhite()) {
                moves.add(new int[]{startXCol + 1, startYRow + 1});
            }
            // Check en passant capture
            if (startXCol == 4 && startYRow > 0 && board[startXCol][startYRow - 1] != null && board[startXCol][startYRow - 1].isWhite()
                    && board[startXCol + 1][startYRow - 1] == null && board[startXCol][startYRow - 1] instanceof Pawn) {
                moves.add(new int[]{startXCol + 1, startYRow - 1});
            }
            if (startXCol == 4 && startYRow < 7 && board[startXCol][startYRow + 1] != null && board[startXCol][startYRow + 1].isWhite()
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
            if (startXCol > 1 && startYRow > 0 && board[startXCol - 1][startYRow - 1] != null && !board[startXCol - 1][startYRow - 1].isWhite()) {
                moves.add(new int[]{startXCol - 1, startYRow - 1});
            }
            if (startXCol > 1 && startYRow < 7 && board[startXCol - 1][startYRow + 1] != null && !board[startXCol - 1][startYRow + 1].isWhite()) {
                moves.add(new int[]{startXCol - 1, startYRow + 1});
            }
            // Check en passant capture
            if (startXCol == 3 && startYRow > 0 && board[startXCol][startYRow - 1] != null && !board[startXCol][startYRow - 1].isWhite()
                    && board[startXCol - 1][startYRow - 1] == null && board[startXCol][startYRow - 1] instanceof Pawn) {
                moves.add(new int[]{startXCol - 1, startYRow - 1});
            }
            if (startXCol == 3 && startYRow < 7 && board[startXCol][startYRow + 1] != null && !board[startXCol][startYRow + 1].isWhite()
                    && board[startXCol - 1][startYRow + 1] == null && board[startXCol][startYRow + 1] instanceof Pawn) {
                moves.add(new int[]{startXCol - 1, startYRow + 1});
            }
        }
        return moves;
    }

}