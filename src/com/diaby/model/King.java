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

    public King(ChessPiece piece) {
        super(piece.getPieceName(), piece.getImageName(), piece.getColor(), piece.getRow(), piece.getCol());
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

    public ArrayList<int[]> PossiblesMoves(int startYRow, int startXCol, ChessPiece[][] board) {
        ArrayList<int[]> moves = new ArrayList<>();

        // Check moves to the right
        if (startXCol + 1 < 8) {
            ChessPiece piece = board[startYRow][startXCol + 1];
            if (piece == null || piece.getColor() != this.getColor()) {
                // Try the move and check if king is in check
                ChessPiece[][] testBoard = ChessBoard.copyBoard(board);
                testBoard[startYRow][startXCol + 1] = testBoard[startYRow][startXCol];
                testBoard[startYRow][startXCol] = null;
                if (!isInCheck(this.isWhite(), testBoard)) {
                    moves.add(new int[]{startYRow, startXCol + 1});
                }
            }
        }

        // Check moves to the left
        if (startXCol - 1 >= 0) {
            ChessPiece piece = board[startYRow][startXCol - 1];
            if (piece == null || piece.getColor() != this.getColor()) {
                // Try the move and check if king is in check
                ChessPiece[][] testBoard = ChessBoard.copyBoard(board);
                testBoard[startYRow][startXCol - 1] = testBoard[startYRow][startXCol];
                testBoard[startYRow][startXCol] = null;
                if (!isInCheck(this.isWhite(), testBoard)) {
                    moves.add(new int[]{startYRow, startXCol - 1});
                }
            }
        }

        // Check moves to the bottom
        if (startYRow + 1 < 8) {
            ChessPiece piece = board[startYRow + 1][startXCol];
            if (piece == null || piece.getColor() != this.getColor()) {
                // Try the move and check if king is in check
                ChessPiece[][] testBoard = ChessBoard.copyBoard(board);
                testBoard[startYRow + 1][startXCol] = testBoard[startYRow][startXCol];
                testBoard[startYRow][startXCol] = null;
                if (!isInCheck(this.isWhite(), testBoard)) {
                    moves.add(new int[]{startYRow + 1, startXCol});
                }
            }
        }

        // Check moves to the top
        if (startYRow - 1 >= 0) {
            ChessPiece piece = board[startYRow - 1][startXCol];
            if (piece == null || piece.getColor() != this.getColor()) {
                // Try the move and check if king is in check
                ChessPiece[][] testBoard = ChessBoard.copyBoard(board);
                testBoard[startYRow - 1][startXCol] = testBoard[startYRow][startXCol];
                testBoard[startYRow][startXCol] = null;
                if (!isInCheck(this.isWhite(), testBoard)) {
                    moves.add(new int[]{startYRow - 1, startXCol});
                }
            }
        }

        // Check diagonal moves to the top-right
        if (startYRow - 1 >= 0 && startXCol + 1 < 8) {
            ChessPiece piece = board[startYRow - 1][startXCol + 1];
            if (piece == null || piece.getColor() != this.getColor()) {
                // Try the move and check if king is in check
                ChessPiece[][] testBoard = ChessBoard.copyBoard(board);
                testBoard[startYRow - 1][startXCol + 1] = testBoard[startYRow][startXCol];
                testBoard[startYRow][startXCol] = null;
                if (!isInCheck(this.isWhite(), testBoard)) {
                    moves.add(new int[]{startYRow - 1, startXCol + 1});
                }
            }
        }

        // Check diagonal moves to the top-left
        if (startYRow - 1 >= 0 && startXCol - 1 >= 0) {
            ChessPiece piece = board[startYRow - 1][startXCol - 1];
            if (piece == null || piece.getColor() != this.getColor()) {
                // Try the move and check if king is in check
                ChessPiece[][] testBoard = ChessBoard.copyBoard(board);
                testBoard[startYRow - 1][startXCol - 1] = testBoard[startYRow][startXCol];
                testBoard[startYRow][startXCol] = null;
                if (!isInCheck(this.isWhite(), testBoard)) {
                    moves.add(new int[]{startYRow - 1, startXCol - 1});
                }

            }
        }

        // Check diagonal moves to the bottom-right
        if (startYRow + 1 < 8 && startXCol + 1 < 8) {
            ChessPiece piece = board[startYRow + 1][startXCol + 1];
            if (piece == null || piece.getColor() != this.getColor()) {
                // Try the move and check if king is in check
                ChessPiece[][] testBoard = ChessBoard.copyBoard(board);
                testBoard[startYRow + 1][startXCol + 1] = testBoard[startYRow][startXCol];
                testBoard[startYRow][startXCol] = null;
                if (!isInCheck(this.isWhite(), testBoard)) {
                    moves.add(new int[]{startYRow + 1, startXCol + 1});
                }
            }
        }

        // Check diagonal moves to the bottom-left
        if (startYRow + 1 < 8 && startXCol - 1 >= 0) {
            ChessPiece piece = board[startYRow + 1][startXCol - 1];
            if (piece == null || piece.getColor() != this.getColor()) {
                // Try the move and check if king is in check
                ChessPiece[][] testBoard = ChessBoard.copyBoard(board);
                testBoard[startYRow + 1][startXCol - 1] = testBoard[startYRow][startXCol];
                testBoard[startYRow][startXCol] = null;
                if (!isInCheck(this.isWhite(), testBoard)) {
                    moves.add(new int[]{startYRow + 1, startXCol - 1});
                }

            }
        }

        // petit roque
        if(!this.getHasMoved()  && startXCol == 4) {
            ChessPiece rook = board[startYRow][7];
            if(rook instanceof Rook && !((Rook) rook).getHasMoved() && board[startYRow][5] == null && board[startYRow][6] == null) {
                boolean isInCheck = false;
                ChessPiece piece = board[startYRow][startXCol];
                if (isInCheck(piece.isWhite(), board)) {
                    isInCheck = true;
                }
                if (!isInCheck) {
                    ChessPiece arrivalCase = board[startYRow][startXCol + 2];
                    if (arrivalCase == null || arrivalCase.getColor() != this.getColor()) {
                        // Try the move and check if king is in check
                        ChessPiece[][] testBoard = ChessBoard.copyBoard(board);
                        testBoard[startYRow][startXCol + 2] = testBoard[startYRow][startXCol];
                        testBoard[startYRow][startXCol] = null;
                        if (!isInCheck(this.isWhite(), testBoard)) {
                            moves.add(new int[]{startYRow, startXCol + 2});
                        }
                    }
                }
            }
        }

        // grand roque
        if(!this.getHasMoved() && startXCol == 4) {
            ChessPiece rook = board[startYRow][0];
            if(rook instanceof Rook && !((Rook) rook).getHasMoved() && board[startYRow][1] == null && board[startYRow][2] == null && board[startYRow][3] == null) {
                boolean isInCheck = false;
                ChessPiece piece = board[startYRow][startXCol];
                if (isInCheck(piece.isWhite(), board)) {
                    isInCheck = true;
                }
                if (!isInCheck) {
                    ChessPiece arrivalCase = board[startYRow][startXCol - 2];
                    if (arrivalCase == null || arrivalCase.getColor() != this.getColor()) {
                        // Try the move and check if king is in check
                        ChessPiece[][] testBoard = ChessBoard.copyBoard(board);
                        testBoard[startYRow][startXCol - 2] = testBoard[startYRow][startXCol];
                        testBoard[startYRow][startXCol] = null;
                        if (!isInCheck(this.isWhite(), testBoard)) {
                            moves.add(new int[] {startYRow, startXCol - 2});
                        }
                    }
                }
            }
        }

        return moves;
    }


    public boolean isInCheck(boolean isWhiteKing, ChessPiece[][] board) {
        // Find the king's position
        int[] kingPos = null;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPiece piece = board[row][col];
                if (piece instanceof King && piece.isWhite() == isWhiteKing) {
                    kingPos = new int[]{row, col};
                }
            }
        }

        // Check if any opponent piece can attack the king
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPiece piece = board[row][col];
                if (piece != null && piece.isWhite() != isWhiteKing) {
                    ArrayList<int[]> moves = piece.PossiblesMoves(row, col, board);
                    for (int[] move : moves) {
                        if (kingPos != null && move[0] == kingPos[0] && move[1] == kingPos[1]) {
                            return true;
                        }
                    }
                }
            }
        }

        // The king is not in check
        return false;
    }

    public String getSymbol() {
        return (getColor() == Color.WHITE ? "B" : "N");
    }

}




