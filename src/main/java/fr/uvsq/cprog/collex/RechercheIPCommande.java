package fr.uvsq.cprog.collex;

/**
 * Commande pour rechercher l'adresse IP à partir d'un nom qualifié de machine
 * Exemple : www.uvsq.fr -> 193.51.31.90
 */
public class RechercheIPCommande implements Commande {

    private final Dns dns;               // Référence au serveur DNS
    private final NomMachine nomMachine; // Nom de la machine à rechercher

    /**
     * Constructeur
     *
     * @param dns        serveur DNS à interroger
     * @param nomMachine nom qualifié de la machine
     */
    public RechercheIPCommande(Dns dns, NomMachine nomMachine) {
        this.dns = dns;
        this.nomMachine = nomMachine;
    }

    /**
     * Exécute la commande
     *
     * @return l'adresse IP correspondante ou un message d'erreur si introuvable
     */
    @Override
    public String execute() {
        // Recherche dans le DNS
        DnsItem item = dns.getItem(nomMachine);

        // Si trouvé, retourne l'adresse IP, sinon message d'erreur
        if (item != null) {
            return item.getAdresseIP().toString();
        } else {
            return "ERREUR : Nom de machine introuvable !";
        }
    }
}
