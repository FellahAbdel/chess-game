package variant.model;

import java.awt.*;
import java.util.ArrayList;

public class Knight extends ChessPiece {
    public Knight(String imageName,Color color, int row, int col) {
        super("Knight", imageName, color, row, col);
    }

    public Knight(ChessPiece piece) {
        super(piece.getPieceName(), piece.getImageName(), piece.getColor(), piece.getRow(), piece.getCol());

    }

    @Override
    public boolean isValidMove(int startYRow, int startXCol, int endYRow, int endXCol, ChessPiece[][] board) {
        // Vérifie si le déplacement forme un L
        int deltaX = Math.abs(endXCol - startXCol);
        int deltaY = Math.abs(endYRow - startYRow);
        if ((deltaX == 1 && deltaY == 2) || (deltaX == 2 && deltaY == 1)) {
            // Vérifie si la case de destination est vide ou occupée par une pièce de la couleur opposée
            if (board[endYRow][endXCol] == null || !board[endYRow][endXCol].getColor().equals(getColor())) {
                return true;
            }
        }

        return false;
    }

    public ArrayList<int[]> PossiblesMoves(int startYRow, int startXCol, ChessPiece[][] board) {
        ArrayList<int[]> moves = new ArrayList<>();
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



    public String getSymbol(){
        return (getColor() == Color.WHITE ? "B" : "N");
    }
}