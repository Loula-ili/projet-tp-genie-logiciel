package fr.univ_lyon1.info.m1.cv_search.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Représente une liste de candidats favoris avec notation et commentaires.
 * Implémente le pattern Observer pour notifier les changements.
 * Pattern: Singleton + Observer
 */
public final class Shortlist implements Iterable<Applicant> {

    private static Shortlist instance;

    private final List<Applicant> candidates = new ArrayList<>();
    private final Map<String, Integer> ratings = new HashMap<>();
    private final Map<String, String> comments = new HashMap<>();
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    /**
     * Constructeur privé (Singleton).
     */
    private Shortlist() {
        // Private constructor for Singleton
    }

    /**
     * Retourne l'instance unique de la shortlist (Singleton).
     *
     * @return L'instance unique.
     */
    public static synchronized Shortlist getInstance() {
        if (instance == null) {
            instance = new Shortlist();
        }
        return instance;
    }

    /**
     * Ajoute un candidat à la shortlist.
     *
     * @param applicant Le candidat à ajouter.
     */
    public void addCandidate(final Applicant applicant) {
        if (applicant == null || contains(applicant)) {
            return;
        }
        List<Applicant> old = new ArrayList<>(candidates);
        candidates.add(applicant);
        pcs.firePropertyChange("shortlist", old, Collections.unmodifiableList(candidates));
    }

    /**
     * Retire un candidat de la shortlist.
     *
     * @param applicant Le candidat à retirer.
     */
    public void removeCandidate(final Applicant applicant) {
        if (applicant == null) {
            return;
        }
        List<Applicant> old = new ArrayList<>(candidates);
        candidates.remove(applicant);
        ratings.remove(applicant.getName());
        comments.remove(applicant.getName());
        pcs.firePropertyChange("shortlist", old, Collections.unmodifiableList(candidates));
    }

    /**
     * Vérifie si un candidat est dans la shortlist.
     *
     * @param applicant Le candidat à vérifier.
     * @return true si le candidat est présent.
     */
    public boolean contains(final Applicant applicant) {
        return applicant != null && candidates.stream()
                .anyMatch(a -> a.getName().equals(applicant.getName()));
    }

    /**
     * Définit une note pour un candidat (1-5 étoiles).
     *
     * @param applicantName Nom du candidat.
     * @param rating        Note de 1 à 5.
     */
    public void setRating(final String applicantName, final int rating) {
        int clampedRating = Math.max(1, Math.min(5, rating));
        ratings.put(applicantName, clampedRating);
        pcs.firePropertyChange("rating", null, applicantName);
    }

    /**
     * Retourne la note d'un candidat.
     *
     * @param applicantName Nom du candidat.
     * @return La note (1-5) ou 0 si non noté.
     */
    public int getRating(final String applicantName) {
        return ratings.getOrDefault(applicantName, 0);
    }

    /**
     * Définit un commentaire pour un candidat.
     *
     * @param applicantName Nom du candidat.
     * @param comment       Le commentaire.
     */
    public void setComment(final String applicantName, final String comment) {
        comments.put(applicantName, comment);
        pcs.firePropertyChange("comment", null, applicantName);
    }

    /**
     * Retourne le commentaire associé à un candidat.
     *
     * @param applicantName Nom du candidat.
     * @return Le commentaire ou une chaîne vide.
     */
    public String getComment(final String applicantName) {
        return comments.getOrDefault(applicantName, "");
    }

    /**
     * Retourne une copie non modifiable de la liste des candidats.
     *
     * @return Liste des candidats.
     */
    public List<Applicant> getCandidates() {
        return Collections.unmodifiableList(candidates);
    }

    /**
     * Retourne le nombre de candidats dans la shortlist.
     *
     * @return Le nombre de candidats.
     */
    public int size() {
        return candidates.size();
    }

    /**
     * Vide complètement la shortlist.
     */
    public void clear() {
        List<Applicant> old = new ArrayList<>(candidates);
        candidates.clear();
        ratings.clear();
        comments.clear();
        pcs.firePropertyChange("shortlist", old, Collections.unmodifiableList(candidates));
    }

    @Override
    public Iterator<Applicant> iterator() {
        return candidates.iterator();
    }

    /**
     * Ajoute un écouteur de changements.
     *
     * @param listener L'écouteur à ajouter.
     */
    public void addPropertyChangeListener(final PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    /**
     * Retire un écouteur de changements.
     *
     * @param listener L'écouteur à retirer.
     */
    public void removePropertyChangeListener(final PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }
}
