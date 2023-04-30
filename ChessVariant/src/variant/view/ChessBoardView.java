package variant.view;

import variant.model.ChessBoard;
import variant.model.ChessPiece;
import variant.model.Pawn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/** Fenetre graphique (classe heritant de JFrame) qui reagit au clic de souris (implements MouseListener)
 *   qui affiche le damier, qui permet de selectionner/deplacer des pieces d'echec
 */
public class ChessBoardViewVariant extends JFrame implements MouseListener {
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

    public ChessBoardViewVariant() {
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
        chessBoard.setLayout(new GridLayout(SIZE_ROW_BOARD,SIZE_COLUMN_BOARD)); // le damier sera une grille N * M
        chessBoard.setPreferredSize(boardSize);
        chessBoard.setBounds(0, 0, boardSize.width, boardSize.height);

        // Dans le damier, ajout de N*M panneau, chacun correspondant à une case de la grille
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

    protected ChessPiece selectedPiece = null;

    public void removeSquare(int rowY, int colX)
    {
        JPanel squareToRemove = (JPanel) chessBoard.getComponent((rowY * SIZE_COLUMN_BOARD) + colX);
        squareToRemove.removeAll();
        squareToRemove.repaint();
        squareToRemove.revalidate();
    }
    private void promoteInto(JDialog promotionDialog, Pawn pawn, int rowY, int colX, String pieceType, String imageName){
        pawn.promotePawn(pawn, rowY, colX, pieceType, board.getTileBoard());
        promotionDialog.dispose();
        JPanel promotionSquare = (JPanel) chessBoard.getComponent((rowY * SIZE_ROW_BOARD) + colX);
        promotionSquare.removeAll();
        JLabel image = new JLabel(new ImageIcon(imageName));
        promotionSquare.add(image);
        promotionSquare.repaint();
        promotionSquare.revalidate();
    }


    private void promotionView(ChessPiece selectedPiece, int rowY, int colX){
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
        String princesse = pawn.isWhite()? imagePath + "princesse_b.png" : imagePath + "princesse_n.png";
        String imperatrice = pawn.isWhite()? imagePath + "imperatrice_b.png" : imagePath + "imperatrice_n.png";
        String sauterelle = pawn.isWhite()? imagePath + "sauterelle_b.png" : imagePath + "sauterelle_n.png";
        String noctambule = pawn.isWhite()? imagePath + "noctambule_b.png" : imagePath + "noctambule_n.png";

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

        queenButton.addActionListener(e1 -> {
            promoteInto(promotionDialog, pawn, rowY, colX, "Queen", queen);
        });

        rookButton.addActionListener(e12 -> {
            promoteInto(promotionDialog, pawn, rowY, colX, "Rook", rook);

        });

        bishopButton.addActionListener(e13 -> {
            promoteInto(promotionDialog, pawn, rowY, colX, "Bishop", bishop);
        });

        knightButton.addActionListener(e14 -> {
            promoteInto(promotionDialog, pawn, rowY, colX, "Knight", knight);
        });
        princesseButton.addActionListener(e15 -> {
            promoteInto(promotionDialog,pawn,rowY,colX,"Princesse",princesse);

        });
        imperatriceButton.addActionListener(e16 -> {
            promoteInto(promotionDialog,pawn,rowY,colX,"Imperatrice",imperatrice);

        });
        sauterelleButton.addActionListener(e17 -> {
            promoteInto(promotionDialog,pawn,rowY,colX,"Sauterelle",sauterelle);

        });
        noctambuleButton.addActionListener(e18 -> {
            promoteInto(promotionDialog,pawn,rowY,colX,"Noctambule",noctambule);

        });

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
    public void mousePressed(MouseEvent e) {
        // Conversion de la position cliquée en position de la grille
        int colX = e.getX() / (ChessBoardViewVariant.SIZE_CASE_X);
        int rowY = e.getY() / (ChessBoardViewVariant.SIZE_CASE_Y);

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
                removeSquare(selectedPiece.getRow(),selectedPiece.getCol());

                if(selectedPiece instanceof Pawn)
                {
                    if(colX != selectedPiece.getCol() && piece == null)
                    {
                        int capturedPieceRow = selectedPiece.getColor() == Color.WHITE ? rowY - 1 : rowY + 1;
                        board.movePiece(selectedPiece.getRow(), selectedPiece.getCol(), rowY, colX);
                        removeSquare(capturedPieceRow,colX);
                    }
                    if(colX != selectedPiece.getCol() && piece != null && piece.getColor() != selectedPiece.getColor())
                    {
                        board.movePiece(selectedPiece.getRow(), selectedPiece.getCol(), rowY, colX);
                        removeSquare(rowY,colX);
                    }
                    if((rowY == 0 || rowY == 7))
                    {
                        promotionView(selectedPiece,rowY,colX);
                    }
                }
                else if (board.isOccupied(rowY, colX) && board.getPieceAt(rowY,colX).getColor() != selectedPiece.getColor()) {
                    board.movePiece(selectedPiece.getRow(), selectedPiece.getCol(), rowY, colX);
                    removeSquare(rowY,colX);
                }
                board.movePiece(selectedPiece.getRow(), selectedPiece.getCol(), rowY, colX);
                selectedPiece = null;
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
        JFrame frame = new ChessBoardViewVariant();
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE );
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo( null );
        frame.setVisible(true);
}
}

