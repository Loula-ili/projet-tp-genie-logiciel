package fr.univ_lyon1.info.m1.cv_search.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Tests pour la classe WeightedExperienceStrategy.
 */
class WeightedExperienceStrategyTest {

    private WeightedExperienceStrategy strategy;
    private Applicant applicant;

    @BeforeEach
    void setUp() {
        strategy = new WeightedExperienceStrategy();
        applicant = new Applicant("Test Candidate");
    }

    @Test
    void testMatchesWithHighSkillAndNoExperience() {
        applicant.setSkill("java", 80);
        List<String> skills = Arrays.asList("java");
        
        // 0.7 * 80 + 0.3 * 0 = 56 >= 50
        assertTrue(strategy.matches(applicant, skills));
    }

    @Test
    void testMatchesWithLowSkillButHighExperience() {
        applicant.setSkill("python", 40);
        applicant.addExperience("Company A", 2010, 2020, Arrays.asList("python"));
        
        List<String> skills = Arrays.asList("python");
        
        // 0.7 * 40 + 0.3 * 100 (10 ans d'exp) = 28 + 30 = 58 >= 50
        assertTrue(strategy.matches(applicant, skills));
    }

    @Test
    void testDoesNotMatchWithLowScores() {
        applicant.setSkill("c++", 30);
        List<String> skills = Arrays.asList("c++");
        
        // 0.7 * 30 + 0.3 * 0 = 21 < 50
        assertFalse(strategy.matches(applicant, skills));
    }

    @Test
    void testMatchesWithMultipleSkills() {
        applicant.setSkill("java", 70);
        applicant.setSkill("python", 60);
        applicant.addExperience("Company A", 2015, 2020, Arrays.asList("java"));
        applicant.addExperience("Company B", 2018, 2023, Arrays.asList("python"));
        
        List<String> skills = Arrays.asList("java", "python");
        
        // java: 0.7*70 + 0.3*50 = 49 + 15 = 64
        // python: 0.7*60 + 0.3*50 = 42 + 15 = 57
        // moyenne: (64 + 57) / 2 = 60.5 >= 50
        assertTrue(strategy.matches(applicant, skills));
    }

    @Test
    void testDoesNotMatchWithEmptySkillsList() {
        applicant.setSkill("java", 90);
        List<String> skills = Arrays.asList();
        
        assertFalse(strategy.matches(applicant, skills));
    }

    @Test
    void testMatchesAtThreshold() {
        // Test au seuil exact de 50%
        applicant.setSkill("java", 50);
        List<String> skills = Arrays.asList("java");
        
        // 0.7 * 50 + 0.3 * 0 = 35 < 50
        assertFalse(strategy.matches(applicant, skills));
        
        // Ajout d'expérience pour atteindre le seuil
        applicant.addExperience("Company", 2015, 2020, Arrays.asList("java"));
        // 0.7 * 50 + 0.3 * 50 = 35 + 15 = 50 >= 50
        assertTrue(strategy.matches(applicant, skills));
    }

    @Test
    void testMatchesWithVeryHighExperience() {
        applicant.setSkill("java", 40);
        // 15 ans d'expérience (le score d'expérience est plafonné à 1.0)
        applicant.addExperience("Company A", 2005, 2020, Arrays.asList("java"));
        
        List<String> skills = Arrays.asList("java");
        
        // 0.7 * 40 + 0.3 * 100 (plafonné à 100) = 28 + 30 = 58 >= 50
        assertTrue(strategy.matches(applicant, skills));
    }
}
