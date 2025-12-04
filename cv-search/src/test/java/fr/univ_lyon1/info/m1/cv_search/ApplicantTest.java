package fr.univ_lyon1.info.m1.cv_search;
import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

import fr.univ_lyon1.info.m1.cv_search.model.Applicant;
import fr.univ_lyon1.info.m1.cv_search.model.ApplicantBuilder;
import fr.univ_lyon1.info.m1.cv_search.model.ApplicantList;
import fr.univ_lyon1.info.m1.cv_search.model.ApplicantListBuilder;

/**
 * Tests unitaires pour la classe Applicant et ses builders.
 * Vérifie que la lecture et le parsing des fichiers YAML fonctionnent correctement.
 */
public class ApplicantTest {

    /**
     * Vérifie que le builder peut lire un fichier YAML basique et créer un candidat.
     * Test la lecture du nom et des compétences depuis le fichier applicant1.yaml.
     */
    @Test
    public void readApplicant() {
        // Création du builder avec le fichier YAML
        ApplicantBuilder builder = new ApplicantBuilder("applicant1.yaml");

        // Construction de l'objet Applicant depuis le fichier
        Applicant a = builder.build();

        // Vérifications : le candidat doit avoir les bonnes compétences et le bon nom
        assertThat(70, is(a.getSkill("c++")));
        assertThat("John Smith", is(a.getName()));
        
        // Test trivial (garde-fou)
        assertThat(2 + 2, is(4));
    }

    /**
     * Vérifie que le builder peut lire plusieurs fichiers YAML dans un même répertoire.
     * Teste le chargement de tous les candidats et la recherche d'un candidat spécifique.
     */
    @Test
    public void readManyApplicant() {
        // Création du builder qui lit tous les fichiers .yaml du répertoire courant
        ApplicantListBuilder builder = new ApplicantListBuilder(new File("."));

        // Construction de la liste complète des candidats
        ApplicantList list = builder.build();

        // Recherche du candidat "John Smith" dans la liste
        boolean johnFound = false;
        for (Applicant a : list) {
            if (a.getName().equals("John Smith")) {
                // Vérification des compétences de John Smith
                assertThat(90, is(a.getSkill("c")));
                assertThat(70, is(a.getSkill("c++")));
                johnFound = true;
            }
        }
        // Vérifie que le candidat a bien été trouvé
        assertThat(johnFound, is(true));
    }

    /**
     * Test bonus : vérifie le comportement de getSkill avec des noms null ou vides.
     * Ce test vérifie que la méthode gère correctement les cas limites.
     */
    @Test
    public void testGetSkillWithNullAndEmptyStrings() {
        // Given : création d'un candidat avec quelques compétences
        Applicant applicant = new Applicant("Test Person");
        applicant.setSkill("java", 80);
        applicant.setSkill("python", 70);
        
        // When/Then : test avec null (ne doit pas planter)
        assertThat(0, is(applicant.getSkill(null)));
        
        // When/Then : test avec chaîne vide (ne doit pas planter)
        assertThat(0, is(applicant.getSkill("")));
        
        // When/Then : test avec espaces uniquement (ne doit pas planter)
        assertThat(0, is(applicant.getSkill("   ")));
    }
}
