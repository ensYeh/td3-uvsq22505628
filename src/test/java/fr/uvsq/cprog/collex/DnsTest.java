package fr.uvsq.cprog.collex;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * Classe de test JUnit 4 pour la classe Dns.
 * Elle vérifie le bon fonctionnement des opérations de recherche et d'ajout.
 */
public class DnsTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder(); // Crée un dossier temporaire pour les fichiers de test

    private Path propertiesFile; // Fichier de propriétés temporaire
    private Path dnsFile; // Fichier DNS temporaire
    private Dns dns; // Instance de Dns à tester

    /**
     * Initialisation avant chaque test.
     * Création des fichiers temporaires et chargement du serveur DNS avec ces
     * fichiers.
     */
    @Before
    public void setUp() throws IOException {
        // Crée un fichier DNS temporaire
        dnsFile = tempFolder.newFile("dns.txt").toPath();
        Files.write(dnsFile, List.of(
                "www.uvsq.fr 193.51.31.90",
                "poste.uvsq.fr 193.51.31.154",
                "ecampus.uvsq.fr 193.51.25.12"));

        // Crée le fichier de propriétés
        propertiesFile = tempFolder.newFile("config.properties").toPath();
        String normalizedPath = dnsFile.toAbsolutePath().toString().replace("\\", "/"); // remplace les backslashes
        Files.writeString(propertiesFile, "fichier.dns=" + normalizedPath);

        // Instancie Dns avec trim pour éviter les retours chariot
        dns = new Dns(propertiesFile);
    }

    /**
     * Test de la méthode getItem(AdresseIP) :
     * Vérifie que la recherche par adresse IP retourne bien le DnsItem
     * correspondant.
     */
    @Test
    public void testGetItemByIp() {
        AdresseIP ip = new AdresseIP("193.51.31.90");
        DnsItem item = dns.getItem(ip);
        assertNotNull(item); // L'item doit exister
        assertEquals("www.uvsq.fr", item.getNomMachine().getNom()); // Vérifie le nom associé
    }

    /**
     * Test de la méthode getItem(NomMachine) :
     * Vérifie que la recherche par nom de machine retourne bien le DnsItem
     * correspondant.
     */
    @Test
    public void testGetItemByNomMachine() {
        NomMachine nom = new NomMachine("poste.uvsq.fr");
        DnsItem item = dns.getItem(nom);
        assertNotNull(item); // L'item doit exister
        assertEquals("193.51.31.154", item.getAdresseIP().getIp()); // Vérifie l'IP associée
    }

    /**
     * Test de la méthode getItems(String domaine) :
     * Vérifie que toutes les machines d'un domaine donné sont bien retournées.
     */
    @Test
    public void testGetItemsByDomaine() {
        List<DnsItem> items = dns.getItems("uvsq.fr");
        assertEquals(3, items.size()); // Il doit y avoir 3 machines dans le domaine uvsq.fr
    }

    /**
     * Test de la méthode addItem pour un ajout valide :
     * Vérifie que l'item est ajouté en mémoire et dans le fichier.
     */
    @Test
    public void testAddItemSuccess() throws IOException {
        AdresseIP newIp = new AdresseIP("193.51.25.24");
        NomMachine newNom = new NomMachine("pikachu.uvsq.fr");
        dns.addItem(newIp, newNom); // Ajout de la machine

        // Vérification que l'item est bien ajouté
        DnsItem item = dns.getItem(newIp);
        assertNotNull(item);
        assertEquals("pikachu.uvsq.fr", item.getNomMachine().getNom());

        // Vérification que le fichier DNS est mis à jour
        List<String> lignes = Files.readAllLines(dnsFile);
        assertTrue(lignes.contains("pikachu.uvsq.fr 193.51.25.24"));
    }

    /**
     * Test de l'ajout d'une IP déjà existante :
     * Doit lever une exception IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddItemDuplicateIp() throws IOException {
        AdresseIP duplicateIp = new AdresseIP("193.51.31.90"); // IP existante
        NomMachine newNom = new NomMachine("nouveau.uvsq.fr");
        dns.addItem(duplicateIp, newNom); // Devrait lever une exception
    }

    /**
     * Test de l'ajout d'un nom de machine déjà existant :
     * Doit lever une exception IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddItemDuplicateNom() throws IOException {
        AdresseIP newIp = new AdresseIP("193.51.25.99");
        NomMachine duplicateNom = new NomMachine("www.uvsq.fr"); // Nom existant
        dns.addItem(newIp, duplicateNom); // Devrait lever une exception
    }
}
