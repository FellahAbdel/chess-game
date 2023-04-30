package variant.model;

import java.awt.*;
import java.util.ArrayList;

public class Pawn extends ChessPiece {

    public Pawn(Color color, int row, int col,boolean isWhiteTurn) {
        super("Pawn", color, row, col, isWhiteTurn);
    }

    public Pawn(ChessPiece piece) {
        super(piece.getPieceName(), piece.getColor(), piece.getRow(), piece.getCol(), piece.getWhiteTurn());
    }

    public void promotePawn(Pawn pawn, int row, int col, String pieceType, ChessPiece[][] board) {
        Color color = pawn.getColor();
        ChessPiece newPiece;
        switch (pieceType) {
            case "Queen":
                newPiece = new Queen(color, row, col,pawn.getWhiteTurn());
                break;
            case "Rook":
                newPiece = new Rook(color, row, col,pawn.getWhiteTurn());
                break;
            case "Bishop":
                newPiece = new Bishop(color, row, col,pawn.getWhiteTurn());
                break;
            case "Knight":
                newPiece = new Knight(color, row, col,pawn.getWhiteTurn());
                break;
            case "Imperatrice":
                newPiece = new Imperatrice(color,row,col,pawn.getWhiteTurn());
                break;
            case "Princesse":
                newPiece = new Princesse(color,row,col,pawn.getWhiteTurn());
                break;
            case "Noctambule":
                newPiece = new Noctambule(color,row,col,pawn.getWhiteTurn());
                break;
            case "Sauterelle":
                newPiece = new Sauterelle(color,row,col,pawn.getWhiteTurn());
                break;
            default:
                throw new IllegalArgumentException("Invalid piece type");
        }
        board[row][col] = newPiece;
    }

    public ArrayList<int[]> PossiblesMoves(int startYRow, int startXCol, ChessPiece[][] board) {
        ArrayList<int[]> moves = new ArrayList<>();

        if (this.isWhite()) {

            // Check one step forward
            if (startYRow < 7 && board[startYRow + 1][startXCol] == null) {
                moves.add(new int[]{startYRow + 1, startXCol});
            }
            // Check two steps forward
            if (startYRow == 1 && board[startYRow + 1][startXCol] == null && board[startYRow + 2][startXCol] == null) {
                moves.add(new int[]{startYRow + 2, startXCol});
            }
            // Check diagonal captures
            if (startYRow < 8 && startXCol > 0 && board[startYRow + 1][startXCol - 1] != null && !board[startYRow + 1][startXCol - 1].isWhite()) {
                moves.add(new int[]{startYRow + 1, startXCol - 1});
            }
            if (startYRow < 8 && startXCol < 11 && board[startYRow + 1][startXCol + 1] != null && !board[startYRow + 1][startXCol + 1].isWhite()) {
                moves.add(new int[]{startYRow + 1, startXCol + 1});
            }
            // Check en passant capture
            if (startYRow == 4 && startXCol > 0 && board[startYRow][startXCol - 1] != null && !board[startYRow][startXCol - 1].isWhite()
                    && board[startYRow + 1][startXCol - 1] == null && board[startYRow][startXCol - 1] instanceof Pawn) {
                moves.add(new int[]{startYRow + 1, startXCol - 1});
            }
            if (startYRow == 4 && startXCol < 11 && board[startYRow][startXCol + 1] != null && !board[startYRow][startXCol + 1].isWhite()
                    && board[startYRow + 1][startXCol + 1] == null && board[startYRow][startXCol + 1] instanceof Pawn) {
                moves.add(new int[]{startYRow + 1, startXCol + 1});
            }
        } else {
            // Check one step forward
            if (startYRow > 0 && board[startYRow - 1][startXCol] == null) {
                moves.add(new int[]{startYRow - 1, startXCol});
            }
            // Check two steps forward
            if (startYRow == 6 && board[startYRow - 1][startXCol] == null && board[startYRow - 2][startXCol] == null) {
                moves.add(new int[]{startYRow - 2, startXCol});
            }
            // Check diagonal captures
            if (startYRow > 0 && startXCol > 0 && board[startYRow - 1][startXCol - 1] != null && board[startYRow - 1][startXCol - 1].isWhite()) {
                moves.add(new int[]{startYRow - 1, startXCol - 1});
            }
            if (startYRow > 0 && startXCol < 11 && board[startYRow - 1][startXCol + 1] != null && board[startYRow - 1][startXCol + 1].isWhite()) {
                moves.add(new int[]{startYRow - 1, startXCol + 1});
            }
            // Check en passant capture
            if (startYRow == 3 && startXCol > 0 && board[startYRow][startXCol - 1] != null && board[startYRow][startXCol - 1].isWhite()
                    && board[startYRow - 1][startXCol - 1] == null && board[startYRow][startXCol - 1] instanceof Pawn) {
                moves.add(new int[]{startYRow - 1, startXCol - 1});
            }
            if (startYRow == 3 && startXCol < 11 && board[startYRow][startXCol + 1] != null && board[startYRow][startXCol + 1].isWhite()
                    && board[startYRow - 1][startXCol + 1] == null && board[startYRow][startXCol + 1] instanceof Pawn) {
                moves.add(new int[]{startYRow - 1, startXCol + 1});
            }
        }
        return moves;
    }
}