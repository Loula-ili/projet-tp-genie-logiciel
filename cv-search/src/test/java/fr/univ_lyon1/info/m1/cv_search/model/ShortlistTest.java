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
 * Tests unitaires pour la classe Shortlist.
 * <p>
 * Vérifie le bon fonctionnement des patterns implémentés :
 * - Singleton : une seule instance existe
 * - Observer : les écouteurs sont notifiés des changements
 * - Gestion des candidats favoris (ajout, suppression, notes, commentaires)
 * </p>
 */
public class ShortlistTest {

    /** Instance de la shortlist (Singleton). */
    private Shortlist shortlist;
    
    /** Premier candidat de test. */
    private Applicant applicant1;
    
    /** Deuxième candidat de test. */
    private Applicant applicant2;

    /**
     * Initialisation avant chaque test.
     * Réinitialise la shortlist et crée des candidats de test.
     */
    @BeforeEach
    void setUp() {
        // Récupération de l'instance unique et vidage de la liste
        shortlist = Shortlist.getInstance();
        shortlist.clear();

        // Création de candidats de test avec compétences
        applicant1 = new Applicant("Alice Test");
        applicant1.setSkill("Java", 90);
        applicant1.setSkill("Python", 80);

        applicant2 = new Applicant("Bob Test");
        applicant2.setSkill("C++", 85);
    }

    /**
     * Test du pattern Singleton.
     * Vérifie que getInstance() retourne toujours la même instance.
     */
    @Test
    void testSingletonPattern() {
        Shortlist instance1 = Shortlist.getInstance();
        Shortlist instance2 = Shortlist.getInstance();

        // Les deux références doivent pointer vers le même objet
        assertSame(instance1, instance2, "Singleton pattern verification");
    }

    /**
     * Test de l'ajout d'un candidat dans la shortlist.
     * Vérifie que la taille augmente et que le candidat est bien présent.
     */
    @Test
    void testAddCandidate() {
        // Vérifie que la shortlist est initialement vide
        assertThat(shortlist.size(), is(0));

        // Ajoute un candidat
        shortlist.addCandidate(applicant1);

        // Vérifie que la taille a augmenté et que le candidat est présent
        assertThat(shortlist.size(), is(1));
        assertTrue(shortlist.contains(applicant1));
    }

    @Test
    void testRemoveCandidate() {
        // Teste la suppression d'un candidat de la shortlist
        shortlist.addCandidate(applicant1);
        assertThat(shortlist.size(), is(1));

        shortlist.removeCandidate(applicant1);

        assertThat(shortlist.size(), is(0));
        assertFalse(shortlist.contains(applicant1));
    }

    @Test
    void testAddDuplicateCandidate() {
        // Vérifie qu'on ne peut pas ajouter deux fois le même candidat
        shortlist.addCandidate(applicant1);

        shortlist.addCandidate(applicant1);

        // Then - No duplicate allowed
        assertThat(shortlist.size(), is(1));
    }

    @Test
    void testSetAndGetRating() {
        // Teste l'attribution et la récupération d'une note pour un candidat
        shortlist.addCandidate(applicant1);

        shortlist.setRating(applicant1.getName(), 4);

        assertThat(shortlist.getRating(applicant1.getName()), is(4));
    }

    @Test
    void testSetAndGetComment() {
        // Teste l'ajout et la récupération d'un commentaire sur un candidat
        shortlist.addCandidate(applicant1);

        shortlist.setComment(applicant1.getName(), "Excellent candidat");

        assertThat(shortlist.getComment(applicant1.getName()), is("Excellent candidat"));
    }

    @Test
    void testObserverPattern() {
        // Vérifie que les observateurs sont notifiés quand la shortlist change (pattern
        // Observer)
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
        // Vérifie qu'on peut récupérer la liste de tous les candidats
        shortlist.addCandidate(applicant1);
        shortlist.addCandidate(applicant2);

        var candidates = shortlist.getCandidates();

        assertThat(candidates.size(), is(2));
        assertTrue(candidates.contains(applicant1));
        assertTrue(candidates.contains(applicant2));
    }

    @Test
    void testClear() {
        // Teste que clear() vide complètement la shortlist
        shortlist.addCandidate(applicant1);
        shortlist.addCandidate(applicant2);
        assertThat(shortlist.size(), is(2));

        shortlist.clear();

        assertThat(shortlist.size(), is(0));
    }

    /**
     * Test bonus : vérifie que les notes restent attachées au bon candidat.
     * Teste qu'on ne peut pas récupérer la note d'un candidat non ajouté.
     */
    @Test
    void testRatingForNonExistentCandidate() {
        // Given : shortlist vide
        
        // When : tentative de récupération d'une note pour un candidat non ajouté
        Integer rating = shortlist.getRating(applicant1.getName());
        
        // Then : devrait retourner 0 par défaut (pas de note)
        assertThat(rating, is(0));
    }

    /**
     * Test bonus : vérifie que les commentaires restent attachés au bon candidat
     * même après suppression et réajout.
     */
    @Test
    void testCommentPersistenceAfterRemove() {
        // Given : candidat avec commentaire
        shortlist.addCandidate(applicant1);
        shortlist.setComment(applicant1.getName(), "Premier commentaire");
        
        // When : suppression puis réajout du candidat
        shortlist.removeCandidate(applicant1);
        shortlist.addCandidate(applicant1);
        
        // Then : le commentaire ne devrait plus exister (réinitialisé)
        String comment = shortlist.getComment(applicant1.getName());
        assertThat(comment, is(""));
    }

    /**
     * Test bonus : vérifie que setRating avec des valeurs limites fonctionne.
     * Teste les bornes 1-5 pour les notes (0 étant la valeur par défaut).
     */
    @Test
    void testRatingBoundaryValues() {
        // Given : candidat dans la shortlist
        shortlist.addCandidate(applicant1);
        
        // When : attribution de note minimale (1)
        shortlist.setRating(applicant1.getName(), 1);
        
        // Then : note à 1
        assertThat(shortlist.getRating(applicant1.getName()), is(1));
        
        // When : attribution de note maximale (5)
        shortlist.setRating(applicant1.getName(), 5);
        
        // Then : note à 5
        assertThat(shortlist.getRating(applicant1.getName()), is(5));
    }
}
