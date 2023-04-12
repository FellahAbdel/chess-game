//package com.diaby;
//
//public class RegleDuJeu {
//
//    public static boolean estFinDeJeu( ChessBoard board) {
//        // Vérifie si l'un des joueurs a perdu toutes ses pièces ou si le jeu est bloqué
//        return board.getNbOfWhitePiece() == 0
//                || board.getNbOfBlackPiece() == 0;
//    }
//
////    public static boolean estPat(ChessPiece piece, ChessBoard board) {
////        // Vérifie si le roi est en échec
////        if (estEchec(piece, board)) {
////            return false;
////        }
////
////        // Vérifie si le joueur a des mouvements possibles
////        for (ChessPiece p : board.getPiecesByColor(piece.getColor())) {
////            for (int row = 0; row < 8; row++) {
////                for (int col = 0; col < 8; col++) {
////                    if (p.peutDeplacer(row, col, plateau)) {
////                        // Si un mouvement est possible, ce n'est pas un pat
////                        return false;
////                    }
////                }
////            }
////        }
////
////        // Si aucun mouvement n'est possible, il s'agit d'un pat
////        return true;
////    }
//    public static boolean estEchecEtMat(ChessPiece piece, ChessBoard board) {
//        // Vérifie si le joueur est en échec et mat
//        ChessBoard roi = board.getRoi(piece.getCouleur());
//
//        if (!estEchec(roi, plateau)) {
//            return false;
//        }
//
//        for (int ligne = 0; ligne < 8; ligne++) {
//            for (int colonne = 0; colonne < 8; colonne++) {
//                ChessPiece pieceCourante = board.getPieceAt(ligne, colonne);
//
//                if (pieceCourante != null && pieceCourante.getColor() == piece.getColor()) {
//                    for (int nouvelleLigne = 0; nouvelleLigne < 8; nouvelleLigne++) {
//                        for (int nouvelleColonne = 0; nouvelleColonne < 8; nouvelleColonne++) {
//                            if (estDeplacementValide(pieceCourante, nouvelleLigne, nouvelleColonne, board)) {
//                                return false;
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//        return true;
//    }
//
//    public static boolean estEchec(ChessPiece piece, ChessBoard board) {
//        // Vérifie si le joueur est en échec
//        for (int ligne = 0; ligne < 8; ligne++) {
//            for (int colonne = 0; colonne < 8; colonne++) {
//                ChessPiece pieceCourante = board.getPieceAt(ligne, colonne);
//
//                if (pieceCourante != null && pieceCourante.getColor() != piece.getColor()) {
//                    if (pieceCourante.isValidMove(ligne, colonne, piece.getRow(), piece.getCol(), board)) {
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
//    }
//
//
//
//}
//
