package variant.model;

import variant.view.Game;

import java.awt.*;
import java.util.ArrayList;

public class ChessBoard extends Game {

    private static final int SIZE_COLUMN_BOARD = 10;
    private static final int SIZE_ROW_BOARD = 8;
    private ChessPiece[][] tileBoard;
    public boolean[][] highLightCase = new boolean[SIZE_ROW_BOARD][SIZE_COLUMN_BOARD];

    public ChessBoard() {
        tileBoard = new ChessPiece[SIZE_ROW_BOARD][SIZE_COLUMN_BOARD ];
    }

    /**
     * Initialise le tableau de pièces d'échecs selon la position des pièces blanches.
     *
     * @param whitePiecesAtBottom vrai si les pièces blanches sont placées en bas, faux si elles sont placées en haut
     */
    public void initialize(boolean whitePiecesAtBottom) {
        Color color = whitePiecesAtBottom ? Color.BLACK : Color.WHITE;
        Color oppositeColor = whitePiecesAtBottom ? Color.WHITE : Color.BLACK;

        // Initialiser les pièces blanches
        tileBoard[0][0] = new Rook(color, 0, 0, whitePiecesAtBottom);
        tileBoard[0][1] = new Knight(color, 0, 1, whitePiecesAtBottom);
        tileBoard[0][2] = new Princesse(color, 0, 2, whitePiecesAtBottom);
        tileBoard[0][3] = new Bishop(color, 0, 3, whitePiecesAtBottom);
        tileBoard[0][4] = new Queen(color, 0, 4, whitePiecesAtBottom);
        tileBoard[0][5] = new King(color, 0, 5, whitePiecesAtBottom);
        tileBoard[0][6] = new Bishop(color, 0, 6, whitePiecesAtBottom);
        tileBoard[0][7] = new Imperatrice(color, 0, 7, whitePiecesAtBottom);
        tileBoard[0][8] = new Knight(color, 0, 8, whitePiecesAtBottom);
        tileBoard[0][9] = new Rook(color, 0, 9, whitePiecesAtBottom);
        for (int j = 0; j < SIZE_COLUMN_BOARD; j++) {
            tileBoard[1][j] = new Pawn(color, 1, j, whitePiecesAtBottom);
        }

        // Initialiser les pièces noires
        tileBoard[7][0] = new Rook(oppositeColor, 7, 0, whitePiecesAtBottom);
        tileBoard[7][1] = new Knight(oppositeColor, 7, 1, whitePiecesAtBottom);
        tileBoard[7][2] = new Princesse(oppositeColor, 7, 2, whitePiecesAtBottom);
        tileBoard[7][3] = new Bishop(oppositeColor, 7, 3, whitePiecesAtBottom);
        tileBoard[7][4] = new Queen(oppositeColor, 7, 4, whitePiecesAtBottom);
        tileBoard[7][5] = new King(oppositeColor, 7, 5, whitePiecesAtBottom);
        tileBoard[7][6] = new Bishop(oppositeColor, 7, 6, whitePiecesAtBottom);
        tileBoard[7][7] = new Imperatrice(oppositeColor, 7, 7, whitePiecesAtBottom);
        tileBoard[7][8] = new Knight(oppositeColor, 7, 8, whitePiecesAtBottom);
        tileBoard[7][9] = new Rook(oppositeColor, 7, 9, whitePiecesAtBottom);
        for (int j = 0; j <SIZE_COLUMN_BOARD; j++) {
            tileBoard[6][j] = new Pawn(oppositeColor, 6, j, whitePiecesAtBottom);
        }

    }

