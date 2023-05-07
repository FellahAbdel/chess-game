package variant.model;

import java.awt.*;
import java.util.ArrayList;

public class Noctambule extends ChessPiece {

    public Noctambule(Color color, int row, int col,boolean isWhiteTurn) {
        super("Noctambule", color, row, col, isWhiteTurn);
    }

    public Noctambule(ChessPiece piece) {
        super(piece.getPieceName(), piece.getColor(), piece.getRow(), piece.getCol(),piece.getWhitePiecesAtBottom());

    }

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
     * Ajouter les cases diagonales accessibles pour prolonger le mouvement du cavalier
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
     * Ajouter les cases horizontales et verticales accessibles pour prolonger le mouvement du cavalier
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

}




