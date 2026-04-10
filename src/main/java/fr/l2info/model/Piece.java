package fr.l2info.model;

/**
 * Piece contenue dans la grille de Taquin
 */
public class Piece {
    private int valeur;

    public Piece(int valeur) {
        this.valeur = valeur;
    }

    /**
     * Retourne la valeur de la pièce
     */
    public int getValeur() {
        return valeur;
    }

    public void setValeur(int valeur) {
        this.valeur = valeur;
    }
}
