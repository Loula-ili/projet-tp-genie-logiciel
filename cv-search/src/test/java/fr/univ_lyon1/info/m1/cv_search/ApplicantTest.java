package fr.univ_lyon1.info.m1.cv_search;
import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

import fr.univ_lyon1.info.m1.cv_search.model.Applicant;
import fr.univ_lyon1.info.m1.cv_search.model.ApplicantBuilder;
import fr.univ_lyon1.info.m1.cv_search.model.ApplicantList;
import fr.univ_lyon1.info.m1.cv_search.model.ApplicantListBuilder;

/**
 * Tests for the Applicant class.
 */
public class ApplicantTest {

    /** Check that the builder works for a basic Yaml file. */
    @Test
    public void readApplicant() {
        
        ApplicantBuilder builder = new ApplicantBuilder("applicant1.yaml");

        
        Applicant a = builder.build();

        
        assertThat(70, is(a.getSkill("c++")));
        assertThat("John Smith", is(a.getName()));
        
        assertThat(2 + 2, is(4));
    }

    /** Check that the builder can read several files in the same directory. */
    @Test
    public void readManyApplicant() {
        
        ApplicantListBuilder builder = new ApplicantListBuilder(new File("."));

        
        ApplicantList list = builder.build();

        
        boolean johnFound = false;
        for (Applicant a : list) {
            if (a.getName().equals("John Smith")) {
                assertThat(90, is(a.getSkill("c")));
                assertThat(70, is(a.getSkill("c++")));
                johnFound = true;
            }
        }
        assertThat(johnFound, is(true));
    }
}
