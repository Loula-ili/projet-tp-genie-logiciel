package fr.univ_lyon1.info.m1.cv_search;

import fr.univ_lyon1.info.m1.cv_search.view.JfxView;
import fr.univ_lyon1.info.m1.cv_search.model.ApplicantList;
import fr.univ_lyon1.info.m1.cv_search.controller.ApplicantController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Point d'entrée principal de l'application CV Search.
 * Initialise le modèle, les vues JavaFX et le contrôleur (pattern MVC).
 */
public class App extends Application {

    @Override
    public void start(final Stage stage) throws Exception {
        // Modèle unique et partagé
        ApplicantList model = new ApplicantList();

        //  Deux vues liées au même modèle
        JfxView view1 = new JfxView(stage, 600, 600);
        JfxView view2 = new JfxView(new Stage(), 400, 400);

        //  Contrôleur
        ApplicantController controller = new ApplicantController(model);

        // Synchronisation automatique : mise à jour des vues quand le modèle change
        model.addPropertyChangeListener(evt -> {
            view1.updateApplicantList(model.getList());
            view2.updateApplicantList(model.getList());
        });

        //  Connexion des actions de la vue au contrôleur
        view1.setOnAddApplicant(controller::addApplicant);
        view2.setOnAddApplicant(controller::addApplicant);

        // transmettre aussi la stratégie choisie
        view1.setOnSearch((skills, strategy) -> controller.search(skills, strategy));
        view2.setOnSearch((skills, strategy) -> controller.search(skills, strategy));

        // Connexion de l'import PDF
        view1.setOnImportPdf(controller::importPdfCv);
        view2.setOnImportPdf(controller::importPdfCv);
    }

    /**
     * Point d'entrée principal du programme.
     */
    public static void main(final String[] args) {
        Application.launch(args);
    }
}
