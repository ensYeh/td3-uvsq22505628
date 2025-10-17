package fr.uvsq.cprog.collex;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests unitaires pour la classe AdresseIP
 */
public class AdresseIPTest {

    /**
     * Teste la création d'une adresse IP valide
     */
    @Test
    public void testAdresseValide() {
        AdresseIP ip = new AdresseIP("192.168.0.1");
        assertEquals("192.168.0.1", ip.getIp());
    }

    /**
     * Teste que la création avec null lance IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAdresseNull() {
        new AdresseIP(null);
    }

    /**
     * Teste qu'une adresse avec moins de 4 octets lance IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAdresseInvalideFormat() {
        new AdresseIP("192.168.1"); // seulement 3 parties
    }

    /**
     * Teste qu'une adresse avec un octet > 255 lance IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAdresseOctetTropGrand() {
        new AdresseIP("192.168.0.256");
    }

    /**
     * Teste qu'une adresse avec un octet non numérique lance IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAdresseNonNumerique() {
        new AdresseIP("192.168.abc.1");
    }
}
