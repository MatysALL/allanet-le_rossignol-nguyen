package fr.l2info.model;

public interface EcouteurModele {
    // Matys
    // Ce sera l'interface qui définit comment le modèle va prévenir les vues qu'une
    // pièce à bougé.
    public void modelMisAJour(Object source);
}
