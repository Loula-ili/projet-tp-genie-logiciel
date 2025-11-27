package fr.univ_lyon1.info.m1.cv_search.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Tests pour la classe Shortlist (Singleton + Observer).
 */
public class ShortlistTest {

    private Shortlist shortlist;
    private Applicant applicant1;
    private Applicant applicant2;

    @BeforeEach
    void setUp() {
        // Initialisation du Singleton
        shortlist = Shortlist.getInstance();
        shortlist.clear();

        // Création des candidats de test
        applicant1 = new Applicant("Alice Test");
        applicant1.setSkill("Java", 90);
        applicant1.setSkill("Python", 80);

        applicant2 = new Applicant("Bob Test");
        applicant2.setSkill("C++", 85);
    }

    @Test
    void testSingletonPattern() {
        
        Shortlist instance1 = Shortlist.getInstance();
        Shortlist instance2 = Shortlist.getInstance();

        
        assertSame(instance1, instance2, "Singleton pattern verification");
    }

    @Test
    void testAddCandidate() {
        
        assertThat(shortlist.size(), is(0));

        
        shortlist.addCandidate(applicant1);

        
        assertThat(shortlist.size(), is(1));
        assertTrue(shortlist.contains(applicant1));
    }

    @Test
    void testRemoveCandidate() {
        
        shortlist.addCandidate(applicant1);
        assertThat(shortlist.size(), is(1));

        
        shortlist.removeCandidate(applicant1);

        
        assertThat(shortlist.size(), is(0));
        assertFalse(shortlist.contains(applicant1));
    }

    @Test
    void testAddDuplicateCandidate() {
        
        shortlist.addCandidate(applicant1);

        
        shortlist.addCandidate(applicant1);

        // Then - No duplicate allowed
        assertThat(shortlist.size(), is(1));
    }

    @Test
    void testSetAndGetRating() {
        
        shortlist.addCandidate(applicant1);

        
        shortlist.setRating(applicant1.getName(), 4);

        
        assertThat(shortlist.getRating(applicant1.getName()), is(4));
    }

    @Test
    void testSetAndGetComment() {
        
        shortlist.addCandidate(applicant1);

        
        shortlist.setComment(applicant1.getName(), "Excellent candidat");

        
        assertThat(shortlist.getComment(applicant1.getName()), is("Excellent candidat"));
    }

    @Test
    void testObserverPattern() {
        
        final boolean[] notified = {false};
        PropertyChangeListener listener = new PropertyChangeListener() {
            @Override
            public void propertyChange(final PropertyChangeEvent evt) {
                notified[0] = true;
            }
        };
        shortlist.addPropertyChangeListener(listener);

        
        shortlist.addCandidate(applicant1);

        
        assertTrue(notified[0], "Observer notification test");
    }

    @Test
    void testGetCandidates() {
        
        shortlist.addCandidate(applicant1);
        shortlist.addCandidate(applicant2);

        
        var candidates = shortlist.getCandidates();

        
        assertThat(candidates.size(), is(2));
        assertTrue(candidates.contains(applicant1));
        assertTrue(candidates.contains(applicant2));
    }

    @Test
    void testClear() {
        
        shortlist.addCandidate(applicant1);
        shortlist.addCandidate(applicant2);
        assertThat(shortlist.size(), is(2));

        
        shortlist.clear();

        
        assertThat(shortlist.size(), is(0));
    }
}
