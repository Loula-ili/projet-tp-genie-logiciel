package fr.univ_lyon1.info.m1.tp_test;

/**
 * Interface définissant des opérations de manipulation de chaînes de caractères.
 * Toutes les méthodes prennent une ou plusieurs chaînes en entrée et retournent une chaîne modifiée.
 */
public interface ICharManipulator {
    /**
     * Inverse l'ordre des caractères dans une chaîne.
     * 
     * @param s La chaîne à inverser.
     * @return La chaîne avec les caractères dans l'ordre inverse.
     */
    String invertOrder(String s);

    /**
     * Inverse la casse (majuscules ⇔ minuscules) de chaque caractère.
     * 
     * @param s La chaîne dont on veut inverser la casse.
     * @return La chaîne avec la casse inversée.
     */
    String invertCase(String s);

    /**
     * Supprime toutes les occurrences d'un motif dans une chaîne.
     * 
     * @param string La chaîne source.
     * @param pattern Le motif à supprimer.
     * @return La chaîne sans le motif.
     */
    String removePattern(String string, String pattern);
}
