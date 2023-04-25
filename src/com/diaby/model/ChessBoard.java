package com.diaby.model;

//import javax.swing.text.Position;

import java.awt.*;
import java.util.ArrayList;

public class ChessBoard {
    private ChessPiece[][] tileBoard;
    public boolean[][] highLightCase = new boolean[8][8];
    public ChessBoard() {
        tileBoard = new ChessPiece[8][8];
        this.initialize();

    }

    private void initialize() {
        // Initialiser les pièces blanches
        tileBoard[0][0] = new Rook("tour_b.png", Color.WHITE, 0, 0);
        tileBoard[0][1] = new Knight("cavalier_b.png",Color.WHITE, 0, 1);
        tileBoard[0][2] = new Bishop("fou_b.png",Color.WHITE, 0, 2);
        tileBoard[0][3] = new Queen("reine_b.png",Color.WHITE, 0, 3);
        tileBoard[0][4] = new King("roi_b.png",Color.WHITE, 0, 4);
        tileBoard[0][5] = new Bishop("fou_b.png",Color.WHITE, 0, 5);
        tileBoard[0][6] = new Knight("cavalier_b.png",Color.WHITE, 0, 6);
        tileBoard[0][7] = new Rook("tour_b.png",Color.WHITE, 0, 7);
        for (int j = 0; j < 8; j++) {
            tileBoard[1][j] = new Pawn("pion_b.png",Color.WHITE, 1, j);
        }

        // Initialiser les pièces noires
        tileBoard[7][0] = new Rook("tour_n.png",Color.BLACK, 7, 0);
        tileBoard[7][1] = new Knight("cavalier_n.png",Color.BLACK, 7, 1);
        tileBoard[7][2] = new Bishop("fou_n.png",Color.BLACK, 7, 2);
        tileBoard[7][3] = new Queen("reine_n.png",Color.BLACK, 7, 3);
        tileBoard[7][4] = new King("roi_n.png",Color.BLACK, 7, 4);
        tileBoard[7][5] = new Bishop("fou_n.png",Color.BLACK, 7, 5);
        tileBoard[7][6] = new Knight("cavalier_n.png",Color.BLACK, 7, 6);
        tileBoard[7][7] = new Rook("tour_n.png",Color.BLACK, 7, 7);
        for (int j = 0; j < 8; j++) {
            tileBoard[6][j] = new Pawn("pion_n.png",Color.BLACK, 6, j);
        }
    }
    public static ChessPiece[][] copyBoard(ChessPiece[][] board) {
        ChessPiece[][] copy = new ChessPiece[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPiece piece = board[row][col];
                if (piece != null ) {
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
        for(int i = 0; i< 8; i++)
            for(int j = 0; j< 8; j++)
                highLightCase[i][j]=false;
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
        tileBoard[row][col] = null ;
    }

    public int getNbOfWhitePiece() {
        ArrayList<ChessPiece> pieces = new ArrayList<>();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPiece piece = tileBoard[row][col];
                if (piece != null && piece.isWhite()) {
                    pieces.add(piece);
                }
            }
        }
        return pieces.size();
    }

    public int getNbOfBlackPiece() {
        ArrayList<ChessPiece> pieces = new ArrayList<>();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPiece piece = tileBoard[row][col];
                if (piece != null && !piece.isWhite()) {
                    pieces.add(piece);
                }
            }
        }
        return pieces.size();
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

    public King getKing(boolean isWhiteKing)
    {
        King king = null;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPiece piece = tileBoard[row][col];
                if (piece instanceof King && piece.isWhite() == isWhiteKing) {
                    king = (King) getPieceAt(row,col);
                }
            }
        }

        return king;
    }

    public void move(int startRow, int startCol, int endRow, int endCol) {
        ChessPiece startPiece = getPieceAt(startRow, startCol);
        ChessPiece endPiece = getPieceAt(endRow, endCol);
        if(endPiece != null && (endPiece.getColor() != startPiece.getColor()))
        {
            endPiece.setCaptured();
            removePieceAt(endRow, endCol);
            setPieceAt(endRow, endCol, startPiece);
            setPieceAt(startRow, startCol, null);
            startPiece.setRow(endRow);
            startPiece.setCol(endCol);
        }
        else
        {
            setPieceAt(endRow, endCol, startPiece);
            setPieceAt(startRow, startCol, null);
            startPiece.setRow(endRow);
            startPiece.setCol(endCol);
        }

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
        if ( startPiece instanceof Pawn && endCol != startCol && endPiece == null) {
            int capturedPieceRow = startPiece.isWhite() ? endRow - 1 : endRow + 1;
            ChessPiece capturedPiece = getPieceAt(capturedPieceRow, endCol);
            capturedPiece.setCaptured();
            removePieceAt(capturedPieceRow, endCol);
        }

        move(startRow, startCol, endRow, endCol);
        // Mise à hasMoved = true, une fois le déplacement effectué pour le roi et la tour.
        if(startPiece instanceof King){
            ((King) startPiece).setHasMoved(true);
        }else if (startPiece instanceof Rook){
            ((Rook) startPiece).setHasMoved(true);
        }
        return true;
    }




    public void printBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPiece piece = getPieceAt(row, col);
                if (piece == null) {
                    System.out.print("- ");
                } else {
                    System.out.print(piece.getImageName() + " ");
                }
            }
            System.out.println();
        }
    }


}
