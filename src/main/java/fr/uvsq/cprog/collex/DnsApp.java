package fr.uvsq.cprog.collex;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * Classe principale de l'application DNS.
 * Interagit avec l'utilisateur pour exécuter les commandes DNS.
 */
public class DnsApp {

    private final Dns dns;
    private final DnsTUI tui;

    /**
     * Constructeur : charge le serveur DNS à partir d'un fichier de propriétés.
     *
     * @param propertiesPath chemin du fichier de propriétés
     * @throws IOException si le fichier de propriétés ou la base DNS ne peut pas être lu
     */
    public DnsApp(Path propertiesPath) throws IOException {
        this.dns = new Dns(propertiesPath);
        this.tui = new DnsTUI(dns);
    }

    /**
     * Boucle principale de l'application.
     * Récupère les commandes de l'utilisateur, les exécute et affiche le résultat.
     */
    public void run() {
        boolean quitter = false;
        while (!quitter) {
            try {
                Commande cmd = tui.nextCommande();
                if (cmd == null) {
                    continue; // ignore les lignes vides
                }
                String resultat = cmd.execute();
                tui.affiche(resultat);

                // Si la commande est QuitterCommande, on arrête la boucle
                if (cmd instanceof QuitterCommande) {
                    quitter = true;
                }
            } catch (Exception e) {
                tui.affiche("ERREUR : " + e.getMessage());
            }
        }
        tui.close();
    }

    /**
     * Point d'entrée de l'application.
     *
     * @param args chemin du fichier de propriétés en argument
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage : java fr.uvsq.cprog.collex.DnsApp <fichier.properties>");
            return;
        }
        Path propPath = Path.of(args[0]);
        try {
            DnsApp app = new DnsApp(propPath);
            app.run();
        } catch (IOException e) {
            System.err.println("Impossible de charger la base DNS : " + e.getMessage());
        }
    }
}
