package fr.univ_lyon1.info.m1.cv_search.model;

import java.util.List;

/**
 * Stratégie de correspondance qui sélectionne les candidats
 * ayant au moins une compétence avec un score supérieur ou égal à 70 %.
 */
public class MaxSkillAbove70Strategy implements MatchingStrategy {

    /**
     * Vérifie si le candidat possède au moins une compétence ≥ 70 %.
     *
     * @param applicant Candidat à évaluer.
     * @param skills    Liste des compétences à vérifier.
     * @return {@code true} si au moins une compétence atteint 70 %, sinon {@code false}.
     */
    @Override
    public boolean matches(final Applicant applicant, final List<String> skills) {
        for (String s : skills) {
            if (applicant.getSkill(s) >= 70) {
                return true;
            }
        }
        return false;
    }
}

