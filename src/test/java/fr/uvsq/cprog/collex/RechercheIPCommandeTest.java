package fr.uvsq.cprog.collex;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Classe de test pour la commande RechercheIPCommande.
 * Elle vérifie que la recherche d'une adresse IP à partir
 * d'un nom de machine fonctionne correctement.
 */
public class RechercheIPCommandeTest {

    private Dns dns; // Instance du serveur DNS utilisée pour les tests

    /**
     * Méthode exécutée avant chaque test.
     * Elle crée :
     * - un fichier temporaire contenant la base DNS
     * - un fichier temporaire de propriétés pointant vers ce fichier
     * - une instance de Dns chargée à partir de ce fichier de propriétés
     * 
     * @throws IOException si un problème survient lors de l'écriture des fichiers
     */
    @Before
    public void setUp() throws IOException {
        // Création d'un fichier temporaire pour la base DNS
        Path tempFile = Files.createTempFile("dns", ".txt").toAbsolutePath();
        List<String> lignes = List.of(
                "www.uvsq.fr 193.51.31.90",
                "poste.uvsq.fr 193.51.31.154");
        Files.write(tempFile, lignes);

        // Création d'un fichier de propriétés temporaire
        Path propFile = Files.createTempFile("dnsprops", ".properties").toAbsolutePath();
        // Remplace les backslashes par des slashs pour Windows
        Files.write(propFile, List.of("fichier.dns=" + tempFile.toString().replace("\\", "/")));

        // Chargement du DNS avec le fichier de propriétés
        dns = new Dns(propFile);
    }

    /**
     * Test de la recherche d'une adresse IP existante.
     * Vérifie que la commande RechercheIPCommande retourne
     * l'adresse IP correcte pour un nom de machine présent dans la base.
     * 
     * @throws Exception si un problème survient lors de l'exécution de la commande
     */
    @Test
    public void testRechercheIPExistante() throws Exception {
        NomMachine nom = new NomMachine("www.uvsq.fr");
        Commande cmd = new RechercheIPCommande(dns, nom);
        String resultat = cmd.execute();
        assertEquals("193.51.31.90", resultat);
    }

    /**
     * Test de la recherche d'une adresse IP inexistante.
     * Vérifie que la commande RechercheIPCommande retourne
     * le message d'erreur attendu lorsque le nom de machine n'est pas dans la base.
     * 
     * @throws Exception si un problème survient lors de l'exécution de la commande
     */
    @Test
    public void testRechercheIPInexistante() throws Exception {
        NomMachine nom = new NomMachine("inconnu.uvsq.fr");
        Commande cmd = new RechercheIPCommande(dns, nom);
        String resultat = cmd.execute();
        assertEquals("ERREUR : Nom de machine introuvable !", resultat);
    }
}
