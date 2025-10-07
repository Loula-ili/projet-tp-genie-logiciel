package fr.univ_lyon1.info.m1.cv_search.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Représente un candidat possédant un nom et une liste de compétences avec leurs scores.
 */
public class Applicant {
    private Map<String, Integer> skills = new HashMap<>();
    private String name;
    private double average; // moyenne des compétences

    /**
     * Retourne le score associé à une compétence donnée.
     *
     * @param skillName Nom de la compétence.
     * @return Score de la compétence ou 0 si non présente.
     */
    public int getSkill(final String skillName) {
        return skills.getOrDefault(skillName, 0);
    }

    /**
     * Associe une valeur de score à une compétence pour ce candidat.
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
     * @return Nom du candidat.
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
     * Retourne la moyenne des scores du candidat.
     *
     * @return Moyenne des scores.
     */
    public double getAverage() {
        return average;
    }

    /**
     * Définit la moyenne des scores du candidat.
     *
     * @param avg Moyenne à définir.
     */
    public void setAverage(final double avg) {
        this.average = avg;
    }
}
