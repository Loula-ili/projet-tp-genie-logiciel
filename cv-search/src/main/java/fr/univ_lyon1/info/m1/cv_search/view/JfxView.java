package fr.univ_lyon1.info.m1.cv_search.view;

import java.util.List;
import java.util.function.Consumer;

import fr.univ_lyon1.info.m1.cv_search.model.Applicant;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/** Vue du pattern MVC. */
public class JfxView {
    private HBox searchSkillsBox;
    private VBox resultBox;
    private ComboBox<String> strategyComboBox;

    private Consumer<List<String>> onSearch;
    private Consumer<Applicant> onAddApplicant;

    /**
     * Crée la vue principale JavaFX.
     *
     * @param stage  Fenêtre principale.
     * @param width  Largeur de la scène.
     * @param height Hauteur de la scène.
     */
    public JfxView(final Stage stage, final int width, final int height) {
        stage.setTitle("Search for CV");

        VBox root = new VBox();
        root.getChildren().add(createNewSkillWidget());
        root.getChildren().add(createCurrentSearchSkillsWidget());
        root.getChildren().add(createStrategySelector());
        root.getChildren().add(createSearchWidget());
        root.getChildren().add(createResultsWidget());

        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Crée le widget permettant d’ajouter une compétence.
     *
     * @return Un conteneur graphique.
     */
    private Node createNewSkillWidget() {
        HBox newSkillBox = new HBox();
        Label labelSkill = new Label("Skill:");
        TextField textField = new TextField();
        Button submitButton = new Button("Add skill");
        newSkillBox.getChildren().addAll(labelSkill, textField, submitButton);
        newSkillBox.setSpacing(10);

        EventHandler<ActionEvent> skillHandler = event -> {
            String text = textField.getText().strip();
            if (text.isEmpty()) {
                return;
            }

            Button skillBtn = new Button(text);
            searchSkillsBox.getChildren().add(skillBtn);
            skillBtn.setOnAction(e -> searchSkillsBox.getChildren().remove(skillBtn));

            textField.setText("");
            textField.requestFocus();
        };

        submitButton.setOnAction(skillHandler);
        textField.setOnAction(skillHandler);
        return newSkillBox;
    }

    /**
     * Crée le widget contenant les compétences recherchées.
     *
     * @return Un conteneur graphique.
     */
    private Node createCurrentSearchSkillsWidget() {
        searchSkillsBox = new HBox();
        searchSkillsBox.setSpacing(5);
        return searchSkillsBox;
    }

    /**
     * Crée le widget affichant les résultats.
     *
     * @return Un conteneur graphique.
     */
    private Node createResultsWidget() {
        resultBox = new VBox();
        resultBox.setSpacing(5);
        return resultBox;
    }

    /**
     * Crée le sélecteur de stratégie de recherche.
     *
     * @return Un ComboBox configuré.
     */
    private Node createStrategySelector() {
        strategyComboBox = new ComboBox<>();
        strategyComboBox.getItems().addAll("All >= 50%", "All >= 60%", "Average >= 50%");
        strategyComboBox.getSelectionModel().selectFirst();
        return strategyComboBox;
    }

    /**
     * Crée le bouton de recherche.
     *
     * @return Un bouton déclenchant la recherche.
     */
    private Node createSearchWidget() {
        Button search = new Button("Search");
        search.setOnAction(event -> {
            List<String> skills = searchSkillsBox.getChildren().stream()
                    .map(node -> ((Button) node).getText())
                    .toList();
            if (onSearch != null) {
                onSearch.accept(skills);
            }
        });
        return search;
    }

    /**
     * Définit le comportement à exécuter lors d'une recherche.
     *
     * @param handler Fonction de callback à exécuter.
     */
    public void setOnSearch(final Consumer<List<String>> handler) {
        this.onSearch = handler;
    }

    /**
     * Définit le comportement à exécuter lors de l’ajout d’un candidat.
     *
     * @param handler Fonction de callback à exécuter.
     */
    public void setOnAddApplicant(final Consumer<Applicant> handler) {
        this.onAddApplicant = handler;
    }

    /**
     * Affiche la liste des candidats avec leur moyenne sur les compétences recherchées.
     *
     * @param applicants Liste des candidats à afficher.
     */
    public void updateApplicantList(final List<Applicant> applicants) {
        resultBox.getChildren().clear();
        for (Applicant a : applicants) {
            String text = String.format(
                    "%s - Moyenne: %.2f%%",
                    a.getName(),
                    a.getAverage()
            );
            resultBox.getChildren().add(new Label(text));
        }
    }

    /**
     * Retourne la stratégie de correspondance sélectionnée.
     *
     * @return Nom de la stratégie choisie.
     */
    public String getSelectedStrategy() {
        return strategyComboBox.getValue();
    }
}
