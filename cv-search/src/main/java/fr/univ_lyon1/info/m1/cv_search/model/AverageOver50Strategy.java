package fr.univ_lyon1.info.m1.cv_search.model;

import java.util.List;

/**
 * Stratégie qui sélectionne les candidats dont la moyenne
 * des compétences demandées est d'au moins 50 %.
 */
public class AverageOver50Strategy implements MatchingStrategy {

    /**
     * Vérifie si la moyenne des scores du candidat pour les compétences données
     * est supérieure ou égale à 50 %.
     *
     * @param applicant Candidat à évaluer.
     * @param skills    Liste des compétences à prendre en compte.
     * @return {@code true} si la moyenne est ≥ 50 %, sinon {@code false}.
     */
    @Override
    public boolean matches(final Applicant applicant, final List<String> skills) {
        if (skills.isEmpty()) {
            return false;
        }

        double sum = 0;
        for (String s : skills) {
            sum += applicant.getSkill(s);
        }
        double avg = sum / skills.size();
        return avg >= 50;
    }
}
