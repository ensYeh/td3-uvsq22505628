package fr.uvsq.cprog.collex;

import static org.junit.Assert.assertEquals;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;
import org.junit.Before;
import org.junit.Test;

/**
 * Test JUnit pour la classe DnsTUI.
 * Vérifie que la lecture des commandes et l'exécution fonctionnent
 * correctement.
 */
public class DnsTUITest {

    private Dns dns;

    /**
     * Prépare un DNS avec un fichier temporaire avant chaque test.
     * 
     * @throws IOException si la création des fichiers échoue
     */
    @Before
    public void setUp() throws IOException {
        Path tempFile = Files.createTempFile("dns", ".txt").toAbsolutePath();
        Files.write(tempFile, List.of("www.uvsq.fr 193.51.31.90"));

        Path propFile = Files.createTempFile("dnsprops", ".properties").toAbsolutePath();
        Files.write(propFile, List.of("fichier.dns=" + tempFile.toString().replace("\\", "/")));

        dns = new Dns(propFile);
    }

    /**
     * Vérifie que la commande pour rechercher l'adresse IP à partir du nom de
     * machine
     * retourne le résultat attendu.
     */
    @Test
    public void testNextCommandeNom() throws Exception {
        // Simule l'entrée utilisateur "www.uvsq.fr\n"
        ByteArrayInputStream in = new ByteArrayInputStream("www.uvsq.fr\n".getBytes());
        System.setIn(in);

        DnsTUI tui = new DnsTUI(dns);
        Commande cmd = tui.nextCommande();
        String result = cmd.execute();

        assertEquals("193.51.31.90", result);
    }

}
