package fr.l2info.view;

import fr.l2info.model.EcouteurModele;
import fr.l2info.model.Taquin;

import javax.swing.*;
import java.awt.*;

public class CoupsJouesView extends JPanel implements EcouteurModele {

    private Taquin modele;
    private JLabel label;

    public CoupsJouesView(Taquin modele) {
        this.modele = modele;
        modele.addEcouteur(this);

        label = new JLabel("Nombre de coups : 0");
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        add(label);
    }

    @Override
    public void modelMisAJour(Object source) {
        label.setText("Nombre de coups : " + modele.getNbCoups());
    }
}