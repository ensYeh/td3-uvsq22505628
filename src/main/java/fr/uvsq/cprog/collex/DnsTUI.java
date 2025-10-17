package fr.uvsq.cprog.collex;

import java.util.Scanner;

/**
 * Interface utilisateur textuelle pour le DNS.
 */

public class DnsTUI {
    private Scanner scanner;
    private final Dns dns;
    /**
     * Constructeur de la classe DnsTUI
     * @param dns
     */
    public DnsTUI(Dns dns) {
        this.dns = dns;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Lit la ligne de l'utilisateur et retourne la commande correspondante.
     *
     * @return commande à exécuter
     */
    public Commande nextCommande() {
        System.out.print("> ");
        String ligne = scanner.nextLine().trim();

        if (ligne.equalsIgnoreCase("quit") || ligne.equalsIgnoreCase("exit")) {
            return new QuitterCommande();
        }

        try {
            if (ligne.startsWith("add ")) {
                String[] parts = ligne.split("\\s+");
                if (parts.length != 3) {
                    return () -> "ERREUR : syntaxe invalide pour add !";
                }
                AdresseIP ip = new AdresseIP(parts[1]);
                NomMachine nom = new NomMachine(parts[2]);
                return new AjouterItemCommande(dns, ip, nom);
            }

            if (ligne.startsWith("ls ")) {
                boolean trierParIP = false;
                String domaine;
                String[] parts = ligne.split("\\s+");
                if (parts.length == 3 && parts[1].equals("-a")) {
                    trierParIP = true;
                    domaine = parts[2];
                } else if (parts.length == 2) {
                    domaine = parts[1];
                } else {
                    return () -> "ERREUR : syntaxe invalide pour ls !";
                }
                return new ListeMachinesCommande(dns, domaine, trierParIP);
            }

            // Si la ligne est une IP
            if (ligne.matches("\\d+\\.\\d+\\.\\d+\\.\\d+")) {
                AdresseIP ip = new AdresseIP(ligne);
                return new RechercheNomCommande(dns, ip);
            }

            // Sinon on considère que c'est un nom
            NomMachine nom = new NomMachine(ligne);
            return new RechercheIPCommande(dns, nom);

        } catch (Exception e) {
            // Pour toute erreur de parsing ou invalide
            return () -> "ERREUR : " + e.getMessage();
        }
    }

    /** Affiche le résultat à l'utilisateur. */
    public void affiche(String resultat) {
        System.out.println(resultat);
    }

    /** Ferme le scanner. */
    public void close() {
        scanner.close();
    }
}
