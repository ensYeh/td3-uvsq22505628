package fr.uvsq.cprog.collex;

import java.util.Objects;

/**
 * Représente une entrée du DNS : une adresse IP associée à un nom de machine
 */
public class DnsItem {
    private final AdresseIP adresseIP;
    private final NomMachine nomMachine;

    /**
     * Constructeur
     * 
     * @param adresseIP  une adresse IP valide
     * @param nomMachine un nom qualifié de machine valide
     */
    public DnsItem(AdresseIP adresseIP, NomMachine nomMachine) {
        if (adresseIP == null || nomMachine == null) {
            throw new IllegalArgumentException("Adresse IP et NomMachine ne peuvent pas être null");
        }
        this.adresseIP = adresseIP;
        this.nomMachine = nomMachine;
    }

    /**
     * Retourne l'adresse IP
     * 
     * @return adresse IP
     */
    public AdresseIP getAdresseIP() {
        return adresseIP;
    }

    /**
     * Retourne le nom de machine
     * 
     * @return nom qualifié de machine
     */
    public NomMachine getNomMachine() {
        return nomMachine;
    }

    @Override
    public String toString() {
        return adresseIP + " " + nomMachine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof DnsItem))
            return false;
        DnsItem dnsItem = (DnsItem) o;
        return adresseIP.equals(dnsItem.adresseIP) && nomMachine.equals(dnsItem.nomMachine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adresseIP, nomMachine);
    }
}
