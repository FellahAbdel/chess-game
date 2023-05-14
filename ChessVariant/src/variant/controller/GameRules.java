package variant.controller;

import variant.model.ChessBoard;
import variant.model.ChessPiece;
import variant.model.King;

/**
 * Classe en charge des méthodes permettant d'arrêter le jeu.
 */
public class GameRules {

    /**
     * Renvoie true si c'est un pat, false sinon
     *
     * @param isWhite   true si c'est un pion blanc et false sinon
     * @param board     L'échiquier actuel représenté par une matrice de ChessPiece
     * @return true si c'est un pat et false sinon
     */
    public static boolean draw(boolean isWhite, ChessBoard board) {
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

    /**
     * Renvoie true si c'est un échec et mat, false sinon
     *
     * @param isWhite   true si c'est un pion blanc et false sinon
     * @param board     L'échiquier actuel représenté par une matrice de ChessPiece
     * @param chess     instance de la classe ChessBoard
     * @return true si c'est un échec et mat, false sinon
     */
    public static boolean checkMate(boolean isWhite, ChessPiece[][] board, ChessBoard chess) {
        // Vérifie si le joueur est en échec et mat
        King roi = chess.getKing(isWhite);

        return roi.isInCheck(roi.isWhite(), board) && roi.possiblesMoves(roi.getRow(), roi.getCol(), board).isEmpty();
    }

}
