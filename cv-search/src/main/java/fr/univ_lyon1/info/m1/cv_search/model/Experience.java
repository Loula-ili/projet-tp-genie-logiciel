package fr.univ_lyon1.info.m1.cv_search.model;

import java.util.List;

/**
 * Représente une expérience professionnelle d’un candidat.
 */
public class Experience {
    private final String company;
    private final int start;
    private final int end;
    private final List<String> keywords;

    /**
     * Constructeur d'une expérience professionnelle.
     *
     * @param company  Nom de l'entreprise.
     * @param start    Année de début.
     * @param end      Année de fin.
     * @param keywords Liste des compétences utilisées.
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
     *
     * @return Nombre d'années.
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
