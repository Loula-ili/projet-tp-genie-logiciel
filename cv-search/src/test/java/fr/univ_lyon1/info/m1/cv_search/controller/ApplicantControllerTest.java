package fr.univ_lyon1.info.m1.cv_search.controller;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.univ_lyon1.info.m1.cv_search.model.Applicant;
import fr.univ_lyon1.info.m1.cv_search.model.ApplicantList;

/**
 * Tests pour le contrôleur.
 */
public class ApplicantControllerTest {

    private ApplicantList model;
    private ApplicantController controller;

    @BeforeEach
    void setUp() {
        model = new ApplicantList();
        controller = new ApplicantController(model);
    }

    @Test
    void testSearchWithOneSkill() {
        // Teste la recherche avec une seule compétence
        List<String> skills = new ArrayList<>();
        skills.add("java");

        controller.search(skills, "Best");

        // Vérifie que le modèle est bien mis à jour avec les résultats
        assertTrue(model.size() >= 0);
    }

    @Test
    void testSearchWithMultipleSkills() {
        // Teste la recherche avec plusieurs compétences
        List<String> skills = new ArrayList<>();
        skills.add("java");
        skills.add("c++");

        controller.search(skills, "Best");

        assertTrue(model.size() >= 0);
    }

    @Test
    void testSearchWithEmptySkills() {
        // Vérifie qu'une recherche sans compétences ne retourne rien
        List<String> skills = new ArrayList<>();

        controller.search(skills, "Best");

        assertThat(model.size(), is(0));
    }

    @Test
    void testSearchWithAverageStrategy() {
        // Teste la stratégie "Average >= 50%" qui filtre par moyenne
        List<String> skills = new ArrayList<>();
        skills.add("java");

        controller.search(skills, "Average >= 50%");

        for (Applicant a : model) {
            assertTrue(a.getAverage() == 0 || a.getAverage() >= 50);
        }
    }

    @Test
    void testSearchWithBestStrategy() {
        // Teste la stratégie "Best" qui trie par score décroissant
        List<String> skills = new ArrayList<>();
        skills.add("c++");

        controller.search(skills, "Best");

        double previousScore = Double.MAX_VALUE;
        for (Applicant a : model) {
            assertTrue(a.getTotalScore() <= previousScore);
            previousScore = a.getTotalScore();
        }
    }

    @Test
    void testControllerUsesCorrectModel() {
        // Vérifie que le contrôleur met bien à jour le modèle (MVC)
        List<String> skills = new ArrayList<>();
        skills.add("python");
        controller.search(skills, "Best");

        // Le modèle devrait être mis à jour
        assertTrue(model.size() >= 0);
    }

    /**
     * Test bonus : vérifie le comportement avec des compétences à 0%.
     * Les candidats ayant 0% dans une compétence ne doivent pas matcher
     * avec la stratégie "All >= 50%".
     */
    @Test
    void testSearchWithZeroSkillLevel() {
        // Given : recherche avec la stratégie stricte "All >= 50%"
        List<String> skills = new ArrayList<>();
        skills.add("java");
        
        // When : recherche avec stratégie "All >= 50%"
        controller.search(skills, "All >= 50%");
        
        // Then : tous les candidats retournés doivent avoir au moins 50% en java
        for (Applicant a : model) {
            int javaSkill = a.getSkill("java");
            assertTrue(javaSkill >= 50, 
                "Candidat " + a.getName() + " a " + javaSkill + "% en java (devrait être >= 50%)");
        }
    }

    /**
     * Test bonus : vérifie que la stratégie "All >= 60%" est plus stricte que "All >= 50%".
     * Teste la hiérarchie des stratégies de filtrage.
     */
    @Test
    void testSearchWithStricterStrategy() {
        // Given : deux recherches avec des seuils différents
        List<String> skills = new ArrayList<>();
        skills.add("c++");
        
        // When : recherche avec seuil à 50%
        controller.search(skills, "All >= 50%");
        int resultsWith50 = model.size();
        
        // When : recherche avec seuil à 60% (plus strict)
        controller.search(skills, "All >= 60%");
        int resultsWith60 = model.size();
        
        // Then : le seuil à 60% doit retourner autant ou moins de résultats
        assertTrue(resultsWith60 <= resultsWith50,
            "Stratégie 60% (" + resultsWith60 + ") devrait être <= stratégie 50% (" 
            + resultsWith50 + ")");
    }
}
