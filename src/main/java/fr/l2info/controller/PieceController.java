package fr.l2info.controller;


import fr.l2info.enums.Direction;
import fr.l2info.model.Taquin;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
/** Contrôleur ayant pour but de gérer les touches pressés par l'utilisateur
 * pour le déplacement des pièces
 */
public class PieceController extends KeyAdapter{

    private final Taquin modele;

    public PieceController(Taquin modele) {
        this.modele = modele;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char c = Character.toLowerCase(e.getKeyChar());
        Direction direction = Direction.getFromKey(c);
        if (direction != null) {
            modele.tryMovement(direction);
        }
    }
}
