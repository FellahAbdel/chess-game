package com.diaby.view;
import com.diaby.model.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
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

    private ChessPiece sourcePiece;
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



    private void removeSquare(JPanel oldSquare){
        oldSquare.removeAll();
        oldSquare.repaint();
        oldSquare.revalidate();
    }

    private void promoteInto(JDialog promotionDialog, Pawn pawn, int rowY, int colX,String pieceType, String imageName){
        pawn.promotePawn(pawn, rowY, colX, pieceType, board.getTileBoard());
        promotionDialog.dispose();
        JPanel promotionSquare = (JPanel) chessBoard.getComponent((rowY * SIZE_ROW_BOARD) + colX);
        promotionSquare.removeAll();
        String fileName = imageName;
        JLabel image = new JLabel(new ImageIcon(fileName));
        promotionSquare.add(image);
        promotionSquare.repaint();
        promotionSquare.revalidate();
    }

    private void promotionView(ChessPiece selectedPiece, int rowY, int colX){
        // Ouvre la boîte de dialogue de promotion
        JDialog promotionDialog = new JDialog();
        promotionDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        promotionDialog.setSize(250, 120);
        promotionDialog.setTitle("Promote into");
        promotionDialog.setLocationRelativeTo(null);
        promotionDialog.setLayout(new GridLayout(1, 4));
        promotionDialog.setUndecorated(false); // enlève le bouton de fermeture du panneau
        promotionDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        JButton queenButton;
        JButton rookButton;
        JButton bishopButton;
        JButton knightButton;
        Pawn pawn = (Pawn) selectedPiece;

        // Ajoute les boutons pour chaque type de promotion
        String imagePath = "src/com/diaby/model/img/";
        String queen = pawn.isWhite() ? imagePath + "reine_b.png" : imagePath + "reine_n.png";
        String rook = pawn.isWhite() ? imagePath + "tour_b.png" : imagePath + "tour_n.png";
        String bishop = pawn.isWhite() ? imagePath + "fou_b.png" : imagePath + "fou_n.png";
        String knight = pawn.isWhite() ? imagePath + "cavalier_b.png" : imagePath + "cavalier_n.png";

        queenButton = new JButton(new ImageIcon(queen));
        rookButton = new JButton(new ImageIcon(rook));
        bishopButton = new JButton(new ImageIcon(bishop));
        knightButton = new JButton(new ImageIcon(knight));

        promotionDialog.add(queenButton);
        promotionDialog.add(rookButton);
        promotionDialog.add(bishopButton);
        promotionDialog.add(knightButton);

        queenButton.addActionListener(e1 -> {
            this.promoteInto(promotionDialog, pawn, rowY, colX, "Queen", queen);
        });

        rookButton.addActionListener(e12 -> {
            this.promoteInto(promotionDialog, pawn, rowY, colX, "Rook", rook);

        });

        bishopButton.addActionListener(e13 -> {
            this.promoteInto(promotionDialog, pawn, rowY, colX, "Bishop", bishop);
        });

        knightButton.addActionListener(e14 -> {
            this.promoteInto(promotionDialog, pawn, rowY, colX, "Knight", knight);
        });

        // fermeture une fois le clique capturé
        queenButton.addActionListener(e1 -> promotionDialog.dispose());
        rookButton.addActionListener(e1 -> promotionDialog.dispose());
        bishopButton.addActionListener(e1 -> promotionDialog.dispose());
        knightButton.addActionListener(e1 -> promotionDialog.dispose());

        promotionDialog.setVisible(true);
        board.movePiece(selectedPiece.getRow(), selectedPiece.getCol(), rowY, colX);
    }

    /**
     *
     * @param sourceRow
     * @param sourceCol
     * @param rowY
     * @param colX
     * @param startColumn
     * @param endColumn
     * @param n
     */
    private void castling(int sourceRow, int sourceCol, int rowY, int colX, int startColumn, int endColumn, int n){
        // Déplace la tour.
        board.movePiece(sourceRow, startColumn, sourceRow, endColumn);
        // Déplace le roi
        board.movePiece(sourceRow, sourceCol, rowY, colX);
        // Mise à jour de l'interface graphique.
        JPanel oldKingSquare = (JPanel) chessBoard.getComponent(rowY * SIZE_ROW_BOARD + 4);
        JPanel oldRookSquare = (JPanel) chessBoard.getComponent(rowY * SIZE_ROW_BOARD + n);

        this.removeSquare(oldKingSquare);
        this.removeSquare(oldRookSquare);
    }
    public void mousePressed(MouseEvent e) {
        // Conversion de la position cliquée en position de la grille
        int colX = e.getX() / (ChessBoardView.SIZE_CASE_X);
        int rowY = e.getY() / (ChessBoardView.SIZE_CASE_Y);

        int selectedComponentIndex = rowY * SIZE_ROW_BOARD + colX ;

        ChessPiece selectedPiece = board.getPieceAt(rowY, colX);
        // Pièce non null et non mis en evidence (Pour ne pas qu'en cliquant sur la piece adverse au lieu de la
        // bouffer on affiche les mouvements possibles de la pièce adverse.
        if(selectedPiece != null && !board.highLightCase[rowY][colX]){
            // On cliqué sur l'un des pions.
            // Liste des coordonnées possibles du joueur.
            ArrayList<int[]> moves = selectedPiece.possiblesMoves(rowY, colX, board.getTileBoard());
            board.resetHighlight();

            // On met en evidence tous les mouvements possibles du joueur.
            for(int[] move : moves){
                int  i = move[0];
                int j = move[1];
                board.highLightCase[i][j] = true ;
            }

            this.sourcePiece = selectedPiece ;
            /*selectedRow = selectedPiece.getRow();
            selectedCol = selectedPiece.getCol();*/
        }
        // Si on clique sur une tuile mise en evidence.
        if (board.highLightCase[rowY][colX]) {
            board.resetHighlight();
            // C'est là qu'on  fait les tests de déplacements.
            int sourceRow = sourcePiece.getRow();
            int sourceCol = sourcePiece.getCol();
            int componentIndex = sourceRow * SIZE_ROW_BOARD + sourceCol;
            JPanel oldSquare = (JPanel) chessBoard.getComponent(componentIndex);
            this.removeSquare(oldSquare);

            // Verification pour la prise en passant.
            if (sourcePiece instanceof Pawn && colX != sourceCol && selectedPiece == null) {
                board.movePiece(sourceRow, sourceCol, rowY, colX);
                int capturedPieceRow = sourcePiece.isWhite() ? rowY - 1 : rowY + 1 ;
                int capturedSquareIndex = capturedPieceRow * SIZE_ROW_BOARD + colX ;
                JPanel capturedSquare = (JPanel) chessBoard.getComponent(capturedSquareIndex);
                this.removeSquare(capturedSquare);
            }else if(sourcePiece instanceof Pawn && colX != sourceCol && selectedPiece != null && sourcePiece.getColor() != selectedPiece.getColor()) {
                board.movePiece(sourceRow, sourceCol, rowY, colX);
                JPanel capturedSquare = (JPanel) chessBoard.getComponent(selectedComponentIndex);
                this.removeSquare(capturedSquare);
            } else if(sourcePiece instanceof Pawn && (rowY == 0 || rowY == 7)){
                promotionView(sourcePiece, rowY, colX);
            } else if (sourcePiece instanceof King && sourceRow == rowY && Math.abs(sourceCol - colX) == 2){
                // si nous sommes sur la même ligne, aucune piece entre la tour et le roi et que le roi et la toure n'ont pas été deplacé alors
                // on peut roqué. (Aussi le roi n'est pas en position d'echec)
                // Roque court.
                if(colX > sourceCol){
                    this.castling(sourceRow, sourceCol, rowY, colX, 7, 5, 7);
                }// Roque long.
                else if(colX < sourceCol){
                    this.castling(sourceRow, sourceCol, rowY, colX, 0, 3, 0);
                }
            } else if (board.isOccupied(rowY, colX) && selectedPiece.getColor() != sourcePiece.getColor()){
                // Il y a une pièce adverse à la destination.
                board.movePiece(sourceRow, sourceCol, rowY, colX);
                JPanel capturedSquare = (JPanel) chessBoard.getComponent(selectedComponentIndex);
                this.removeSquare(capturedSquare);
            }
            board.movePiece(sourceRow, sourceCol, rowY, colX);
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

    public void displayBoard(){
        // Definir et afficher la fenetre graphique correspondant au damier
        JFrame frame = new ChessBoardView();
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE );
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo( null );
        frame.setVisible(true);
    }
}

