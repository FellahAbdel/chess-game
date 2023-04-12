package com.diaby;

public class RegleDuJeu {
    public static boolean estFinDeJeu(PlateauDeJeu plateau) {
        // Vérifie si l'un des joueurs a perdu toutes ses pièces ou si le jeu est bloqué
        return plateau.estJoueurSansPieces(Couleur.BLANC) || plateau.estJoueurSansPieces(Couleur.NOIR) || plateau.estJeuBloque();
    }
    /*  public static boolean estFinDeJeu(PlateauDeJeu plateau) {
        // Vérifie si l'un des joueurs a perdu toutes ses pièces ou si le jeu est bloqué
        return plateau.getNbPiecesBlanches() == 0
                || plateau.getNbPiecesNoires() == 0
                || estPat(plateau);
    }*/

    public static boolean estDeplacementValide(Piece piece, int nouvelleLigne, int nouvelleColonne, PlateauDeJeu plateau) {
        // Vérifie si le déplacement de la pièce est valide en fonction des règles du jeu
        if (!plateau.estPositionValide(nouvelleLigne, nouvelleColonne)) {
            return false;
        }

        if (plateau.getPiece(nouvelleLigne, nouvelleColonne) != null && plateau.getPiece(nouvelleLigne, nouvelleColonne).getCouleur() == piece.getCouleur()) {
            return false;
        }

        if (!piece.estDeplacementValide(nouvelleLigne, nouvelleColonne, plateau)) {
            return false;
        }

        // Vérifie si le déplacement laisse le roi en échec
        PlateauDeJeu plateauTemporaire = plateau.clone();
        plateauTemporaire.deplacerPiece(piece.getLigne(), piece.getColonne(), nouvelleLigne, nouvelleColonne);

        Piece roi = plateauTemporaire.getRoi(piece.getCouleur());
        return !estEchec(roi, plateauTemporaire);
    }

    public static boolean estEchecEtMat(Piece piece, PlateauDeJeu plateau) {
        // Vérifie si le joueur est en échec et mat
        Piece roi = plateau.getRoi(piece.getCouleur());

        if (!estEchec(roi, plateau)) {
            return false;
        }

        for (int ligne = 0; ligne < PlateauDeJeu.NOMBRE_DE_LIGNES; ligne++) {
            for (int colonne = 0; colonne < PlateauDeJeu.NOMBRE_DE_COLONNES; colonne++) {
                Piece pieceCourante = plateau.getPiece(ligne, colonne);

                if (pieceCourante != null && pieceCourante.getCouleur() == piece.getCouleur()) {
                    for (int nouvelleLigne = 0; nouvelleLigne < PlateauDeJeu.NOMBRE_DE_LIGNES; nouvelleLigne++) {
                        for (int nouvelleColonne = 0; nouvelleColonne < PlateauDeJeu.NOMBRE_DE_COLONNES; nouvelleColonne++) {
                            if (estDeplacementValide(pieceCourante, nouvelleLigne, nouvelleColonne, plateau)) {
                                return false;
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    public static boolean estEchec(Piece piece, PlateauDeJeu plateau) {
        // Vérifie si le joueur est en échec
        for (int ligne = 0; ligne < PlateauDeJeu.NOMBRE_DE_LIGNES; ligne++) {
            for (int colonne = 0; colonne < PlateauDeJeu.NOMBRE_DE_COLONNES; colonne++) {
                Piece pieceCourante = plateau.getPiece(ligne, colonne);

                if (pieceCourante != null && pieceCourante.getCouleur() != piece.getCouleur()) {
                    if (pieceCourante.estDeplacementValide(ligne, colonne, piece.getLigne(), piece.getColonne(), plateau)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

