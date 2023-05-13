package variant.view;


import variant.controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


/**
 * Fenêtre graphique (classe héritant de JFrame) qui réagit au clic de souris (implements MouseListener)
 * qui affiche deux pions de couleur opposés, qui permet de sélectionner la couleur des pièces qui sera en bas
 * de l'échiquier ou en haut de l'échiquier.
 */
public class Game extends JFrame implements MouseListener {

    private boolean whitePiecesAtBottom;
    private ChessBoardView chessBoardView;
    private GameController gameController;

    private JButton playButton;

    public Game() {
        whitePiecesAtBottom = false;
    }

    /**
     * Méthode permettant de donner un visuel afin de faciliter le choix de la couleur de pieces
     * dont on désire jouer avec.
     */
    public void createAndShowGUI() {
        setTitle("Jouer aux échecs");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 300);
        setLocationRelativeTo(null); // Centres the window on the screen
        setResizable(false); // Prevents resizing the window

        // Add the two buttons with images
        JButton whiteButton = new JButton(new ImageIcon("src/com/diaby/model/img/Pawn_b.png"));
        whiteButton.setName("white");
        whiteButton.setPreferredSize(new Dimension(120, 120)); // Sets the button size
        whiteButton.addMouseListener(this);

        JButton blackButton = new JButton(new ImageIcon("src/com/diaby/model/img/Pawn_n.png"));
        blackButton.setName("black");
        blackButton.setPreferredSize(new Dimension(120, 120)); // Sets the button size
        blackButton.addMouseListener(this);

        playButton = new JButton("Commencer la partie");
        playButton.setEnabled(false);
        playButton.setPreferredSize(new Dimension(120, 40)); // Sets the button size
        playButton.addActionListener(e -> {
            dispose();
            chessBoardView = new ChessBoardView(whitePiecesAtBottom);
            gameController = new GameController(chessBoardView);
            gameController.startGame();
        });

        JPanel panel = new JPanel();
        panel.setBackground(new Color(0x22, 0x22, 0x22)); // Sets the panel background color
        panel.setLayout(new BorderLayout());

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(new Color(0x22, 0x22, 0x22)); // Sets the panel background color
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); // Centers the buttons and adds some space
        // between them
        buttonsPanel.add(whiteButton);
        buttonsPanel.add(blackButton);

        JLabel label = new JLabel("Faite le choix de la couleur de la pièce qui sera placé en bas de l'échiquier");
        label.setForeground(Color.WHITE); // Sets the label text color
        label.setHorizontalAlignment(JLabel.CENTER);

        panel.add(label, BorderLayout.NORTH);
        panel.add(buttonsPanel, BorderLayout.CENTER);
        panel.add(playButton, BorderLayout.SOUTH);

        getContentPane().add(panel);
        setVisible(true);
    }

    /**
     * Met à true ou false whitePiecesAtBottom.
     * Si true, les pieces blanches sont en bas de la grille de jeu.
     * Si false, les pieces blanches sont en haut de la grille de jeu.
     *
     * @param e événement pointeur clic.
     */
    public void mouseClicked(MouseEvent e) {
        JButton button = (JButton) e.getSource();
        if (button.getName().equals("white")) {
            whitePiecesAtBottom = true;
        }
        if (button.getName().equals("black")) {
            whitePiecesAtBottom = false;
        }

        playButton.setEnabled(true);
    }


    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    /**
     * Affiche l'interface player.
     */
    public void displayGame() {
        SwingUtilities.invokeLater(() -> {
            Game game = new Game();
            game.createAndShowGUI();
        });
    }

}
