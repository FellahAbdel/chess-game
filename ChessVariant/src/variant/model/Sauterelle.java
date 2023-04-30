package variant.model;

import java.awt.*;
import java.util.ArrayList;

public class Sauterelle extends ChessPiece {
    public Sauterelle(Color color, int row, int col,boolean isWhiteTurn) {
        super("Sauterelle", color, row, col,isWhiteTurn);
    }

    public Sauterelle(ChessPiece piece) {
        super(piece.getPieceName(), piece.getColor(), piece.getRow(), piece.getCol(), piece.getWhiteTurn());

    }

    public boolean isValidMove(int startYRow, int startXCol, int endYRow, int endXCol, ChessPiece[][] board) {
        // Vérifier si la case de destination est sur la même ligne, colonne ou diagonale que la case de départ
        if (startYRow == endYRow || startXCol == endXCol || Math.abs(startYRow - endYRow) == Math.abs(startXCol - endXCol)) {
            // Vérifier si la case de destination est vide ou occupée par une pièce adverse
            if (isValidCaptureOrEmpty(endYRow, endXCol, board)) {
                // Vérifier si la trajectoire est dégagée (pas de pièce entre la case de départ et d'arrivée)
                if (isPathClear(startYRow, startXCol, endYRow, endXCol, board)) {
                    // Vérifier si la case de destination est accessible en sautant par-dessus une autre pièce
                    if (isJumpingOver(startYRow, startXCol, endYRow, endXCol, board)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public ArrayList<int[]> PossiblesMoves(int startYRow, int startXCol, ChessPiece[][] board) {
        ArrayList<int[]> possibleMoves = new ArrayList<>();

        // Parcourir toutes les cases de la même ligne, colonne ou diagonale que la case de départ
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 12; j++) {
                if (isValidMove(startYRow, startXCol, i, j, board)) {
                    possibleMoves.add(new int[]{i, j});
                }
            }
        }

        return possibleMoves;
    }

    /**
     * Vérifier si la case de destination est accessible en sautant par-dessus une autre pièce
     */
    private boolean isJumpingOver(int startYRow, int startXCol, int endYRow, int endXCol, ChessPiece[][] board) {
        // Vérifier si la case de destination est adjacente à la case de départ
        if (Math.abs(startYRow - endYRow) <= 1 && Math.abs(startXCol - endXCol) <= 1) {
            return true;
        }

        // Vérifier si la trajectoire est horizontale, verticale ou diagonale
        boolean isHorizontal = (startYRow == endYRow);
        boolean isVertical = (startXCol == endXCol);
        boolean isDiagonal = (Math.abs(startYRow - endYRow) == Math.abs(startXCol - endXCol));

        if (isHorizontal || isVertical || isDiagonal) {
            int dy = Integer.signum(endYRow - startYRow);
            int dx = Integer.signum(endXCol - startXCol);

            int currentYRow = startYRow + dy;
            int currentXCol = startXCol + dx;

            // Parcourirla trajectoire de la sauterelle en sautant par-dessus les autres pièces
            while (currentYRow != endYRow || currentXCol != endXCol) {
                // Vérifier si la case courante est sur le plateau
                if (!isOnBoard(currentYRow, currentXCol)) {
                    return false;
                }
                // Vérifier si la case courante contient une pièce
                if (board[currentYRow][currentXCol] != null) {
                    // Sauter par-dessus la pièce
                    currentYRow += dy;
                    currentXCol += dx;

                    // Vérifier si la case après le saut est sur le plateau
                    if (!isOnBoard(currentYRow, currentXCol)) {
                        return false;
                    }

                    // Vérifier si la case après le saut est la case de destination
                    if (currentYRow == endYRow && currentXCol == endXCol) {
                        return true;
                    }

                    // Vérifier si la case après le saut contient une pièce
                    if (board[currentYRow][currentXCol] != null) {
                        return false;
                    }
                }

                currentYRow += dy;
                currentXCol += dx;
            }

            // Si toutes les cases entre la case de départ et la case de destination sont vides
            return true;
        }

        // Si la trajectoire n'est pas valide
        return false;
    }

    /**
     * Vérifie si la position donnée est sur le plateau
     */
    public boolean isOnBoard(int row, int col) {
        return (row >= 0 && col >= 0 && row < 8 && col < 12);
    }

    /**
     * Vérifie si la position donnée est vide ou contient une pièce de couleur différente
     */
    public boolean isValidCaptureOrEmpty(int row, int col, ChessPiece[][] board) {
        return (board[row][col] == null || board[row][col].getColor() != this.getColor());
    }

    public static boolean isPathClear(int startYRow, int startXCol, int endYRow, int endXCol, ChessPiece[][] board) {
        // Calculate the direction of the move
        int deltaY = Integer.compare(endYRow, startYRow);
        int deltaX = Integer.compare(endXCol, startXCol);

        // Check all squares between the start and end squares, excluding the start and end squares
        for (int i = startYRow + deltaY, j = startXCol + deltaX; i != endYRow || j != endXCol; i += deltaY, j += deltaX) {
            if (board[i][j] != null) {
                return false; // There is an obstacle on the path
            }
        }

        return true; // The path is clear
    }
}

