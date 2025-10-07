package fr.univ_lyon1.info.m1.cv_search;

import fr.univ_lyon1.info.m1.cv_search.view.JfxView;
import fr.univ_lyon1.info.m1.cv_search.model.ApplicantList;
import fr.univ_lyon1.info.m1.cv_search.controller.ApplicantController;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(final Stage stage) throws Exception {
        // 1. Modèle partagé
        ApplicantList model = new ApplicantList();

        // 2. Contrôleur qui agit sur le modèle
        ApplicantController controller = new ApplicantController(model);

        // 3. Création de deux vues
        JfxView view1 = new JfxView(stage, 600, 600);
        JfxView view2 = new JfxView(new Stage(), 400, 400);

        // 4. Toutes les vues observent le modèle
        model.addPropertyChangeListener(evt -> view1.updateApplicantList(model.getList()));
        model.addPropertyChangeListener(evt -> view2.updateApplicantList(model.getList()));

        // 5. Actions des vues → passent par le contrôleur
        view1.setOnAddApplicant(controller::addApplicant);
        view2.setOnAddApplicant(controller::addApplicant);
        view1.setOnSearch(controller::search);
        view2.setOnSearch(controller::search);
    }

    public static void main(final String[] args) {
        Application.launch(args);
    }
}