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
    /**
     * Panneau principal
     */
    JPanel mainPanel;
    /**
     * Panneau correspondant au damier
     */
    JPanel chessBoard;
    static final int SIZE_COLUMN_BOARD = 8;
    static final int SIZE_ROW_BOARD = 8;
    static final int SIZE_CASE_X = 75;
    static final int SIZE_CASE_Y = 75;
    ChessBoard board;

    static final Color BLACK_CASE = Color.gray;
    static final Color WHITE_CASE = Color.white;
    static final Color HIGHLIGHT_CASE = Color.red;

    public ChessBoardView() {
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
        chessBoard.setLayout(new GridLayout(SIZE_COLUMN_BOARD, SIZE_ROW_BOARD)); // le damier sera une grille N * M
        chessBoard.setPreferredSize(boardSize);
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

    /**
     * Methode d'affichage de la grille
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

                if (board.highLightCase[row][col]) {
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
     *
     * @param e evenement pointeur presse
     */

    private ChessPiece selectedPiece = null;

    public void mousePressed(MouseEvent e) {
        // Conversion de la position cliquée en position de la grille
        int colX = e.getX() / (ChessBoardView.SIZE_CASE_X);
        int rowY = e.getY() / (ChessBoardView.SIZE_CASE_Y);

        ChessPiece piece = board.getPieceAt(rowY, colX);
        if (selectedPiece == null) { // Premier clic pour sélectionner la pièce
            if (piece != null ) { // Vérifie que la pièce appartient au joueur dont c'est le tour
                selectedPiece = piece;
                ArrayList<int[]> moves = selectedPiece.PossiblesMoves(rowY, colX, board.getTileBoard());
                board.resetHighlight();
                System.out.println("nom piece : " + selectedPiece.getPieceName());
                for (int[] move : moves) {

                    System.out.println("row = " + move[0] + " " + "col = " + move[1]);
                    board.highLightCase[move[0]][move[1]] = true;
                }
            }
        } else { // Deuxième clic pour déplacer la pièce
            if (board.highLightCase[rowY][colX]) { // Vérifie que la case cliquée est un mouvement possible
                board.resetHighlight();
                JPanel oldSquare = (JPanel) chessBoard.getComponent((selectedPiece.getRow() * SIZE_ROW_BOARD) + selectedPiece.getCol());
                oldSquare.removeAll();
                oldSquare.repaint();
                oldSquare.revalidate();

                // déplacement de la pièce indépendamment des conditions

                // Vérification pour la prise en passant
                if (selectedPiece instanceof Pawn && colX != selectedPiece.getCol() && piece == null) {
                    board.movePiece(selectedPiece.getRow(), selectedPiece.getCol(), rowY, colX);
                    int capturedPieceRow = selectedPiece.getColor() == Color.WHITE ? rowY - 1 : rowY + 1;
                    JPanel capturedSquare = (JPanel) chessBoard.getComponent((capturedPieceRow * SIZE_ROW_BOARD) + colX);
                    capturedSquare.removeAll();
                    capturedSquare.repaint();
                    capturedSquare.revalidate();

                } else if(selectedPiece instanceof Pawn && colX != selectedPiece.getCol() && piece != null && piece.getColor() != selectedPiece.getColor()){// Si une pièce a été capturée
                    board.movePiece(selectedPiece.getRow(), selectedPiece.getCol(), rowY, colX);
                    JPanel capturedSquare = (JPanel) chessBoard.getComponent((rowY * SIZE_ROW_BOARD) + colX);
                    capturedSquare.removeAll();
                    capturedSquare.repaint();
                    capturedSquare.revalidate();
                } else if (selectedPiece instanceof Pawn && (rowY == 0 || rowY == 7)) {  // Promotion du pion en reine
                    // Ouvre la boîte de dialogue de promotion
                    JDialog promotionDialog = new JDialog();
                    promotionDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    promotionDialog.setSize(250, 120);
                    promotionDialog.setLocationRelativeTo(null);
                    promotionDialog.setLayout(new GridLayout(1, 4));
                    promotionDialog.setUndecorated(true); // enlève le bouton de fermeture du panneau
                    JButton queenButton;
                    JButton rookButton;
                    JButton bishopButton;
                    JButton knightButton;
                    Pawn pawn = (Pawn) selectedPiece;

                    // Ajoute les boutons pour chaque type de promotion

                    if(pawn.isWhite())
                    {
                        queenButton = new JButton(new ImageIcon("src/com/diaby/model/img/reine_b.png"));
                        rookButton = new JButton(new ImageIcon("src/com/diaby/model/img/tour_b.png"));
                        bishopButton = new JButton(new ImageIcon("src/com/diaby/model/img/fou_b.png"));
                        knightButton = new JButton(new ImageIcon("src/com/diaby/model/img/cavalier_b.png"));
                        promotionDialog.add(queenButton);
                        promotionDialog.add(rookButton);
                        promotionDialog.add(bishopButton);
                        promotionDialog.add(knightButton);

                        queenButton.addActionListener(e1 -> {
                            pawn.promotePawn(pawn, rowY, colX, "Queen", board.getTileBoard());
                            promotionDialog.dispose();
                            JPanel promotionSquare = (JPanel) chessBoard.getComponent((rowY * SIZE_ROW_BOARD) + colX);
                            promotionSquare.removeAll();
                            String fileName = "src/com/diaby/model/img/reine_b.png";
                            JLabel image = new JLabel(new ImageIcon(fileName));
                            promotionSquare.add(image);
                            promotionSquare.repaint();
                            promotionSquare.revalidate();

                        });
                        rookButton.addActionListener(e12 -> {
                            pawn.promotePawn(pawn, rowY, colX, "Rook",board.getTileBoard());
                            promotionDialog.dispose();
                            JPanel promotionSquare = (JPanel) chessBoard.getComponent((rowY * SIZE_ROW_BOARD) + colX);
                            promotionSquare.removeAll();
                            String fileName = "src/com/diaby/model/img/tour_b.png";
                            JLabel image = new JLabel(new ImageIcon(fileName));
                            promotionSquare.add(image);
                            promotionSquare.repaint();
                            promotionSquare.revalidate();


                        });
                        bishopButton.addActionListener(e13 -> {
                            pawn.promotePawn(pawn, rowY, colX, "Bishop", board.getTileBoard());
                            promotionDialog.dispose();
                            JPanel promotionSquare = (JPanel) chessBoard.getComponent((rowY * SIZE_ROW_BOARD) + colX);
                            promotionSquare.removeAll();
                            String fileName = "src/com/diaby/model/img/fou_b.png";
                            JLabel image = new JLabel(new ImageIcon(fileName));
                            promotionSquare.add(image);
                            promotionSquare.repaint();
                            promotionSquare.revalidate();

                        });
                        knightButton.addActionListener(e14 -> {
                            pawn.promotePawn(pawn, rowY, colX, "Knight", board.getTileBoard());
                            promotionDialog.dispose();
                            JPanel promotionSquare = (JPanel) chessBoard.getComponent((rowY * SIZE_ROW_BOARD) + colX);
                            promotionSquare.removeAll();
                            String fileName = "src/com/diaby/model/img/cavalier_b.png";
                            JLabel image = new JLabel(new ImageIcon(fileName));
                            promotionSquare.add(image);
                            promotionSquare.repaint();
                            promotionSquare.revalidate();

                        });

                        // fermeture une fois le clique capturé
                        queenButton.addActionListener(e1 -> promotionDialog.dispose());
                        rookButton.addActionListener(e1 -> promotionDialog.dispose());
                        bishopButton.addActionListener(e1 -> promotionDialog.dispose());
                        knightButton.addActionListener(e1 -> promotionDialog.dispose());
                    }
                    else{
                        queenButton = new JButton(new ImageIcon("src/com/diaby/model/img/reine_n.png"));
                        rookButton = new JButton(new ImageIcon("src/com/diaby/model/img/tour_n.png"));
                        bishopButton = new JButton(new ImageIcon("src/com/diaby/model/img/fou_n.png"));
                        knightButton = new JButton(new ImageIcon("src/com/diaby/model/img/cavalier_n.png"));
                        promotionDialog.add(queenButton);
                        promotionDialog.add(rookButton);
                        promotionDialog.add(bishopButton);
                        promotionDialog.add(knightButton);

                        queenButton.addActionListener(e1 -> {
                            pawn.promotePawn(pawn, rowY, colX, "Queen", board.getTileBoard());
                            promotionDialog.dispose();
                            JPanel promotionSquare = (JPanel) chessBoard.getComponent((rowY * SIZE_ROW_BOARD) + colX);
                            promotionSquare.removeAll();
                            String fileName = "src/com/diaby/model/img/reine_n.png";
                            JLabel image = new JLabel(new ImageIcon(fileName));
                            promotionSquare.add(image);
                            promotionSquare.repaint();
                            promotionSquare.revalidate();

                        });
                        rookButton.addActionListener(e12 -> {
                            pawn.promotePawn(pawn, rowY, colX, "Rook",board.getTileBoard());
                            promotionDialog.dispose();
                            JPanel promotionSquare = (JPanel) chessBoard.getComponent((rowY * SIZE_ROW_BOARD) + colX);
                            promotionSquare.removeAll();
                            String fileName = "src/com/diaby/model/img/tour_n.png";
                            JLabel image = new JLabel(new ImageIcon(fileName));
                            promotionSquare.add(image);
                            promotionSquare.repaint();
                            promotionSquare.revalidate();

                        });
                        bishopButton.addActionListener(e13 -> {
                            pawn.promotePawn(pawn, rowY, colX, "Bishop", board.getTileBoard());
                            promotionDialog.dispose();
                            JPanel promotionSquare = (JPanel) chessBoard.getComponent((rowY * SIZE_ROW_BOARD) + colX);
                            promotionSquare.removeAll();
                            String fileName = "src/com/diaby/model/img/fou_n.png";
                            JLabel image = new JLabel(new ImageIcon(fileName));
                            promotionSquare.add(image);
                            promotionSquare.repaint();
                            promotionSquare.revalidate();

                        });
                        knightButton.addActionListener(e14 -> {
                            pawn.promotePawn(pawn, rowY, colX, "Knight", board.getTileBoard());
                            promotionDialog.dispose();
                            JPanel promotionSquare = (JPanel) chessBoard.getComponent((rowY * SIZE_ROW_BOARD) + colX);
                            promotionSquare.removeAll();
                            String fileName = "src/com/diaby/model/img/cavalier_n.png";
                            JLabel image = new JLabel(new ImageIcon(fileName));
                            promotionSquare.add(image);
                            promotionSquare.repaint();
                            promotionSquare.revalidate();

                        });
                        // fermeture une fois le clique capturé
                        queenButton.addActionListener(e1 -> promotionDialog.dispose());
                        rookButton.addActionListener(e1 -> promotionDialog.dispose());
                        bishopButton.addActionListener(e1 -> promotionDialog.dispose());
                        knightButton.addActionListener(e1 -> promotionDialog.dispose());
                    }

                    promotionDialog.setVisible(true);
                    board.movePiece(selectedPiece.getRow(), selectedPiece.getCol(), rowY, colX);
                }else if (selectedPiece instanceof King ) {// Roque
                    // Roque court
                    if (colX > selectedPiece.getCol()) {

                        // Déplace la tour
                        board.movePiece(selectedPiece.getRow(), 7, selectedPiece.getRow(), 5);
                        // Déplace le roi
                        board.movePiece(selectedPiece.getRow(), selectedPiece.getCol(), rowY, colX);
                        // Met à jour l'interface graphique
                        JPanel oldKingSquare = (JPanel) chessBoard.getComponent((rowY * SIZE_ROW_BOARD) + 4);

                        JPanel oldRookSquare = (JPanel) chessBoard.getComponent((rowY * SIZE_ROW_BOARD) + 7);

                        oldKingSquare.removeAll();
                        oldKingSquare.repaint();
                        oldKingSquare.revalidate();
                        oldRookSquare.removeAll();
                        oldRookSquare.repaint();
                        oldRookSquare.revalidate();


                    } else if(colX < selectedPiece.getCol()){// Roque long
                        // Déplace la tour
                        board.movePiece(selectedPiece.getRow(), 0, selectedPiece.getRow(), 3);
                        // Déplace le roi
                        board.movePiece(selectedPiece.getRow(), selectedPiece.getCol(), rowY, colX);
                        // Met à jour l'interface graphique
                        JPanel oldKingSquare = (JPanel) chessBoard.getComponent((rowY * SIZE_ROW_BOARD) + 4);

                        JPanel oldRookSquare = (JPanel) chessBoard.getComponent((rowY * SIZE_ROW_BOARD));

                        oldKingSquare.removeAll();
                        oldKingSquare.repaint();
                        oldKingSquare.revalidate();

                        oldRookSquare.removeAll();
                        oldRookSquare.repaint();
                        oldRookSquare.revalidate();


                    }
                } else if (board.isOccupied(rowY, colX) && board.getPieceAt(rowY,colX).getColor() != selectedPiece.getColor()) {
                    board.movePiece(selectedPiece.getRow(), selectedPiece.getCol(), rowY, colX);
                    JPanel capturedSquare = (JPanel) chessBoard.getComponent((rowY * SIZE_ROW_BOARD) + colX);
                    capturedSquare.removeAll();
                    capturedSquare.repaint();
                    capturedSquare.revalidate();
                }
            }
            board.movePiece(selectedPiece.getRow(), selectedPiece.getCol(), rowY, colX);
            selectedPiece = null;
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

