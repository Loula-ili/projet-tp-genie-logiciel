package fr.univ_lyon1.info.m1.cv_search.controller;

import java.io.File;
import java.util.List;

import fr.univ_lyon1.info.m1.cv_search.model.Applicant;
import fr.univ_lyon1.info.m1.cv_search.model.ApplicantList;
import fr.univ_lyon1.info.m1.cv_search.model.ApplicantListBuilder;

/** Contrôleur MVC reliant le modèle et la vue. */
public class ApplicantController {
    private final ApplicantList model;

    public ApplicantController(ApplicantList model) {
        this.model = model;
    }

    /** Recherche de candidats selon les compétences et calcul de la moyenne. */
    public void search(List<String> skills) {
        ApplicantList allApplicants = new ApplicantListBuilder(new File(".")).build();
        ApplicantList filtered = new ApplicantList();

        for (Applicant a : allApplicants) {
            if (!skills.isEmpty() && matches(a, skills, "All >= 50%")) {
                // Calcul de la moyenne sur les compétences recherchées
                double sum = 0;
                for (String s : skills) sum += a.getSkill(s);
                double avg = skills.isEmpty() ? 0 : sum / skills.size();
                a.setAverage(avg);
                filtered.add(a);
            }
        }

        model.setList(filtered);
    }

    /** Ajout d’un candidat au modèle */
    public void addApplicant(Applicant a) {
        model.add(a);
    }

    /** Vérifie si un candidat correspond selon la stratégie choisie. */
    private boolean matches(Applicant a, List<String> skills, String strategy) {
        switch (strategy) {
            case "All >= 50%":
                for (String s : skills) if (a.getSkill(s) < 50) return false;
                return true;
            case "All >= 60%":
                for (String s : skills) if (a.getSkill(s) < 60) return false;
                return true;
            case "Average >= 50%":
                double sum = 0;
                for (String s : skills) sum += a.getSkill(s);
                double avg = skills.isEmpty() ? 0 : sum / skills.size();
                return avg >= 50;
            default:
                return false;
        }
    }
}
