package fr.univ_lyon1.info.m1.cv_search.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

/**
 * Builder reading a Yaml file to build an applicant object.
 */
public class ApplicantBuilder {

    private File file;

    /**
     * @param f Yaml file describing the applicant.
     */
    public ApplicantBuilder(final File f) {
        this.file = f;
    }

    /**
     * @param filename Name of the Yaml file describing the applicant.
     */
    public ApplicantBuilder(final String filename) {
        this.file = new File(filename);
    }

    /**
     * Build the applicant from the Yaml file provided to the constructor.
     */
    public Applicant build() {
        Applicant a = new Applicant();
        Yaml yaml = new Yaml();
        Map<String, Object> map;

        try {
            map = yaml.load(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new Error(e);
        }

        // Vérification que le fichier YAML est valide
        if (map == null) {
            System.err.println("Warning: Invalid YAML file: " + file.getName());
            return null;
        }

        // 🔹 Nom
        a.setName((String) map.get("name"));

        // 🔹 Compétences
        @SuppressWarnings("unchecked")
        Map<String, Integer> skills = (Map<String, Integer>) map.get("skills");
        if (skills != null) {
            for (Map.Entry<String, Integer> entry : skills.entrySet()) {
                a.setSkill(entry.getKey(), entry.getValue());
            }
        }

        // 🔹 Expériences professionnelles
        @SuppressWarnings("unchecked")
        Map<String, Map<String, Object>> experiences = (Map<String, Map<String, Object>>) map.get("experience");

        if (experiences != null) {
            for (Map.Entry<String, Map<String, Object>> entry : experiences.entrySet()) {
                String company = entry.getKey();
                Map<String, Object> expData = entry.getValue();

                int start = (int) expData.get("start");
                int end = (int) expData.get("end");

                @SuppressWarnings("unchecked")
                List<String> keywords = (List<String>) expData.get("keywords");

                a.addExperience(company, start, end, keywords);
            }
        }

        return a;
    }
}
