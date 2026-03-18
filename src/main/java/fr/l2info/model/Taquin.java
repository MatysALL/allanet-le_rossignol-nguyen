package fr.l2info.model;

import fr.l2info.enums.Direction;
import fr.l2info.enums.MovementResult;

import java.util.Random;

import java.util.ArrayList;
import java.util.List;

public class Taquin {
    private final int size;
    private final Piece[][] pieces;

    private int xHole;
    private int yHole;

    // liste des listeners, et du nombre de coups pour la partie visuelle. ( Matys )
    private List<EcouteurModele> ecouteurs = new ArrayList<>();
    private int nbCoups = 0;

    public Taquin(int size) {
        this.size = size;
        pieces = new Piece[size][size];
        int i = -1;

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                pieces[x][y] = new Piece(i++);
            }
        }
    }

    public MovementResult tryMovement(Direction direction) {
        return tryMovement(xHole, yHole, direction);
    }

    public MovementResult tryMovement(int x, int y, Direction direction) {
        if (x + direction.getX() >= size || y + direction.getY() >= size) {
            return MovementResult.OverGrid;
        }

        if (x + direction.getX() < 0 || y + direction.getY() < 0) {
            return MovementResult.OverGrid;
        }

        Piece from = pieces[y][x];

        if (from == null) {
            return MovementResult.OverGrid;
        }
        // là ???
        Piece to = pieces[y][x];

        if (to == null) {
            return MovementResult.OverGrid;
        }

        if (from.getValeur() != -1 || to.getValeur() != -1) {
            return MovementResult.HoleNotSelected;
        }

        movement(x, y, direction);

        return MovementResult.Success;
    }

    public void movement(int x, int y, Direction direction) {
        movement(x, y, x + direction.getX(), y + direction.getY());
    }

    public void movement(int xFrom, int yFrom, int xTo, int yTo) {
        Piece from = pieces[yFrom][xFrom];
        Piece to = pieces[yTo][xTo];
        int i = from.getValeur();
        from.setValeur(to.getValeur());
        to.setValeur(i);

        if(from.getValeur() == -1) {
            xHole = xFrom;
            yHole = yFrom;
        } else {
            xHole = xTo;
            yHole = yTo;
        }

        // Incrémenter le nombre de coups ( Matys )
        this.nbCoups++;
        // Appeler fireChangement() pour prévenir les vues ( Matys )
        this.fireChangement();
    }

    public void mix() {
        Random rn = new Random();
        int moves = rn.nextInt(size - 500) + 250;
        mix(moves);
    }

    public void mix(int moves) {
        Random rn = new Random();
        int i = 0;

        int x = 0;
        int y = 0;

        while (i < moves) {
            Direction direction = Direction.values()[rn.nextInt(Direction.values().length)];

            if (tryMovement(x, y, direction) == MovementResult.Success) {
                i++;
                x += direction.getX();
                y += direction.getY();
            }
        }
    }

    @Deprecated
    public void mixOld(int moves) {
        Random rn = new Random();

        int i = 0;
        int tries = 0;
        while (i < moves && tries < 1000) {
            int x = rn.nextInt(size - 1);
            int y = rn.nextInt(size - 1);
            Direction direction = Direction.values()[rn.nextInt(Direction.values().length)];

            System.out.println(x + " " + y + " " + direction);

            if (tryMovement(x, y, direction) == MovementResult.Success) {
                i++;
            }

            tries++;
        }
    }

    public String toAsciiTable() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sb.append(pieces[i][j].getValeur()).append("\t");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public String toAsciiTableV2() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n-------------------\n");
        for (int i = 0; i < size; i++) {
            sb.append("| ");
            for (int j = 0; j < size; j++) {
                sb.append(pieces[i][j].getValeur()).append("\t| ");
            }
            sb.append("\n-------------------\n");
        }

        return sb.toString();
    }

    // Matys ->
    // Rajouter addEcouteur, et un compteur de coups
    // à chaque fois qu'une pièce bouge dans movement(à, appelle fireChangement()
    // pour prévenir les vues)

    public void addEcouteur(EcouteurModele ecouteur) {
        ecouteurs.add(ecouteur);
    }

    public void fireChangement() {
        for (EcouteurModele ecouteur : ecouteurs) {
            ecouteur.modelMisAJour(this);
        }
    }

    public int getNbCoups() {
        return nbCoups;
    }
}
