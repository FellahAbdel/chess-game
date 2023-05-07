package variant.model;

import java.awt.*;
import java.util.ArrayList;

public class Pawn extends ChessPiece {

    public Pawn(Color color, int row, int col,boolean isWhiteTurn) {
        super("Pawn", color, row, col, isWhiteTurn);
    }

    public Pawn(ChessPiece piece) {
        super(piece.getPieceName(), piece.getColor(), piece.getRow(), piece.getCol(), piece.getWhitePiecesAtBottom());
    }

    public void promotePawn(Pawn pawn, int row, int col, String pieceType, ChessPiece[][] board) {
        Color color = pawn.getColor();
        ChessPiece newPiece;
        switch (pieceType) {
            case "Queen":
                newPiece = new Queen(color, row, col,pawn.getWhitePiecesAtBottom());
                break;
            case "Rook":
                newPiece = new Rook(color, row, col,pawn.getWhitePiecesAtBottom());
                break;
            case "Bishop":
                newPiece = new Bishop(color, row, col,pawn.getWhitePiecesAtBottom());
                break;
            case "Knight":
                newPiece = new Knight(color, row, col,pawn.getWhitePiecesAtBottom());
                break;
            case "Imperatrice":
                newPiece = new Imperatrice(color,row,col,pawn.getWhitePiecesAtBottom());
                break;
            case "Princesse":
                newPiece = new Princesse(color,row,col,pawn.getWhitePiecesAtBottom());
                break;
            case "Noctambule":
                newPiece = new Noctambule(color,row,col,pawn.getWhitePiecesAtBottom());
                break;
            case "Sauterelle":
                newPiece = new Sauterelle(color,row,col,pawn.getWhitePiecesAtBottom());
                break;
            default:
                throw new IllegalArgumentException("Invalid piece type");
        }
        board[row][col] = newPiece;
    }

    public ArrayList<int[]> possiblesMoves(int startXCol, int startYRow, ChessPiece[][] board) {
        ArrayList<int[]> moves = new ArrayList<>();

        return possiblesMovesTurn(startXCol, startYRow, board, moves, !this.getWhitePiecesAtBottom());

    }

    private void descent(int startXCol, int startYRow, ChessPiece[][] board, ArrayList<int[]> moves, Boolean isWhite) {
        if (startXCol < 7 && board[startXCol + 1][startYRow] == null) {
            moves.add(new int[]{startXCol + 1, startYRow});
        }
        // Check two steps forward
        if (startXCol == 1 && board[startXCol + 1][startYRow] == null && board[startXCol + 2][startYRow] == null) {
            moves.add(new int[]{startXCol + 2, startYRow});
        }
        // Check diagonal captures
        if (startXCol < 8 && startYRow > 0 && board[startXCol + 1][startYRow - 1] != null && board[startXCol + 1][startYRow - 1].isWhite() == isWhite) {
            moves.add(new int[]{startXCol + 1, startYRow - 1});
        }
        if (startXCol < 8 && startYRow < 11 && board[startXCol + 1][startYRow + 1] != null && board[startXCol + 1][startYRow + 1].isWhite() == isWhite) {
            moves.add(new int[]{startXCol + 1, startYRow + 1});
        }

    }

    private void climb(int startXCol, int startYRow, ChessPiece[][] board, ArrayList<int[]> moves, Boolean isWhite) {
        if (startXCol > 0 && board[startXCol - 1][startYRow] == null) {
            moves.add(new int[]{startXCol - 1, startYRow});
        }
        // Check two steps forward
        if (startXCol == 6 && board[startXCol - 1][startYRow] == null && board[startXCol - 2][startYRow] == null) {
            moves.add(new int[]{startXCol - 2, startYRow});
        }
        // Check diagonal captures
        if (startXCol > 0 && startYRow > 0 && board[startXCol - 1][startYRow - 1] != null && board[startXCol - 1][startYRow - 1].isWhite() == isWhite) {
            moves.add(new int[]{startXCol - 1, startYRow - 1});
        }
        if (startXCol > 0 && startYRow < 11 && board[startXCol - 1][startYRow + 1] != null && board[startXCol - 1][startYRow + 1].isWhite() == isWhite) {
            moves.add(new int[]{startXCol - 1, startYRow + 1});
        }

    }

    private ArrayList<int[]> possiblesMovesTurn(int startXCol, int startYRow, ChessPiece[][] board,
                                                ArrayList<int[]> moves, Boolean whiteTurn) {
        if (whiteTurn) {
            if (this.isWhite()) {  // Descente blanche.
                descent(startXCol, startYRow, board, moves, false);
            } else { // Monté Noir.
                climb(startXCol, startYRow, board, moves, true);
            }
        } else {
            if (!this.isWhite()) {  // Descente Noire
                descent(startXCol, startYRow, board, moves, true);
            } else {  // Montee blanche.
                climb(startXCol, startYRow, board, moves, false);
            }
        }

        return moves;
    }

}