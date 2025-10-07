package fr.univ_lyon1.info.m1.cv_search.model;

import java.util.List;

/**
 * Stratégie de correspondance qui sélectionne uniquement
 * les candidats ayant un score d'au moins 50 % pour toutes
 * les compétences recherchées.
 */
public class AllOver50Strategy implements MatchingStrategy {

    /**
     * Vérifie si le candidat possède au moins 50 % dans chacune des compétences données.
     *
     * @param applicant Candidat à évaluer.
     * @param skills    Liste des compétences à vérifier.
     * @return {@code true} si toutes les compétences sont ≥ 50 %, sinon {@code false}.
     */
    @Override
    public boolean matches(final Applicant applicant, final List<String> skills) {
        for (String s : skills) {
            if (applicant.getSkill(s) < 50) {
                return false;
            }
        }
        return true;
    }
}
