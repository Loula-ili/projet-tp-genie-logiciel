package fr.univ_lyon1.info.m1.cv_search.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Wrapper autour d'une {@link List} de {@link Applicant} implémentant le pattern Observer.
 */
public class ApplicantList implements Iterable<Applicant> {
    private final List<Applicant> list = new ArrayList<>();
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    /**
     * Ajoute un candidat et notifie les observateurs.
     *
     * @param a Le candidat à ajouter.
     */
    public void add(final Applicant a) {
        List<Applicant> old = new ArrayList<>(list);
        list.add(a);
        pcs.firePropertyChange("applicants", old, Collections.unmodifiableList(list));
    }

    /**
     * Retourne le nombre de candidats dans la liste.
     *
     * @return Le nombre total de candidats.
     */
    public int size() {
        return list.size();
    }

    @Override
    public Iterator<Applicant> iterator() {
        return list.iterator();
    }

    /**
     * Vide la liste des candidats et notifie les observateurs.
     */
    public void clear() {
        List<Applicant> old = new ArrayList<>(list);
        list.clear();
        pcs.firePropertyChange("applicants", old, Collections.unmodifiableList(list));
    }

    /**
     * Remplace le contenu de la liste de candidats et notifie les observateurs.
     *
     * @param other Nouvelle liste de candidats à copier.
     */
    public void setList(final ApplicantList other) {
        List<Applicant> old = new ArrayList<>(list);
        list.clear();
        list.addAll(other.list);
        pcs.firePropertyChange("applicants", old, Collections.unmodifiableList(list));
    }

    /**
     * Retourne une copie non modifiable de la liste actuelle de candidats.
     *
     * @return Liste non modifiable des candidats.
     */
    public List<Applicant> getList() {
        return Collections.unmodifiableList(list);
    }

    // -------------------------------
    // Pattern Observer (listeners)
    // -------------------------------

    /**
     * Ajoute un écouteur aux changements de propriétés de la liste.
     *
     * @param l L'écouteur à ajouter.
     */
    public void addPropertyChangeListener(final PropertyChangeListener l) {
        pcs.addPropertyChangeListener(l);
    }

    /**
     * Supprime un écouteur précédemment ajouté.
     *
     * @param l L'écouteur à retirer.
     */
    public void removePropertyChangeListener(final PropertyChangeListener l) {
        pcs.removePropertyChangeListener(l);
    }
}
