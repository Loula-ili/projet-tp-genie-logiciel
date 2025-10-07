package fr.univ_lyon1.info.m1.cv_search.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Wrapper around {@link List<Applicant>} implementing the observer pattern.
 */
public class ApplicantList implements Iterable<Applicant> {
    private final List<Applicant> list = new ArrayList<>();
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    /**
     * Add an applicant and notify listeners.
     */
    public void add(final Applicant a) {
        List<Applicant> old = new ArrayList<>(list);
        list.add(a);
        pcs.firePropertyChange("applicants", old, Collections.unmodifiableList(list));
    }

/**
 * Get the number of applicants in the list.
 */
public int size() {
    return list.size();
}

    @Override
    public Iterator<Applicant> iterator() {
        return list.iterator();
    }

    /**
     * Clear the list of applicants and notify listeners.
     */
    public void clear() {
        List<Applicant> old = new ArrayList<>(list);
        list.clear();
        pcs.firePropertyChange("applicants", old, Collections.unmodifiableList(list));
    }

    /**
     * Replace the content of the applicant list and notify listeners.
     */
    public void setList(final ApplicantList other) {
        List<Applicant> old = new ArrayList<>(list);
        list.clear();
        list.addAll(other.list);
        pcs.firePropertyChange("applicants", old, Collections.unmodifiableList(list));
    }

    /**
     * Return an unmodifiable copy of the current applicants.
     */
    public List<Applicant> getList() {
        return Collections.unmodifiableList(list);
    }

    // -------------------------------
    // Observer pattern (listeners)
    // -------------------------------
    public void addPropertyChangeListener(PropertyChangeListener l) {
        pcs.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        pcs.removePropertyChangeListener(l);
    }
}
