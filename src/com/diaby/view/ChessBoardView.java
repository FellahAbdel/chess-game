package com.diaby.view;
import com.diaby.model.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;

/** Fenetre graphique (classe heritant de JFrame) qui reagit au clic de souris (implements MouseListener)
 *   qui affiche le damier, qui permet de selectionner/deplacer des pieces d'echec
 */
public class ChessBoardView extends JFrame implements MouseListener {
    /** Panneau principal */
    JPanel mainPanel;
    /** Panneau correspondant au damier */
    JPanel chessBoard;
    static final int SIZE_COLUMN_BOARD = 8;
    static final int SIZE_ROW_BOARD = 8;
    static final int SIZE_CASE_X = 75;
    static final int SIZE_CASE_Y = 75;
    ChessBoard board;

    static final Color BLACK_CASE = Color.gray;
    static final Color WHITE_CASE = Color.white;
    static final Color HIGHLIGHT_CASE = Color.red;

    public ChessBoardView(){
        // Taille d'un element graphique
        Dimension boardSize = new Dimension(SIZE_CASE_X * SIZE_COLUMN_BOARD, SIZE_CASE_Y * SIZE_ROW_BOARD);

        // Definition du panel principal
        mainPanel = new JPanel();
        getContentPane().add(mainPanel); // Ajout du panel principal a la fenetre principale (JFrame)
        mainPanel.setPreferredSize(boardSize);
        mainPanel.addMouseListener(this); // Ajout de la gestion des clics

        // Definition du panel contenant le damier
        chessBoard = new JPanel();
        mainPanel.add(chessBoard); // Ajout du panel damier au panel principal
        chessBoard.setLayout( new GridLayout(SIZE_COLUMN_BOARD, SIZE_ROW_BOARD) ); // le damier sera une grille N * M
        chessBoard.setPreferredSize( boardSize );
        chessBoard.setBounds(0, 0, boardSize.width, boardSize.height);

        // Dans le damier, ajout de N*M panneau, chacun correspondant a une case de la grille
        for (int i = 0; i < SIZE_COLUMN_BOARD * SIZE_ROW_BOARD; i++) {
            JPanel square = new JPanel(new BorderLayout());
            square.setBorder(BorderFactory.createLineBorder(Color.black));
            chessBoard.add(square);
        }

        // Instantciation de l'echiquier
        board = new ChessBoard();
        // Afiiche la grille
        drawGrid();
    }

    /** Methode d'affichage de la grille
     *
     */
    public void drawGrid() {
        JPanel square;
        int index = 0;
        // Pour chaque case de la grille
        for (int row = 0; row < SIZE_ROW_BOARD; row++) {
            for (int col = 0; col < SIZE_COLUMN_BOARD; col++) {
                square = (JPanel) chessBoard.getComponent(index);
                // Dessin case noire/ case blanche
                int colorIndex = (row + col) % 2;
                square.setBackground((colorIndex == 0) ? BLACK_CASE : WHITE_CASE);

                // Obtention de la piece sur la case et ajout d'une image dans un JLabel
                ChessPiece piece = board.getPieceAt(row, col);
                if (piece != null) {
                    String fileName = "src/com/diaby/model/img/" + piece.getImageName();
                    JLabel image = new JLabel(new ImageIcon(fileName));
                    square.add(image);
                }

                if(board.highLightCase[row][col]){
                    square.setBackground(HIGHLIGHT_CASE);
                }
                // Incrémentation de l'indice pour passer à la case suivante
                index++;
            }
        }
    }
    /**
     * Capture du bonton presse du pointeur sur la fenetre graphique
     * @param e evenement pointeur presse
     */
    /**
     * Capture du bonton presse du pointeur sur la fenetre graphique
     * @param e evenement pointeur presse
     */

//    public void mousePressed(MouseEvent e) {
//        // Conversion de la position cliquée en position de la grille
//        int colX = e.getX() / (ChessBoardView.SIZE_CASE_X);
//        int rowY = e.getY() / (ChessBoardView.SIZE_CASE_Y);
//
//        ChessPiece piece = board.getPieceAt(rowY, colX);
//
//        if (piece != null) {
//            // Test si la pièce est blanche
//            if (piece.isWhite()) {
//                if (piece instanceof Pawn) {
//                    boolean isValid = piece.isValidMove(rowY, colX, rowY + 1, colX, board.getTileBoard());
//                    if (isValid) {
//                        board.resetHighlight();
//                        board.highLightCase[rowY + 1][colX] = true;
//                    }
//                }
//            }
//            // Test si la pièce est noire
//            else if (piece.isBlack()) {
//                if (piece instanceof Pawn) {
//                    boolean isValid = piece.isValidMove(rowY, colX, rowY - 1, colX, board.getTileBoard());
//                    if (isValid) {
//                        board.resetHighlight();
//                        board.highLightCase[rowY - 1][colX] = true;
//                    }
//                }
//            }
//        } else {
//            // Test si la case cliquée est mise en surbrillance
//            if (board.highLightCase[rowY][colX]) {
//                // Récupération de la pièce sélectionnée
//                ChessPiece selectedPieceWhite = board.getPieceAt(rowY-1, colX);
//                ChessPiece selectedPieceBlack = board.getPieceAt(rowY+1,colX);
//
//                // Test si la pièce sélectionnée existe et qu'elle est de la même couleur que la case cliquée
//                if (selectedPieceWhite != null && selectedPieceWhite.isWhite()) {
//                    // Suppression de l'ancienne pièce
//                    JPanel oldSquare = (JPanel) chessBoard.getComponent(((rowY-1) * SIZE_ROW_BOARD) + colX);
//                    oldSquare.removeAll();
//                    oldSquare.repaint();
//                    oldSquare.revalidate();
//                    board.resetHighlight();
//                    // Déplacement de la pièce dans la grille
//                    board.movePiece(rowY-1, colX, rowY, colX);
//                } else if (selectedPieceBlack != null && selectedPieceBlack.isBlack()) {
//                    // Suppression de l'ancienne pièce
//                    JPanel oldSquare = (JPanel) chessBoard.getComponent(((rowY+1) * SIZE_ROW_BOARD) + colX);
//                    oldSquare.removeAll();
//                    oldSquare.repaint();
//                    oldSquare.revalidate();
//                    board.resetHighlight();
//                    // Déplacement de la pièce dans la grille
//                    board.movePiece(rowY+1, colX, rowY, colX);
//                }
//            }
//        }
//        // Mise à jour de l'affichage
//        drawGrid();
//    }


