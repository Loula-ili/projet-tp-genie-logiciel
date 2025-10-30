package fr.univ_lyon1.info.m1.cv_search.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import fr.univ_lyon1.info.m1.cv_search.model.Applicant;
import fr.univ_lyon1.info.m1.cv_search.model.ApplicantList;
import fr.univ_lyon1.info.m1.cv_search.model.ApplicantListBuilder;

/**
 * Contrôleur MVC reliant le modèle et la vue.
 */
public class ApplicantController {
    private final ApplicantList model;

    /**
     * Crée un contrôleur de candidats.
     *
     * @param model Le modèle de liste de candidats à manipuler.
     */
    public ApplicantController(final ApplicantList model) {
        this.model = model;
    }

    /**
     * Recherche de candidats selon les compétences et la stratégie choisie.
     * Le score final prend en compte 70 % des compétences + 30 % de l’expérience.
     *
     * @param skills   Liste des compétences recherchées.
     * @param strategy Stratégie de filtrage sélectionnée dans la vue.
     */
    public void search(final List<String> skills, final String strategy) {
        ApplicantList allApplicants =
                new ApplicantListBuilder(new File(".")).build();
        ApplicantList filtered = new ApplicantList();

        for (Applicant a : allApplicants) {
            if (skills.isEmpty()) {
                continue;
            }

            // Vérifie si le candidat correspond à la stratégie
            if (!matches(a, skills, strategy)) {
                continue;
            }

            // Calcul du score pondéré (70 % compétence + 30 % expérience)
            double sum = 0;
            for (String s : skills) {
                int skillScore = a.getSkill(s);
                double expBonus =
                        a.getExperienceScore(s) * 100; // 10 ans = +100 pts

                double combined = 0.7 * skillScore + 0.3 * expBonus;
                sum += combined;

                // Debug facultatif
                System.out.printf(
                        "Candidat %-15s | Compétence %-10s | Skill=%3d | "
                                + "Exp=%2d ans | Score=%.1f%n",
                        a.getName(),
                        s,
                        skillScore,
                        (int) a.getExperienceYearsForSkill(s),
                        combined
                );
            }

            double avg = sum / skills.size();
            a.setTotalScore(avg); // Score combiné global

            // Calcul de la moyenne réelle uniquement pour la stratégie "Average >= 50%"
            if ("Average >= 50%".equals(strategy)) {
                double rawSum = 0;
                for (String s : skills) {
                    rawSum += a.getSkill(s);
                }
                double moyenne = rawSum / skills.size();
                a.setAverage(moyenne);
            } else {
                a.setAverage(0);
            }

            filtered.add(a);
        }

        // Trie les candidats du meilleur au moins bon selon le totalScore
        List<Applicant> sortedList = new ArrayList<>(filtered.getList());
        sortedList.sort(
                (a1, a2) -> Double.compare(a2.getTotalScore(), a1.getTotalScore())
        );

        ApplicantList sortedApplicantList = new ApplicantList();
        for (Applicant a : sortedList) {
            sortedApplicantList.add(a);
        }

        // Met à jour la vue
        model.setList(sortedApplicantList);
    }

    /**
     * Vérifie si un candidat correspond à la stratégie choisie.
     *
     * @param a        Le candidat à évaluer.
     * @param skills   Liste des compétences recherchées.
     * @param strategy Stratégie de filtrage.
     * @return true si le candidat correspond à la stratégie.
     */
    private boolean matches(final Applicant a, final List<String> skills,
                            final String strategy) {
        switch (strategy) {
            case "All >= 50%":
                for (String s : skills) {
                    if (a.getSkill(s) < 50) {
                        return false;
                    }
                }
                return true;

            case "All >= 60%":
                for (String s : skills) {
                    if (a.getSkill(s) < 60) {
                        return false;
                    }
                }
                return true;

            case "Average >= 50%":
                double sum = 0;
                for (String s : skills) {
                    sum += a.getSkill(s);
                }
                double avg = sum / skills.size();
                return avg >= 50;

            case "Max Skill >= 70%":
                for (String s : skills) {
                    if (a.getSkill(s) >= 70) {
                        return true;
                    }
                }
                return false;

            default:
                return false;
        }
    }

    /**
     * Ajoute un candidat au modèle.
     *
     * @param a Candidat à ajouter.
     */
    public void addApplicant(final Applicant a) {
        model.add(a);
    }
}
