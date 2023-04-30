package variant;

import java.awt.*;
import java.util.ArrayList;

public class Pawn extends ChessPiece {

    public Pawn(String imageName,Color color, int row, int col) {
        super("Pawn",imageName ,color, row, col);
    }

    public Pawn(ChessPiece piece) {
        super(piece.getPieceName(), piece.getImageName(), piece.getColor(), piece.getRow(), piece.getCol());
    }

    @Override
    public boolean isValidMove(int startYRow, int startXCol, int endYRow, int endXCol, ChessPiece[][] board) {
        // Vérifie si le déplacement est vertical
        int deltaX = endXCol - startXCol;
        int deltaY = endYRow- startYRow;
        if (deltaX != 0) {
            return false;
        }
        // Vérifie si le déplacement est d'une case vers l'avant en fonction de la couleur
        if (isWhite()) {
            // Vérifie si le pion n'a pas encore été déplacé et s'il se déplace de deux cases vers l'avant
            if (!hasMoved && deltaY == 2 && board[startYRow + 1][startXCol] == null) {
                return true;
            }
            // Vérifie si le pion se déplace d'une case vers l'avant
            if (deltaY == 1 && board[endYRow][endXCol] == null) {
                return true;
            }
        } else {
            // Vérifie si le pion n'a pas encore été déplacé et s'il se déplace de deux cases vers l'avant
            if (!hasMoved && deltaY == -2 && board[startYRow - 1][startXCol] == null) {
                return true;
            }
            // Vérifie si le pion se déplace d'une case vers l'avant
            if (deltaY == -1 && board[endYRow][endXCol] == null) {
                return true;
            }
        }

        // Si aucun des cas précédents n'est vérifié, le mouvement n'est pas valide
        return false;
    }

    public void promotePawn(Pawn pawn, int row, int col, String pieceType, ChessPiece[][] board) {
        Color color = pawn.getColor();
        ChessPiece newPiece;
        switch (pieceType) {
            case "Queen":
                if (pawn.isWhite()) {
                    newPiece = new Queen("reine_b.png", color, row, col);
                } else {
                    newPiece = new Queen("reine_n.png", color, row, col);
                }
                break;
            case "Rook":
                if (pawn.isWhite()) {
                    newPiece = new Rook("tour_b.png", color, row, col);
                } else {
                    newPiece = new Rook("tour_n.png", color, row, col);
                }
                break;
            case "Bishop":
                if (pawn.isWhite()) {
                    newPiece = new Bishop("fou_b.png", color, row, col);
                } else {
                    newPiece = new Bishop("fou_n.png", color, row, col);
                }
                break;
            case "Knight":
                if (pawn.isWhite()) {
                    newPiece = new Knight("cavalier_b.png", color, row, col);
                } else {
                    newPiece = new Knight("cavalier_n.png", color, row, col);
                }
                break;
            case "Imperatrice":
                if(pawn.isWhite())
                {
                    newPiece = new Imperatrice("imperatrice_b.png",color,row,col);
                }else{
                    newPiece = new Imperatrice("imperatrice_n.png",color,row,col);
                }
                break;
            case "Princesse":
                if(pawn.isWhite())
                {
                    newPiece = new Princesse("princesse_b.png",color,row,col);
                }else{
                    newPiece = new Princesse("princesse_n.png",color,row,col);
                }
                break;
            case "Noctambule":
                if(pawn.isWhite())
                {
                    newPiece = new Noctambule("noctambule_b.png",color,row,col);
                }else{
                    newPiece = new Noctambule("noctambule_n.png",color,row,col);
                }
                break;
            case "Sauterelle":
                if(pawn.isWhite())
                {
                    newPiece = new Sauterelle("sauterelle_b.png",color,row,col);
                }else{
                    newPiece = new Sauterelle("sauterelle_n.png",color,row,col);
                }
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



    public String getSymbol(){
        return (getColor() == Color.WHITE ? "B" : "N");
    }
}