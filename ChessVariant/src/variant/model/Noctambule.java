package variant.model;

import java.awt.*;
import java.util.ArrayList;

/**
 * Classe représentant une pièce d'échecs de type Noctambule.
 * Elle étend la classe ChessPiece et redéfinit la méthode possiblesMoves() pour calculer les déplacements possibles
 * pour une Noctambule.
 */
public class Noctambule extends ChessPiece {

    /**
     * Constructeur pour créer une nouvelle pièce de type Noctambule.
     *
     * @param color                 la couleur de la pièce
     * @param row                   la ligne sur laquelle la pièce se trouve
     * @param col                   la colonne sur laquelle la pièce se trouve
     * @param whitePiecesAtBottom   true si c'est au tour des blancs de jouer, false sinon
     */
    public Noctambule(Color color, int row, int col,boolean whitePiecesAtBottom) {
        super("Noctambule", color, row, col, whitePiecesAtBottom);
    }

    /**
     * Constructeur pour créer une nouvelle Noctambule en copiant une autre pièce d'échecs.
     *
     * @param piece La pièce d'échecs à copier
     */
    public Noctambule(ChessPiece piece) {
        super(piece.getPieceName(), piece.getColor(), piece.getRow(), piece.getCol(),piece.getWhitePiecesAtBottom());

    }

    /**
     * Retourne une liste d'entiers qui représente les mouvements possibles pour cette pièce en fonction de l'état actuel de l'échiquier.
     *
     * @param startYRow la ligne de départ de la pièce.
     * @param startXCol la colonne de départ de la pièce.
     * @param board     l'état actuel de l'échiquier.
     * @return une liste d'entiers qui représente les mouvements possibles pour cette pièce.
     */
    @Override
    public ArrayList<int[]> possiblesMoves(int startYRow, int startXCol, ChessPiece[][] board) {
        ArrayList<int[]> possibleMoves = new ArrayList<>();

        // Ajouter les cases diagonales accessibles pour prolonger le mouvement du cavalier
        addDiagonalMoves(startYRow, startXCol, board, possibleMoves);

        // Ajouter les cases horizontales et verticales accessibles pour prolonger le mouvement du cavalier
        addHorizontalAndVerticalMoves(startYRow, startXCol, board, possibleMoves);

        return possibleMoves;
    }

    /**
     * Ajoute les cases diagonales accessibles pour prolonger le mouvement du cavalier.
     *
     * @param startYRow     la ligne de départ de la pièce.
     * @param startXCol     la colonne de départ de la pièce.
     * @param board         l'état actuel de l'échiquier.
     * @param possibleMoves une liste d'entiers qui représente les mouvements possibles pour cette pièce.
     */
    private void addDiagonalMoves(int startYRow, int startXCol, ChessPiece[][] board, ArrayList<int[]> possibleMoves) {
        int[] deltaYValues = {-2, -1, 1, 2};
        int[] deltaXValues = {-2, -1, 1, 2};

        for (int deltaY : deltaYValues) {
            for (int deltaX : deltaXValues) {
                if (Math.abs(deltaY) != Math.abs(deltaX)) { // Seulement les diagonales
                    int endYRow = startYRow + deltaY;
                    int endXCol = startXCol + deltaX;
                    if (isOnBoard(endYRow, endXCol) && isValidCaptureOrEmpty(endYRow, endXCol, board)) {
                        possibleMoves.add(new int[]{endYRow, endXCol});
                    }
                }
            }
        }
    }

    /**
     * Ajoute les cases horizontales et verticales accessibles pour prolonger le mouvement du cavalier.
     *
     * @param startYRow     la ligne de départ de la pièce.
     * @param startXCol     la colonne de départ de la pièce.
     * @param board         l'état actuel de l'échiquier.
     * @param possibleMoves une liste d'entiers qui représente les mouvements possibles pour cette pièce.
     */
    private void addHorizontalAndVerticalMoves(int startYRow, int startXCol, ChessPiece[][] board, ArrayList<int[]> possibleMoves) {
        int[] deltaValues = {-1, 1};

        for (int delta : deltaValues) {
            // Horizontal
            int endYRow = startYRow;
            int endXCol = startXCol + delta;
            while (isOnBoard(endYRow, endXCol) && isValidCaptureOrEmpty(endYRow, endXCol, board)) {
                possibleMoves.add(new int[]{endYRow, endXCol});
                if (board[endYRow][endXCol] != null) {
                    break;
                }
                endXCol += delta;
            }

            // Vertical
            endYRow = startYRow + delta;
            endXCol = startXCol;
            while (isOnBoard(endYRow, endXCol) && isValidCaptureOrEmpty(endYRow, endXCol, board)) {
                possibleMoves.add(new int[]{endYRow, endXCol});
                if (board[endYRow][endXCol] != null) {
                    break;
                }
                endYRow += delta;
            }
        }
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

}




