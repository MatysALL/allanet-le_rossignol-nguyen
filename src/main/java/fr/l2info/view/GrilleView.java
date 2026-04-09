package fr.l2info.view;

import fr.l2info.model.EcouteurModele;
import fr.l2info.model.Taquin;

import javax.swing.*;
import java.awt.*;

public class GrilleView extends JPanel implements EcouteurModele {

    private Color gris = Color.GRAY;
    private Color vert  = Color.GREEN;
    private Color noir = Color.BLACK;

    private int[][] grilleSimulation = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, -1} // Le trou est en (3,3)
    };

    private final int size = 4;

    private final int xTrou = 3;
    private final int yTrou = 3;

    public GrilleView() {
        this.setLayout(new GridLayout(size, size));
        dessinerGrille();
    }

    private void dessinerGrille() {
        this.removeAll();

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                int val = grilleSimulation[y][x];

                JButton bouton = new JButton(val == -1 ? "" : String.valueOf(val));
                bouton.setFont(new Font("Arial", Font.BOLD, 24));
                bouton.setFocusable(false);

                if (val == -1) { //trou
                    bouton.setBackground(noir);
                    bouton.setBorderPainted(false);
                    bouton.setEnabled(false);
                } else if (estVoisinDuTrou(x, y)) {
                    bouton.setBackground(vert);
                    bouton.setForeground(noir);
                } else {
                    bouton.setBackground(gris);
                    bouton.setForeground(noir);
                }

                this.add(bouton);
            }
        }
        this.revalidate();
        this.repaint();
    }

    private boolean estVoisinDuTrou(int x, int y) {
        return (Math.abs(x - xTrou) + Math.abs(y - yTrou)) == 1;
    }

    @Override
    public void modelMisAJour(Object source) {
        // à voir plus tard
    }
}