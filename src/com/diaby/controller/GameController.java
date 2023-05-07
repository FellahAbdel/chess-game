package com.diaby.controller;
import com.diaby.view.ChessBoardView;

/**
 * Classe en charge de lancer le jeu.
 */
public class GameController {
    private ChessBoardView chessBoardView;

    /**
     * Constructeur pour instancier une instance de ChessBoardView dans GameController.
     *
     * @param chessBoardView l'instance de ChessBoardview à instancier.
     */
    public GameController(ChessBoardView chessBoardView) {
        this.chessBoardView = chessBoardView;
    }

    /**
     * méthode permettant de lancer le jeu.
     */
    public void startGame() {
        // afficher plateau jeu
        chessBoardView.displayBoard();
    }

}
