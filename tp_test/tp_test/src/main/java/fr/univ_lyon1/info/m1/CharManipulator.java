package fr.univ_lyon1.info.m1.tp_test;

/**
 * Implémentation de l'interface ICharManipulator.
 * Fournit des méthodes pour manipuler des chaînes de caractères.
 */
public class CharManipulator implements ICharManipulator {

    /**
     * Renverse l'ordre des caractères dans une chaîne.
     * Par exemple : "hello" devient "olleh".
     * 
     * @param s La chaîne à inverser.
     * @return La chaîne avec les caractères dans l'ordre inverse.
     */
    @Override
    public String invertOrder(String s) {
        // Utilise StringBuilder.reverse() pour inverser efficacement
        return new StringBuilder(s).reverse().toString();
    }

    /**
     * Inverse la casse de chaque caractère.
     * Les majuscules deviennent minuscules et vice versa.
     * Les chiffres et symboles restent inchangés.
     * Par exemple : "Hello123" devient "hELLO123".
     * 
     * @param s La chaîne dont on veut inverser la casse.
     * @return La chaîne avec la casse inversée.
     */
    @Override
    public String invertCase(String s) {
        // Parcourt chaque caractère et inverse sa casse
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (Character.isUpperCase(c)) {
                sb.append(Character.toLowerCase(c));
            } else if (Character.isLowerCase(c)) {
                sb.append(Character.toUpperCase(c));
            } else {
                sb.append(c); // chiffre ou symbole reste identique
            }
        }
        return sb.toString();
    }

    /**
     * Supprime toutes les occurrences d'un motif dans une chaîne.
     * Continue à supprimer jusqu'à ce qu'il n'y ait plus d'occurrences.
     * Par exemple : removePattern("ababab", "ab") retourne "".
     * 
     * @param string La chaîne source.
     * @param pattern Le motif à supprimer.
     * @return La chaîne sans aucune occurrence du motif.
     */
    @Override
    public String removePattern(final String string, final String pattern) {
        // Si le motif est vide, retourne la chaîne inchangée
        if (pattern.isEmpty()) {
            return string;
        }
        // Supprime toutes les occurrences du motif
        String result = string;
        while (result.contains(pattern)) {
            result = result.replace(pattern, "");
        }
        return result;
    }
}
