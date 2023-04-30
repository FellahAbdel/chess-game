package variant.model;

import java.awt.*;
import java.util.ArrayList;

public class Noctambule extends ChessPiece {

    public Noctambule( String imageName, Color color, int row, int col) {
        super("Noctambule", imageName, color, row, col);
    }

    public Noctambule(ChessPiece piece) {
        super(piece.getPieceName(), piece.getImageName(), piece.getColor(), piece.getRow(), piece.getCol());

    }

    @Override
    public boolean isValidMove(int startYRow, int startXCol, int endYRow, int endXCol, ChessPiece[][] board) {
        // Vérifier si le déplacement est un prolongement du mouvement du cavalier en ligne droite
        int deltaY = Math.abs(endYRow - startYRow);
        int deltaX = Math.abs(endXCol - startXCol);

        return (deltaY == 2 && deltaX == 1) || (deltaY == 1 && deltaX == 2) ||
                ((deltaY > 2 && deltaX == 0) || (deltaX > 2 && deltaY == 0)) &&
                        isPathClear(startYRow, startXCol, endYRow, endXCol, board);
    }

    @Override
    public ArrayList<int[]> PossiblesMoves(int startYRow, int startXCol, ChessPiece[][] board) {
        ArrayList<int[]> possibleMoves = new ArrayList<>();

        // Ajouter les cases diagonales accessibles pour prolonger le mouvement du cavalier
        addDiagonalMoves(startYRow, startXCol, board, possibleMoves);

        // Ajouter les cases horizontales et verticales accessibles pour prolonger le mouvement du cavalier
        addHorizontalAndVerticalMoves(startYRow, startXCol, board, possibleMoves);

        return possibleMoves;
    }

    @Override
    public String getSymbol() {
        return "N";
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




