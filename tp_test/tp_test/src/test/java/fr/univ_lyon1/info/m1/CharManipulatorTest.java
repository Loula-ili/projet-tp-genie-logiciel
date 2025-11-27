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
        assertEquals("A", manipulator.invertOrder("A"));
        assertEquals("DCBA", manipulator.invertOrder("ABCD"));
        assertEquals("321DCBA", manipulator.invertOrder("ABCD123"));
    }

    @Test
    void orderEmptyString() {
        assertEquals("", manipulator.invertOrder(""));
    }

    // ===== Tests pour invertCase =====

    @Test
    void caseNormalString() {
        assertEquals("ABcd", manipulator.invertCase("abCD"));
        assertEquals("HELLO world", manipulator.invertCase("hello WORLD"));
        assertEquals("JaVa", manipulator.invertCase("jAvA"));
    }

    @Test
    void caseEmptyString() {
        assertEquals("", manipulator.invertCase(""));
    }

    @Test
    void caseWithNumbers() {
        assertEquals("ABC123def", manipulator.invertCase("abc123DEF"));
    }

    @Test
    void caseOnlyNumbers() {
        assertEquals("123", manipulator.invertCase("123"));
    }

    // ===== Tests pour removePattern (TDD) =====

    @Test
    void removePatternNormalCase() {
        assertEquals("cc", manipulator.removePattern("coucou", "ou"));
        assertEquals("hello", manipulator.removePattern("helloworld", "world"));
    }

    @Test
    void removePatternMultipleOccurrences() {
        assertEquals("", manipulator.removePattern("aabb", "ab"));
        assertEquals("c", manipulator.removePattern("ababcabab", "ab"));
    }

    @Test
    void removePatternNotFound() {
        assertEquals("hello", manipulator.removePattern("hello", "xyz"));
    }

    @Test
    void removePatternEmptyString() {
        assertEquals("", manipulator.removePattern("", "test"));
    }

    @Test
    void removePatternEmptyPattern() {
        assertEquals("hello", manipulator.removePattern("hello", ""));
    }
}
