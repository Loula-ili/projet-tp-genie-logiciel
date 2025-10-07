package fr.univ_lyon1.info.m1.cv_search;

import fr.univ_lyon1.info.m1.cv_search.view.JfxView;
import fr.univ_lyon1.info.m1.cv_search.model.ApplicantList;
import fr.univ_lyon1.info.m1.cv_search.controller.ApplicantController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Point d'entrée principal de l'application CV Search.
 * <p>
 * Initialise le modèle, les vues JavaFX et le contrôleur,
 * puis configure les interactions entre eux selon le pattern MVC.
 * </p>
 */
public class App extends Application {

    /**
     * Méthode appelée au démarrage de l'application JavaFX.
     * Initialise le modèle, les vues et le contrôleur.
     *
     * @param stage Fenêtre principale.
     * @throws Exception En cas d'erreur d'initialisation.
     */
    @Override
    public void start(final Stage stage) throws Exception {
        // 1. Modèle unique et partagé
        ApplicantList model = new ApplicantList();

        // 2. Deux vues, liées au même modèle
        JfxView view1 = new JfxView(stage, 600, 600);
        JfxView view2 = new JfxView(new Stage(), 400, 400);

        // 3. Contrôleur lié au modèle
        ApplicantController controller = new ApplicantController(model);

        // 4. Synchronisation automatique : toutes les vues écoutent le modèle
        model.addPropertyChangeListener(evt -> {
            view1.updateApplicantList(model.getList());
            view2.updateApplicantList(model.getList());
        });

        // 5. Actions des vues → passent par le modèle / contrôleur
        view1.setOnAddApplicant(controller::addApplicant);
        view2.setOnAddApplicant(controller::addApplicant);
        view1.setOnSearch(controller::search);
        view2.setOnSearch(controller::search);
    }

    /**
     * Point d'entrée principal du programme.
     *
     * @param args Arguments de la ligne de commande.
     */
    public static void main(final String[] args) {
        Application.launch(args);
    }
}
