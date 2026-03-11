package fr.l2info.model;

import fr.l2info.enums.Direction;
import fr.l2info.enums.MovementResult;

public class Taquin {
    private final int size;
    private final Piece[][] pieces = {};

    public Taquin(int size) {
        this.size = size;
        int i = -1;

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                pieces[x][y] = new Piece(i++);
            }
        }
    }

    public MovementResult tryMovement(int x, int y, Direction direction) {
        if(x + direction.getX() > size || y + direction.getY() > size) {
            return MovementResult.OverGrid;
        }

        if(x + direction.getX() < 0 || y + direction.getY() < 0) {
            return MovementResult.OverGrid;
        }

        Piece from = pieces[x][y];

        if(from == null) {
            return MovementResult.OverGrid;
        }

        Piece to = pieces[x][y];

        if(to == null) {
            return MovementResult.OverGrid;
        }

        if(from.getValeur() != -1 || to.getValeur() != -1) {
            return MovementResult.HoleNotSelected;
        }

        movement(x, y, direction);

        return MovementResult.Success;
    }

    public void movement(int x, int y, Direction direction) {
        movement(x, y, x + direction.getX(), y + direction.getY());
    }

    public void movement(int xFrom, int yFrom, int xTo, int yTo) {
        Piece from = pieces[xFrom][yFrom];
        Piece to = pieces[xTo][yTo];
        int i = from.getValeur();
        from.setValeur(to.getValeur());
        to.setValeur(i);
    }
}
