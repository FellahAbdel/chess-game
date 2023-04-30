package variant.model;

import java.awt.*;
import java.util.ArrayList;

public class Princesse extends ChessPiece{

    public Princesse( String imageName, Color color, int row, int col) {
        super("Princesse", imageName, color, row, col);
    }

    public Princesse(ChessPiece piece) {
        super(piece.getPieceName(), piece.getImageName(), piece.getColor(), piece.getRow(), piece.getCol());

    }

    @Override
    public boolean isValidMove(int startYRow, int startXCol, int endYRow, int endXCol, ChessPiece[][] board) {
        int deltaY = Math.abs(endYRow - startYRow);
        int deltaX = Math.abs(endXCol - startXCol);
        if ((deltaY == 2 && deltaX == 1) || (deltaX == 2 && deltaY == 1)) {
            return isValidCaptureOrEmpty(endYRow, endXCol, board);
        } else if (deltaX == deltaY) {
            return isPathClear(startYRow, startXCol, endYRow, endXCol, board) && isValidCaptureOrEmpty(endYRow, endXCol, board);
        }
        return false;
    }
    /**
     * Vérifie si la position donnée est sur le plateau
     */
    public boolean isOnBoard(int row, int col) {
        return (row >= 0 && col >= 0 && row < 7 && col < 7);
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

    @Override
    public ArrayList<int[]> PossiblesMoves(int startYRow, int startXCol, ChessPiece[][] board) {
        ArrayList<int[]> moves = new ArrayList<int[]>();

        // Ajout des mouvements de la pièce comme un fou

        // Upper Left Diagonal
        for(int i=startYRow-1, j=startXCol-1; i>=0 && j>=0; i--, j--){
            if(board[i][j] == null) {
                moves.add(new int[]{i, j});
            }
            else if(board[i][j].isWhite() != this.isWhite()){
                moves.add(new int[]{i, j});
                break;
            }
            else {
                break;
            }
        }

        // Upper Right Diagonal
        for(int i=startYRow-1, j=startXCol+1; i>=0 && j<12; i--, j++){
            if(board[i][j] == null) {
                moves.add(new int[]{i, j});
            }
            else if(board[i][j].isWhite() != this.isWhite()){
                moves.add(new int[]{i, j});
                break;
            }
            else {
                break;
            }
        }

        // Lower Left Diagonal
        for(int i=startYRow+1, j=startXCol-1; i<8 && j>=0; i++, j--){
            if(board[i][j] == null) {
                moves.add(new int[]{i, j});
            }
            else if(board[i][j].isWhite() != this.isWhite()){
                moves.add(new int[]{i, j});
                break;
            }
            else {
                break;
            }
        }

        // Lower Right Diagonal
        for(int i=startYRow+1, j=startXCol+1; i<8 && j<12; i++, j++){
            if(board[i][j] == null) {
                moves.add(new int[]{i, j});
            }
            else if(board[i][j].isWhite() != this.isWhite()){
                moves.add(new int[]{i, j});
                break;
            }
            else {
                break;
            }
        }


        // Ajout des mouvements de la pièce comme un cavalier
        // Two steps up
        if(startYRow-2 >= 0){
            // One step to the left

            if(startXCol-1 >= 0 && (board[startYRow-2][startXCol-1] == null || board[startYRow-2][startXCol-1].isWhite() != this.isWhite())){
                moves.add(new int[]{startYRow-2, startXCol-1});
            }
            // One step to the right
            if(startXCol+1 < 12 && (board[startYRow-2][startXCol+1] == null || board[startYRow-2][startXCol+1].isWhite() != this.isWhite())){
                moves.add(new int[]{startYRow-2, startXCol+1});
            }
        }

        // Two steps down
        if(startYRow+2 < 8){
            // One step to the left
            if(startXCol-1 >= 0 && (board[startYRow+2][startXCol-1] == null || board[startYRow+2][startXCol-1].isWhite() != this.isWhite())){
                moves.add(new int[]{startYRow+2, startXCol-1});
            }
            // One step to the right
            if(startXCol+1 < 12 && (board[startYRow+2][startXCol+1] == null || board[startYRow+2][startXCol+1].isWhite() != this.isWhite())){
                moves.add(new int[]{startYRow+2, startXCol+1});
            }
        }

        // Two steps to the left
        if(startXCol-2 >= 0){
            // One step up
            if(startYRow-1 >= 0 && (board[startYRow-1][startXCol-2] == null || board[startYRow-1][startXCol-2].isWhite() != this.isWhite())){
                moves.add(new int[]{startYRow-1, startXCol-2});
            }
            // One step down
            if(startYRow+1 < 8 && (board[startYRow+1][startXCol-2] == null || board[startYRow+1][startXCol-2].isWhite() != this.isWhite())){
                moves.add(new int[]{startYRow+1, startXCol-2});
            }
        }

        // Two steps to the right
        if(startXCol+2 < 12){
            // One step up
            if(startYRow-1 >= 0 && (board[startYRow-1][startXCol+2] == null || board[startYRow-1][startXCol+2].isWhite() != this.isWhite())){
                moves.add(new int[]{startYRow-1, startXCol+2});
            }
            // One step down
            if(startYRow+1 < 8 && (board[startYRow+1][startXCol+2] == null || board[startYRow+1][startXCol+2].isWhite() != this.isWhite())){
                moves.add(new int[]{startYRow+1, startXCol+2});
            }
        }

        return moves;
    }

    @Override
    public String getSymbol() {
        return "P";
    }
}
