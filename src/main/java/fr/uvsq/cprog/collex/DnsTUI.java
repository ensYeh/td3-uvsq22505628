package fr.uvsq.cprog.collex;

import java.util.Scanner;

public class DnsTUI {
    private Scanner scanner;

    public DnsTUI() {
        scanner = new Scanner(System.in);
    }

    // Méthode qui lit la ligne de l'utilisateur et retourne une Commande
    public Commande nextCommande() {
        System.out.print("> ");
        String ligne = scanner.nextLine();
        // Pour l'instant on retourne null car on fera l'interface plus tard
        return null;
    }

    // Méthode qui affiche un résultat
    public void affiche(String resultat) {
        System.out.println(resultat);
    }

    // Ferme le scanner
    public void close() {
        scanner.close();
    }
}
