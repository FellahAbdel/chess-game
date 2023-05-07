package com.diaby.model;

import java.awt.*;
import java.util.ArrayList;


public class King extends ChessPiece {
    private boolean hasMoved;

    public King(Color color, int row, int col, boolean isWhiteTurn) {
        super("King", color, row, col, isWhiteTurn);
        hasMoved = false;
    }

    public King(ChessPiece piece) {
        super(piece.getPieceName(), piece.getColor(), piece.getRow(), piece.getCol(), piece.getWhiteTurn());
    }

    public boolean getHasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    private boolean isValidPosition(int x, int y) {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }

    public ArrayList<int[]> possiblesMoves(int startYRow, int startXCol, ChessPiece[][] board) {
        ArrayList<int[]> moves = new ArrayList<>();
        // Check moves to the right
        if (startXCol + 1 < 8) {
            ChessPiece piece = board[startYRow][startXCol + 1];
            if (piece == null || piece.isWhite() != this.isWhite()) {
                // Try the move and check if king is in check
                ChessPiece[][] testBoard = ChessBoard.copyBoard(board);
                testBoard[startYRow][startXCol + 1] = testBoard[startYRow][startXCol];
                testBoard[startYRow][startXCol] = null;
                if (!isInCheck(this.isWhite(), testBoard)) {
                    moves.add(new int[]{startYRow, startXCol + 1});
                }
            }
        }

        // Check moves to the left
        if (startXCol - 1 >= 0) {
            ChessPiece piece = board[startYRow][startXCol - 1];
            if (piece == null || piece.isWhite() != this.isWhite()) {
                // Try the move and check if king is in check
                ChessPiece[][] testBoard = ChessBoard.copyBoard(board);
                testBoard[startYRow][startXCol - 1] = testBoard[startYRow][startXCol];
                testBoard[startYRow][startXCol] = null;
                if (!isInCheck(this.isWhite(), testBoard)) {
                    moves.add(new int[]{startYRow, startXCol - 1});
                }
            }
        }

        // Check moves to the bottom
        if (startYRow + 1 < 8) {
            ChessPiece piece = board[startYRow + 1][startXCol];
            if (piece == null || piece.isWhite() != this.isWhite()) {
                // Try the move and check if king is in check
                ChessPiece[][] testBoard = ChessBoard.copyBoard(board);
                testBoard[startYRow + 1][startXCol] = testBoard[startYRow][startXCol];
                testBoard[startYRow][startXCol] = null;
                if (!isInCheck(this.isWhite(), testBoard)) {
                    moves.add(new int[]{startYRow + 1, startXCol});
                }
            }
        }

        // Check moves to the top
        if (startYRow - 1 >= 0) {
            ChessPiece piece = board[startYRow - 1][startXCol];
            if (piece == null || piece.isWhite() != this.isWhite()) {
                // Try the move and check if king is in check
                ChessPiece[][] testBoard = ChessBoard.copyBoard(board);
                testBoard[startYRow - 1][startXCol] = testBoard[startYRow][startXCol];
                testBoard[startYRow][startXCol] = null;
                if (!isInCheck(this.isWhite(), testBoard)) {
                    moves.add(new int[]{startYRow - 1, startXCol});
                }
            }
        }

        // Check diagonal moves to the top-right
        if (startYRow - 1 >= 0 && startXCol + 1 < 8) {
            ChessPiece piece = board[startYRow - 1][startXCol + 1];
            if (piece == null || piece.isWhite() != this.isWhite()) {
                // Try the move and check if king is in check
                ChessPiece[][] testBoard = ChessBoard.copyBoard(board);
                testBoard[startYRow - 1][startXCol + 1] = testBoard[startYRow][startXCol];
                testBoard[startYRow][startXCol] = null;
                if (!isInCheck(this.isWhite(), testBoard)) {
                    moves.add(new int[]{startYRow - 1, startXCol + 1});
                }
            }
        }

        // Check diagonal moves to the top-left
        if (startYRow - 1 >= 0 && startXCol - 1 >= 0) {
            ChessPiece piece = board[startYRow - 1][startXCol - 1];
            if (piece == null || piece.isWhite() != this.isWhite()) {
                // Try the move and check if king is in check
                ChessPiece[][] testBoard = ChessBoard.copyBoard(board);
                testBoard[startYRow - 1][startXCol - 1] = testBoard[startYRow][startXCol];
                testBoard[startYRow][startXCol] = null;
                if (!isInCheck(this.isWhite(), testBoard)) {
                    moves.add(new int[]{startYRow - 1, startXCol - 1});
                }

            }
        }

        // Check diagonal moves to the bottom-right
        if (startYRow + 1 < 8 && startXCol + 1 < 8) {
            ChessPiece piece = board[startYRow + 1][startXCol + 1];
            if (piece == null || piece.isWhite() != this.isWhite()) {
                // Try the move and check if king is in check
                ChessPiece[][] testBoard = ChessBoard.copyBoard(board);
                testBoard[startYRow + 1][startXCol + 1] = testBoard[startYRow][startXCol];
                testBoard[startYRow][startXCol] = null;
                if (!isInCheck(this.isWhite(), testBoard)) {
                    moves.add(new int[]{startYRow + 1, startXCol + 1});
                }
            }
        }

        // Check diagonal moves to the bottom-left
        if (startYRow + 1 < 8 && startXCol - 1 >= 0) {
            ChessPiece piece = board[startYRow + 1][startXCol - 1];
            if (piece == null || piece.isWhite() != this.isWhite()) {
                // Try the move and check if king is in check
                ChessPiece[][] testBoard = ChessBoard.copyBoard(board);
                testBoard[startYRow + 1][startXCol - 1] = testBoard[startYRow][startXCol];
                testBoard[startYRow][startXCol] = null;
                if (!isInCheck(this.isWhite(), testBoard)) {
                    moves.add(new int[]{startYRow + 1, startXCol - 1});
                }

            }
        }

        // petit roque
        if (!this.getHasMoved() && startXCol == 4) {
            ChessPiece rook = board[startYRow][7];
            if (rook instanceof Rook && !((Rook) rook).getHasMoved() && board[startYRow][5] == null && board[startYRow][6] == null) {
                boolean isInCheck = false;
                ChessPiece piece = board[startYRow][startXCol];
                if (isInCheck(piece.isWhite(), board)) {
                    isInCheck = true;
                }
                if (!isInCheck) {
                    ChessPiece kingTempPos = board[startYRow][startXCol + 2];
                    if (kingTempPos == null || kingTempPos.isWhite() != this.isWhite()) {
                        // Try the move and check if king is in check
                        ChessPiece[][] testBoard = ChessBoard.copyBoard(board);
                        testBoard[startYRow][startXCol + 2] = testBoard[startYRow][startXCol];
                        testBoard[startYRow][startXCol] = null;
                        if (!isInCheck(this.isWhite(), testBoard)) {
                            moves.add(new int[]{startYRow, startXCol + 2});
                        }

                    }
                }
            }
        }

        // grand roque
        if (!this.getHasMoved() && startXCol == 4) {
            ChessPiece rook = board[startYRow][0];
            if (rook instanceof Rook && !((Rook) rook).getHasMoved() && board[startYRow][1] == null && board[startYRow][2] == null && board[startYRow][3] == null) {
                boolean isInCheck = false;
                ChessPiece piece = board[startYRow][startXCol];
                if (isInCheck(piece.isWhite(), board)) {
                    isInCheck = true;
                }
                if (!isInCheck) {
                    ChessPiece kingTempPos = board[startYRow][startXCol - 2];
                    if (kingTempPos == null || kingTempPos.isWhite() != this.isWhite()) {
                        // Try the move and check if king is in check
                        ChessPiece[][] testBoard = ChessBoard.copyBoard(board);
                        testBoard[startYRow][startXCol - 2] = testBoard[startYRow][startXCol];
                        testBoard[startYRow][startXCol] = null;
                        if (!isInCheck(this.isWhite(), testBoard)) {
                            moves.add(new int[]{startYRow, startXCol - 2});
                        }

                    }
                }
            }
        }


        return moves;
    }


    public boolean isInCheck(boolean isWhiteKing, ChessPiece[][] board) {
        // Find the king's position
        int[] kingPos = null;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPiece piece = board[row][col];
                if (piece instanceof King && piece.isWhite() == isWhiteKing) {
                    kingPos = new int[]{row, col};
                }
            }
        }

        // Check if any opponent piece can attack the king
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPiece piece = board[row][col];
                if (piece != null && piece.isWhite() != isWhiteKing && !(piece instanceof King)) {
                    ArrayList<int[]> moves = piece.possiblesMoves(row, col, board);
                    for (int[] move : moves) {
                        if (kingPos != null && move[0] == kingPos[0] && move[1] == kingPos[1]) {
                            return true;
                        }
                    }
                }

            }
        }

        // The king is not in check
        return false;
    }

}




