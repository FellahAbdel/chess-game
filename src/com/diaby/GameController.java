package com.diaby;

public class GameController {
    protected ChessBoard board;
    protected BoardView boardView;
    protected ChessPiece selectedPiece;

    public GameController(ChessBoard board, BoardView boardView) {
        this.board = board;
        this.boardView = boardView;
    }

    public void play() {
        // Boucle de jeu
        while (!ReglesDuJeu.estFinDeJeu(board)) {
            // Afficher le plateau de jeu
            boardView.display(board);

            // Demander à l'utilisateur de sélectionner une pièce
            int[] startCoords = boardView.getPieceSelection();

            // Vérifier si une pièce est présente à la position sélectionnée
            if (!board.isPieceAt(startCoords[0], startCoords[1])) {
                boardView.displayErrorMessage("Aucune pièce sélectionnée. Veuillez réessayer.");
                continue;
            }

            // Récupérer la pièce sélectionnée
            selectedPiece = board.getPieceAt(startCoords[0], startCoords[1]);

            // Vérifier si la pièce appartient au joueur en cours
            if (selectedPiece.getColor() != currentPlayerColor) {
                boardView.displayErrorMessage("Cette pièce n'appartient pas au joueur en cours. Veuillez réessayer.");
                continue;
            }

            // Demander à l'utilisateur de sélectionner une position d'arrivée
            int[] endCoords = boardView.getMoveSelection();

            // Vérifier si le déplacement est valide
            if (!ReglesDuJeu.estDeplacementValide(selectedPiece, endCoords[0], endCoords[1], board)) {
                boardView.displayErrorMessage("Déplacement invalide. Veuillez réessayer.");
                continue;
            }

            // Déplacer la pièce sur le plateau de jeu
            board.movePiece(startCoords[0], startCoords[1], endCoords[0], endCoords[1]);

            // Passer au joueur suivant
            currentPlayerColor = currentPlayerColor == Color.WHITE ? Color.BLACK : Color.WHITE;
        }

        // Afficher le message de fin de jeu
        boardView.displayEndOfGameMessage();
    }
}

