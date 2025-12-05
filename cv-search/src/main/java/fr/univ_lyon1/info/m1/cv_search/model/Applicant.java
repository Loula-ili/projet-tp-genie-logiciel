package fr.univ_lyon1.info.m1.cv_search.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Représente un candidat dans le système de recherche de CV.
 * <p>
 * Un candidat possède :
 * - Un nom
 * - Des compétences avec leur niveau (0-100)
 * - Des expériences professionnelles
 * - Des scores calculés (moyenne et score total)
 * </p>
 * 
 * Cette classe est le cœur du modèle métier de l'application.
 */
public class Applicant {

    /** Map des compétences : nom de la compétence -> niveau (0-100). */
    private final Map<String, Integer> skills = new HashMap<>();
    
    /** Nom du candidat. */
    private String name;
    
    /** Moyenne des compétences (0-100), recalculée automatiquement. */
    private double average;
    
    /** Score total combinant compétences et expérience, utilisé pour le classement. */
    private double totalScore = 0;
    
    /** Liste des expériences professionnelles du candidat. */
    private final List<Experience> experiences = new ArrayList<>();

    /**
     * Constructeur par défaut.
     * Crée un candidat sans nom ni compétences.
     */
    public Applicant() {
        // constructeur par défaut
    }

    /**
     * Constructeur avec nom.
     * Crée un candidat avec un nom donné.
     * 
     * @param name Le nom du candidat.
     */
    public Applicant(final String name) {
        this.name = name;
    }

    // ----- Gestion des compétences (Skills) -----

    /**
     * Retourne le score d'une compétence donnée.
     * La recherche est insensible à la casse et ignore les espaces.
     * 
     * @param skillName Nom de la compétence recherchée.
     * @return Score de la compétence (0-100) si elle existe, sinon 0.
     */
    public int getSkill(final String skillName) {
        if (skillName == null) {
            return 0;
        }
        // Normalisation : minuscules et sans espaces
        String normalized = skillName.trim().toLowerCase();
        for (Map.Entry<String, Integer> e : skills.entrySet()) {
            if (e.getKey().trim().toLowerCase().equals(normalized)) {
                return e.getValue();
            }
        }
        return 0;
    }

    /**
     * Ajoute ou met à jour une compétence avec son niveau.
     * Le niveau est automatiquement borné entre 0 et 100.
     * Recalcule les scores agrégés après modification.
     * 
     * @param skillName Nom de la compétence.
     * @param value     Niveau de la compétence (sera borné entre 0 et 100).
     */
    public void setSkill(final String skillName, final int value) {
        if (skillName == null) {
            return;
        }
        String key = skillName.trim();
        // Borne la valeur entre 0 et 100
        int val = Math.max(0, Math.min(100, value));
        skills.put(key, val);
        // Recalcule la moyenne et le score total
        recomputeAggregateScores();
    }

    /**
     * Retourne une copie de la map des compétences.
     * 
     * @return Une nouvelle HashMap contenant toutes les compétences.
     */
    public Map<String, Integer> getSkillsMap() {
        return new HashMap<>(skills);
    }

    // ----- Nom du candidat -----

    /**
     * Retourne le nom du candidat.
     * 
     * @return Le nom du candidat.
     */
    public String getName() {
        return name;
    }

    /**
     * Définit le nom du candidat.
     * 
     * @param name Le nouveau nom.
     */
    public void setName(final String name) {
        this.name = name;
    }

    // ----- Scores agrégés (Average / TotalScore) -----

    /**
     * Retourne la moyenne des compétences.
     * 
     * @return La moyenne (0-100).
     */
    public double getAverage() {
        return average;
    }

    /**
     * Définit manuellement la moyenne des compétences.
     * 
     * @param avg La nouvelle moyenne.
     */
    public void setAverage(final double avg) {
        this.average = avg;
    }

    /**
     * Retourne le score total du candidat.
     * Ce score combine compétences et expérience (70% - 30%).
     * 
     * @return Le score total.
     */
    public double getTotalScore() {
        return totalScore;
    }

    /**
     * Définit manuellement le score total du candidat.
     * 
     * @param totalScore Le nouveau score total.
     */
    public void setTotalScore(final double totalScore) {
        this.totalScore = totalScore;
    }

    /**
     * Recalcule automatiquement la moyenne et le score total des compétences.
     * Appelé automatiquement après chaque modification de compétence.
     */
    private void recomputeAggregateScores() {
        if (skills.isEmpty()) {
            this.average = 0;
            this.totalScore = 0;
            return;
        }
        // Calcule la somme de toutes les compétences
        double sum = 0;
        for (int v : skills.values()) {
            sum += v;
        }
        // Moyenne : somme / nombre de compétences
        this.average = sum / skills.size();
        // Score total : somme brute des compétences
        this.totalScore = sum;
    }

    // ----- Gestion des expériences professionnelles -----

    /**
     * Ajoute une expérience professionnelle au candidat.
     * 
     * @param company   Nom de l'entreprise.
     * @param startYear Année de début.
     * @param endYear   Année de fin.
     * @param keywords  Liste des compétences utilisées durant cette expérience.
     */
    public void addExperience(final String company, final int startYear,
            final int endYear, final List<String> keywords) {
        experiences.add(new Experience(company, startYear, endYear, keywords));
    }

    /**
     * Retourne une copie de la liste des expériences.
     * 
     * @return Une nouvelle liste contenant toutes les expériences.
     */
    public List<Experience> getExperiences() {
        return new ArrayList<>(experiences);
    }

    /**
     * Calcule le nombre total d'années d'expérience pour une compétence donnée.
     * Parcourt toutes les expériences et additionne les durées où la compétence
     * apparaît dans les mots-clés.
     * 
     * @param skill La compétence recherchée.
     * @return Le nombre total d'années d'expérience (peut être supérieur à la durée
     *         réelle si plusieurs expériences simultanées utilisent la compétence).
     */
    public int getExperienceYearsForSkill(final String skill) {
        if (skill == null) {
            return 0;
        }
        // Normalisation pour la comparaison
        String normalized = skill.trim().toLowerCase();
        int total = 0;
        
        // Parcourt toutes les expériences
        for (Experience e : experiences) {
            // Vérifie si la compétence est dans les mots-clés de cette expérience
            for (String kw : e.getKeywords()) {
                if (kw != null && kw.trim().toLowerCase().equals(normalized)) {
                    total += e.getDuration();
                    break; // Ne compte qu'une fois par expérience
                }
            }
        }
        return total;
    }

    /**
     * Calcule un score d'expérience normalisé entre 0 et 1 pour une compétence.
     * La formule utilisée : années / 10 (plafonné à 1.0).
     * Ainsi, 10 ans d'expérience = score de 1.0.
     * 
     * @param skill La compétence pour laquelle calculer le score d'expérience.
     * @return Un score entre 0.0 et 1.0.
     */
    public double getExperienceScore(final String skill) {
        int years = getExperienceYearsForSkill(skill);
        if (years <= 0) {
            return 0.0;
        }
        // Normalisation : 10 ans = 1.0, plafonné à 1.0 maximum
        return Math.min(1.0, years / 10.0);
    }
}
