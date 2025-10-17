package fr.uvsq.cprog.collex;

import java.util.Objects;

/**
 * Représente une adresse IP au format x.x.x.x
 */
public class AdresseIP {
    private final String ip;

    /**
     * Constructeur : vérifie que l'adresse IP est valide
     * 
     * @param ip une chaîne de type "192.168.0.1"
     */
    public AdresseIP(String ip) {
        if (ip == null) {
            throw new IllegalArgumentException("Adresse IP ne peut pas être null");
        }

        // On découpe l'adresse par les points
        String[] parts = ip.split("\\.");
        if (parts.length != 4) {
            throw new IllegalArgumentException("Adresse IP invalide : " + ip);
        }

        // Vérifie que chaque partie est un nombre entre 0 et 255
        for (String part : parts) {
            try {
                int n = Integer.parseInt(part);
                if (n < 0 || n > 255) {
                    throw new IllegalArgumentException("Adresse IP invalide : " + ip);
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Adresse IP invalide : " + ip);
            }
        }

        this.ip = ip;
    }

    /**
     * @return l'adresse IP sous forme de chaîne
     */
    public String getIp() {
        return ip;
    }

    @Override
    public String toString() {
        return ip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof AdresseIP))
            return false;
        AdresseIP adresseIP = (AdresseIP) o;
        return ip.equals(adresseIP.ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip);
    }
}
