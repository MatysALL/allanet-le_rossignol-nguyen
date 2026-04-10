package fr.l2info.model;

import fr.l2info.enums.Direction;
import fr.l2info.enums.MovementResult;

import java.util.*;

public class Taquin {
    private final int size;
    private final Piece[][] pieces;

    private int xHole;
    private int yHole;

    // liste des listeners, et du nombre de coups pour la partie visuelle. ( Matys )
    private List<EcouteurModele> ecouteurs = new ArrayList<>();
    private int nbCoups = 0;

    /**
     * Crée un nouveau Taquin de taille size x size
     * L'état initial a le trou en bas à droite (size-1, size-1) et pièces numérotées de 0 à size²-2
     */
    public Taquin(int size) {
        this.size = size;
        pieces = new Piece[size][size];
        int i = 0;

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (x == size - 1 && y == size - 1)
                    pieces[x][y] = new Piece(-1); // trou en bas à droite
                else
                    pieces[x][y] = new Piece(i++);
            }
        }
        xHole = size - 1;
        yHole = size - 1;
    }

    /**
     * Tente de déplacer la pièce adjacente au trou dans la direction indiquée
     * La direction est exprimée depuis le trou
     * (ex : Direction.RIGHT = déplace la pièce à droite du trou vers le trou)
     */
    public MovementResult tryMovement(Direction direction) {
        return tryMovement(xHole, yHole, direction);
    }

    /** Tente un déplacement depuis (x,y) vers la direction donnée */
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
        int targetX = x + direction.getX();
        int targetY = y + direction.getY();
        Piece to = pieces[targetY][targetX];

        if (to == null) {
            return MovementResult.OverGrid;
        }

        if (from.getValeur() != -1 && to.getValeur() != -1) {
            return MovementResult.HoleNotSelected;
        }

        movement(x, y, direction);

        return MovementResult.Success;
    }

    public void movement(int x, int y, Direction direction) {
        movement(x, y, x + direction.getX(), y + direction.getY());
    }

    /** Echange les pièces aux positions (xFrom,yFrom) et (xTo,yTo)
     * (ignore toutes conditions)
     * */
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
        int moves = rn.nextInt(size - 70) + 30;
        mix(moves);
    }

    public void mix(int moves) {
        Random rn = new Random();
        int i = 0;

        int x = xHole;
        int y = yHole;

        while (i < moves) {
            Direction direction = Direction.values()[rn.nextInt(Direction.values().length)];

            if (tryMovement(x, y, direction) == MovementResult.Success) {
                i++;
                x += direction.getX();
                y += direction.getY();
            }
        }
    }

    public void mixWithDirectionCheck(int moves) {
        Random rn = new Random();
        int i = 0;

        int x = xHole;
        int y = yHole;

        while (i < moves) {
            HashSet<Direction> directions = getPossibleDirections();
            Direction direction = new ArrayList<>(directions).get(rn.nextInt(directions.size()));

            if (tryMovement(x, y, direction) == MovementResult.Success) {
                i++;
                x += direction.getX();
                y += direction.getY();
            }
        }
    }

    public HashSet<Direction> getPossibleDirections() {
        HashSet<Direction> directions = new HashSet<>();
        for (Direction direction : Direction.values()) {
            if (xHole + direction.getX() >= size || yHole + direction.getY() >= size) {
                continue;
            }

            if (xHole + direction.getX() < 0 || yHole + direction.getY() < 0) {
                continue;
            }

            directions.add(direction);
        }
        return directions;
    }

    public HashSet<Direction> getPossibleDirections(int x, int y) {
        HashSet<Direction> directions = new HashSet<>();
        for (Direction direction : Direction.values()) {
            if (x + direction.getX() >= size || y + direction.getY() >= size) {
                continue;
            }

            if (x + direction.getX() < 0 || y + direction.getY() < 0) {
                continue;
            }

            directions.add(direction);
        }
        return directions;
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

    @Deprecated
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

    /** Réinitialise le tableau */
    public void reset() {
        int i = 0;

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (x == size - 1 && y == size - 1)
                    pieces[x][y] = new Piece(-1); // trou en bas à droite
                else
                    pieces[x][y] = new Piece(i++);
            }
        }
        xHole = size - 1;
        yHole = size - 1;
        nbCoups = 0;
    }

    // Getters
    public int getSize()  {
        return size;
    }

    public int getXHole() {
        return xHole;
    }

    public int getYHole() {
        return yHole;
    }

    public int getValeur(int ligne, int colonne) {
        return pieces[ligne][colonne].getValeur();
    }

    public int getNbCoups() {
        return nbCoups;
    }

    /** Renvoie true si le puzzle est résolu */
    public boolean isResolved() {
        int prevu = 1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(i == size - 1 && j == size - 1) {
                    return true;
                }

                if (pieces[i][j].getValeur() != prevu++) {
                    return false;
                }
            }
        }
        return true;
    }

    public void nouvellePartie(int nbMouvements) {
        reset();
        mixWithDirectionCheck(nbMouvements);
        nbCoups = 0;
        fireChangement();
    }
}