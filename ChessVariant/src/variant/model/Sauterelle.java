package variant.model;

import java.awt.*;
import java.util.ArrayList;

/**
 * Classe représentant une pièce d'échecs de type Sauterelle.
 * Elle étend la classe ChessPiece et redéfinit la méthode possiblesMoves() pour calculer les déplacements possibles
 * pour une Sauterelle.
 */
public class Sauterelle extends ChessPiece {
    /**
     * Constructeur pour créer une nouvelle pièce de type Noctambule.
     *
     * @param color                 la couleur de la pièce
     * @param row                   la ligne sur laquelle la pièce se trouve
     * @param col                   la colonne sur laquelle la pièce se trouve
     * @param whitePiecesAtBottom   true si c'est au tour des blancs de jouer, false sinon
     */
    public Sauterelle(Color color, int row, int col,boolean whitePiecesAtBottom) {
        super("Sauterelle", color, row, col,whitePiecesAtBottom);
    }

    /**
     * Constructeur pour créer une nouvelle Noctambule en copiant une autre pièce d'échecs.
     *
     * @param piece La pièce d'échecs à copier
     */
    public Sauterelle(ChessPiece piece) {
        super(piece.getPieceName(), piece.getColor(), piece.getRow(), piece.getCol(), piece.getWhitePiecesAtBottom());

    }

    /**
     * Vérifie si le mouvement de la sauterelle est valide.
     *
     * @param startYRow ligne de la case de départ
     * @param startXCol colonne de la case de départ
     * @param endYRow ligne de la case de destination
     * @param endXCol colonne de la case de destination
     * @param board plateau de jeu
     * @return true si le mouvement est valide, false sinon
     */
    public boolean isValidMove(int startYRow, int startXCol, int endYRow, int endXCol, ChessPiece[][] board) {
        // Vérifier si la case de destination est sur la même ligne, colonne ou diagonale que la case de départ
        if (startYRow == endYRow || startXCol == endXCol || Math.abs(startYRow - endYRow) == Math.abs(startXCol - endXCol)) {
            // Vérifier si la case de destination est vide ou occupée par une pièce adverse
            if (isValidCaptureOrEmpty(endYRow, endXCol, board)) {
                // Vérifier si la trajectoire est dégagée (pas de pièce entre la case de départ et d'arrivée)
                if (isPathClear(startYRow, startXCol, endYRow, endXCol, board)) {
                    // Vérifier si la case de destination est accessible en sautant par-dessus une autre pièce
                    return isJumpingOver(startYRow, startXCol, endYRow, endXCol, board);
                }
            }
        }
        return false;
    }



    /**
     * Renvoie une liste de tous les mouvements possibles pour la sauterelle.
     *
     * @param startYRow ligne de la case de départ
     * @param startXCol colonne de la case de départ
     * @param board plateau de jeu
     * @return une liste de tableaux d'entiers représentant les cases accessibles par la sauterelle
     */
    @Override
    public ArrayList<int[]> possiblesMoves(int startYRow, int startXCol, ChessPiece[][] board) {
        ArrayList<int[]> possibleMoves = new ArrayList<>();

        // Parcourir toutes les cases de la même ligne, colonne ou diagonale que la case de départ
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 10; j++) {
                if (isValidMove(startYRow, startXCol, i, j, board)) {
                    possibleMoves.add(new int[]{i, j});
                }
            }
        }

        return possibleMoves;
    }

    /**
     * Vérifier si la case de destination est accessible en sautant par-dessus une autre pièce.
     *
     * @param startYRow ligne de la case de départ
     * @param startXCol colonne de la case de départ
     * @param endYRow ligne de la case de destination
     * @param endXCol colonne de la case de destination
     * @param board plateau de jeu
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
     * Vérifie si la position donnée est sur le plateau.
     *
     * @param row la ligne a vérifié.
     * @param col la colonne a vérifié.
     * @return true si la position donnée est sur le plateau, false sinon.
     */
    public boolean isOnBoard(int row, int col) {
        return (row >= 0 && col >= 0 && row < 8 && col < 10);
    }

    /**
     * Vérifie si la position donnée est vide ou contient une pièce de couleur différente.
     *
     * @param row   la ligne a vérifié.
     * @param col   la colonne a vérifié.
     * @param board l'état actuel de l'échiquier.
     * @return true si la position donnée est vide ou contient une pièce de couleur différente, false sinon.
     */
    public boolean isValidCaptureOrEmpty(int row, int col, ChessPiece[][] board) {
        return (board[row][col] == null || board[row][col].getColor() != this.getColor());
    }

    /**
     * Vérifie si le chemin entre deux cases sur un plateau d'échecs est libre.
     *
     * @param startYRow la rangée (numérotée de haut en bas) de la case de départ.
     * @param startXCol la colonne (numérotée de gauche à droite) de la case de départ.
     * @param endYRow la rangée (numérotée de haut en bas) de la case d'arrivée.
     * @param endXCol la colonne (numérotée de gauche à droite) de la case d'arrivée.
     * @param board un tableau de pièces d'échecs représentant l'état actuel du plateau.
     * @return true si le chemin entre les cases est libre, false sinon.
     */
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

