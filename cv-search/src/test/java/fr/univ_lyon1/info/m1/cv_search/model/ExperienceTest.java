package fr.univ_lyon1.info.m1.cv_search.model;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Test bonus : tests unitaires pour la classe Experience.
 * Vérifie le calcul de la durée et la gestion des mots-clés.
 */
public class ExperienceTest {

    /**
     * Test bonus : vérifie que getDuration() calcule correctement la durée.
     * Teste plusieurs cas : durée normale, durée courte, durée longue.
     */
    @Test
    public void testGetDuration() {
        // Given : création d'une expérience de 3 ans (2018-2021)
        List<String> keywords = Arrays.asList("java", "spring");
        Experience exp1 = new Experience("Entreprise A", 2018, 2021, keywords);
        
        // When/Then : la durée doit être 3 ans
        assertThat(3, is(exp1.getDuration()));
        
        // Given : expérience d'un an (2020-2021)
        Experience exp2 = new Experience("Entreprise B", 2020, 2021, keywords);
        
        // When/Then : la durée doit être 1 an
        assertThat(1, is(exp2.getDuration()));
        
        // Given : longue expérience de 10 ans (2010-2020)
        Experience exp3 = new Experience("Entreprise C", 2010, 2020, keywords);
        
        // When/Then : la durée doit être 10 ans
        assertThat(10, is(exp3.getDuration()));
    }

    /**
     * Test bonus : vérifie que getKeywords() retourne bien la liste des compétences.
     * Teste avec différentes listes de mots-clés.
     */
    @Test
    public void testGetKeywords() {
        // Given : expérience avec plusieurs mots-clés
        List<String> keywords = Arrays.asList("java", "python", "docker");
        Experience exp = new Experience("Tech Corp", 2019, 2022, keywords);
        
        // When : récupération des mots-clés
        List<String> retrievedKeywords = exp.getKeywords();
        
        // Then : la liste doit contenir tous les mots-clés
        assertThat(3, is(retrievedKeywords.size()));
        assertThat(true, is(retrievedKeywords.contains("java")));
        assertThat(true, is(retrievedKeywords.contains("python")));
        assertThat(true, is(retrievedKeywords.contains("docker")));
    }

    /**
     * Test bonus : vérifie le comportement avec une durée nulle (même année).
     * Cas limite où start = end.
     */
    @Test
    public void testZeroDuration() {
        // Given : expérience commencée et terminée la même année
        List<String> keywords = Arrays.asList("stage");
        Experience exp = new Experience("StartUp", 2023, 2023, keywords);
        
        // When/Then : la durée doit être 0
        assertThat(0, is(exp.getDuration()));
    }
}
