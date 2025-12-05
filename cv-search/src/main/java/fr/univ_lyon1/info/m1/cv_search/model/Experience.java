package fr.univ_lyon1.info.m1.cv_search.model;

import java.util.List;

/**
 * Représente une expérience professionnelle d'un candidat.
 * <p>
 * Chaque expérience contient le nom de l'entreprise, les dates de début et fin,
 * ainsi qu'une liste de mots-clés (compétences) utilisés durant cette période.
 * </p>
 */
public class Experience {
    /** Nom de l'entreprise où s'est déroulée l'expérience. */
    private final String company;
    
    /** Année de début de l'expérience. */
    private final int start;
    
    /** Année de fin de l'expérience. */
    private final int end;
    
    /** Liste des compétences (mots-clés) utilisées pendant cette expérience. */
    private final List<String> keywords;

    /**
     * Constructeur d'une expérience professionnelle.
     *
     * @param company  Nom de l'entreprise.
     * @param start    Année de début.
     * @param end      Année de fin.
     * @param keywords Liste des compétences utilisées durant cette période.
     */
    public Experience(final String company, final int start,
                      final int end, final List<String> keywords) {
        this.company = company;
        this.start = start;
        this.end = end;
        this.keywords = keywords;
    }

    /**
     * Retourne la durée de l'expérience en années.
     * Calcule simplement la différence entre l'année de fin et l'année de début.
     *
     * @return Nombre d'années d'expérience.
     */
    public int getDuration() {
        return end - start;
    }

    /**
     * Retourne la liste des mots-clés (compétences) associés à cette expérience.
     *
     * @return Liste des compétences.
     */
    public List<String> getKeywords() {
        return keywords;
    }

    /**
     * Retourne le nom de l'entreprise.
     *
     * @return Nom de l'entreprise.
     */
    public String getCompany() {
        return company;
    }
}
