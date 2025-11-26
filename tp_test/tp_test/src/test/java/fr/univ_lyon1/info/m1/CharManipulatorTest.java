package fr.univ_lyon1.info.m1.tp_test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CharManipulatorTest {

    private CharManipulator manipulator;

    @BeforeEach
    void setUp() {
        manipulator = new CharManipulator();
    }

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
}
