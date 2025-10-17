package fr.uvsq.cprog.collex;

import java.io.IOException;

/**
 * Commande pour ajouter une nouvelle entrée dans la base DNS.
 * Exemple : add 193.51.25.24 pikachu.uvsq.fr
 */
public class AjouterItemCommande implements Commande {

    private final Dns dns;
    private final AdresseIP ip;
    private final NomMachine nomMachine;

    /**
     * Constructeur
     *
     * @param dns        serveur DNS à mettre à jour
     * @param ip         adresse IP à ajouter
     * @param nomMachine nom qualifié de la machine
     */
    public AjouterItemCommande(Dns dns, AdresseIP ip, NomMachine nomMachine) {
        this.dns = dns;
        this.ip = ip;
        this.nomMachine = nomMachine;
    }

    /**
     * Exécute la commande
     *
     * @return message de confirmation ou message d'erreur
     */
    @Override
    public String execute() {
        try {
            dns.addItem(ip, nomMachine);
            return "Ajout réussi : " + ip + " " + nomMachine;
        } catch (IllegalArgumentException | IOException e) {
            return e.getMessage();
        }
    }
}
