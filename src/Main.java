import com.diaby.model.ChessBoard;
import com.diaby.model.*;

import java.awt.*;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        // Initialisation du jeu
//        ChessBoard game = new ChessBoard();
//        Scanner scanner = new Scanner(System.in);
//        boolean gameOver = false;
//        Player p1 = new Player("r", Color.white);
//        Player p2 = new Player("p", Color.black);
//
//        // Choix aléatoire du joueur qui commence
//        Random rnd = new Random();
//        Player currentPlayer = rnd.nextInt(2) == 0 ? p1 : p2;
//
//        // Boucle principale
//        while (!gameOver) {
//            // Affichage du plateau de jeu
//            game.printBoard();
//
//            // Demande au joueur en cours de jouer
//            System.out.println("Au tour du joueur " + currentPlayer.getColor() + " de jouer !");
//            System.out.println("Entrez les coordonnées de la pièce que vous voulez déplacer (ex: a2) :");
//            String startInput = scanner.nextLine();
//            System.out.println("Entrez les coordonnées de la case où vous voulez déplacer la pièce (ex: a4) :");
//            String endInput = scanner.nextLine();
//
//            // Conversion des entrées en coordonnées
//            int startRow = Character.getNumericValue(startInput.charAt(1)) - 1;
//            int startCol = startInput.charAt(0) - 'a';
//            int endRow = Character.getNumericValue(endInput.charAt(1)) - 1;
//            int endCol = endInput.charAt(0) - 'a';
//
//            // Vérification de la validité du mouvement
//            if (game.movePiece(startRow, startCol, endRow, endCol)) {
//                // Changement de joueur
//                currentPlayer = currentPlayer == p1 ? p2 : p1;
//            } else {
//                System.out.println("Mouvement invalide. Veuillez réessayer.");
//            }
//        }
//
//        // Fermeture du scanner
//        scanner.close();

//        ChessBoard chess = new ChessBoard();
//        chess.printBoard();
//
//        System.out.println(chess.movePiece(1, 0, 2, 0));
//        chess.printBoard();

    }
}