    public void mousePressed(MouseEvent e) {
        // Conversion de la position cliquée en position de la grille
        int colX = e.getX() / (ChessBoardView.SIZE_CASE_X);
        int rowY = e.getY() / (ChessBoardView.SIZE_CASE_Y);

        ChessPiece piece = board.getPieceAt(rowY, colX);


        if (piece instanceof Pawn) {
            ArrayList<int[]> moves = piece.PossiblesMoves(rowY, colX, board.getTileBoard());
            board.resetHighlight();
            for (int[] move : moves) {
                board.highLightCase[move[0]][move[1]] = true;
            }
        }
        else {
            // Test si la case cliquée est mise en surbrillance
            if (board.highLightCase[rowY][colX]) {
                // Récupération de la pièce sélectionnée
                ChessPiece selectedPieceWhite = board.getPieceAt(rowY - 1, colX);
                ChessPiece selectedPieceBlack = board.getPieceAt(rowY + 1, colX);

                // Test si la pièce sélectionnée existe et qu'elle est de la même couleur que la case cliquée
                if (selectedPieceWhite != null && selectedPieceWhite.isWhite()) {
                    // Suppression de l'ancienne pièce
                    JPanel oldSquare = (JPanel) chessBoard.getComponent(((rowY - 1) * SIZE_ROW_BOARD) + colX);
                    oldSquare.removeAll();
                    oldSquare.repaint();
                    oldSquare.revalidate();
                    board.resetHighlight();
                    // Déplacement de la pièce dans la grille
                    board.movePiece(rowY - 1, colX, rowY, colX);
                }

                if (selectedPieceBlack != null && selectedPieceBlack.isBlack()) {
                    // Suppression de l'ancienne pièce
                    JPanel oldSquare = (JPanel) chessBoard.getComponent(((rowY + 1) * SIZE_ROW_BOARD) + colX);
                    oldSquare.removeAll();
                    oldSquare.repaint();
                    oldSquare.revalidate();
                    board.resetHighlight();
                    // Déplacement de la pièce dans la grille
                    board.movePiece(rowY + 1, colX, rowY, colX);
                }
            }
        }
        if(piece instanceof Knight)
        {
            ArrayList<int[]> moves = piece.PossiblesMoves(rowY, colX, board.getTileBoard());
            board.resetHighlight();
            for (int[] move : moves) {
                board.highLightCase[move[0]][move[1]] = true;
            }
        }
        if(piece instanceof Bishop)
        {
             ArrayList<int[]> moves = piece.PossiblesMoves(rowY, colX, board.getTileBoard());
             board.resetHighlight();
             for (int[] move : moves) {
                 board.highLightCase[move[0]][move[1]] = true;
             }
        }
        if(piece instanceof Rook)
        {
            ArrayList<int[]> moves = piece.PossiblesMoves(rowY, colX, board.getTileBoard());
            board.resetHighlight();
            for (int[] move : moves) {
                board.highLightCase[move[0]][move[1]] = true;
            }
        }
        if(piece instanceof Queen)
        {
            ArrayList<int[]> moves = piece.PossiblesMoves(rowY, colX, board.getTileBoard());
            board.resetHighlight();
            for (int[] move : moves) {
                board.highLightCase[move[0]][move[1]] = true;
            }
        }
        if(piece instanceof King)
        {
            ArrayList<int[]> moves = piece.PossiblesMoves(rowY, colX, board.getTileBoard());
            board.resetHighlight();
            for (int[] move : moves) {
                board.highLightCase[move[0]][move[1]] = true;
            }
        }
        // Mise à jour de l'affichage
        drawGrid();

    }

    public void mouseReleased(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e){
        // On met en evidence les mouvements possibles
    }
    public void mouseExited(MouseEvent e) {
        // On efface l'évidence.
    }

    public static void main(String[] args) {
        // Definir et afficher la fenetre graphique correspondant au damier
        JFrame frame = new ChessBoardView();
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE );
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo( null );
        frame.setVisible(true);
}
}

