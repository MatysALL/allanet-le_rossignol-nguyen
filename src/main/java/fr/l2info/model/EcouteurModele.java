package fr.l2info.model;

/**
 * Modèle écouteur
 */
public interface EcouteurModele {
    // Matys
    // Ce sera l'interface qui définit comment le modèle va prévenir les vues qu'une
    // pièce à bougé.

    /**
     * Mise à jour du modèle
     * @param source
     */
    public void modelMisAJour(Object source);
}
