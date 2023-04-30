package variant.model;

import java.awt.*;
import java.util.ArrayList;

public class Princesse extends ChessPiece{

    public Princesse(Color color, int row, int col,boolean isWhiteTurn) {
        super("Princesse", color, row, col, isWhiteTurn);
    }

    public Princesse(ChessPiece piece) {
        super(piece.getPieceName(), piece.getColor(), piece.getRow(), piece.getCol(), piece.getWhiteTurn());

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

}
