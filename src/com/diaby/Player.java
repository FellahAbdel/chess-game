package com.diaby;
import java.awt.*;
import java.util.ArrayList;

public class Player {
    private String name;
    private Color color;
    private ArrayList<ChessPiece> pieces;

    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
        this.pieces = new ArrayList<ChessPiece>();
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public ArrayList<ChessPiece> getPieces() {
        return pieces;
    }

    public void addPiece(ChessPiece piece) {
        pieces.add(piece);
    }

    public void removePiece(ChessPiece piece) {
        pieces.remove(piece);
    }

    public boolean makeMove(ChessBoard board) {
        // Vérifie si la pièce appartient au joueur et si le mouvement est légal
        ChessPiece piece = move.getPiece();
        if (piece.getColor() != this.color || !piece.canMoveTo(board, move.getDestination())) {
            return false;
        }
        // Vérifie si le mouvement met le roi du joueur en échec
        Board testBoard = new Board(board); // crée une copie du plateau
        testBoard.movePiece(move); // effectue le mouvement sur la copie
        if (testBoard.isKingInCheck(this.color)) {
            return false;
        }
        // Effectue le mouvement sur le plateau original
        board.movePiece(move);
        return true;

    }
}

