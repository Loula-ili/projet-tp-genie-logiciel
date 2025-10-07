package fr.univ_lyon1.info.m1.cv_search.model;

import java.util.List;

/**
 * Définit une stratégie de correspondance entre un candidat et une liste de compétences.
 * <p>
 * Les classes qui implémentent cette interface doivent préciser les critères utilisés
 * pour déterminer si un candidat correspond aux compétences recherchées.
 * </p>
 */
public interface MatchingStrategy {

    /**
     * Vérifie si le candidat correspond aux compétences fournies selon la stratégie implémentée.
     *
     * @param applicant Candidat à évaluer.
     * @param skills    Liste des compétences à vérifier.
     * @return {@code true} si le candidat correspond à la stratégie, {@code false} sinon.
     */
    boolean matches(Applicant applicant, List<String> skills);
}
