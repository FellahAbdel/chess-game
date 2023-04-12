package com.diaby;

import javax.swing.*;
import java.awt.*;

public class BoardView extends JPanel {

    private ChessBoard board;
    private final Image[] images;

    public BoardView(ChessBoard board) {
        this.board = board;
        this.images = loadImages();

        setPreferredSize(new Dimension(400, 400));
    }

    private Image[] loadImages() {
        Image[] images = new Image[12];

        images[0] = Toolkit.getDefaultToolkit().getImage("images/w_pawn.png");
        images[1] = Toolkit.getDefaultToolkit().getImage("images/w_knight.png");
        images[2] = Toolkit.getDefaultToolkit().getImage("images/w_bishop.png");
        images[3] = Toolkit.getDefaultToolkit().getImage("images/w_rook.png");
        images[4] = Toolkit.getDefaultToolkit().getImage("images/w_queen.png");
        images[5] = Toolkit.getDefaultToolkit().getImage("images/w_king.png");
        images[6] = Toolkit.getDefaultToolkit().getImage("images/b_pawn.png");
        images[7] = Toolkit.getDefaultToolkit().getImage("images/b_knight.png");
        images[8] = Toolkit.getDefaultToolkit().getImage("images/b_bishop.png");
        images[9] = Toolkit.getDefaultToolkit().getImage("images/b_rook.png");
        images[10] = Toolkit.getDefaultToolkit().getImage("images/b_queen.png");
        images[11] = Toolkit.getDefaultToolkit().getImage("images/b_king.png");

        return images;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw board
        int x, y;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                x = j * 50;
                y = i * 50;

                if ((i + j) % 2 == 0) {
                    g.setColor(Color.WHITE);
                } else {
                    g.setColor(Color.GRAY);
                }

                g.fillRect(x, y, 50, 50);

                // Draw piece
                if (board.isPieceAt(i, j)) {
                    ChessPiece piece = board.getPieceAt(i, j);
                    int index = piece.getColor() == Color.WHITE ? 0 : 6;

                    if (piece instanceof Pawn) {
                        index += 0;
                    } else if (piece instanceof Knight) {
                        index += 1;
                    } else if (piece instanceof Bishop) {
                        index += 2;
                    } else if (piece instanceof Rook) {
                        index += 3;
                    } else if (piece instanceof Queen) {
                        index += 4;
                    } else if (piece instanceof King) {
                        index += 5;
                    }

                    g.drawImage(images[index], x, y, this);
                }
            }
        }
    }
}

