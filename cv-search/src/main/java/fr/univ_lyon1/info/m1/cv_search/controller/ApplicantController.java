package fr.univ_lyon1.info.m1.cv_search.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import fr.univ_lyon1.info.m1.cv_search.model.Applicant;
import fr.univ_lyon1.info.m1.cv_search.model.ApplicantList;
import fr.univ_lyon1.info.m1.cv_search.model.ApplicantListBuilder;
import fr.univ_lyon1.info.m1.cv_search.model.MatchingStrategy;
import fr.univ_lyon1.info.m1.cv_search.model.StrategyFactory;

/**
 * Contrôleur MVC reliant le modèle et la vue dans l'application CV Search.
 * <p>
 * Ce contrôleur gère les actions utilisateur (recherche, ajout de candidats)
 * et met à jour le modèle en conséquence. Il applique les stratégies de filtrage
 * et calcule les scores combinés (compétences + expérience) pour le classement.
 * </p>
 * 
 * Pattern: MVC (Controller) - coordonne les interactions entre la vue et le modèle.
 */
public class ApplicantController {
    /** Le modèle contenant la liste des candidats affichés. */
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

        // Trie les candidats du meilleur au moins bon selon le totalScore (score décroissant)
        List<Applicant> sortedList = new ArrayList<>(filtered.getList());
        sortedList.sort(
                (a1, a2) -> Double.compare(a2.getTotalScore(), a1.getTotalScore())
        );

        // Crée une nouvelle ApplicantList avec les candidats triés
        ApplicantList sortedApplicantList = new ApplicantList();
        for (Applicant a : sortedList) {
            sortedApplicantList.add(a);
        }

        // Met à jour le modèle, ce qui notifie automatiquement toutes les vues observatrices
        model.setList(sortedApplicantList);
    }

    /**
     * Vérifie si un candidat correspond à la stratégie de filtrage choisie.
     * <p>
     * Utilise le pattern Strategy pour déléguer la logique de matching
     * à la stratégie appropriée via la factory.
     * </p>
     *
     * @param a        Le candidat à évaluer.
     * @param skills   Liste des compétences recherchées.
     * @param strategyName Nom de la stratégie de filtrage à appliquer.
     * @return true si le candidat satisfait les critères de la stratégie, false sinon.
     */
    private boolean matches(final Applicant a, final List<String> skills,
                            final String strategyName) {
        MatchingStrategy strategy = StrategyFactory.getStrategy(strategyName);
        
        if (strategy == null) {
            // Stratégie inconnue : rejette le candidat
            System.err.println("Warning: Unknown strategy '" + strategyName + "'");
            return false;
        }
        
        return strategy.matches(a, skills);
    }

    /**
     * Ajoute un candidat au modèle.
     * Cette action déclenche une notification aux vues observatrices.
     *
     * @param a Candidat à ajouter à la liste.
     */
    public void addApplicant(final Applicant a) {
        model.add(a);
    }
}
