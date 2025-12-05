package fr.univ_lyon1.info.m1.cv_search.model;

import java.util.List;

/**
 * Exporte les candidats au format CSV (Comma-Separated Values).
 * <p>
 * Génère un fichier CSV avec les colonnes suivantes :
 * - Nom du candidat
 * - Note moyenne des compétences
 * - Score total (combinant compétences et expérience)
 * - Années totales d'expérience
 * - Liste des compétences avec leurs niveaux
 * </p>
 * 
 * Pattern: Strategy (implémentation concrète) - définit un algorithme d'export CSV.
 */
public class CsvExportStrategy implements ExportStrategy {

    /**
     * Exporte la liste de candidats au format CSV.
     * 
     * @param applicants Liste des candidats à exporter.
     * @return Une chaîne de caractères au format CSV.
     */
    @Override
    public String export(final List<Applicant> applicants) {
        StringBuilder csv = new StringBuilder();

        // Ligne d'en-tête définissant les colonnes
        csv.append("Nom,Note Moyenne,Score Total,Années Expérience,Compétences\n");

        // Génération d'une ligne par candidat
        for (Applicant a : applicants) {
            // Nom du candidat (échappé si contient des caractères spéciaux)
            csv.append(escapeCsv(a.getName())).append(",");
            
            // Note moyenne et score total (2 décimales)
            csv.append(String.format("%.2f", a.getAverage())).append(",");
            csv.append(String.format("%.2f", a.getTotalScore())).append(",");

            // Calcul du nombre total d'années d'expérience
            int totalYears = a.getExperiences().stream()
                    .mapToInt(Experience::getDuration)
                    .sum();
            csv.append(totalYears).append(",");

            // Liste des compétences au format "compétence:niveau"
            String skills = a.getSkillsMap().entrySet().stream()
                    .map(e -> e.getKey() + ":" + e.getValue())
                    .reduce((s1, s2) -> s1 + "; " + s2)
                    .orElse("");
            csv.append('"').append(skills).append('"');

            csv.append("\n");
        }

        return csv.toString();
    }

    /**
     * Retourne l'extension de fichier pour le format CSV.
     * 
     * @return "csv"
     */
    @Override
    public String getFileExtension() {
        return "csv";
    }

    /**
     * Échappe les caractères spéciaux pour le format CSV.
     * Si la valeur contient des virgules, guillemets ou retours à la ligne,
     * elle est encadrée de guillemets et les guillemets internes sont doublés.
     * 
     * @param value La valeur à échapper.
     * @return La valeur échappée selon les règles CSV.
     */
    private String escapeCsv(final String value) {
        if (value == null) {
            return "";
        }
        // Si la valeur contient des caractères spéciaux, on l'encadre de guillemets
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
}
