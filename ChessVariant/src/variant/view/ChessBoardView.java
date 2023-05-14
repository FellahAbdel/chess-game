package variant.view;

import variant.controller.GameRules;
import variant.model.ChessBoard;
import variant.model.ChessPiece;
import variant.model.Pawn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Fenetre graphique (classe heritant de JFrame) qui reagit au clic de souris (implements MouseListener)
 * qui affiche le damier, qui permet de selectionner/deplacer des pieces d'echec
 */
public class ChessBoardView extends Game implements MouseListener {
    /**
     * Panneau principal
     */
    JPanel mainPanel;
    /**
     * Panneau correspondant au damier
     */
    JPanel chessBoard;
    static final int SIZE_COLUMN_BOARD = 12;
    static final int SIZE_ROW_BOARD = 8;
    static final int SIZE_CASE_X = 75;
    static final int SIZE_CASE_Y = 75;
    ChessBoard board;

    static final Color BLACK_CASE = Color.gray;
    static final Color WHITE_CASE = Color.white;
    static final Color HIGHLIGHT_CASE = Color.red;
    private final boolean whitePiecesAtBottom;

    private boolean isTurn = true;
    private ChessPiece sourcePiece;

    public ChessBoardView(boolean whitePiecesAtBottom) {

        this.whitePiecesAtBottom = whitePiecesAtBottom;

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
        chessBoard.setLayout(new GridLayout(SIZE_ROW_BOARD, SIZE_COLUMN_BOARD)); // le damier sera une grille N * M
        chessBoard.setPreferredSize(boardSize);
        chessBoard.setBounds(0, 0, boardSize.width, boardSize.height);

        // Dans le damier, ajout de N*M panneau, chacun correspondant à une case de la grille
        for (int i = 0; i < SIZE_COLUMN_BOARD * SIZE_ROW_BOARD; i++) {
            JPanel square = new JPanel(new BorderLayout());
            square.setBorder(BorderFactory.createLineBorder(Color.black));
            chessBoard.add(square);
        }

        // Instanciation de l'échiquier
        board = new ChessBoard();
        board.initialize(whitePiecesAtBottom);
        // Afiiche la grille
        drawGrid();
    }

