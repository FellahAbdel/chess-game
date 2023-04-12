package com.diaby.view;
import com.diaby.model.ChessBoard;
import com.diaby.model.ChessPiece;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/** Fenetre graphique (classe heritant de JFrame) qui reagit au clic de souris (implements MouseListener)
 *   qui affiche le damier, qui permet de selectionner/deplacer des pieces d'echec
 */
public class ChessBoardView extends JFrame implements MouseListener {
    /** Panneau principal */
    JPanel mainPanel;
    /** Panneau correspondant au damier */
    JPanel chessBoard;
    static final int SIZE_LINE_BOARD = 8;
    static final int SIZE_ROW_BOARD = 8;
    static final int SIZE_CASE_X = 75;
    static final int SIZE_CASE_Y = 75;
    ChessBoard board;

    static final Color BLACK_CASE = Color.gray;
    static final Color WHITE_CASE = Color.white;
    static final Color HIGHLIGHT_CASE = Color.red;

    public ChessBoardView(){
        // Taille d'un element graphique
        Dimension boardSize = new Dimension(SIZE_CASE_X * SIZE_LINE_BOARD, SIZE_CASE_Y * SIZE_ROW_BOARD);

        // Definition du panel principal
        mainPanel = new JPanel();
        getContentPane().add(mainPanel); // Ajout du panel principal a la fenetre principale (JFrame)
        mainPanel.setPreferredSize(boardSize);
        mainPanel.addMouseListener(this); // Ajout de la gestion des clics

        // Definition du panel contenant le damier
        chessBoard = new JPanel();
        mainPanel.add(chessBoard); // Ajout du panel damier au panel principal
        chessBoard.setLayout( new GridLayout(SIZE_LINE_BOARD, SIZE_ROW_BOARD) ); // le damier sera une grille N * M
        chessBoard.setPreferredSize( boardSize );
        chessBoard.setBounds(0, 0, boardSize.width, boardSize.height);

        // Dans le damier, ajout de N*M panneau, chacun correspondant a une case de la grille
        for (int i = 0; i < SIZE_LINE_BOARD * SIZE_ROW_BOARD; i++) {
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
        // Pour chaque case de la grille
        for (int i = 0; i < SIZE_LINE_BOARD * SIZE_ROW_BOARD; i++) {
            square = (JPanel)chessBoard.getComponent(i);

            // Dessin case noire/ case blanche
            int row = (i / SIZE_LINE_BOARD) % 2;
            if (row == 0)
                square.setBackground( i % 2 == 0 ? BLACK_CASE : WHITE_CASE );
            else
                square.setBackground( i % 2 == 0 ? WHITE_CASE : BLACK_CASE );

            // Dessin d'une piece (image chargee dans un jlabel, ajoutee au jpanel)
            ChessPiece pieces = board.getPieceAt(i%SIZE_LINE_BOARD, i/SIZE_LINE_BOARD);
            if( pieces != null ) {
                JLabel piece = new JLabel( new ImageIcon("src/img/" + pieces.getImageName()) );
                square.add(piece);
            }

            // Dessin de la case mise en valeur (changement de la couleur du fond du jpanel)
            if(board.highLightCase[i%SIZE_LINE_BOARD][i/SIZE_LINE_BOARD] ) { square.setBackground(HIGHLIGHT_CASE); }
        }

        // Forcer la mise à jour de l'affichage
        revalidate();
        repaint();
    }

    /**
     * Capture du bonton presse du pointeur sur la fenetre graphique
     * @param e evenement pointeur presse
     */
    public void mousePressed(MouseEvent e){
        // Conversion de la position cliquee en position de la grille
        int c = e.getX() / (ChessBoardView.SIZE_CASE_X);
        int l = e.getY() / (ChessBoardView.SIZE_CASE_Y);

        // si la position cliquee correspond a une piece, affichage des deplacements
        if(board.getPieceAt(c,l) != null) { board.resetHighlight(); board.highLightCase[c][l+1] = true; }

        // si la position correspond a une position de deplacement possible, effectuer le deplacement
        if(board.highLightCase[c][l]) {
            // Supression de l'ancienne piece (jplabel dans le jpanel correspondant à la case de la grille)
            ((JPanel)chessBoard.getComponent((l-1) * SIZE_ROW_BOARD +c)).removeAll();
            board.resetHighlight();
            // Deplacement de la piece
            board.movePiece(c,l-1,c,l); }
        // Mettre a jour l'affichage
        drawGrid();
    }

    public void mouseReleased(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e) {}

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

