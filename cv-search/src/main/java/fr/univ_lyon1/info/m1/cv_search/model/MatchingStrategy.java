package fr.univ_lyon1.info.m1.cv_search.model;

import java.util.List;

public interface MatchingStrategy {
    boolean matches(Applicant applicant, List<String> skills);
}
