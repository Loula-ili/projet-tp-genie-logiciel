package fr.univ_lyon1.info.m1.cv_search.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Représente un candidat possédant un nom, une map de compétences et des
 * expériences.
 */
public class Applicant {

    private final Map<String, Integer> skills = new HashMap<>();
    private String name;
    private double average; // moyenne (0..100)
    private double totalScore = 0;
    private final List<Experience> experiences = new ArrayList<>();

    /**
     * Constructeur par défaut.
     */
    public Applicant() {
        // constructeur par défaut
    }

    /**
     * Constructeur avec nom.
     * 
     * @param name Le nom du candidat.
     */
    public Applicant(final String name) {
        this.name = name;
    }

    // ----- Skills -----

    /**
     * Retourne le score d'une compétence (recherche insensible à la casse).
     * 
     * @param skillName nom de la compétence
     * @return score 0..100 si présent, sinon 0
     */
    public int getSkill(final String skillName) {
        if (skillName == null) {
            return 0;
        }
        String normalized = skillName.trim().toLowerCase();
        for (Map.Entry<String, Integer> e : skills.entrySet()) {
            if (e.getKey().trim().toLowerCase().equals(normalized)) {
                return e.getValue();
            }
        }
        return 0;
    }

    /**
     * Ajoute ou met à jour une compétence.
     * 
     * @param skillName nom
     * @param value     score 0..100
     */
    public void setSkill(final String skillName, final int value) {
        if (skillName == null) {
            return;
        }
        String key = skillName.trim();
        int val = Math.max(0, Math.min(100, value));
        skills.put(key, val);
        recomputeAggregateScores();
    }

    public Map<String, Integer> getSkillsMap() {
        return new HashMap<>(skills);
    }

    // ----- Name -----

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    // ----- Average / TotalScore -----

    public double getAverage() {
        return average;
    }

    public void setAverage(final double avg) {
        this.average = avg;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(final double totalScore) {
        this.totalScore = totalScore;
    }

    private void recomputeAggregateScores() {
        if (skills.isEmpty()) {
            this.average = 0;
            this.totalScore = 0;
            return;
        }
        double sum = 0;
        for (int v : skills.values()) {
            sum += v;
        }
        this.average = sum / skills.size();
        this.totalScore = sum;
    }

    // ----- Experiences -----

    /**
     * Ajoute une expérience professionnelle.
     * 
     * @param company   Nom de l'entreprise
     * @param startYear Année de début
     * @param endYear   Année de fin
     * @param keywords  Liste des compétences utilisées
     */
    public void addExperience(final String company, final int startYear,
            final int endYear, final List<String> keywords) {
        experiences.add(new Experience(company, startYear, endYear, keywords));
    }

    public List<Experience> getExperiences() {
        return new ArrayList<>(experiences);
    }

    /**
     * Nombre total d'années d'expérience pour une compétence (heuristique).
     */
    public int getExperienceYearsForSkill(final String skill) {
        if (skill == null) {
            return 0;
        }
        String normalized = skill.trim().toLowerCase();
        int total = 0;
        for (Experience e : experiences) {
            for (String kw : e.getKeywords()) {
                if (kw != null && kw.trim().toLowerCase().equals(normalized)) {
                    total += e.getDuration();
                    break;
                }
            }
        }
        return total;
    }

    /**
     * Score d'expérience normalisé entre 0 et 1 (10 ans -> 1.0).
     */
    public double getExperienceScore(final String skill) {
        int years = getExperienceYearsForSkill(skill);
        if (years <= 0) {
            return 0.0;
        }
        return Math.min(1.0, years / 10.0);
    }
}
