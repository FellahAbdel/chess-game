package com.diaby.model;

import java.awt.*;
import java.util.ArrayList;

public class King extends ChessPiece {
    private boolean castlingDone = false;
    private boolean hasMoved;

    public King(String imageName,Color color, int row, int col) {
        super("King", imageName, color, row, col);
        hasMoved = false;
    }


    public boolean isCastlingDone() {
        return this.castlingDone;
    }

    public void setCastlingDone(boolean done) {
        this.castlingDone = done;
    }

    public boolean getHasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    @Override
    public boolean isValidMove(int startX, int startY, int endX, int endY, ChessPiece[][] board) {
        // Vérifie si le déplacement se fait d'une case dans n'importe quelle direction
        int deltaX = Math.abs(endX - startX);
        int deltaY = Math.abs(endY - startY);
        if (deltaX <= 1 && deltaY <= 1)
        {
            // check if the King is not moving to a threatened position
            if (isPositionThreatened(board, endX, endY, this.isWhite())) {
                return true;
            }
            // Vérifie si la case de destination est vide ou occupée par une pièce de la couleur opposée
            if (board[endY][endX] == null || !board[endY][endX].getColor().equals(getColor())) {
                return true;
            }
        }

        return false;
    }

    public String getSymbol() {
        return (getColor() == Color.WHITE ? "B" : "N");
    }


