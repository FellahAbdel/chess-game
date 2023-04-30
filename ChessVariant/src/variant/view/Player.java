package variant.view;


import variant.controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class Player extends JFrame implements MouseListener {

    private boolean isWhiteTurn;
    private ChessBoardView chessBoardView ;
    private GameController game;

    private JButton playButton;

    public Player() {
        isWhiteTurn = false;
    }

    public void createAndShowGUI() {
        setTitle("Jouer aux échecs");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
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
            chessBoardView = new ChessBoardView(isWhiteTurn);
            game = new GameController(chessBoardView);
            game.startGame();
        });

        JPanel panel = new JPanel();
        panel.setBackground(new Color(0x22, 0x22, 0x22)); // Sets the panel background color
        panel.setLayout(new BorderLayout());

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(new Color(0x22, 0x22, 0x22)); // Sets the panel background color
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); // Centers the buttons and adds some space between them
        buttonsPanel.add(whiteButton);
        buttonsPanel.add(blackButton);

        JLabel label = new JLabel("Premier joueur à commencer la partie ");
        label.setForeground(Color.WHITE); // Sets the label text color
        label.setHorizontalAlignment(JLabel.CENTER);

        panel.add(label, BorderLayout.NORTH);
        panel.add(buttonsPanel, BorderLayout.CENTER);
        panel.add(playButton, BorderLayout.SOUTH);

        getContentPane().add(panel);
        setVisible(true);
    }

    public void mouseClicked(MouseEvent e) {
        JButton button = (JButton) e.getSource();
        if (button.getName().equals("white")) {
            isWhiteTurn = true;
        }
        if (button.getName().equals("black")) {
            isWhiteTurn = false;
        }

        playButton.setEnabled(true);
    }


    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    public void displayPlayer()
    {
        SwingUtilities.invokeLater(() -> {
            Player player = new Player();
            player.createAndShowGUI();
        });
    }

}
