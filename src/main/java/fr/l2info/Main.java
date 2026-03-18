package fr.l2info;

import fr.l2info.enums.Direction;
import fr.l2info.model.Taquin;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Taquin taquin = new Taquin(4);

        System.out.println(taquin.toAsciiTable());

        taquin.mix(500);

        System.out.println("Après mix");
        System.out.println(taquin.toAsciiTable());

        while (true) {
            System.out.println("Enter une direction (Z, Q, S, D): ");
            int ascii = 0;
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

        // Matys ->
        // Créer la fenêtre JFrame
        // Ajouter la grille
        // Ajouter le compteur de coups
        // Ajouter les listeners
    }
}