package fr.uvsq.cprog.collex;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests unitaires pour la classe DnsItem
 */
public class DnsItemTest {

    /**
     * Teste la création d'une entrée DNS valide
     */
    @Test
    public void testDnsItemValide() {
        AdresseIP ip = new AdresseIP("192.168.0.1");
        NomMachine nm = new NomMachine("www.uvsq.fr");
        DnsItem item = new DnsItem(ip, nm);

        assertEquals(ip, item.getAdresseIP());
        assertEquals(nm, item.getNomMachine());
        assertEquals("192.168.0.1 www.uvsq.fr", item.toString());
    }

    /**
     * Teste que passer null pour l'adresse IP lance IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAdresseNull() {
        NomMachine nm = new NomMachine("www.uvsq.fr");
        new DnsItem(null, nm);
    }

    /**
     * Teste que passer null pour le nom de machine lance IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNomMachineNull() {
        AdresseIP ip = new AdresseIP("192.168.0.1");
        new DnsItem(ip, null);
    }
}
