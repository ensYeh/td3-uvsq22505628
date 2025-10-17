package fr.uvsq.cprog.collex;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Représente un serveur DNS avec une base d'adresses IP et de noms de machines
 */
public class Dns {

    private final List<DnsItem> items = new ArrayList<>(); // Liste des entrées DNS
    private final Path fichierBD; // Chemin du fichier contenant la base

    /**
     * Constructeur : charge la base de données depuis le fichier défini dans
     * properties
     * 
     * @param propertiesPath chemin du fichier de propriétés
     * @throws IOException si la lecture du fichier échoue
     */
    public Dns(Path propertiesPath) throws IOException {
        // Chargement du fichier de propriétés
        Properties props = new Properties();
        try (InputStream in = Files.newInputStream(propertiesPath)) {
            props.load(in);
        }

        String cheminFichierBD = props.getProperty("fichier.dns").trim();
        if (cheminFichierBD == null) {
            throw new IllegalArgumentException("Propriété 'fichier.dns' non définie !");
        }

        this.fichierBD = Path.of(cheminFichierBD);

        // Lecture du fichier DNS si il existe
        if (Files.exists(fichierBD)) {
            List<String> lignes = Files.readAllLines(fichierBD);
            for (String ligne : lignes) {
                if (ligne.trim().isEmpty())
                    continue; // Ignore lignes vides
                String[] parts = ligne.split("\\s+"); // "nomMachine adresseIP"
                if (parts.length == 2) {
                    NomMachine nom = new NomMachine(parts[0]);
                    AdresseIP ip = new AdresseIP(parts[1]);
                    items.add(new DnsItem(ip, nom)); // Ajout à la liste
                }
            }
        }
    }

    /** Retourne l'entrée correspondant à une adresse IP, ou null si introuvable */
    public DnsItem getItem(AdresseIP ip) {
        for (DnsItem item : items) {
            if (item.getAdresseIP().equals(ip))
                return item;
        }
        return null;
    }

    /**
     * Retourne l'entrée correspondant à un nom de machine, ou null si introuvable
     */
    public DnsItem getItem(NomMachine nomMachine) {
        for (DnsItem item : items) {
            if (item.getNomMachine().equals(nomMachine))
                return item;
        }
        return null;
    }

    /** Retourne toutes les machines d'un domaine donné */
    public List<DnsItem> getItems(String domaine) {
        return items.stream()
                .filter(item -> item.getNomMachine().getDomaine().equals(domaine))
                .collect(Collectors.toList());
    }

    /**
     * Ajoute une nouvelle entrée DNS et met à jour le fichier
     * 
     * @throws IOException si l'écriture du fichier échoue
     */
    public void addItem(AdresseIP ip, NomMachine nomMachine) throws IOException {
        if (getItem(ip) != null) {
            throw new IllegalArgumentException("ERREUR : L'adresse IP existe déjà !");
        }
        if (getItem(nomMachine) != null) {
            throw new IllegalArgumentException("ERREUR : Le nom de machine existe déjà !");
        }

        DnsItem nouvelItem = new DnsItem(ip, nomMachine);
        items.add(nouvelItem); // Ajout à la liste

        // Mise à jour du fichier de base
        List<String> lignes = items.stream()
                .map(item -> item.getNomMachine().getNom() + " " + item.getAdresseIP().getIp())
                .collect(Collectors.toList());
        Files.write(fichierBD, lignes);
    }
}
