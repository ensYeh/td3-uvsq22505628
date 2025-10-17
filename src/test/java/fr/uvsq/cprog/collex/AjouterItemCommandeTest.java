package fr.uvsq.cprog.collex;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests pour la commande AjouterItemCommande.
 */
public class AjouterItemCommandeTest {

    private Dns dns;

    /** Initialise le DNS avec un fichier temporaire avant chaque test. */
    @Before
    public void setUp() throws IOException {
        Path tempFile = Files.createTempFile("dns", ".txt").toAbsolutePath();
        List<String> lignes = List.of("www.uvsq.fr 193.51.31.90");
        Files.write(tempFile, lignes);

        Path propFile = Files.createTempFile("dnsprops", ".properties").toAbsolutePath();
        Files.write(propFile, List.of("fichier.dns=" + tempFile.toString().replace("\\", "/")));

        dns = new Dns(propFile);
    }

    /** Vérifie qu'on peut ajouter une machine qui n'existe pas encore. */
    @Test
    public void testAjouterItemSucces() throws Exception {
        AjouterItemCommande cmd = new AjouterItemCommande(dns,
                new AdresseIP("193.51.25.24"),
                new NomMachine("pikachu.uvsq.fr"));
        assertEquals("Ajout réussi : 193.51.25.24 pikachu.uvsq.fr", cmd.execute());
    }

    /** Vérifie le message d'erreur si le nom de machine existe déjà. */
    @Test
    public void testAjouterItemNomExistant() throws Exception {
        AjouterItemCommande cmd = new AjouterItemCommande(dns,
                new AdresseIP("193.51.31.154"),
                new NomMachine("www.uvsq.fr"));
        assertEquals("ERREUR : Le nom de machine existe déjà !", cmd.execute());
    }

    /** Vérifie le message d'erreur si l'adresse IP existe déjà. */
    @Test
    public void testAjouterItemIPExistant() throws Exception {
        AjouterItemCommande cmd = new AjouterItemCommande(dns,
                new AdresseIP("193.51.31.90"),
                new NomMachine("nouvelle.uvsq.fr"));
        assertEquals("ERREUR : L'adresse IP existe déjà !", cmd.execute());
    }
}