    /**
     * Cette méthode permet de créer une copie du tableau d'échecs "board".
     *
     * @param board le tableau d'échecs à copier
     * @return une copie du tableau d'échecs "board"
     */
    public static ChessPiece[][] copyBoard(ChessPiece[][] board) {
        ChessPiece[][] copy = new ChessPiece[SIZE_ROW_BOARD][SIZE_COLUMN_BOARD];
        for (int row = 0; row < SIZE_ROW_BOARD; row++) {
            for (int col = 0; col < SIZE_COLUMN_BOARD ; col++) {
                ChessPiece piece = board[row][col];
                if (piece != null) {
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
                    } else if (piece instanceof Sauterelle) {
                        newPiece = new Sauterelle(piece);
                    } else if (piece instanceof Noctambule) {
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

    /**
     * Retourne le plateau de jeu représenté par un tableau de ChessPiece.
     *
     * @return un tableau de ChessPiece représentant le plateau de jeu.
     */
    public ChessPiece[][] getTileBoard() {
        return tileBoard;
    }

    /**
     * Réinitialise le tableau highLightCase à false pour chaque case.
     */
    public void resetHighlight() {
        for (int i = 0; i < SIZE_ROW_BOARD; i++)
            for (int j = 0; j < SIZE_COLUMN_BOARD; j++)
                highLightCase[i][j] = false;
    }

    /**
     * Retourne la pièce se trouvant à une position donnée.
     *
     * @param row la rangée où se trouve la pièce.
     * @param col la colonne où se trouve la pièce.
     * @return la pièce se trouvant à la position donnée.
     */
    public ChessPiece getPieceAt(int row, int col) {
        return tileBoard[row][col];
    }

    /**
     * Place une pièce donnée à une position donnée.
     *
     * @param row   la rangée où placer la pièce.
     * @param col   la colonne où placer la pièce.
     * @param piece la pièce a placé.
     */
    public void setPieceAt(int row, int col, ChessPiece piece) {
        tileBoard[row][col] = piece;
    }

    /**
     * Enlève la pièce se trouvant à une position donnée.
     *
     * @param row la rangée où se trouve la pièce à enlever.
     * @param col la colonne où se trouve la pièce à enlever.
     * @throws IllegalArgumentException si aucune pièce ne se trouve à la position donnée.
     */
    public void removePieceAt(int row, int col) {
        ChessPiece pieceToRemove = getPieceAt(row, col);
        if (pieceToRemove == null) {
            throw new IllegalArgumentException("No piece at " + row + " " + col);
        }
        tileBoard[row][col] = null;
    }

    /**
     * Vérifie si une case donnée est occupée par une pièce.
     *
     * @param row la rangée de la case à vérifier.
     * @param col la colonne de la case à vérifier.
     * @return true si la case est occupée par une pièce, false sinon.
     */
    public boolean isOccupied(int row, int col) {
        return tileBoard[row][col] != null;
    }

    /**
     * Retourne une liste de toutes les pièces d'une couleur donnée.
     *
     * @param color la couleur des pièces à retourner.
     * @return une liste de toutes les pièces de la couleur donnée.
     */
    public ArrayList<ChessPiece> getPiecesByColor(Color color) {
        ArrayList<ChessPiece> piecesList = new ArrayList<>();
        for (int row = 0; row < SIZE_ROW_BOARD; row++) {
            for (int col = 0; col < SIZE_COLUMN_BOARD; col++) {
                ChessPiece piece = tileBoard[row][col];
                if (piece != null && piece.getColor() == color) {
                    piecesList.add(piece);
                }
            }
        }
        return piecesList;
    }

    /**
     * Retourne le roi d'une couleur donnée.
     *
     * @param isWhiteKing true si le roi à chercher est blanc, false sinon.
     * @return le roi de la couleur donnée.
     */
    public King getKing(boolean isWhiteKing) {
        King king = null;
        for (int row = 0; row < SIZE_ROW_BOARD; row++) {
            for (int col = 0; col < SIZE_COLUMN_BOARD; col++) {
                ChessPiece piece = tileBoard[row][col];
                if (piece instanceof King && piece.isWhite() == isWhiteKing) {
                    king = (King) getPieceAt(row, col);
                }
            }
        }

        return king;
    }

    /**
     * Déplace une pièce d'une position à une autre.
     * Si une pièce ennemie est présente sur la case d'arrivée, elle est capturée.
     * Si une pièce alliée est présente sur la case d'arrivée, aucun déplacement n'est effectué.
     *
     * @param startRow La rangée de départ de la pièce.
     * @param startCol La colonne de départ de la pièce.
     * @param endRow   La rangée d'arrivée de la pièce.
     * @param endCol   La colonne d'arrivée de la pièce.
     */
    public void move(int startRow, int startCol, int endRow, int endCol) {
        ChessPiece startPiece = getPieceAt(startRow, startCol);
        ChessPiece endPiece = getPieceAt(endRow, endCol);
        if (endPiece != null && (endPiece.getColor() != startPiece.getColor())) {
            endPiece.setCaptured();
            removePieceAt(endRow, endCol);
        }
        setPieceAt(endRow, endCol, startPiece);
        setPieceAt(startRow, startCol, null);
        startPiece.setRow(endRow);
        startPiece.setCol(endCol);

    }

    /**
     * Déplace une pièce d'une position à une autre et effectue les opérations supplémentaires liées à ce déplacement,
     * comme la capture en passant et la promotion des pions.
     *
     * @param startRow La rangée de départ de la pièce.
     * @param startCol La colonne de départ de la pièce.
     * @param endRow   La rangée d'arrivée de la pièce.
     * @param endCol   La colonne d'arrivée de la pièce.
     */
    public void movePiece(int startRow, int startCol, int endRow, int endCol) {
        ChessPiece startPiece = getPieceAt(startRow, startCol);
        ChessPiece endPiece = getPieceAt(endRow, endCol);

        if (startPiece == null) {
            return;
        }
        if (endPiece != null && endPiece.getColor() == startPiece.getColor()) {
            return;
        }

        // capture en passant
        if (startPiece instanceof Pawn && endCol != startCol && endPiece == null) {
            int capturedPieceRow = startPiece.isWhite() ? endRow - 1 : endRow + 1;
            ChessPiece capturedPiece = getPieceAt(capturedPieceRow, endCol);
            capturedPiece.setCaptured();
            removePieceAt(capturedPieceRow, endCol);
        }

        move(startRow, startCol, endRow, endCol);
    }


}
