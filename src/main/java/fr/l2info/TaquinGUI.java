package fr.l2info;

import fr.l2info.controller.PieceController;
import fr.l2info.model.Taquin;
import fr.l2info.view.CoupsJouesView;
import fr.l2info.view.GrilleView;

import javax.swing.*;
import java.awt.*;

public class TaquinGUI extends JFrame {

    public TaquinGUI() {
        this(new Taquin(4));
    }

    public TaquinGUI(Taquin modele) {

        GrilleView grille = new GrilleView(modele);
        CoupsJouesView coups = new CoupsJouesView(modele);

        JButton btnNouvelle = new JButton("Nouvelle partie");
        btnNouvelle.addActionListener(e -> modele.nouvellePartie(100));

        JPanel panneauBas = new JPanel(new BorderLayout());
        panneauBas.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        panneauBas.add(coups, BorderLayout.WEST);
        panneauBas.add(btnNouvelle, BorderLayout.EAST);

        setLayout(new BorderLayout());
        add(grille, BorderLayout.CENTER);
        add(panneauBas, BorderLayout.SOUTH);

        addKeyListener(new PieceController(modele));
        setFocusable(true);

        setTitle("Taquin");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        modele.nouvellePartie(100);
    }
}
