package fr.univ_lyon1.info.m1.cv_search.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

/**
 * Constructeur (Builder) qui lit un fichier YAML pour créer un objet candidat.
 * <p>
 * Pattern: Builder - sépare la construction d'un objet complexe de sa représentation.
 * Ce builder parse un fichier YAML contenant le nom, les compétences et
 * les expériences d'un candidat.
 * </p>
 */
public class ApplicantBuilder {

    /** Fichier YAML contenant les informations du candidat. */
    private File file;

    /**
     * Crée un builder à partir d'un objet File.
     * 
     * @param f Fichier YAML décrivant le candidat.
     */
    public ApplicantBuilder(final File f) {
        this.file = f;
    }

    /**
     * Crée un builder à partir d'un chemin de fichier.
     * 
     * @param filename Nom/chemin du fichier YAML décrivant le candidat.
     */
    public ApplicantBuilder(final String filename) {
        this.file = new File(filename);
    }

    /**
     * Construit l'objet candidat à partir du fichier YAML fourni au constructeur.
     * Parse le fichier et extrait le nom, les compétences et les expériences.
     * 
     * @return Un objet Applicant complet, ou null si le fichier est invalide.
     */
    public Applicant build() {
        Applicant a = new Applicant();
        Yaml yaml = new Yaml();
        Map<String, Object> map;

        // Lecture du fichier YAML
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

        // Extraction du nom du candidat
        a.setName((String) map.get("name"));

        // Extraction des compétences (skills)
        @SuppressWarnings("unchecked")
        Map<String, Integer> skills = (Map<String, Integer>) map.get("skills");
        if (skills != null) {
            // Ajoute chaque compétence avec son niveau (0-100)
            for (Map.Entry<String, Integer> entry : skills.entrySet()) {
                a.setSkill(entry.getKey(), entry.getValue());
            }
        }

        // Extraction des expériences professionnelles
        @SuppressWarnings("unchecked")
        Map<String, Map<String, Object>> experiences = (Map<String, Map<String, Object>>) map.get("experience");

        if (experiences != null) {
            // Pour chaque expérience, extrait l'entreprise, les dates et les mots-clés
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
