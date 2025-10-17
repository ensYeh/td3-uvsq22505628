package fr.uvsq.cprog.collex;

import java.util.Objects;

/**
 * Représente un nom qualifié de machine au format "nom.domaine"
 */
public class NomMachine {
    private final String nom;

    /**
     * Constructeur : vérifie que le nom est valide
     * 
     * @param nom une chaîne de type "www.uvsq.fr"
     */
    public NomMachine(String nom) {
        if (nom == null || nom.isEmpty()) {
            throw new IllegalArgumentException("Nom de machine ne peut pas être null ou vide");
        }

        // Vérifie que le nom contient au moins un '.'
        if (!nom.contains(".")) {
            throw new IllegalArgumentException("Nom de machine invalide, doit contenir un domaine : " + nom);
        }

        this.nom = nom;
    }

    /**
     * Retourne le nom complet
     * 
     * @return nom qualifié de machine
     */
    public String getNom() {
        return nom;
    }

    /**
     * Retourne le nom de la machine (avant le premier '.')
     * 
     * @return nom simple
     */
    public String getNomMachine() {
        return nom.split("\\.")[0];
    }

    /**
     * Retourne le domaine (après le premier '.')
     * 
     * @return domaine
     */
    public String getDomaine() {
        int index = nom.indexOf('.');
        return nom.substring(index + 1);
    }

    @Override
    public String toString() {
        return nom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof NomMachine))
            return false;
        NomMachine that = (NomMachine) o;
        return nom.equals(that.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom);
    }
}
