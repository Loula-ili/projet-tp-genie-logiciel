package fr.univ_lyon1.info.m1.cv_search.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory pour créer les stratégies de matching de candidats.
 * <p>
 * Pattern: Factory - centralise la création des objets stratégies.
 * Permet d'éviter les if/switch dans le code client et facilite l'ajout
 * de nouvelles stratégies sans modifier le code existant (Open/Closed).
 * </p>
 */
public final class StrategyFactory {

    private static final Map<String, MatchingStrategy> STRATEGIES = new HashMap<>();

    static {
        // Enregistrement de toutes les stratégies disponibles
        STRATEGIES.put("All >= 50%", new AllOver50Strategy());
        STRATEGIES.put("All >= 60%", new AllOver60Strategy());
        STRATEGIES.put("Average >= 50%", new AverageOver50Strategy());
        STRATEGIES.put("Max Skill >= 70%", new MaxSkillAbove70Strategy());
        STRATEGIES.put("Weighted Experience", new WeightedExperienceStrategy());
    }

    /**
     * Constructeur privé pour empêcher l'instanciation.
     */
    private StrategyFactory() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Retourne la stratégie correspondant au nom donné.
     * 
     * @param strategyName Le nom de la stratégie.
     * @return L'instance de la stratégie, ou null si le nom est inconnu.
     */
    public static MatchingStrategy getStrategy(final String strategyName) {
        return STRATEGIES.get(strategyName);
    }

    /**
     * Vérifie si une stratégie existe pour le nom donné.
     * 
     * @param strategyName Le nom de la stratégie.
     * @return true si la stratégie existe, false sinon.
     */
    public static boolean hasStrategy(final String strategyName) {
        return STRATEGIES.containsKey(strategyName);
    }

    /**
     * Retourne tous les noms de stratégies disponibles.
     * 
     * @return Un tableau contenant tous les noms de stratégies.
     */
    public static String[] getAvailableStrategies() {
        return STRATEGIES.keySet().toArray(new String[0]);
    }
}
