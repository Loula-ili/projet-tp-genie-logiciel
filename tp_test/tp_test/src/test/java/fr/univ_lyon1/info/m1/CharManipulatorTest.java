package fr.univ_lyon1.info.m1.tp_test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CharManipulatorTest {

    private CharManipulator manipulator;

    @BeforeEach
    void setUp() {
        manipulator = new CharManipulator();
    }

    // ===== Tests pour invertOrder =====

    @Test
    void orderNormalString() {
        // Teste l'inversion de l'ordre des caractères sur des chaînes normales
        assertEquals("A", manipulator.invertOrder("A"));
        assertEquals("DCBA", manipulator.invertOrder("ABCD"));
        assertEquals("321DCBA", manipulator.invertOrder("ABCD123"));
    }

    @Test
    void orderEmptyString() {
        // Vérifie que l'inversion d'une chaîne vide retourne une chaîne vide
        assertEquals("", manipulator.invertOrder(""));
    }

    // ===== Tests pour invertCase =====

    @Test
    void caseNormalString() {
        // Teste l'inversion de la casse (majuscules <-> minuscules)
        assertEquals("ABcd", manipulator.invertCase("abCD"));
        assertEquals("HELLO world", manipulator.invertCase("hello WORLD"));
        assertEquals("JaVa", manipulator.invertCase("jAvA"));
    }

    @Test
    void caseEmptyString() {
        // Vérifie que l'inversion de casse d'une chaîne vide retourne une chaîne vide
        assertEquals("", manipulator.invertCase(""));
    }

    @Test
    void caseWithNumbers() {
        // Vérifie que les chiffres ne sont pas affectés par l'inversion de casse
        assertEquals("ABC123def", manipulator.invertCase("abc123DEF"));
    }

    @Test
    void caseOnlyNumbers() {
        // Vérifie qu'une chaîne contenant uniquement des chiffres reste inchangée
        assertEquals("123", manipulator.invertCase("123"));
    }

    // ===== Tests pour removePattern (TDD) =====

    @Test
    void removePatternNormalCase() {
        // Teste la suppression d'un pattern simple
        assertEquals("cc", manipulator.removePattern("coucou", "ou"));
        assertEquals("hello", manipulator.removePattern("helloworld", "world"));
    }

    @Test
    void removePatternMultipleOccurrences() {
        // Teste la suppression de toutes les occurrences, même chevauchantes
        assertEquals("", manipulator.removePattern("aabb", "ab"));
        assertEquals("c", manipulator.removePattern("ababcabab", "ab"));
    }

    @Test
    void removePatternNotFound() {
        // Vérifie que la chaîne reste inchangée si le pattern n'est pas trouvé
        assertEquals("hello", manipulator.removePattern("hello", "xyz"));
    }

    @Test
    void removePatternEmptyString() {
        // Vérifie le comportement avec une chaîne vide
        assertEquals("", manipulator.removePattern("", "test"));
    }

    @Test
    void removePatternEmptyPattern() {
        // Vérifie qu'un pattern vide ne modifie pas la chaîne
        assertEquals("hello", manipulator.removePattern("hello", ""));
    }
}
