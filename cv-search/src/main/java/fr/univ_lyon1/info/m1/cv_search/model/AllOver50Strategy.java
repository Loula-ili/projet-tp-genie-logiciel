package fr.univ_lyon1.info.m1.cv_search.model;

import java.util.List;

public class AllOver50Strategy implements MatchingStrategy {
    @Override
    public boolean matches(Applicant applicant, List<String> skills) {
        for (String s : skills) {
            if (applicant.getSkill(s) < 50) {
                return false;
            }
        }
        return true;
    }
}
