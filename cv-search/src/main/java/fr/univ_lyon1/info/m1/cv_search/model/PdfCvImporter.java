package fr.univ_lyon1.info.m1.cv_search.model;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

/**
 * Classe pour importer des CV au format PDF.
 * Extrait le texte du PDF et tente de parser les compétences et le nom.
 * 
 * Pattern: Builder - construit un objet Applicant depuis un fichier PDF.
 */
public class PdfCvImporter {

    /**
     * Importe un CV depuis un fichier PDF.
     * 
     * @param pdfFile Fichier PDF contenant le CV
     * @return Un objet Applicant avec les informations extraites
     * @throws IOException Si le fichier ne peut pas être lu
     */
    public Applicant importFromPdf(final File pdfFile) throws IOException {
        if (pdfFile == null || !pdfFile.exists()) {
            throw new IOException("Le fichier PDF n'existe pas");
        }

        // Extraction du texte du PDF
        String text = extractTextFromPdf(pdfFile);

        // Création de l'applicant
        Applicant applicant = new Applicant();

        // Extraction du nom (première ligne ou nom du fichier)
        String name = extractName(text, pdfFile);
        applicant.setName(name);

        // Extraction des compétences
        Map<String, Integer> skills = extractSkills(text);
        for (Map.Entry<String, Integer> entry : skills.entrySet()) {
            applicant.setSkill(entry.getKey(), entry.getValue());
        }

        return applicant;
    }

    /**
     * Extrait le texte d'un fichier PDF.
     * 
     * @param pdfFile Fichier PDF
     * @return Texte extrait
     * @throws IOException Si erreur de lecture
     */
    private String extractTextFromPdf(final File pdfFile) throws IOException {
        try (PDDocument document = PDDocument.load(pdfFile)) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }

    /**
     * Extrait le nom du candidat depuis le texte du CV.
     * Analyse les premières lignes pour trouver un nom (1 à 4 mots, moins de 50 caractères).
     * Si aucun nom n'est trouvé, utilise le nom du fichier comme fallback.
     * 
     * @param text Texte extrait du PDF
     * @param pdfFile Fichier PDF source
     * @return Nom du candidat (ou nom du fichier sans extension)
     */
    private String extractName(final String text, final File pdfFile) {
        String[] lines = text.split("\n");
        for (String line : lines) {
            String trimmed = line.trim();
            // Recherche d'une ligne courte qui pourrait être un nom
            if (!trimmed.isEmpty() && trimmed.length() > 2 && trimmed.length() < 50) {
                String[] words = trimmed.split("\\s+");
                // Un nom contient généralement entre 1 et 4 mots
                if (words.length >= 1 && words.length <= 4) {
                    // Évite les lignes qui ressemblent à des listes de compétences
                    boolean looksLikeName = words.length <= 2 
                            || !trimmed.toLowerCase().contains("java");
                    if (looksLikeName) {
                        return trimmed;
                    }
                }
            }
        }
        // Si aucun nom trouvé, utilise le nom du fichier sans extension
        String filename = pdfFile.getName();
        return filename.replaceFirst("[.][^.]+$", "");
    }

    /**
     * Extrait les compétences techniques depuis le texte du CV.
     * Recherche une liste prédéfinie de technologies courantes et calcule un score
     * basé sur la fréquence de mention et le niveau d'expertise indiqué.
     * 
     * @param text Texte extrait du CV
     * @return Map associant chaque compétence trouvée à son score estimé (0-95)
     */
    private Map<String, Integer> extractSkills(final String text) {
        Map<String, Integer> skills = new HashMap<>();
        String lowerText = text.toLowerCase();

        // Liste des compétences techniques à rechercher dans le CV
        String[] commonSkills = {
            "java", "python", "c++", "c#", "javascript", "typescript",
            "react", "angular", "vue", "node", "spring", "django",
            "sql", "mongodb", "postgresql", "mysql",
            "docker", "kubernetes", "git", "linux", "aws", "azure",
            "html", "css", "php", "ruby", "go", "rust",
            "maven", "gradle", "jenkins", "ci/cd"
        };

        for (String skill : commonSkills) {
            // Recherche du mot exact (évite les faux positifs)
            Pattern pattern = Pattern.compile("\\b" + skill + "\\b", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(text);

            if (matcher.find()) {
                // Score de base si la compétence est mentionnée
                int score = 60;

                // Bonus selon le nombre de mentions (max +20)
                int count = 0;
                while (matcher.find()) {
                    count++;
                }
                score += Math.min(count * 5, 20);

                // Bonus selon le niveau d'expertise mentionné
                if (lowerText.contains(skill + " expert") 
                        || lowerText.contains("expert " + skill)) {
                    score = Math.min(score + 20, 95);
                } else if (lowerText.contains(skill + " avancé") 
                        || lowerText.contains("avancé " + skill)) {
                    score = Math.min(score + 10, 85);
                }

                skills.put(skill, Math.min(score, 95));
            }
        }

        return skills;
    }
}
