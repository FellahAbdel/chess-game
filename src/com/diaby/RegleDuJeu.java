package com.diaby;
import com.diaby.model.*;
public class RegleDuJeu {

    public static boolean estFinDeJeu( ChessBoard board) {
        // Vérifie si l'un des joueurs a perdu toutes ses pièces ou si le jeu est bloqué
        return board.getNbOfWhitePiece() == 0
                || board.getNbOfBlackPiece() == 0 ;
    }

    public static boolean estPat(King king, ChessBoard board) {
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

    public static boolean estEchecEtMat(ChessPiece piece, ChessBoard board) {
        // Vérifie si le joueur est en échec et mat
        King roi = board.getKing(piece.isWhite());

        if (!roi.isInCheck(roi.isWhite(), board.getTileBoard())) {
            return false;
        }

        int row , col;
        for (int ligne = 0; ligne < 8; ligne++) {
            for (int colonne = 0; colonne < 8; colonne++) {
                ChessPiece pieceCourante = board.getPieceAt(ligne, colonne);

                if (pieceCourante != null && pieceCourante.getColor() == piece.getColor()) {
                    for (int nouvelleLigne = 0; nouvelleLigne < 8; nouvelleLigne++) {
                        for (int nouvelleColonne = 0; nouvelleColonne < 8; nouvelleColonne++) {
                            row = pieceCourante.getRow();
                            col= pieceCourante.getCol();
                            if (pieceCourante.isValidMove(row,col, nouvelleLigne, nouvelleColonne, board.getTileBoard())) {
                                return false;
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

}
