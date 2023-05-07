package com.diaby.model;

import java.awt.*;
import java.util.ArrayList;

public class Pawn extends ChessPiece {

    private boolean hasJustMoveDouble;
    public Pawn(Color color, int row, int col, boolean isWhiteTurn) {
        super("Pawn", color, row, col, isWhiteTurn);
        hasJustMoveDouble = false;
    }

    public boolean getHasJustMoveDouble() {
        return hasJustMoveDouble;
    }

    public void setHasJustMoveDouble(boolean hasJustMoveDouble) {
        this.hasJustMoveDouble = hasJustMoveDouble;
    }

    public Pawn(ChessPiece piece) {
        super(piece.getPieceName(), piece.getColor(), piece.getRow(), piece.getCol(), piece.getWhite_pieces_at_bottom());
    }

    public void promotePawn(Pawn pawn, int row, int col, String pieceType, ChessPiece[][] board) {
        Color color = pawn.getColor();
        ChessPiece newPiece;
        switch (pieceType) {
            case "Queen":
                newPiece = new Queen(color, row, col, pawn.getWhite_pieces_at_bottom());
                break;
            case "Rook":
                newPiece = new Rook(color, row, col, pawn.getWhite_pieces_at_bottom());
                break;
            case "Bishop":
                newPiece = new Bishop(color, row, col, pawn.getWhite_pieces_at_bottom());
                break;
            case "Knight":
                newPiece = new Knight(color, row, col, pawn.getWhite_pieces_at_bottom());
                break;
            default:
                throw new IllegalArgumentException("Invalid piece type");
        }
        board[row][col] = newPiece;
    }


    private void descent(int startXCol, int startYRow, ChessPiece[][] board, ArrayList<int[]> moves, Boolean
            isWhite) {


        if (startXCol < 7 && board[startXCol + 1][startYRow] == null) {
            moves.add(new int[]{startXCol + 1, startYRow});
        }
        // Check two steps forward
        if (startXCol == 1 &&
                board[startXCol + 1][startYRow] == null &&
                board[startXCol + 2][startYRow] == null) {
            moves.add(new int[]{startXCol + 2, startYRow});
        }
        // Check diagonal captures
        if (startXCol < 8 && startYRow > 0 &&
                board[startXCol + 1][startYRow - 1] != null &&
                board[startXCol + 1][startYRow - 1].isWhite() == isWhite) {
            moves.add(new int[]{startXCol + 1, startYRow - 1});
        }
        if (startXCol < 8 && startYRow < 7 &&
                board[startXCol + 1][startYRow + 1] != null &&
                board[startXCol + 1][startYRow + 1].isWhite() == isWhite) {
            moves.add(new int[]{startXCol + 1, startYRow + 1});
        }
        // Check en passant capture
        if (startXCol == 4 &&
                startYRow > 0 &&
                board[startXCol][startYRow - 1] != null &&
                board[startXCol][startYRow - 1].isWhite() == isWhite &&
                ((Pawn)board[startXCol][startYRow + 1]).getHasJustMoveDouble() &&
                board[startXCol + 1][startYRow - 1] == null &&
                board[startXCol][startYRow - 1] instanceof Pawn) {
            moves.add(new int[]{startXCol + 1, startYRow - 1});
        }
        if (startXCol == 4 &&
                startYRow < 7 &&
                board[startXCol][startYRow + 1] != null &&
                board[startXCol][startYRow + 1].isWhite() == isWhite &&
                ((Pawn)board[startXCol][startYRow + 1]).getHasJustMoveDouble() &&
                board[startXCol + 1][startYRow + 1] == null &&
                board[startXCol][startYRow + 1] instanceof Pawn) {
            moves.add(new int[]{startXCol + 1, startYRow + 1});
        }
    }

    private void climb(int startXCol, int startYRow, ChessPiece[][] board, ArrayList<int[]> moves, Boolean isWhite) {
        if (startXCol > 0 && board[startXCol - 1][startYRow] == null) {
            moves.add(new int[]{startXCol - 1, startYRow});
        }
        // Check two steps forward
        if (startXCol == 6 && board[startXCol - 1][startYRow] == null &&
                board[startXCol - 2][startYRow] == null) {
            moves.add(new int[]{startXCol - 2, startYRow});
        }
        // Check diagonal captures
        if (startXCol > 0 && startYRow > 0 &&
                board[startXCol - 1][startYRow - 1] != null &&
                board[startXCol - 1][startYRow - 1].isWhite() == isWhite) {
            moves.add(new int[]{startXCol - 1, startYRow - 1});
        }
        if (startXCol > 0 && startYRow < 7 &&
                board[startXCol - 1][startYRow + 1] != null &&
                board[startXCol - 1][startYRow + 1].isWhite() == isWhite) {
            moves.add(new int[]{startXCol - 1, startYRow + 1});
        }
        // Check en passant capture
        if (startXCol == 3 && startYRow > 0 &&
                board[startXCol][startYRow - 1] != null &&
                board[startXCol][startYRow - 1].isWhite() == isWhite &&
                ((Pawn)board[startXCol][startYRow - 1]).getHasJustMoveDouble() &&
                board[startXCol - 1][startYRow - 1] == null &&
                board[startXCol][startYRow - 1] instanceof Pawn) {
            moves.add(new int[]{startXCol - 1, startYRow - 1});
        }
        if (startXCol == 3 && startYRow < 7 &&
                board[startXCol][startYRow + 1] != null &&
                board[startXCol][startYRow + 1].isWhite() == isWhite &&
                ((Pawn)board[startXCol][startYRow + 1]).getHasJustMoveDouble() &&
                board[startXCol - 1][startYRow + 1] == null &&
                board[startXCol][startYRow + 1] instanceof Pawn) {
            moves.add(new int[]{startXCol - 1, startYRow + 1});
        }
    }

    private ArrayList<int[]> possiblesMovesTurn(int startXCol, int startYRow, ChessPiece[][] board,
                                               ArrayList<int[]> moves, Boolean whiteTurn) {
        if (whiteTurn) {
            if (this.isWhite()) {  // Descente blanche.
                descent(startXCol, startYRow, board, moves, false);
            } else { // Monté Noir.
                climb(startXCol, startYRow, board, moves, true);
            }
        } else {
            if (!this.isWhite()) {  // Descente Noire
                descent(startXCol, startYRow, board, moves, true);
            } else {  // Montee blanche.
                climb(startXCol, startYRow, board, moves, false);
            }
        }

        return moves;
    }
    @Override
    public ArrayList<int[]> possiblesMoves(int startXCol, int startYRow, ChessPiece[][] board) {
        ArrayList<int[]> moves = new ArrayList<>();

       return possiblesMovesTurn(startXCol, startYRow, board, moves, !this.getWhite_pieces_at_bottom());

    }


}