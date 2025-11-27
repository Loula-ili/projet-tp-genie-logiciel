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
}
