import com.diaby.controller.GameController;
import com.diaby.model.ChessBoard;
import com.diaby.model.*;

import java.awt.*;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
/*        // Initialisation du jeu
        ChessBoard game = new ChessBoard();
        Scanner scanner = new Scanner(System.in);
        boolean gameOver = false;
        Player p1 = new Player("r", Color.white);
        Player p2 = new Player("p", Color.black);

        // Choix aléatoire du joueur qui commence
        Random rnd = new Random();
        Player currentPlayer = rnd.nextInt(2) == 0 ? p1 : p2;*/

        // Boucle principale
//        while (!gameOver) {
//            // Affichage du plateau de jeu
//            game.printBoard();
//
//
//
//            // Conversion des entrées en coordonnées
//
//
//            // Vérification de la validité du mouvement
//
//
//        // Fermeture du scanner
//       }

        GameController game = new GameController();
        game.startGame();
    }
}

