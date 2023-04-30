package com.diaby.controller;
import com.diaby.model.*;
import com.diaby.view.ChessBoardView;

import java.util.ArrayList;

public class RegleDuJeu {

    public static boolean isPat(boolean isWhite, ChessBoard board) {
        King king = board.getKing(isWhite);
        // Vérifie si le roi est en échec
        if (king.isInCheck(king.isWhite(), board.getTileBoard())) {
            return false;
        }

        // Vérifie si le joueur a des mouvements possibles
        ArrayList<ChessPiece> pieceList = board.getPiecesByColor(king.getColor());
        for (ChessPiece p : pieceList ) {
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    if (p.canMove(row, col, board.getTileBoard())) {
                        // Si un mouvement est possible, ce n'est pas un pat
                        return false;
                    }
                }
            }
        }

        // Si aucun mouvement n'est possible, il s'agit d'un pat
        return true;
    }

    public static boolean isCheckMate(boolean isWhite, ChessPiece[][] board, ChessBoard chess) {
        // Vérifie si le joueur est en échec et mat
        King roi = chess.getKing(isWhite);

        return roi.isInCheck(roi.isWhite(), board) && roi.possiblesMoves(roi.getRow(), roi.getCol(), board).isEmpty();
    }

}
