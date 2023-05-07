package variant.model;

import java.awt.*;
import java.util.ArrayList;

public class ChessBoard {
    private ChessPiece[][] tileBoard;
    public boolean[][] highLightCase = new boolean[8][12];
    public ChessBoard() {
        tileBoard = new ChessPiece[8][12];
    }

    public void initialize(boolean isWhiteTurn) {
        Color color = isWhiteTurn ? Color.BLACK : Color.WHITE;
        Color oppositeColor = isWhiteTurn ? Color.WHITE : Color.BLACK ;

        // Initialiser les pièces blanches
        tileBoard[0][0] = new Rook(color, 0, 0, isWhiteTurn);
        tileBoard[0][1] = new Knight(color, 0, 1,isWhiteTurn);
        tileBoard[0][2] = new Princesse(color, 0, 2,isWhiteTurn);
        tileBoard[0][3] = new Sauterelle(color, 0, 3,isWhiteTurn);
        tileBoard[0][4] = new Bishop(color, 0, 4,isWhiteTurn);
        tileBoard[0][5] = new Queen(color, 0, 5,isWhiteTurn);
        tileBoard[0][6] = new King(color, 0, 6,isWhiteTurn);
        tileBoard[0][7] = new Bishop(color, 0, 7,isWhiteTurn);
        tileBoard[0][8] = new Imperatrice(color,0,8,isWhiteTurn);
        tileBoard[0][9] = new Noctambule(color,0,9,isWhiteTurn);
        tileBoard[0][10] = new Knight(color,0,10,isWhiteTurn);
        tileBoard[0][11] = new Rook(color,0,11,isWhiteTurn);
        for (int j = 0; j < 12; j++) {
            tileBoard[1][j] = new Pawn(color, 1, j,isWhiteTurn);
        }

        // Initialiser les pièces noires
        tileBoard[7][0] = new Rook(oppositeColor, 7, 0,isWhiteTurn);
        tileBoard[7][1] = new Knight(oppositeColor, 7, 1,isWhiteTurn);
        tileBoard[7][2] = new Princesse(oppositeColor, 7, 2,isWhiteTurn);
        tileBoard[7][3] = new Sauterelle(oppositeColor, 7, 3,isWhiteTurn);
        tileBoard[7][4] = new Bishop(oppositeColor, 7, 4,isWhiteTurn);
        tileBoard[7][5] = new Queen(oppositeColor, 7, 5,isWhiteTurn);
        tileBoard[7][6] = new King(oppositeColor, 7, 6,isWhiteTurn);
        tileBoard[7][7] = new Bishop(oppositeColor, 7, 7,isWhiteTurn);
        tileBoard[7][8] = new Imperatrice(oppositeColor, 7, 8,isWhiteTurn);
        tileBoard[7][9] = new Noctambule(oppositeColor, 7, 9,isWhiteTurn);
        tileBoard[7][10] = new Knight(oppositeColor, 7, 10,isWhiteTurn);
        tileBoard[7][11] = new Rook(oppositeColor, 7, 11,isWhiteTurn);
        for (int j = 0; j < 12; j++) {
            tileBoard[6][j] = new Pawn(oppositeColor, 6, j, isWhiteTurn);
        }

    }

    public static ChessPiece[][] copyBoard(ChessPiece[][] board) {
        ChessPiece[][] copy = new ChessPiece[8][12];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 12; col++) {
                ChessPiece piece = board[row][col];
                if (piece != null ) {
                    ChessPiece newPiece;
                    if (piece instanceof Pawn) {
                        newPiece = new Pawn(piece);
                    } else if (piece instanceof Rook) {
                        newPiece = new Rook(piece);
                    } else if (piece instanceof Knight) {
                        newPiece = new Knight(piece);
                    } else if (piece instanceof Bishop) {
                        newPiece = new Bishop(piece);
                    } else if (piece instanceof Queen) {
                        newPiece = new Queen(piece);
                    } else if (piece instanceof Princesse) {
                        newPiece = new Princesse(piece);
                    } else if (piece instanceof Imperatrice) {
                        newPiece = new Imperatrice(piece);
                    }else if (piece instanceof Sauterelle) {
                        newPiece = new Sauterelle(piece);
                    }else if (piece instanceof Noctambule) {
                        newPiece = new Noctambule(piece);
                    } else {
                        newPiece = new King(piece);
                    }
                    copy[row][col] = newPiece;
                }
            }
        }
        return copy;
    }


    public ChessPiece[][] getTileBoard() {
        return tileBoard;
    }

    public void resetHighlight() {
        for(int i = 0; i< 8; i++)
            for(int j = 0; j< 12; j++)
                highLightCase[i][j]=false;
    }
    public ChessPiece getPieceAt(int row, int col) {
        return tileBoard[row][col];
    }

    public void setPieceAt(int row, int col, ChessPiece piece) {
        tileBoard[row][col] = piece;
    }

    public void removePieceAt(int row, int col) {
        ChessPiece pieceToRemove = getPieceAt(row, col);
        if (pieceToRemove == null) {
            throw new IllegalArgumentException("No piece at " + row + " " + col);
        }
        tileBoard[row][col] = null ;
    }

    public boolean isOccupied(int row, int col) {
        return tileBoard[row][col] != null;
    }

    public ArrayList<ChessPiece> getPiecesByColor(Color color) {
        ArrayList<ChessPiece> piecesList = new ArrayList<>();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 12; col++) {
                ChessPiece piece = tileBoard[row][col];
                if (piece != null && piece.getColor() == color) {
                    piecesList.add(piece);
                }
            }
        }
        return piecesList;
    }

    public King getKing(boolean isWhiteKing)
    {
        King king = null;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 12; col++) {
                ChessPiece piece = tileBoard[row][col];
                if (piece instanceof King && piece.isWhite() == isWhiteKing) {
                    king = (King) getPieceAt(row,col);
                }
            }
        }

        return king;
    }


    public void move(int startRow, int startCol, int endRow, int endCol) {
        ChessPiece startPiece = getPieceAt(startRow, startCol);
        ChessPiece endPiece = getPieceAt(endRow, endCol);
        if(endPiece != null && (endPiece.getColor() != startPiece.getColor()))
        {
            endPiece.setCaptured();
            removePieceAt(endRow, endCol);
        }
        setPieceAt(endRow, endCol, startPiece);
        setPieceAt(startRow, startCol, null);
        startPiece.setRow(endRow);
        startPiece.setCol(endCol);

    }

    public void movePiece(int startRow, int startCol, int endRow, int endCol) {
        ChessPiece startPiece = getPieceAt(startRow, startCol);
        ChessPiece endPiece = getPieceAt(endRow, endCol);

        if (startPiece == null) {
            return ;
        }
        if (endPiece != null && endPiece.getColor() == startPiece.getColor()) {
            return ;
        }

        // capture en passant
        if ( startPiece instanceof Pawn && endCol != startCol && endPiece == null) {
            int capturedPieceRow = startPiece.isWhite() ? endRow - 1 : endRow + 1;
            ChessPiece capturedPiece = getPieceAt(capturedPieceRow, endCol);
            capturedPiece.setCaptured();
            removePieceAt(capturedPieceRow, endCol);
        }

        move(startRow, startCol, endRow, endCol);
    }


}
