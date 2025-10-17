package fr.uvsq.cprog.collex;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Commande pour lister toutes les machines d'un domaine donné.
 * Exemple : ls uvsq.fr
 * Peut trier par nom de machine ou par adresse IP.
 */
public class ListeMachinesCommande implements Commande {

    private final Dns dns;        // Référence au serveur DNS
    private final String domaine; // Nom du domaine à lister
    private final boolean trierParIP; // true si tri par IP, false si tri par nom

    /**
     * Constructeur
     *
     * @param dns        serveur DNS à interroger
     * @param domaine    domaine dont on veut lister les machines
     * @param trierParIP true pour trier par IP, false pour trier par nom
     */
    public ListeMachinesCommande(Dns dns, String domaine, boolean trierParIP) {
        this.dns = dns;
        this.domaine = domaine;
        this.trierParIP = trierParIP;
    }

    /**
     * Exécute la commande
     *
     * @return la liste des machines sous forme de chaîne, chaque machine sur une ligne
     */
    @Override
    public String execute() {
        List<DnsItem> items = dns.getItems(domaine);

        if (trierParIP) {
            // Tri numérique des IP
            items.sort(Comparator.comparingInt(item -> {
                String[] parts = item.getAdresseIP().getIp().split("\\.");
                return (Integer.parseInt(parts[0]) << 24)
                     | (Integer.parseInt(parts[1]) << 16)
                     | (Integer.parseInt(parts[2]) << 8)
                     | Integer.parseInt(parts[3]);
            }));
        } else {
            // Tri par nom de machine
            items.sort(Comparator.comparing(item -> item.getNomMachine().getNomMachine()));
        }

        return items.stream()
                .map(item -> item.getAdresseIP() + " " + item.getNomMachine())
                .collect(Collectors.joining("\n"));
    }
}
