package com.diaby.model;

//import javax.swing.text.Position;
import com.diaby.model.*;

import java.awt.*;
import java.util.ArrayList;

public class ChessBoard {
    private ChessPiece[][] board;
    public boolean[][] highLightCase = new boolean[8][8];
    public ChessBoard() {
        board = new ChessPiece[8][8];
        this.initialize();

    }

    private void initialize() {
        // Initialiser les pièces blanches
        board[0][0] = new Rook("tour_b.png", Color.WHITE, 0, 0);
        board[0][1] = new Knight("cavalier_b.png",Color.WHITE, 0, 1);
        board[0][2] = new Bishop("fou_b.png",Color.WHITE, 0, 2);
        board[0][3] = new Queen("reine_b.png",Color.WHITE, 0, 3);
        board[0][4] = new King("roi_b.png",Color.WHITE, 0, 4);
        board[0][5] = new Bishop("fou_b.png",Color.WHITE, 0, 5);
        board[0][6] = new Knight("cavalier_b.png",Color.WHITE, 0, 6);
        board[0][7] = new Rook("tour_b.png",Color.WHITE, 0, 7);
        for (int j = 0; j < 8; j++) {
            board[1][j] = new Pawn("pion_b.png",Color.WHITE, j, 1);
        }

        // Initialiser les pièces noires
        board[7][0] = new Rook("tour_n.png",Color.BLACK, 7, 0);
        board[7][1] = new Knight("cavalier_n.png",Color.BLACK, 7, 1);
        board[7][2] = new Bishop("fou_n.png",Color.BLACK, 7, 2);
        board[7][3] = new Queen("reine_n.png",Color.BLACK, 7, 3);
        board[7][4] = new King("roi_n.png",Color.BLACK, 7, 4);
        board[7][5] = new Bishop("fou_n.png",Color.BLACK, 7, 5);
        board[7][6] = new Knight("cavalier_n.png",Color.BLACK, 7, 6);
        board[7][7] = new Rook("tour_n.png",Color.BLACK, 7, 7);
        for (int j = 0; j < 8; j++) {
            board[6][j] = new Pawn("pion_n.png",Color.BLACK, 6, j);
        }
    }

   public void resetHighlight() {
        for(int i = 0; i< 8; i++)
            for(int j = 0; j< 8; j++)
                highLightCase[i][j]=false;
    }
    public ChessPiece getPieceAt(int row, int col) {
        return board[row][col];
    }

    public void setPieceAt(int row, int col, ChessPiece piece) {
        board[row][col] = piece;
    }

    public void removePieceAt(int row, int col) {
        ChessPiece pieceToRemove = getPieceAt(row, col);
        if (pieceToRemove == null) {
            throw new IllegalArgumentException("No piece at " + row + " " + col);
        }
        board[row][col] = null ;
    }

    public boolean isPieceAt(int row, int col) {
        return board[row][col] != null;
    }
    public int getNbOfWhitePiece() {
        ArrayList<ChessPiece> pieces = new ArrayList<>();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPiece piece = board[row][col];
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
                ChessPiece piece = board[row][col];
                if (piece != null && !piece.isWhite()) {
                    pieces.add(piece);
                }
            }
        }
        return pieces.size();
    }

    public boolean isOccupied(int row, int col) {
        return board[row][col] != null;
    }

    public ArrayList<ChessPiece> getPiecesByColor(Color color) {
        ArrayList<ChessPiece> piecesList = new ArrayList<>();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPiece piece = board[row][col];
                if (piece != null && piece.getColor() == color) {
                    piecesList.add(piece);
                }
            }
        }
        return piecesList;
    }

    public void move(int startRow, int startCol, int endRow, int endCol)
    {
        ChessPiece startPiece = getPieceAt(startRow, startCol);
        ChessPiece endPiece = getPieceAt(endRow, endCol);
        if(isOccupied(endRow,endRow))
        {
            endPiece.setCaptured();
            removePieceAt(endRow,endCol);
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

        if (startPiece instanceof Pawn && (endRow == 0 || endRow == 7)) {
            promotePawn((Pawn) startPiece, endRow, endCol);
        }

        if(startPiece instanceof Pawn)
        {
            return captureEnPassant(startPiece, startRow, startCol, endRow, endCol);
        }

        if (startPiece instanceof King && Math.abs(startRow - endRow) == 2) {
            // The move is a castling move
           if(((King) startPiece).castle(startRow,startCol,endRow,endCol,board))
           {
               // Effectue le roque
                int dir = endCol > startCol ? 1 : -1;
                this.movePiece(startRow, startCol, endRow, endCol);
                this.movePiece(startRow, endCol + dir, startRow, endCol - dir);
           }
        }

        // on effectue le déplacement
        if (startPiece.canMoveTo(endCol, endRow, board)) {
            move(startRow, startCol, endRow, endCol);
            return true;
        }
        return true;
    }



    public void promotePawn(Pawn pawn, int row, int col) {
        Color color = pawn.getColor();
        // Replace the pawn with a queen for now
        if(pawn.isWhite())
        {
            Queen queen = new Queen("reine_b.png",color, row, col);
            board[row][col] = queen;
        }
        else{
            Queen queen = new Queen("reine_n.png",color, row, col);
            board[row][col] = queen;
        }
    }

    public boolean captureEnPassant(ChessPiece piece, int startRow, int startCol, int endRow, int endCol) {
        // Vérifie si la pièce est un pion et si le mouvement est un en passant valide
        if (!(piece instanceof Pawn) || endRow == startRow || Math.abs(endCol - startCol) != 1) {
            return false;
        }

        Pawn pawn = (Pawn) piece;
        Color color = pawn.getColor();

        // Vérifie si le mouvement est en direction de l'adversaire
        int dir = color == Color.WHITE ? -1 : 1;
        if (endRow != startRow + dir) {
            return false;
        }

        // Vérifie si le pion attaque un pion adverse en passant
        ChessPiece targetPiece = this.getPieceAt(endRow - dir, endCol);
        if (!(targetPiece instanceof Pawn) || targetPiece.getColor() == color || !((Pawn) targetPiece).getJustMovedDouble()) {
            return false;
        }

        // Effectue le mouvement et enlève le pion adverse en passant
        this.movePiece(startRow, startCol, endRow, endCol);
        this.removePieceAt(endRow - dir, endCol);
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
