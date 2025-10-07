package fr.univ_lyon1.info.m1.cv_search.model;

import java.util.List;

public class AverageOver50Strategy implements MatchingStrategy {
    @Override
    public boolean matches(Applicant applicant, List<String> skills) {
        if (skills.isEmpty()) return false;
        double sum = 0;
        for (String s : skills) {
            sum += applicant.getSkill(s);
        }
        double avg = sum / skills.size();
        return avg >= 50;
    }
}
