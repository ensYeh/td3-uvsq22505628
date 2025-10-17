package fr.uvsq.cprog.collex;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests unitaires pour la classe NomMachine
 */
public class NomMachineTest {

    /**
     * Teste la création d'un nom de machine valide
     */
    @Test
    public void testNomValide() {
        NomMachine nm = new NomMachine("www.uvsq.fr");
        assertEquals("www.uvsq.fr", nm.getNom());
        assertEquals("www", nm.getNomMachine());
        assertEquals("uvsq.fr", nm.getDomaine());
    }

    /**
     * Teste que la création avec null lance IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNomNull() {
        new NomMachine(null);
    }

    /**
     * Teste que la création avec une chaîne vide lance IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNomVide() {
        new NomMachine("");
    }

    /**
     * Teste qu'un nom sans '.' lance IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNomSansDomaine() {
        new NomMachine("machineSeule");
    }
}
