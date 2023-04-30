package variant;

//import javax.swing.*;
//import javax.swing.text.Position;

import java.awt.*;
import java.util.ArrayList;

public class Rook extends ChessPiece {
    private boolean hasMoved; // indique si le pion a déjà été déplacé ou non

    public Rook(String imageName,Color color, int row, int col)
    {
        super("Rook", imageName,color, row, col);
        hasMoved = false;
    }

    public Rook(ChessPiece piece) {
        super(piece.getPieceName(), piece.getImageName(), piece.getColor(), piece.getRow(), piece.getCol());

    }

    public boolean getHasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
    @Override
    public boolean isValidMove(int startYRow, int startXCol, int endYRow, int endXCol, ChessPiece[][] board) {
        ArrayList<int[]> moves = PossiblesMoves(startYRow,startXCol,board);
        for(int[] move : moves)
        {
            if(endYRow == move[0] && endXCol == move[1])
            {
                return true;
            }
        }
        // Vérifie si la case de destination est vide ou occupée par une pièce de la couleur opposée
        if(board[endYRow][endXCol] == null || !board[endYRow][endXCol].getColor().equals(getColor()))
        {
            return true;
        }

        return false;

   }

    public ArrayList<int[]> PossiblesMoves(int startYRow, int startXCol, ChessPiece[][] board) {
        ArrayList<int[]> moves = new ArrayList<>();

        // Check moves to the right
        for (int i = startXCol + 1; i < 12; i++) {
            if (board[startYRow][i] == null) {
                moves.add(new int[]{startYRow, i});
            } else if (board[startYRow][i].isWhite() != this.isWhite()) {
                moves.add(new int[]{startYRow, i});
                break;
            } else {
                break;
            }
        }

        // Check moves to the left
        for (int i = startXCol - 1; i >= 0; i--) {
            if (board[startYRow][i] == null) {
                moves.add(new int[]{startYRow, i});
            } else if (board[startYRow][i].isWhite() != this.isWhite()) {
                moves.add(new int[]{startYRow, i});
                break;
            } else {
                break;
            }
        }

        // Check moves to the bottom
        for (int i = startYRow + 1; i < 8; i++) {
            if (board[i][startXCol] == null) {
                moves.add(new int[]{i, startXCol});
            } else if (board[i][startXCol].isWhite() != this.isWhite()) {
                moves.add(new int[]{i, startXCol});
                break;
            } else {
                break;
            }
        }

        // Check moves to the top
        for (int i = startYRow - 1; i >= 0; i--) {
            if (board[i][startXCol] == null) {
                moves.add(new int[]{i, startXCol});
            } else if (board[i][startXCol].isWhite() != this.isWhite()) {
                moves.add(new int[]{i, startXCol});
                break;
            } else {
                break;
            }
        }

        return moves;
    }



    public String getSymbol(){
        return (getColor() == Color.WHITE ? "B" : "N");
    }
}
