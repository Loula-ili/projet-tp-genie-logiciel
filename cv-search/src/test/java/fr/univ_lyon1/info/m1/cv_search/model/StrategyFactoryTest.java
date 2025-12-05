package fr.univ_lyon1.info.m1.cv_search.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Tests pour la classe StrategyFactory.
 */
class StrategyFactoryTest {

    @Test
    void testGetStrategyReturnsCorrectInstance() {
        MatchingStrategy strategy = StrategyFactory.getStrategy("All >= 50%");
        assertNotNull(strategy);
        assertInstanceOf(AllOver50Strategy.class, strategy);
    }

    @Test
    void testGetStrategyReturnsNullForUnknownStrategy() {
        MatchingStrategy strategy = StrategyFactory.getStrategy("Unknown Strategy");
        assertNull(strategy);
    }

    @Test
    void testHasStrategyReturnsTrueForKnownStrategy() {
        assertTrue(StrategyFactory.hasStrategy("All >= 50%"));
        assertTrue(StrategyFactory.hasStrategy("All >= 60%"));
        assertTrue(StrategyFactory.hasStrategy("Average >= 50%"));
        assertTrue(StrategyFactory.hasStrategy("Max Skill >= 70%"));
        assertTrue(StrategyFactory.hasStrategy("Weighted Experience"));
    }

    @Test
    void testHasStrategyReturnsFalseForUnknownStrategy() {
        assertFalse(StrategyFactory.hasStrategy("Unknown Strategy"));
        assertFalse(StrategyFactory.hasStrategy(""));
        assertFalse(StrategyFactory.hasStrategy(null));
    }

    @Test
    void testGetAvailableStrategiesReturnsAllStrategies() {
        String[] strategies = StrategyFactory.getAvailableStrategies();
        assertNotNull(strategies);
        assertEquals(5, strategies.length);
        
        List<String> strategyList = Arrays.asList(strategies);
        assertTrue(strategyList.contains("All >= 50%"));
        assertTrue(strategyList.contains("All >= 60%"));
        assertTrue(strategyList.contains("Average >= 50%"));
        assertTrue(strategyList.contains("Max Skill >= 70%"));
        assertTrue(strategyList.contains("Weighted Experience"));
    }

    @Test
    void testStrategiesAreSingletonInstances() {
        // Vérifie que les mêmes instances sont retournées
        MatchingStrategy strategy1 = StrategyFactory.getStrategy("All >= 50%");
        MatchingStrategy strategy2 = StrategyFactory.getStrategy("All >= 50%");
        assertSame(strategy1, strategy2);
    }
}