    public boolean castle(int startX, int startY, int endX, int endY, ChessPiece[][] board) {
        // we can't move the piece to a position that has a piece of the same color

        ChessPiece endPiece = board[endX][endY];

        int x = Math.abs(startX - endX);
        int y = Math.abs(startY - endY);

        if (x == 2 && y == 0) {
            // check if the King is not moving to a threatened position
            if (isPositionThreatened(board, endX, endY, this.isWhite())) {
                // check if the King is not castling through a check
                if (!this.castlingDone && canCastle(endPiece, startX, startY, endX, endY, board)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean canCastle(ChessPiece piece, int startRow, int startCol, int endRow, int endCol, ChessPiece[][] board) {
        // Vérifie si la pièce est un roi et si le mouvement est un roque valide
        if (!(piece instanceof King) || Math.abs(endCol - startCol) != 2 || startRow != endRow) {
            return false;
        }

        King king = (King) piece;
        Color color = king.getColor();

        // Vérifie si le roi n'a pas encore bougé et n'est pas en échec
        if (king.getHasMoved() || isInCheck(king, color, board)) {
            return false;
        }

        // Vérifie si les cases entre le roi et la tour sont vides
        int dir = endCol > startCol ? 1 : -1;
        int col = startCol + dir;
        while (col != endCol) {
            if (board[startRow][col] != null) {
                return false;
            }
            col += dir;
        }

        // Vérifie si la tour correspondante n'a pas bougé
        ChessPiece rook = board[startRow][endCol + dir];
        if (!(rook instanceof Rook) || rook.getColor() != color || ((Rook) rook).getHasMoved()) {
            return false;
        }

        return true;
    }

    private boolean isPositionThreatened(ChessPiece[][] board, int x, int y, boolean isWhite) {
        // check if any opposing Knight is threatening the position
        int[][] knightMoves = {{-1, -2}, {-2, -1}, {-2, 1}, {-1, 2}, {1, 2}, {2, 1}, {2, -1}, {1, -2}};
        for (int[] move : knightMoves) {
            int x2 = x + move[0];
            int y2 = y + move[1];
            if (isValidPosition(x2, y2)) {
                ChessPiece piece = board[x2][y2];
                if (piece instanceof Knight && piece.isWhite() != isWhite) {
                    return false;
                }
            }
        }

        // check if any opposing Pawn is threatening the position
        int[][] pawnMoves = isWhite ? new int[][]{{-1, -1}, {-1, 1}} : new int[][]{{1, -1}, {1, 1}};
        for (int[] move : pawnMoves) {
            int x2 = x + move[0];
            int y2 = y + move[1];
            if (isValidPosition(x2, y2)) {
                ChessPiece piece = board[x2][y2];
                if (piece instanceof Pawn && piece.isWhite() != isWhite) {
                    return false;
                }
            }
        }

        // check if any opposing Rook, Bishop, or Queen is threatening the position
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
        for (int[] direction : directions) {
            int x2 = x + direction[0];
            int y2 = y + direction[1];
            while (isValidPosition(x2, y2)) {
                ChessPiece piece = board[x2][y2];
                if (piece != null) {
                    if (piece.isWhite() == isWhite) {
                        break;
                    } else {
                        if ((piece instanceof Rook || piece instanceof Queen) && (direction[0] == 0 || direction[1] == 0)) {
                            return false;
                        } else if ((piece instanceof Bishop || piece instanceof Queen) && direction[0] * direction[1] != 0) {
                            return false;
                        }
                        break;
                    }
                }
                x2 += direction[0];
                y2 += direction[1];
            }
        }

        // check if any opposing King is threatening the position
        int[][] kingMoves = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
        for (int[] move : kingMoves) {
            int x2 = x + move[0];
            int y2 = y + move[1];
            if (isValidPosition(x2, y2)) {
                ChessPiece piece = board[x2][y2];
                if (piece instanceof King && piece.isWhite() != isWhite) {
                    return false;
                }
            }
        }
        // no opposing piece is threatening the position
        return true;
    }

    private boolean isValidPosition(int x, int y) {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }

    public boolean isInCheck(King king, Color color, ChessPiece[][] board) {
        // Récupérer la position du roi du joueur en question

        if (king == null) {
            // Si on ne trouve pas le roi, renvoyer une exception
            throw new RuntimeException("Unable to find " + color + " king position");
        }

        int endX = king.getRow();
        int endY = king.getCol();
        int starX, starY;
        ArrayList<ChessPiece> pieces = getPieces(board);
        // Vérifier si le roi est menacé par une pièce ennemie
        for (ChessPiece piece : pieces) {

            if (piece.getColor() != color) {
                starX = piece.getRow();
                starY = piece.getCol();
                if (piece.isValidMove(starX, starY, endX, endY, board)) {
                    return true;
                }
            }
        }
        return false;
    }



    public ArrayList<int[]> PossiblesMoves(int startYRow, int startXCol, ChessPiece[][] board) {
        ArrayList<int[]> moves = new ArrayList<>();

        // Check moves to the right
        if (startXCol + 1 < 8) {
            ChessPiece piece = board[startYRow][startXCol+1];
            if (piece == null || piece.isWhite() != this.isWhite()) {
                moves.add(new int[]{startYRow, startXCol+1});
            }
        }

        // Check moves to the left
        if (startXCol - 1 >= 0) {
            ChessPiece piece = board[startYRow][startXCol-1];
            if (piece == null || piece.isWhite() != this.isWhite()) {
                moves.add(new int[]{startYRow, startXCol-1});
            }
        }

        // Check moves to the bottom
        if (startYRow + 1 < 8) {
            ChessPiece piece = board[startYRow+1][startXCol];
            if (piece == null || piece.isWhite() != this.isWhite()) {
                moves.add(new int[]{startYRow+1, startXCol});
            }
        }

        // Check moves to the top
        if (startYRow - 1 >= 0) {
            ChessPiece piece = board[startYRow-1][startXCol];
            if (piece == null || piece.isWhite() != this.isWhite()) {
                moves.add(new int[]{startYRow-1, startXCol});
            }
        }

        // Check diagonal moves to the top-right
        if (startYRow - 1 >= 0 && startXCol + 1 < 8) {
            ChessPiece piece = board[startYRow-1][startXCol+1];
            if (piece == null || piece.isWhite() != this.isWhite()) {
                moves.add(new int[]{startYRow-1, startXCol+1});
            }
        }

        // Check diagonal moves to the top-left
        if (startYRow - 1 >= 0 && startXCol - 1 >= 0) {
            ChessPiece piece = board[startYRow-1][startXCol-1];
            if (piece == null || piece.isWhite() != this.isWhite()) {
                moves.add(new int[]{startYRow-1, startXCol-1});
            }
        }

        // Check diagonal moves to the bottom-right
        if (startYRow + 1 < 8 && startXCol + 1 < 8) {
            ChessPiece piece = board[startYRow+1][startXCol+1];
            if (piece == null || piece.isWhite() != this.isWhite()) {
                moves.add(new int[]{startYRow+1, startXCol+1});
            }
        }

        // Check diagonal moves to the bottom-left
        if (startYRow + 1 < 8 && startXCol - 1 >= 0) {
            ChessPiece piece = board[startYRow+1][startXCol-1];
            if (piece == null || piece.isWhite() != this.isWhite()) {
                moves.add(new int[]{startYRow+1, startXCol-1});
            }
        }

        return moves;
    }


}




