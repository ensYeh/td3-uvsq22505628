package fr.uvsq.cprog.collex;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Classe de test pour la commande ListeMachinesCommande.
 * Elle vérifie que la liste des machines d'un domaine
 * est correcte et correctement triée.
 */
public class ListeMachinesCommandeTest {

    private Dns dns;

    @Before
    public void setUp() throws IOException {
        Path tempFile = Files.createTempFile("dns", ".txt").toAbsolutePath();
        List<String> lignes = List.of(
                "www.uvsq.fr 193.51.31.90",
                "poste.uvsq.fr 193.51.31.154",
                "ecampus.uvsq.fr 193.51.25.12"
        );
        Files.write(tempFile, lignes);

        Path propFile = Files.createTempFile("dnsprops", ".properties").toAbsolutePath();
        Files.write(propFile, List.of("fichier.dns=" + tempFile.toString().replace("\\", "/")));

        dns = new Dns(propFile);
    }

    /**
     * Test du tri par nom des machines du domaine uvsq.fr
     */
    @Test
    public void testListeMachinesTrieNom() throws Exception {
        Commande cmd = new ListeMachinesCommande(dns, "uvsq.fr", false);
        String resultat = cmd.execute();
        String attendu =
                "193.51.25.12 ecampus.uvsq.fr\n" +
                "193.51.31.154 poste.uvsq.fr\n" +
                "193.51.31.90 www.uvsq.fr";
        assertEquals(attendu, resultat);
    }

    /**
     * Test du tri par IP des machines du domaine uvsq.fr
     */
    @Test
    public void testListeMachinesTrieIP() throws Exception {
        Commande cmd = new ListeMachinesCommande(dns, "uvsq.fr", true);
        String resultat = cmd.execute();
        String attendu =
                "193.51.25.12 ecampus.uvsq.fr\n" +
                "193.51.31.90 www.uvsq.fr\n" +
                "193.51.31.154 poste.uvsq.fr";
        assertEquals(attendu, resultat);
    }

    /**
     * Test pour un domaine inexistant
     */
    @Test
    public void testListeMachinesDomaineInexistant() throws Exception {
        Commande cmd = new ListeMachinesCommande(dns, "inexistant.fr", false);
        String resultat = cmd.execute();
        assertEquals("", resultat); // pas de machine => chaîne vide
    }
}
