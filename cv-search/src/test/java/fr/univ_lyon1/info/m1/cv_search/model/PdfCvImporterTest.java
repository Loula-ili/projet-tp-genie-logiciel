package fr.univ_lyon1.info.m1.cv_search.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Tests pour la classe PdfCvImporter.
 */
class PdfCvImporterTest {

    @TempDir
    private File tempDir;

    /**
     * Méthode utilitaire pour créer un fichier PDF de test contenant du texte.
     * Utilisée par les différents tests pour générer des CV PDF simulés.
     * 
     * @param file Fichier PDF de destination
     * @param content Contenu textuel à insérer (lignes séparées par \n)
     * @throws IOException Si erreur lors de la création du PDF
     */
    private void createPdfWithText(final File file, final String content) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = 
                    new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(50, 700);

                // Écriture du texte ligne par ligne
                String[] lines = content.split("\n");
                for (String line : lines) {
                    contentStream.showText(line);
                    contentStream.newLineAtOffset(0, -15);
                }

                contentStream.endText();
            }

            document.save(file);
        }
    }

    @Test
    void testImportPdfWithName() throws IOException {
        // Création d'un PDF de test
        File pdfFile = new File(tempDir, "test-cv.pdf");
        String content = "Jean Dupont\n"
                + "Ingénieur logiciel\n"
                + "Compétences: Java, Python, SQL";
        createPdfWithText(pdfFile, content);

        // Import du PDF
        PdfCvImporter importer = new PdfCvImporter();
        Applicant applicant = importer.importFromPdf(pdfFile);

        // Vérifications
        assertNotNull(applicant);
        assertThat(applicant.getName(), equalTo("Jean Dupont"));
    }

    @Test
    void testImportPdfExtractsSkills() throws IOException {
        // PDF avec plusieurs compétences
        File pdfFile = new File(tempDir, "developer-cv.pdf");
        String content = "Marie Martin\n"
                + "Développeuse Full Stack\n"
                + "Compétences: Java, JavaScript, Python, React, SQL, Docker";
        createPdfWithText(pdfFile, content);

        PdfCvImporter importer = new PdfCvImporter();
        Applicant applicant = importer.importFromPdf(pdfFile);

        // Vérifie que les compétences sont extraites
        assertNotNull(applicant);
        assertThat(applicant.getSkill("java"), greaterThan(0));
        assertThat(applicant.getSkill("javascript"), greaterThan(0));
        assertThat(applicant.getSkill("python"), greaterThan(0));
        assertThat(applicant.getSkill("react"), greaterThan(0));
        assertThat(applicant.getSkill("sql"), greaterThan(0));
        assertThat(applicant.getSkill("docker"), greaterThan(0));
    }

    @Test
    void testImportPdfWithNonExistentFile() {
        File nonExistent = new File(tempDir, "inexistant.pdf");

        PdfCvImporter importer = new PdfCvImporter();
        assertThrows(IOException.class, () -> {
            importer.importFromPdf(nonExistent);
        });
    }

    @Test
    void testImportPdfWithNullFile() {
        PdfCvImporter importer = new PdfCvImporter();
        assertThrows(IOException.class, () -> {
            importer.importFromPdf(null);
        });
    }

    @Test
    void testImportPdfUsesFilenameWhenNoName() throws IOException {
        // PDF sans nom clair (juste des compétences)
        File pdfFile = new File(tempDir, "candidate-profile.pdf");
        String content = "Java Python SQL";
        createPdfWithText(pdfFile, content);

        PdfCvImporter importer = new PdfCvImporter();
        Applicant applicant = importer.importFromPdf(pdfFile);

        // Le nom devrait être celui du fichier (sans extension)
        assertNotNull(applicant);
        assertThat(applicant.getName(), equalTo("candidate-profile"));
    }

    @Test
    void testImportPdfWithExpertSkills() throws IOException {
        // Teste que les niveaux d'expertise influencent les scores
        File pdfFile = new File(tempDir, "expert-cv.pdf");
        String content = "Expert Developer\n"
                + "Java expert avec 10 ans d'expérience\n"
                + "Python avancé\n"
                + "SQL";
        createPdfWithText(pdfFile, content);

        PdfCvImporter importer = new PdfCvImporter();
        Applicant applicant = importer.importFromPdf(pdfFile);

        // Vérifie que les scores reflètent les niveaux d'expertise
        int javaScore = applicant.getSkill("java");
        int pythonScore = applicant.getSkill("python");
        int sqlScore = applicant.getSkill("sql");

        assertThat(javaScore, greaterThan(pythonScore));
        assertThat(pythonScore, greaterThan(sqlScore));
    }
}
