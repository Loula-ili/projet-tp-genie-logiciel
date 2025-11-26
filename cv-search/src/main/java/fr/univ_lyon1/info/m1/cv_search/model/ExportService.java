package fr.univ_lyon1.info.m1.cv_search.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Service d'export de candidats utilisant le pattern Strategy.
 * Pattern: Facade + Strategy
 */
public final class ExportService {

    private static ExportService instance;

    private ExportService() {
        // Private constructor for Singleton
    }

    /**
     * Retourne l'instance unique du service d'export (Singleton).
     *
     * @return L'instance unique.
     */
    public static synchronized ExportService getInstance() {
        if (instance == null) {
            instance = new ExportService();
        }
        return instance;
    }

    /**
     * Exporte une liste de candidats vers un fichier.
     *
     * @param applicants Liste des candidats.
     * @param file       Fichier de destination.
     * @param strategy   Stratégie d'export (CSV, JSON, etc.).
     * @throws IOException En cas d'erreur d'écriture.
     */
    public void exportToFile(final List<Applicant> applicants,
            final File file,
            final ExportStrategy strategy) throws IOException {
        String content = strategy.export(applicants);

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
        }
    }

    /**
     * Retourne le contenu exporté sous forme de chaîne sans l'écrire dans un
     * fichier.
     *
     * @param applicants Liste des candidats.
     * @param strategy   Stratégie d'export.
     * @return Le contenu exporté.
     */
    public String exportToString(final List<Applicant> applicants,
            final ExportStrategy strategy) {
        return strategy.export(applicants);
    }
}
