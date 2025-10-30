package fr.univ_lyon1.info.m1.cv_search.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Représente un candidat possédant un nom, une liste de compétences
 * et des expériences professionnelles.
 */
public class Applicant {
    private Map<String, Integer> skills = new HashMap<>();
    private String name;
    private double average; // moyenne des compétences
    private double totalScore = 0;
    private List<Experience> experiences = new ArrayList<>();

    /**
     * Retourne le score total combiné du candidat.
     *
     * @return Score global combiné.
     */
    public double getTotalScore() {
        return totalScore;
    }

    /**
     * Définit le score total combiné du candidat.
     *
     * @param totalScore Score à enregistrer.
     */
    public void setTotalScore(final double totalScore) {
        this.totalScore = totalScore;
    }

    /**
     * Retourne le score associé à une compétence donnée.
     *
     * @param skillName Nom de la compétence.
     * @return Score ou 0 si absent.
     */
    public int getSkill(final String skillName) {
        return skills.getOrDefault(skillName, 0);
    }

    /**
     * Ajoute une compétence et son score.
     *
     * @param skillName Nom de la compétence.
     * @param value     Score associé.
     */
    public void setSkill(final String skillName, final int value) {
        skills.put(skillName, value);
    }

    /**
     * Retourne le nom du candidat.
     *
     * @return Nom.
     */
    public String getName() {
        return name;
    }

    /**
     * Définit le nom du candidat.
     *
     * @param name Nom du candidat.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Retourne la moyenne des compétences.
     *
     * @return Moyenne.
     */
    public double getAverage() {
        return average;
    }

    /**
     * Définit la moyenne des compétences.
     *
     * @param avg Moyenne à définir.
     */
    public void setAverage(final double avg) {
        this.average = avg;
    }

    /**
     * Ajoute une expérience professionnelle au profil du candidat.
     *
     * @param company  Nom de l'entreprise.
     * @param start    Année de début.
     * @param end      Année de fin.
     * @param keywords Liste des compétences pratiquées.
     */
    public void addExperience(final String company, final int start,
                              final int end, final List<String> keywords) {
        experiences.add(new Experience(company, start, end, keywords));
    }

    /**
     * Retourne la liste des expériences du candidat.
     *
     * @return Liste des expériences.
     */
    public List<Experience> getExperiences() {
        return experiences;
    }

    /**
     * Calcule le nombre total d'années d'expérience pour une compétence donnée.
     *
     * @param skill Compétence recherchée.
     * @return Durée totale en années.
     */
    public int getExperienceYearsForSkill(final String skill) {
        int total = 0;
        String normalized = skill.trim().toLowerCase();

        for (Experience e : experiences) {
            for (String kw : e.getKeywords()) {
                if (kw.trim().toLowerCase().equals(normalized)) {
                    total += e.getDuration();
                    break; // On ne compte qu'une fois par expérience.
                }
            }
        }
        return total;
    }

    /**
     * Calcule un score d'expérience normalisé entre 0 et 1.
     * (Exemple : 10 ans = 1.0 = 100 %)
     *
     * @param skill Compétence à évaluer.
     * @return Score d'expérience (max 1.0).
     */
    public double getExperienceScore(final String skill) {
        int years = getExperienceYearsForSkill(skill);
        if (years == 0) {
            return 0.0;
        }
        return Math.min(1.0, years / 10.0); // plafonné à 10 ans = 100 %
    }
}
