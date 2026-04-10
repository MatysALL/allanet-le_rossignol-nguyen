package fr.l2info;

import fr.l2info.enums.Direction;
import fr.l2info.model.Taquin;

import javax.swing.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("--console")) {
            modeConsole();
            return;
        }

        SwingUtilities.invokeLater(() -> new TaquinGUI());

        // Matys ->
        // Créer la fenêtre JFrame
        // Ajouter la grille
        // Ajouter le compteur de coups
        // Ajouter les listeners
    }

    /**
     * Mode de jeu en console afin d'effectuer les testes sans interface Swing
     */
    private static void modeConsole() {
        Taquin taquin = new Taquin(4);

        System.out.println(taquin.toAsciiTable());

        long start = System.nanoTime();
        taquin.mixWithDirectionCheck(50);
        long end = System.nanoTime();
        long duration = end - start;
        long millis = TimeUnit.NANOSECONDS.toMillis(duration);

        System.out.println("Mélangé en " + millis + "ms\n");
        System.out.println(taquin.toAsciiTable());

        while (!taquin.isResolved()) {
            System.out.println("Enter une direction (Z, Q, S, D): ");
            int ascii;
            try {
                ascii = System.in.read();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            char c = Character.toLowerCase((char) ascii);

            Direction direction = Direction.getFromKey(c);

            if (direction != null) {
                taquin.tryMovement(direction);
                System.out.println(taquin.toAsciiTable());
            }
        }
        System.out.println("Bravo ! Résolu en " + taquin.getNbCoups() + " coups.");
    }
}