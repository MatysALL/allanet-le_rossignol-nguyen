package fr.l2info.view;

import fr.l2info.enums.Direction;
import fr.l2info.model.EcouteurModele;
import fr.l2info.model.Taquin;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 * Grille du jeu
 */
public class GrilleView extends JPanel implements EcouteurModele {

    private Color gris = Color.GRAY;
    private Color vert = Color.GREEN;
    private Color noir = Color.BLACK;

    private int tailleCase = 90;
    private Taquin modele;

    /**
     * Survol d'une case jouable (bonus)
     */
    private int survolLigne = -1;
    private int survolColonne = -1;

    /**
     * Construit la grille ( prend un taquin )
     */
    public GrilleView(Taquin modele) {
        this.modele = modele;
        modele.addEcouteur(this);

        int size = modele.getSize();
        setPreferredSize(new Dimension(size * tailleCase, size * tailleCase));
        setBackground(Color.WHITE);

        /**
         * Evenement d'un clic - clic sur la piece en fonctione de la posiiton de la souris.
         */
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = e.getX() / tailleCase;
                int row = e.getY() / tailleCase;
                if (col < size && row < size) {
                    cliqueSurPiece(row, col);
                }
            }
        });

        /**
         * Ajoute l'evenement pour le survol de la souris
         */
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int col = e.getX() / tailleCase;
                int row = e.getY() / tailleCase;
                if (col != survolColonne || row != survolLigne) {
                    survolColonne = col;
                    survolLigne = row;
                    repaint();
                }
            }
        });
    }

    // ( importé )
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int size = modele.getSize();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                dessinerCase(g, row, col);
            }
        }
    }

    /**
     * dessine les cases ( lors du paintComponent )
     */
    private void dessinerCase(Graphics g, int row, int col) {
        int val = modele.getValeur(row, col);
        int x   = col * tailleCase;
        int y   = row * tailleCase;

        if (val == -1) {
            g.setColor(noir);
            g.fillRect(x, y, tailleCase, tailleCase);
        } else {
            boolean deplacable = estVoisinDuTrou(row, col);
            boolean survole    = (survolLigne == row && survolColonne == col);

            g.setColor((deplacable && survole) ? vert : gris);
            g.fillRect(x, y, tailleCase, tailleCase);

            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 28));
            String texte = String.valueOf(val + 1);
            FontMetrics fm = g.getFontMetrics();
            int tx = x + (tailleCase - fm.stringWidth(texte)) / 2;
            int ty = y + (tailleCase - fm.getHeight()) / 2 + fm.getAscent();
            g.drawString(texte, tx, ty);
        }

        g.setColor(noir);
        g.drawRect(x, y, tailleCase - 1, tailleCase - 1);
    }

    /**
     * Modification du model lorsqu'une pièce est cliqué ( sort de la boucle une fois que tryMovement est bon )
     */
    private void cliqueSurPiece(int row, int col) {
        int xHole = modele.getXHole();
        int yHole = modele.getYHole();
        int dx = col - xHole;
        int dy = row - yHole;

        for (Direction dir : Direction.values()) {
            if (dir.getX() == dx && dir.getY() == dy) {
                modele.tryMovement(dir);
                break;
            }
        }
    }

    /**
     * Regarde si la case est voisine au trou
     */
    private boolean estVoisinDuTrou(int row, int col) {
        int xHole = modele.getXHole();
        int yHole = modele.getYHole();
        return Math.abs(col - xHole) + Math.abs(row - yHole) == 1;
    }

    /**
     * Mise à jour du Modèle classique + renvoie de message de victoire si isResolved renvoie vrai
     */
    @Override
    public void modelMisAJour(Object source) {
        repaint();
        if (modele.getNbCoups() > 0 && modele.isResolved()) {
            SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(
                            this,
                            "Puzzle résolu en " + modele.getNbCoups() + " coups, tu aurais pu faire mieux !",
                            "Victoire",
                            JOptionPane.INFORMATION_MESSAGE
                    )
            );
        }
    }
}

