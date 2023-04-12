package piece;

import java.util.ArrayList;
import java.util.List;

public class Board {



    public boolean isInCheckmate(Color color) {
        // Vérifier d'abord si le joueur est en échec
        if (!isInCheck(color)) {
            return false;
        }

        // Parcourir toutes les pièces du joueur en question
        for (Piece piece : pieces) {
            if (piece.getColor() == color) {
                // Vérifier si la pièce a un coup valide
                for (Position position : piece.getPossibleMoves(this)) {
                    Board clone = clone();
                    clone.movePiece(piece.getPosition(), position);
                    if (!clone.isInCheck(color)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean isDraw() {
        // Vérifier s'il y a eu une répétition de position
        if (isThreeFoldRepetition()) {
            return true;
        }

        // Vérifier s'il n'y a plus suffisamment de matériel pour qu'un joueur puisse gagner
        if (isInsufficientMaterial()) {
            return true;
        }

        return false;
    }

    private boolean isThreeFoldRepetition() {
        int repetitionCount = 0;
        for (int i = 0; i < moveHistory.size(); i++) {
            BoardPosition position = moveHistory.get(i);
            if (Collections.frequency(moveHistory, position) == 3) {
                repetitionCount++;
                if (repetitionCount == 2) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isInsufficientMaterial() {
        int totalPieces = 0;
        for (Piece piece : pieces) {
            if (piece instanceof Pawn || piece instanceof Rook || piece instanceof Queen) {
                return false; // Il y a suffisamment de matériel pour gagner
            }
            if (piece instanceof Bishop || piece instanceof Knight) {
                totalPieces++;
            }
        }
        return totalPieces <= 1; // Il n'y a pas assez de matériel pour gagner
    }

}



    public static boolean estPat(Piece piece, PlateauDeJeu plateau) {
        // Vérifie si le roi est en échec
        if (estEchec(piece, plateau)) {
            return false;
        }

        // Vérifie si le joueur a des mouvements possibles
        for (Piece p : plateau.getPiecesByColor(piece.getColor())) {
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    if (p.peutDeplacer(row, col, plateau)) {
                        // Si un mouvement est possible, ce n'est pas un pat
                        return false;
                    }
                }
            }
        }

        // Si aucun mouvement n'est possible, il s'agit d'un pat
        return true;
    }

//    public boolean isValidMove(int startX, int startY, int endX, int endY, ChessPiece[][] board) {
//        // Vérifie si le déplacement se fait d'une case dans n'importe quelle direction
//        int deltaX = Math.abs(endX - startX);
//        int deltaY = Math.abs(endY - startY);
//        if (deltaX <= 1 && deltaY <= 1)
//        {
//            // Vérifie si la case de destination est vide ou occupée par une pièce de la couleur opposée
//            if (board[endY][endX] == null || !board[endY][endX].getColor().equals(getColor())) {
//                return true;
//            }
//        }
//
//        return false;
//    }
