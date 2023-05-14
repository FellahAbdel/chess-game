# Uitlisation du programme

Notre jeu d'échecs est une application Java qui permet aux joueurs de jouer 
au célèbre jeu d'échecs. Lorsque le jeu est lancé, un pop-up s'ouvre 
pour permettre au joueur de choisir la couleur des pièces qu'il souhaite jouer avec.

![choix_couleurs_joueurs.png](capture/choix_couleurs_joueurs.png)

Si le joueur choisit la couleur blanche, ses pièces seront placées en bas du plateau
de jeu.

![blanc_en_bas.png](capture/blanc_en_bas.png)

Sinon elles seront placées en haut.

![noir_en_bas.png](capture/noir_en_bas.png)

Une fois la couleur choisie, le joueur peut commencer à jouer en sélectionnant une 
de ses pièces et en cliquant sur une des cases de déplacement possibles mises 
en surbrillance.

![surbrillance.png](capture/surbrillance.png)

Si le déplacement est valide, la pièce sera déplacée et le tour
passera à l'autre joueur. Si le déplacement est invalide, le joueur est invité à 
jouer de nouveau.

![deplacement.png](capture/deplacement.png)

Le jeu se poursuit ainsi jusqu'à ce qu'un joueur mette l'autre joueur en échec et mat,
ce qui signifie que le roi du joueur est menacé et qu'il n'a aucun mouvement valide 
pour l'éviter. Dans ce cas, un pop-up s'ouvre pour signaler la fin de la partie. 

![echec_et_mat.png](capture/echec_et_mat.png)

Si aucun joueur n'a la possibilité de mettre 
l'autre en échec et mat, il peut y avoir un match nul, appelé pat.
Dans ce cas, un pop-up s'ouvre pour signaler la fin de la partie et aucun joueur ne
gagne.

![estPat.png](capture/estPat.png)

 L'interface utilisateur est simple et facile à utiliser,
avec des pièces facilement reconnaissables et des mouvements de déplacement clairement 
indiqués. La fenêtre de jeu et le pop-up de fin de partie se ferment automatiquement
une fois que le joueur a cliqué sur le bouton "OK", signalant ainsi la fin de la 
partie.
