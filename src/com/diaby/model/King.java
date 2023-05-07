package com.diaby.model;

import java.awt.*;
import java.util.ArrayList;

/**
 * Classe représentant une pièce d'échecs de type King (Roi).
 * Elle étend la classe ChessPiece et redéfinit la méthode possiblesMoves() pour calculer les déplacements possibles
 * pour un Roi.
 */
public class King extends ChessPiece {
    private boolean hasMoved;

    /**
     * Constructeur pour créer une nouvelle pièce de type King.
     *
     * @param color                  la couleur de la pièce
     * @param row                    la ligne sur laquelle la pièce se trouve
     * @param col                    la colonne sur laquelle la pièce se trouve
     * @param whitePiecesAtBottom    true si c'est au tour des blancs de jouer, false sinon
     */
    public King(Color color, int row, int col, boolean whitePiecesAtBottom) {
        super("King", color, row, col, whitePiecesAtBottom);
        hasMoved = false;
    }

    /**
     * Constructeur pour créer un nouveau roi en copiant une autre pièce d'échecs.
     *
     * @param piece La pièce d'échecs à copier
     */
    public King(ChessPiece piece) {
        super(piece.getPieceName(), piece.getColor(), piece.getRow(), piece.getCol(), piece.getWhitePiecesAtBottom());
    }

    /**
     * Retourne un booléen indiquant si la tour a déjà bougé depuis le début de la partie.
     *
     * @return True si la tour a déjà bougé, False sinon.
     */
    public boolean getHasMoved() {
        return hasMoved;
    }

    /**
     * Modifie le booléen qui indique si la tour a déjà bougé depuis le début de la partie.
     *
     * @param hasMoved True si la tour a déjà bougé, False sinon.
     */
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    /**
     * Retourne la liste de tous les déplacements possibles du Roi à partir de sa position actuelle
     * sur l'échiquier.
     *
     * @param startYRow La ligne du cavalier sur l'échiquier (de 0 à 7)
     * @param startXCol La colonne du cavalier sur l'échiquier (de 0 à 7)
     * @param board     L'échiquier actuel représenté par une matrice de ChessPiece
     * @return Une liste d'entiers représentant les positions (ligne, colonne) des cases où le roi peut se déplacer.
     */
    public ArrayList<int[]> possiblesMoves(int startYRow, int startXCol, ChessPiece[][] board) {
        ArrayList<int[]> moves = new ArrayList<>();
        // Check moves to the right
        if (startXCol + 1 < 8) {
            ChessPiece piece = board[startYRow][startXCol + 1];
            if (piece == null || piece.isWhite() != this.isWhite()) {
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
            if (piece == null || piece.isWhite() != this.isWhite()) {
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
            if (piece == null || piece.isWhite() != this.isWhite()) {
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
            if (piece == null || piece.isWhite() != this.isWhite()) {
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
            if (piece == null || piece.isWhite() != this.isWhite()) {
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
            if (piece == null || piece.isWhite() != this.isWhite()) {
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
            if (piece == null || piece.isWhite() != this.isWhite()) {
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
            if (piece == null || piece.isWhite() != this.isWhite()) {
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
        if (!this.getHasMoved() && startXCol == 4) {
            ChessPiece rook = board[startYRow][7];
            if (rook instanceof Rook && !((Rook) rook).getHasMoved() && board[startYRow][5] == null && board[startYRow][6] == null) {
                boolean isInCheck = false;
                ChessPiece piece = board[startYRow][startXCol];
                if (isInCheck(piece.isWhite(), board)) {
                    isInCheck = true;
                }
                if (!isInCheck) {
                    ChessPiece kingTempPos = board[startYRow][startXCol + 2];
                    if (kingTempPos == null || kingTempPos.isWhite() != this.isWhite()) {
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
        if (!this.getHasMoved() && startXCol == 4) {
            ChessPiece rook = board[startYRow][0];
            if (rook instanceof Rook && !((Rook) rook).getHasMoved() && board[startYRow][1] == null && board[startYRow][2] == null && board[startYRow][3] == null) {
                boolean isInCheck = false;
                ChessPiece piece = board[startYRow][startXCol];
                if (isInCheck(piece.isWhite(), board)) {
                    isInCheck = true;
                }
                if (!isInCheck) {
                    ChessPiece kingTempPos = board[startYRow][startXCol - 2];
                    if (kingTempPos == null || kingTempPos.isWhite() != this.isWhite()) {
                        // Try the move and check if king is in check
                        ChessPiece[][] testBoard = ChessBoard.copyBoard(board);
                        testBoard[startYRow][startXCol - 2] = testBoard[startYRow][startXCol];
                        testBoard[startYRow][startXCol] = null;
                        if (!isInCheck(this.isWhite(), testBoard)) {
                            moves.add(new int[]{startYRow, startXCol - 2});
                        }

                    }
                }
            }
        }


        return moves;
    }

    /**
     * Permet de tester si la position actuelle du roi est menacé par une pièce adverse
     * autrement si cette dernière est en position d'échec.
     *
     * @param isWhiteKing   true si c'est le roi blanc et false sinon
     * @param board         L'échiquier actuel représenté par une matrice de ChessPiece
     * @return un boolean true si le roi est en échec et false sinon
     */
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
                if (piece != null && piece.isWhite() != isWhiteKing && !(piece instanceof King)) {
                    ArrayList<int[]> moves = piece.possiblesMoves(row, col, board);
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

}




