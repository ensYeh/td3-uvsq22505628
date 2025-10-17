package fr.uvsq.cprog.collex;

/**
 * Interface représentant une commande DNS
 */
public interface Commande {
    /**
     * Exécute la commande et retourne le résultat sous forme de chaîne
     * 
     * @return résultat de l'exécution
     * @throws Exception en cas d'erreur
     */
    String execute() throws Exception;
}
