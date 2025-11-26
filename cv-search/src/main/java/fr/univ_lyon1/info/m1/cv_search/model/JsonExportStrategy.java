package fr.univ_lyon1.info.m1.cv_search.model;

import java.util.List;

/**
 * Exporte les candidats au format JSON.
 * Pattern: Strategy (implémentation concrète).
 */
public class JsonExportStrategy implements ExportStrategy {

    @Override
    public String export(final List<Applicant> applicants) {
        StringBuilder json = new StringBuilder();
        json.append("{\n  \"candidates\": [\n");

        for (int i = 0; i < applicants.size(); i++) {
            Applicant a = applicants.get(i);
            json.append("    {\n");
            json.append("      \"name\": \"").append(escapeJson(a.getName())).append("\",\n");
            json.append("      \"average\": ").append(String.format("%.2f", a.getAverage())).append(",\n");
            json.append("      \"totalScore\": ").append(String.format("%.2f", a.getTotalScore())).append(",\n");

            // Compétences
            json.append("      \"skills\": {\n");
            var skills = a.getSkillsMap();
            int skillCount = 0;
            for (var entry : skills.entrySet()) {
                json.append("        \"").append(escapeJson(entry.getKey())).append("\": ")
                        .append(entry.getValue());
                if (++skillCount < skills.size()) {
                    json.append(",");
                }
                json.append("\n");
            }
            json.append("      },\n");

            // Expériences
            json.append("      \"experiences\": [\n");
            var experiences = a.getExperiences();
            for (int j = 0; j < experiences.size(); j++) {
                Experience exp = experiences.get(j);
                json.append("        {\n");
                json.append("          \"company\": \"").append(escapeJson(exp.getCompany())).append("\",\n");
                json.append("          \"duration\": ").append(exp.getDuration()).append(",\n");
                json.append("          \"keywords\": [");

                var keywords = exp.getKeywords();
                for (int k = 0; k < keywords.size(); k++) {
                    json.append("\"").append(escapeJson(keywords.get(k))).append("\"");
                    if (k < keywords.size() - 1) {
                        json.append(", ");
                    }
                }
                json.append("]\n");
                json.append("        }");
                if (j < experiences.size() - 1) {
                    json.append(",");
                }
                json.append("\n");
            }
            json.append("      ]\n");

            json.append("    }");
            if (i < applicants.size() - 1) {
                json.append(",");
            }
            json.append("\n");
        }

        json.append("  ]\n}");
        return json.toString();
    }

    @Override
    public String getFileExtension() {
        return "json";
    }

    private String escapeJson(final String value) {
        if (value == null) {
            return "";
        }
        return value.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}
