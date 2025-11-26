package fr.univ_lyon1.info.m1.cv_search.model;

import java.util.List;

/**
 * Exporte les candidats au format CSV.
 * Pattern: Strategy (implémentation concrète).
 */
public class CsvExportStrategy implements ExportStrategy {

    @Override
    public String export(final List<Applicant> applicants) {
        StringBuilder csv = new StringBuilder();

        // En-tête
        csv.append("Nom,Note Moyenne,Score Total,Années Expérience,Compétences\n");

        // Données
        for (Applicant a : applicants) {
            csv.append(escapeCsv(a.getName())).append(",");
            csv.append(String.format("%.2f", a.getAverage())).append(",");
            csv.append(String.format("%.2f", a.getTotalScore())).append(",");

            // Calcul total années d'expérience
            int totalYears = a.getExperiences().stream()
                    .mapToInt(Experience::getDuration)
                    .sum();
            csv.append(totalYears).append(",");

            // Liste des compétences
            String skills = a.getSkillsMap().entrySet().stream()
                    .map(e -> e.getKey() + ":" + e.getValue())
                    .reduce((s1, s2) -> s1 + "; " + s2)
                    .orElse("");
            csv.append('"').append(skills).append('"');

            csv.append("\n");
        }

        return csv.toString();
    }

    @Override
    public String getFileExtension() {
        return "csv";
    }

    private String escapeCsv(final String value) {
        if (value == null) {
            return "";
        }
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
}
