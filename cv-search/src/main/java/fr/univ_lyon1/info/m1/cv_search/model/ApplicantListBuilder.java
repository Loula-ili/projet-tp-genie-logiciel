package fr.univ_lyon1.info.m1.cv_search.model;

import java.io.File;

/**
 * Builder reading Yaml files in a directory to build Applicants.
 */
public class ApplicantListBuilder {

    private File directory;

    /**
     * @param directory Directory where Yaml files for applicants should be searched.
     */
    public ApplicantListBuilder(final File directory) {
        this.directory = directory;
    }

    /**
     * Build the list of applicants.
     */
    public ApplicantList build() {
        ApplicantList applicants = new ApplicantList();
        for (File f : directory.listFiles()) {
            if (f.isFile() && f.getName().endsWith(".yaml")) {
                Applicant a = new ApplicantBuilder(f).build();
                applicants.add(a);
            }
        }
        return applicants;
    }
}
