package com.diaby.model;

import java.awt.*;
import java.util.ArrayList;

public class ChessBoard {
    private ChessPiece[][] tileBoard;
    public boolean[][] highLightCase = new boolean[8][8];

    public ChessBoard() {
        tileBoard = new ChessPiece[8][8];
    }

    public void initialize(boolean isWhiteTurn) {
        Color color = isWhiteTurn ? Color.BLACK : Color.WHITE;
        Color oppositeColor = isWhiteTurn ? Color.WHITE : Color.BLACK;

        // Initialiser les pièces blanches
        tileBoard[0][0] = new Rook(color, 0, 0, isWhiteTurn);
        tileBoard[0][1] = new Knight(color, 0, 1, isWhiteTurn);
        tileBoard[0][2] = new Bishop(color, 0, 2, isWhiteTurn);
        tileBoard[0][3] = new Queen(color, 0, 3, isWhiteTurn);
        tileBoard[0][4] = new King(color, 0, 4, isWhiteTurn);
        tileBoard[0][5] = new Bishop(color, 0, 5, isWhiteTurn);
        tileBoard[0][6] = new Knight(color, 0, 6, isWhiteTurn);
        tileBoard[0][7] = new Rook(color, 0, 7, isWhiteTurn);
        for (int j = 0; j < 8; j++) {
            tileBoard[1][j] = new Pawn(color, 1, j, isWhiteTurn);
        }

        // Initialiser les pièces noires
        tileBoard[7][0] = new Rook(oppositeColor, 7, 0, isWhiteTurn);
        tileBoard[7][1] = new Knight(oppositeColor, 7, 1, isWhiteTurn);
        tileBoard[7][2] = new Bishop(oppositeColor, 7, 2, isWhiteTurn);
        tileBoard[7][3] = new Queen(oppositeColor, 7, 3, isWhiteTurn);
        tileBoard[7][4] = new King(oppositeColor, 7, 4, isWhiteTurn);
        tileBoard[7][5] = new Bishop(oppositeColor, 7, 5, isWhiteTurn);
        tileBoard[7][6] = new Knight(oppositeColor, 7, 6, isWhiteTurn);
        tileBoard[7][7] = new Rook(oppositeColor, 7, 7, isWhiteTurn);
        for (int j = 0; j < 8; j++) {
            tileBoard[6][j] = new Pawn(oppositeColor, 6, j, isWhiteTurn);
        }

    }

    public static ChessPiece[][] copyBoard(ChessPiece[][] board) {
        ChessPiece[][] copy = new ChessPiece[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPiece piece = board[row][col];
                if (piece != null) {
                    ChessPiece newPiece;
                    if (piece instanceof Pawn) {
                        newPiece = new Pawn(piece);
                    } else if (piece instanceof Rook) {
                        newPiece = new Rook(piece);
                    } else if (piece instanceof Knight) {
                        newPiece = new Knight(piece);
                    } else if (piece instanceof Bishop) {
                        newPiece = new Bishop(piece);
                    } else if (piece instanceof Queen) {
                        newPiece = new Queen(piece);
                    } else {
                        newPiece = new King(piece);
                    }
                    copy[row][col] = newPiece;
                }
            }
        }
        return copy;
    }

    public ChessPiece[][] getTileBoard() {
        return tileBoard;
    }

    public void resetHighlight() {
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                highLightCase[i][j] = false;
    }

    public ChessPiece getPieceAt(int row, int col) {
        return tileBoard[row][col];
    }

    public void setPieceAt(int row, int col, ChessPiece piece) {
        tileBoard[row][col] = piece;
    }

    public void removePieceAt(int row, int col) {
        ChessPiece pieceToRemove = getPieceAt(row, col);
        if (pieceToRemove == null) {
            throw new IllegalArgumentException("No piece at " + row + " " + col);
        }
        tileBoard[row][col] = null;
    }

    public boolean isOccupied(int row, int col) {
        return tileBoard[row][col] != null;
    }

    public ArrayList<ChessPiece> getPiecesByColor(Color color) {
        ArrayList<ChessPiece> piecesList = new ArrayList<>();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPiece piece = tileBoard[row][col];
                if (piece != null && piece.getColor() == color) {
                    piecesList.add(piece);
                }
            }
        }
        return piecesList;
    }

    public King getKing(boolean isWhiteKing) {
        King king = null;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPiece piece = tileBoard[row][col];
                if (piece instanceof King && piece.isWhite() == isWhiteKing) {
                    king = (King) getPieceAt(row, col);
                }
            }
        }

        return king;
    }

    public void resetBooleanPawn(boolean isWhitePiece) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPiece piece = tileBoard[row][col];
                if (piece instanceof Pawn && piece.isWhite() == isWhitePiece) {
                    ((Pawn) piece).setHasJustMoveDouble(false);
                }
            }
        }
    }

    public void move(int startRow, int startCol, int endRow, int endCol) {
        ChessPiece startPiece = getPieceAt(startRow, startCol);
        ChessPiece endPiece = getPieceAt(endRow, endCol);
        if (endPiece != null && (endPiece.getColor() != startPiece.getColor())) {
            endPiece.setCaptured();
            removePieceAt(endRow, endCol);
        }
        setPieceAt(endRow, endCol, startPiece);
        setPieceAt(startRow, startCol, null);
        startPiece.setRow(endRow);
        startPiece.setCol(endCol);

    }

    public boolean movePiece(int startRow, int startCol, int endRow, int endCol) {
        ChessPiece startPiece = getPieceAt(startRow, startCol);
        ChessPiece endPiece = getPieceAt(endRow, endCol);

        if (startPiece == null) {
            return false;
        }
        if (endPiece != null && endPiece.getColor() == startPiece.getColor()) {
            return false;
        }

        // capture en passant
        if (!startPiece.getWhiteTurn()) {
            if (startPiece instanceof Pawn && endCol != startCol && endPiece == null) {
                int capturedPieceRow = startPiece.isWhite() ? endRow - 1 : endRow + 1;
                ChessPiece capturedPiece = getPieceAt(capturedPieceRow, endCol);
                capturedPiece.setCaptured();
                removePieceAt(capturedPieceRow, endCol);
            }
        } else {
            if (startPiece instanceof Pawn && endCol != startCol && endPiece == null) {
                int capturedPieceRow = !startPiece.isWhite() ? endRow - 1 : endRow + 1;
                ChessPiece capturedPiece = getPieceAt(capturedPieceRow, endCol);
                capturedPiece.setCaptured();
                removePieceAt(capturedPieceRow, endCol);
            }
        }


        move(startRow, startCol, endRow, endCol);

        if (startPiece instanceof Pawn && Math.abs(startRow - endRow) == 2) {
            ((Pawn) startPiece).setHasJustMoveDouble(true);
        }

        // Mise à hasMoved = true, une fois le déplacement effectué pour le roi et la tour.
        if (startPiece instanceof King) {
            ((King) startPiece).setHasMoved(true);
        } else if (startPiece instanceof Rook) {
            ((Rook) startPiece).setHasMoved(true);
        }
        return true;
    }

}
