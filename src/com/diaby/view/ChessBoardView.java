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
                // Incrémentation de l'indice pour passer à la case suivante
                index++;
            }
        }
    }
    /**
     * Capture du bonton presse du pointeur sur la fenetre graphique
     * @param e evenement pointeur presse
     */
    public void mousePressed(MouseEvent e){
        // Conversion de la position cliquee en position de la grille
        int row = e.getX() / (ChessBoardView.SIZE_CASE_X);
        int col = e.getY() / (ChessBoardView.SIZE_CASE_Y);

        // si la position cliquee correspond a une piece, affichage des deplacements
        if(board.getPieceAt(row,col) != null) { board.resetHighlight(); board.highLightCase[row][col+1] = true; }

        // si la position correspond a une position de deplacement possible, effectuer le deplacement
        if(board.highLightCase[row][col]) {
            // Supression de l'ancienne piece (jplabel dans le jpanel correspondant à la case de la grille)
            ((JPanel)chessBoard.getComponent((col-1) * SIZE_ROW_BOARD +row)).removeAll();
            board.resetHighlight();
            // Deplacement de la piece
            board.movePiece(row,col-1,row,col); }
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
/*
        JFrame frame = new JFrame();
        frame.setBounds(10, 10, 512, 512);
        frame.setUndecorated(true);
        JPanel pn=new JPanel(){
            @Override
            public void paint(Graphics g) {
                boolean white=true;
                for(int y= 0;y<8;y++){
                    for(int x= 0;x<8;x++){
                        if(white){
                            g.setColor(new Color(235,235, 208));
                        }else{
                            g.setColor(new Color(119, 148, 85));

                        }
                        g.fillRect(x*64, y*64, 64, 64);
                        white=!white;
                    }
                    white=!white;
                }*/
/*                for(Piece p: ps){
                    int ind=0;
                    if(p.name.equalsIgnoreCase("king")){
                        ind=0;
                    }
                    if(p.name.equalsIgnoreCase("queen")){
                        ind=1;
                    }
                    if(p.name.equalsIgnoreCase("bishop")){
                        ind=2;
                    }
                    if(p.name.equalsIgnoreCase("knight")){
                        ind=3;
                    }
                    if(p.name.equalsIgnoreCase("rook")){
                        ind=4;
                    }
                    if(p.name.equalsIgnoreCase("pawn")){
                        ind=5;
                    }
                    if(!p.isWhite){
                        ind+=6;
                    }
                    g.drawImage(imgs[ind], p.xp*64, p.yp*64, this);
                }*/
        /*    }

        };
        frame.add(pn);
        frame.setDefaultCloseOperation(3);
        frame.setVisible(true);
    }*/
}
}

