package fr.univ_lyon1.info.m1.cv_search.model;

import java.util.List;

/**
 * Interface pour exporter des données de candidats dans différents formats.
 * Pattern: Strategy pour l'export de données.
 */
public interface ExportStrategy {

    /**
     * Exporte une liste de candidats dans un format spécifique.
     *
     * @param applicants Liste des candidats à exporter.
     * @return Le contenu exporté sous forme de chaîne.
     */
    String export(List<Applicant> applicants);

    /**
     * Retourne l'extension de fichier pour ce format.
     *
     * @return L'extension (ex: "csv", "json").
     */
    String getFileExtension();
}