    /**
     * Methode d'affichage de la grille
     */
    public void drawGrid() {
        JPanel square;
        String filePath = "src/com/diaby/model/img/";
        String fileName;
        String pieceName;
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
                    pieceName = piece.getPieceName();
                    fileName = piece.isWhite() ? filePath + pieceName + "_b.png" : filePath + pieceName + "_n.png";
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
     * Promouvoir le pion sélectionné en une autre pièce.
     *
     * @param promotionDialog La boîte de dialogue de promotion.
     * @param pawn            Le pion à promouvoir.
     * @param rowY            La rangée Y où se trouve le pion.
     * @param colX            La colonne X où se trouve le pion.
     * @param pieceType       Le type de pièce auquel promouvoir le pion (Reine, Tour, Fou, Cavalier).
     * @param imageName       Le nom du fichier image à utiliser pour représenter la nouvelle pièce.
     */
    private void promoteInto(JDialog promotionDialog, Pawn pawn, int rowY, int colX, String pieceType,
                             String imageName) {
        pawn.promotePawn(pawn, rowY, colX, pieceType, board.getTileBoard());
        promotionDialog.dispose();
        JPanel promotionSquare = (JPanel) chessBoard.getComponent((rowY * SIZE_ROW_BOARD) + colX);
        promotionSquare.removeAll();
        JLabel image = new JLabel(new ImageIcon(imageName));
        promotionSquare.add(image);
        promotionSquare.repaint();
        promotionSquare.revalidate();
    }

    /**
     * Affiche la boîte de dialogue de promotion et ajoute les boutons pour chaque type de promotion.
     * Cette méthode est appelée lorsqu'un pion atteint l'autre extrémité du plateau.
     *
     * @param selectedPiece La pièce sélectionnée à promouvoir.
     * @param rowY          La ligne y sur laquelle le pion est promu.
     * @param colX          La colonne x sur lequel le pion est promu.
     */
    private void promotionView(ChessPiece selectedPiece, int rowY, int colX) {
        // Ouvre la boîte de dialogue de promotion
        JDialog promotionDialog = new JDialog();
        promotionDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        promotionDialog.setSize(400, 120);
        promotionDialog.setTitle("Promote into");
        promotionDialog.setLocationRelativeTo(null);
        promotionDialog.setLayout(new GridLayout(1, 8));
        promotionDialog.setUndecorated(true); // enlève le bouton de fermeture du panneau

        JButton queenButton;
        JButton rookButton;
        JButton bishopButton;
        JButton knightButton;
        JButton princesseButton;
        JButton imperatriceButton;
        JButton sauterelleButton;
        JButton noctambuleButton;
        Pawn pawn = (Pawn) selectedPiece;

        // Ajoute les boutons pour chaque type de promotion
        String imagePath = "src/com/diaby/model/img/";
        String queen = pawn.isWhite() ? imagePath + "reine_b.png" : imagePath + "reine_n.png";
        String rook = pawn.isWhite() ? imagePath + "tour_b.png" : imagePath + "tour_n.png";
        String bishop = pawn.isWhite() ? imagePath + "fou_b.png" : imagePath + "fou_n.png";
        String knight = pawn.isWhite() ? imagePath + "cavalier_b.png" : imagePath + "cavalier_n.png";
        String princesse = pawn.isWhite() ? imagePath + "princesse_b.png" : imagePath + "princesse_n.png";
        String imperatrice = pawn.isWhite() ? imagePath + "imperatrice_b.png" : imagePath + "imperatrice_n.png";
        String sauterelle = pawn.isWhite() ? imagePath + "sauterelle_b.png" : imagePath + "sauterelle_n.png";
        String noctambule = pawn.isWhite() ? imagePath + "noctambule_b.png" : imagePath + "noctambule_n.png";

        queenButton = new JButton(new ImageIcon(queen));
        rookButton = new JButton(new ImageIcon(rook));
        bishopButton = new JButton(new ImageIcon(bishop));
        knightButton = new JButton(new ImageIcon(knight));
        princesseButton = new JButton(new ImageIcon(princesse));
        imperatriceButton = new JButton(new ImageIcon(imperatrice));
        sauterelleButton = new JButton(new ImageIcon(sauterelle));
        noctambuleButton = new JButton(new ImageIcon(noctambule));

        promotionDialog.add(queenButton);
        promotionDialog.add(rookButton);
        promotionDialog.add(bishopButton);
        promotionDialog.add(knightButton);
        promotionDialog.add(princesseButton);
        promotionDialog.add(imperatriceButton);
        promotionDialog.add(sauterelleButton);
        promotionDialog.add(noctambuleButton);

        queenButton.addActionListener(e1 -> promoteInto(promotionDialog, pawn, rowY, colX, "Queen", queen));
        rookButton.addActionListener(e12 -> promoteInto(promotionDialog, pawn, rowY, colX, "Rook", rook));
        bishopButton.addActionListener(e13 -> promoteInto(promotionDialog, pawn, rowY, colX, "Bishop", bishop));
        knightButton.addActionListener(e14 -> promoteInto(promotionDialog, pawn, rowY, colX, "Knight", knight));
        princesseButton.addActionListener(e15 -> promoteInto(promotionDialog, pawn, rowY, colX, "Princesse",
                princesse));
        imperatriceButton.addActionListener(e16 -> promoteInto(promotionDialog, pawn, rowY, colX, "Imperatrice",
                imperatrice));
        sauterelleButton.addActionListener(e17 -> promoteInto(promotionDialog, pawn, rowY, colX, "Sauterelle",
                sauterelle));
        noctambuleButton.addActionListener(e18 -> promoteInto(promotionDialog, pawn, rowY, colX, "Noctambule",
                noctambule));

        // fermeture une fois le clique capturé
        queenButton.addActionListener(e1 -> promotionDialog.dispose());
        rookButton.addActionListener(e1 -> promotionDialog.dispose());
        bishopButton.addActionListener(e1 -> promotionDialog.dispose());
        knightButton.addActionListener(e1 -> promotionDialog.dispose());
        princesseButton.addActionListener(e1 -> promotionDialog.dispose());
        imperatriceButton.addActionListener(e1 -> promotionDialog.dispose());
        sauterelleButton.addActionListener(e1 -> promotionDialog.dispose());
        noctambuleButton.addActionListener(e1 -> promotionDialog.dispose());

        promotionDialog.setVisible(true);

    }

    /**
     * Supprime une case de l'interface graphique à partir de ses coordonnées (rangée, colonne).
     *
     * @param rowY la rangée de la case à supprimer
     * @param colX la colonne de la case à supprimer
     */
    public void removeSquare(int rowY, int colX) {
        JPanel squareToRemove = (JPanel) chessBoard.getComponent((rowY * SIZE_COLUMN_BOARD) + colX);
        squareToRemove.removeAll();
        squareToRemove.repaint();
        squareToRemove.revalidate();
    }

    /**
     * Capture du bouton presse du pointeur sur la fenêtre graphique
     *
     * @param e événement pointeur presse
     */
    public void mousePressed(MouseEvent e) {
        // Conversion de la position cliquée en position de la grille
        int colX = e.getX() / (ChessBoardView.SIZE_CASE_X);
        int rowY = e.getY() / (ChessBoardView.SIZE_CASE_Y);

        ChessPiece selectedPiece = board.getPieceAt(rowY, colX);
        // Pièce non null et non mis en evidence (Pour ne pas qu'en cliquant sur la piece adverse au lieu de la
        // bouffer on affiche les mouvements possibles de la pièce adverse).

        if (selectedPiece != null && !board.highLightCase[rowY][colX] && selectedPiece.isWhite() == isTurn) { //
            // Premier clic pour sélectionner la pièce
            // On clique sur l'un des pions.
            // Liste des coordonnées possibles du joueur.
            ArrayList<int[]> moves = selectedPiece.possiblesMoves(rowY, colX, board.getTileBoard());
            board.resetHighlight();

            // On met en evidence tous les mouvements possibles du joueur.
            for (int[] move : moves) {
                int i = move[0];
                int j = move[1];
                board.highLightCase[i][j] = true;
            }

            sourcePiece = selectedPiece;

        }

        // Si on clique sur une tuile mise en evidence.
        if (board.highLightCase[rowY][colX]) {
            board.resetHighlight();
            // C'est là qu'on fait les tests de déplacements.
            int sourceRow = sourcePiece.getRow();
            int sourceCol = sourcePiece.getCol();
            removeSquare(sourceRow, sourceCol);

            if (sourcePiece instanceof Pawn) {
                if (colX != sourceCol && selectedPiece != null && sourcePiece.getColor() != selectedPiece.getColor()) {
                    board.movePiece(sourceRow, sourceCol, rowY, colX);
                    removeSquare(rowY, colX);
                }
                if ((rowY == 0 || rowY == 7)) {
                    promotionView(selectedPiece, rowY, colX);
                }
            } else if (selectedPiece != null && board.isOccupied(rowY, colX) && selectedPiece.getColor() != sourcePiece.getColor()) {
                board.movePiece(sourceRow, selectedPiece.getCol(), rowY, colX);
                removeSquare(rowY, colX);
            }
            board.movePiece(sourceRow, sourceCol, rowY, colX);
            // On passe au joueur suivant que si et seulement si le joueur courant a fini son mouvement.
            isTurn = sourcePiece.getColor() != Color.WHITE;

            if (GameRules.draw(isTurn, board)) {
                JOptionPane.showMessageDialog(mainPanel, "Fin du jeu c'est un pat");
                dispose();
            }

            if (GameRules.checkMate(isTurn, board.getTileBoard(), board)) {
                JOptionPane.showMessageDialog(mainPanel, "Fin du jeu, échec et mat ");
                dispose();
            }
        }
        // Mise à jour de l'affichage
        drawGrid();
    }


    public void mouseReleased(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
        // On met en evidence les mouvements possibles
    }

    public void mouseExited(MouseEvent e) {
        // On efface l'évidence.
    }

    /**
     * Crée une fenêtre graphique correspondant au damier et l'affiche.
     */
    public void displayBoard() {
        // Définir et afficher la fenêtre graphique correspondant au damier
        JFrame frame = new ChessBoardView(whitePiecesAtBottom);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}

