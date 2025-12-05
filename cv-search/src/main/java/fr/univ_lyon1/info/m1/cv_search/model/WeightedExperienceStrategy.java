package fr.univ_lyon1.info.m1.cv_search.model;

import java.util.List;

/**
 * Stratégie de correspondance qui pondère les compétences et l'expérience.
 * <p>
 * Cette stratégie combine :
 * - 70% du score de compétence déclaré
 * - 30% du score d'expérience professionnelle
 * 
 * Un candidat est retenu si son score pondéré moyen est >= 50%.
 * </p>
 */
public class WeightedExperienceStrategy implements MatchingStrategy {

    private static final double SKILL_WEIGHT = 0.7;
    private static final double EXPERIENCE_WEIGHT = 0.3;
    private static final double THRESHOLD = 50.0;

    /**
     * Vérifie si le candidat correspond aux compétences demandées
     * en tenant compte de son expérience professionnelle.
     *
     * @param applicant Candidat à évaluer.
     * @param skills    Liste des compétences à vérifier.
     * @return {@code true} si le score pondéré moyen est >= 50%, sinon {@code false}.
     */
    @Override
    public boolean matches(final Applicant applicant, final List<String> skills) {
        if (skills.isEmpty()) {
            return false;
        }

        double totalWeightedScore = 0;

        for (String skill : skills) {
            int skillScore = applicant.getSkill(skill);
            double experienceScore = applicant.getExperienceScore(skill) * 100; // 0-1 -> 0-100

            // Combinaison pondérée
            double weightedScore = SKILL_WEIGHT * skillScore + EXPERIENCE_WEIGHT * experienceScore;
            totalWeightedScore += weightedScore;
        }

        double averageWeightedScore = totalWeightedScore / skills.size();
        return averageWeightedScore >= THRESHOLD;
    }
}
