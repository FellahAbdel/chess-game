package variant.model;

import java.awt.*;
import java.util.ArrayList;

/**
 * Classe représentant une pièce d'échecs de type Pawn (Pion).
 * Elle étend la classe ChessPiece et redéfinit la méthode possiblesMoves() pour calculer les déplacements possibles
 * pour un Pion.
 */
public class Pawn extends ChessPiece {

    /**
     * Constructeur pour créer une nouvelle pièce de type Rook.
     *
     * @param color                 la couleur de la pièce
     * @param row                   la ligne sur laquelle la pièce se trouve
     * @param col                   la colonne sur laquelle la pièce se trouve
     * @param whitePiecesAtBottom   true si c'est au tour des blancs de jouer, false sinon
     */
    public Pawn(Color color, int row, int col,boolean whitePiecesAtBottom) {
        super("Pawn", color, row, col, whitePiecesAtBottom);
    }

    /**
     * Constructeur pour créer un nouveau pion en copiant une autre pièce d'échecs.
     *
     * @param piece La pièce d'échecs à copier
     */
    public Pawn(ChessPiece piece) {
        super(piece.getPieceName(), piece.getColor(), piece.getRow(), piece.getCol(), piece.getWhitePiecesAtBottom());
    }

    /**
     * Promeut un pion en une autre pièce d'échecs.
     *
     * @param pawn      le pion à promouvoir.
     * @param row       la ligne sur laquelle se trouve le pion à promouvoir.
     * @param col       la colonne sur laquelle se trouve le pion à promouvoir.
     * @param pieceType le type de pièce en laquelle le pion doit être promu ("Queen", "Rook", "Bishop" ou "Knight").
     * @param board     le tableau de jeu de l'échiquier sur lequel la pièce sera placée.
     * @throws IllegalArgumentException si le type de pièce n'est pas valide.
     */
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


    /**
     * Ajoute les mouvements possibles d'un pion à une liste donnée en fonction de la position actuelle sur le plateau de jeu.
     *
     * @param startXCol indice de la colonne de départ du pion
     * @param startYRow indice de la ligne départ du pion
     * @param board     état actuel de l'échiquier
     * @param moves     un ArrayList pour stocker les mouvements légaux du pion
     * @param isWhite   une valeur booléenne indiquant si le pion est blanc ou noir
     */
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

    /**
     * Ajoute les mouvements possibles d'un pion à une liste donnée en fonction de la position actuelle sur le plateau de jeu.
     * @param startXCol la colonne de la position actuelle du pion
     * @param startYRow la rangée de la position actuelle du pion
     * @param board le plateau de jeu actuel
     * @param moves la liste des mouvements possibles
     * @param isWhite la couleur du joueur qui possède le pion (true si blanc, false si noir).
     */
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

    /**
     * Cette méthode calcule tous les mouvements possibles pour une pièce de jeu d'échecs à partir de sa position actuelle sur le plateau.
     * @param startXCol la colonne de départ de la pièce
     * @param startYRow la ligne de départ de la pièce
     * @param board le plateau de jeu d'échecs actuel
     * @param moves la liste des mouvements possibles de la pièce
     * @param whiteTurn un booléen qui indique si c'est au tour des blancs de jouer
     * @return une liste de tableaux d'entiers représentant les coordonnées des cases où la pièce peut se déplacer
     */
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

    /**
     * Retourne la liste de tous les déplacements possibles pour le pion à partir de sa position actuelle
     * sur l'échiquier.
     *
     * @param startYRow La ligne du pion sur l'échiquier (de 0 à 7)
     * @param startXCol La colonne du pion sur l'échiquier (de 0 à 7)
     * @param board     L'échiquier actuel représenté par une matrice de ChessPiece
     * @return Une liste d'entiers représentant les positions (ligne, colonne) des cases où la tour peut se déplacer.
     */
    public ArrayList<int[]> possiblesMoves(int startXCol, int startYRow, ChessPiece[][] board) {
        ArrayList<int[]> moves = new ArrayList<>();

        return possiblesMovesTurn(startXCol, startYRow, board, moves, !this.getWhitePiecesAtBottom());

    }

}