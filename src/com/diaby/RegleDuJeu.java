package com.diaby;
import com.diaby.model.*;
public class RegleDuJeu {

    public static boolean estPat(boolean isWhite, ChessBoard board) {
        King king = board.getKing(isWhite);
        // Vérifie si le roi est en échec
        if (king.isInCheck(king.isWhite(), board.getTileBoard())) {
            return false;
        }

        // Vérifie si le joueur a des mouvements possibles
        for (ChessPiece p : board.getPiecesByColor(king.getColor())) {
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

    public static boolean estEchecEtMat(boolean isWhite, ChessPiece[][] board, ChessBoard chess) {
        // Vérifie si le joueur est en échec et mat
        King roi = chess.getKing(isWhite);

        return roi.isInCheck(roi.isWhite(), board) && roi.possiblesMoves(roi.getRow(), roi.getCol(), board).isEmpty();
    }

}
