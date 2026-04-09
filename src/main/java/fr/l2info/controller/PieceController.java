package fr.l2info.controller;


import fr.l2info.enums.Direction;
import fr.l2info.model.Taquin;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PieceController extends KeyAdapter{

    private final Taquin modele;

    public PieceController(Taquin modele) {
        this.modele = modele;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        Direction direction = Direction.getFromKey(c);
        if (direction != null) {
            modele.tryMovement(direction);
        }
    }
}
