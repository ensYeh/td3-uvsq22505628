package fr.uvsq.cprog.collex;

/**
 * Commande pour quitter l'application.
 */
public class QuitterCommande implements Commande {

    /**
     * Exécute la commande de quitter.
     * 
     * @return message indiquant la fin de l'application
     */
    @Override
    public String execute() {
        return "Au revoir !";
    }
}
