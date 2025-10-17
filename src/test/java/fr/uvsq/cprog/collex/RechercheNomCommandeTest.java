package fr.uvsq.cprog.collex;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Classe de test pour la commande RechercheNomCommande.
 * Elle vérifie que la recherche d'un nom qualifié de machine
 * à partir d'une adresse IP fonctionne correctement.
 */
public class RechercheNomCommandeTest {

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
        Path tempFile = Files.createTempFile("dns", ".txt").toAbsolutePath();
        List<String> lignes = List.of(
                "www.uvsq.fr 193.51.31.90",
                "poste.uvsq.fr 193.51.31.154"
        );
        Files.write(tempFile, lignes);

        Path propFile = Files.createTempFile("dnsprops", ".properties").toAbsolutePath();
        Files.write(propFile, List.of("fichier.dns=" + tempFile.toString().replace("\\", "/")));

        dns = new Dns(propFile);
    }

    /**
     * Test de la recherche d'un nom existant à partir d'une IP.
     * Vérifie que la commande retourne le nom correct.
     *
     * @throws Exception si un problème survient lors de l'exécution de la commande
     */
    @Test
    public void testRechercheNomExistante() throws Exception {
        AdresseIP ip = new AdresseIP("193.51.31.90");
        Commande cmd = new RechercheNomCommande(dns, ip);
        String resultat = cmd.execute();
        assertEquals("www.uvsq.fr", resultat);
    }

    /**
     * Test de la recherche d'un nom inexistant à partir d'une IP.
     * Vérifie que la commande retourne le message d'erreur attendu.
     *
     * @throws Exception si un problème survient lors de l'exécution de la commande
     */
    @Test
    public void testRechercheNomInexistante() throws Exception {
        AdresseIP ip = new AdresseIP("192.168.0.1");
        Commande cmd = new RechercheNomCommande(dns, ip);
        String resultat = cmd.execute();
        assertEquals("ERREUR : Adresse IP introuvable !", resultat);
    }
}
