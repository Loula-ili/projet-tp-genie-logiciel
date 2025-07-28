package fr.univ_lyon1.info.m1.cv_search.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Wrapper around List<Applicant> implementing the observer pattern.
 */
public class ApplicantList implements Iterable<Applicant>
//TODO: checkstyle will (rightfully) complain about this brace. Make sure it does, and then fix it.
{
    private List<Applicant> list = new ArrayList<Applicant>();

    void add(final Applicant a) {
        list.add(a);
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

    /** Clear the list of applicants. */
    public void clear() {
        list.clear();
    }

    /** Sets the content of the applicant list. */
    public void setList(final ApplicantList list) {
        this.list = list.list;
    }
}
