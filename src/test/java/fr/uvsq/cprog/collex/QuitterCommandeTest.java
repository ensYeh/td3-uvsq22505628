package fr.uvsq.cprog.collex;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Test de la commande QuitterCommande.
 */
public class QuitterCommandeTest {

    /** Vérifie que la commande retourne le message attendu. */
    @Test
    public void testExecute() {
        QuitterCommande cmd = new QuitterCommande();
        assertEquals("Au revoir !", cmd.execute());
    }
}
