package fr.univ_lyon1.info.m1.cv_search.model;

import java.io.File;

/**
 * Constructeur (Builder) qui lit les fichiers YAML d'un répertoire
 * pour créer une liste de candidats.
 * <p>
 * Pattern: Builder - facilite la construction d'objets complexes à partir de fichiers.
 * </p>
 */
public class ApplicantListBuilder {

    /** Répertoire contenant les fichiers YAML des candidats. */
    private File directory;

    /**
     * Crée un builder pour lire les fichiers YAML de candidats.
     * 
     * @param directory Répertoire où chercher les fichiers YAML des candidats.
     */
    public ApplicantListBuilder(final File directory) {
        this.directory = directory;
    }

    /**
     * Construit la liste complète des candidats en parcourant tous les fichiers YAML.
     * Chaque fichier .yaml du répertoire est analysé et converti en objet Applicant.
     * 
     * @return La liste complète des candidats trouvés dans le répertoire.
     */
    public ApplicantList build() {
        ApplicantList applicants = new ApplicantList();
        // Parcourt tous les fichiers du répertoire
        for (File f : directory.listFiles()) {
            // Ne traite que les fichiers YAML
            if (f.isFile() && f.getName().endsWith(".yaml")) {
                // Construit un candidat à partir du fichier
                Applicant a = new ApplicantBuilder(f).build();
                if (a != null) {
                    applicants.add(a);
                }
            }
        }
        return applicants;
    }
}
