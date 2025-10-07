package fr.univ_lyon1.info.m1.cv_search.controller;

import java.io.File;
import java.util.List;

import fr.univ_lyon1.info.m1.cv_search.model.*;

/** Contrôleur MVC : agit uniquement sur le modèle. */
public class ApplicantController {
    private final ApplicantList model;
    private MatchingStrategy strategy;

    public ApplicantController(ApplicantList model) {
        this.model = model;
        // Stratégie par défaut
        this.strategy = new AllOver50Strategy();
    }

    /** Permet de changer la stratégie à la volée */
    public void setStrategy(MatchingStrategy strategy) {
        this.strategy = strategy;
    }

    /** Recherche de candidats selon les compétences et la stratégie. */
    public void search(List<String> skills) {
        ApplicantList allApplicants = new ApplicantListBuilder(new File(".")).build();
        ApplicantList filtered = new ApplicantList();

        for (Applicant a : allApplicants) {
            if (strategy.matches(a, skills)) {
                filtered.add(a);
            }
        }

        // Mise à jour du modèle → toutes les vues observant le modèle seront notifiées
        model.setList(filtered);
    }

    /** Ajout d'un candidat au modèle. */
    public void addApplicant(Applicant a) {
        model.add(a);
    }
}
