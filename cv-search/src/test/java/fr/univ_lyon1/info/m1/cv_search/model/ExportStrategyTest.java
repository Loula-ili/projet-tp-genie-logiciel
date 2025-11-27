package fr.univ_lyon1.info.m1.cv_search.model;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests pour les stratégies d'export (Pattern Strategy).
 */
public class ExportStrategyTest {

    private List<Applicant> applicants;

    @BeforeEach
    void setUp() {
        applicants = new ArrayList<>();

        // Créer des candidats de test avec le Builder
        ApplicantBuilder builder1 = new ApplicantBuilder("applicant1.yaml");
        Applicant a1 = builder1.build();
        if (a1 != null) {
            applicants.add(a1);
        }

        ApplicantBuilder builder2 = new ApplicantBuilder("applicant2.yaml");
        Applicant a2 = builder2.build();
        if (a2 != null) {
            applicants.add(a2);
        }
    }

    @Test
    void testCsvExport() {
        
        ExportStrategy csvStrategy = new CsvExportStrategy();

        
        String result = csvStrategy.export(applicants);

        
        assertThat(result, containsString("Nom,"));
        assertThat(result, containsString(","));
        assertTrue(result.split("\n").length > 1, "CSV format check");
    }

    @Test
    void testCsvFileExtension() {
        
        ExportStrategy csvStrategy = new CsvExportStrategy();

        
        String extension = csvStrategy.getFileExtension();

        
        assertThat(extension, org.hamcrest.CoreMatchers.is("csv"));
    }

    @Test
    void testJsonExport() {
        
        ExportStrategy jsonStrategy = new JsonExportStrategy();

        
        String result = jsonStrategy.export(applicants);

        
        assertThat(result, containsString("{"));
        assertThat(result, containsString("}"));
        assertThat(result, containsString("\"candidates\""));
        assertThat(result, containsString("\"name\""));
        assertThat(result, containsString("\"skills\""));
    }

    @Test
    void testJsonFileExtension() {
        
        ExportStrategy jsonStrategy = new JsonExportStrategy();

        
        String extension = jsonStrategy.getFileExtension();

        
        assertThat(extension, org.hamcrest.CoreMatchers.is("json"));
    }

    @Test
    void testExportEmptyList() {
        
        List<Applicant> emptyList = new ArrayList<>();
        ExportStrategy csvStrategy = new CsvExportStrategy();

        
        String result = csvStrategy.export(emptyList);

        
        assertThat(result, containsString("Nom,"));
        // Header only
    }

    @Test
    void testExportServiceFacade() {
        
        ExportService service = ExportService.getInstance();

        
        String csvResult = service.exportToString(applicants, new CsvExportStrategy());
        String jsonResult = service.exportToString(applicants, new JsonExportStrategy());

        
        assertThat(csvResult, containsString(","));
        assertThat(jsonResult, containsString("{"));
    }
}
