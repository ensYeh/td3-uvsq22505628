package fr.uvsq.cprog.collex;

/**
 * Commande pour rechercher le nom qualifié d'une machine à partir d'une adresse IP.
 * Exemple : 193.51.31.90 -> www.uvsq.fr
 */
public class RechercheNomCommande implements Commande {

    private final Dns dns;      // Référence au serveur DNS
    private final AdresseIP ip; // Adresse IP à rechercher

    /**
     * Constructeur
     *
     * @param dns serveur DNS à interroger
     * @param ip  adresse IP de la machine à rechercher
     */
    public RechercheNomCommande(Dns dns, AdresseIP ip) {
        this.dns = dns;
        this.ip = ip;
    }

    /**
     * Exécute la commande
     *
     * @return le nom qualifié de la machine ou un message d'erreur si introuvable
     */
    @Override
    public String execute() {
        DnsItem item = dns.getItem(ip);

        if (item != null) {
            return item.getNomMachine().toString();
        } else {
            return "ERREUR : Adresse IP introuvable !";
        }
    }
}
