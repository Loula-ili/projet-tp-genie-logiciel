package fr.univ_lyon1.info.m1.cv_search.view;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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

/** Vue JavaFX (pattern MVC). */
public class JfxView {
    private HBox searchSkillsBox;
    private VBox resultBox;
    private ComboBox<String> strategyComboBox;

    // --- callbacks pour le contrôleur ---
    private Consumer<List<String>> onSearch;
    private Consumer<Applicant> onAddApplicant;

    /**
     * Constructeur : crée la fenêtre et ses widgets.
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

    /** Widget pour saisir une nouvelle compétence. */
    private Node createNewSkillWidget() {
        HBox newSkillBox = new HBox();
        Label labelSkill = new Label("Skill:");
        TextField textField = new TextField();
        Button submitButton = new Button("Add skill");
        newSkillBox.getChildren().addAll(labelSkill, textField, submitButton);
        newSkillBox.setSpacing(10);

        EventHandler<ActionEvent> skillHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                String text = textField.getText().trim();
                if (text.isEmpty()) {
                    return;
                }

                Button skillBtn = new Button(text);
                searchSkillsBox.getChildren().add(skillBtn);
                skillBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent event) {
                        searchSkillsBox.getChildren().remove(skillBtn);
                    }
                });

                textField.setText("");
                textField.requestFocus();
            }
        };

        submitButton.setOnAction(skillHandler);
        textField.setOnAction(skillHandler);
        return newSkillBox;
    }

    /** Zone affichant les compétences recherchées. */
    private Node createCurrentSearchSkillsWidget() {
        searchSkillsBox = new HBox();
        searchSkillsBox.setSpacing(5);
        return searchSkillsBox;
    }

    /** Zone affichant les résultats de recherche. */
    private Node createResultsWidget() {
        resultBox = new VBox();
        resultBox.setSpacing(5);
        return resultBox;
    }

    /** Sélecteur de stratégie de recherche. */
    private Node createStrategySelector() {
        strategyComboBox = new ComboBox<>();
        strategyComboBox.getItems().addAll(
                "All >= 50%",
                "All >= 60%",
                "Average >= 50%",
                "Max Skill >= 70%"
        );
        strategyComboBox.getSelectionModel().selectFirst();
        return strategyComboBox;
    }

    /** Bouton Search relié au contrôleur via callback. */
    private Node createSearchWidget() {
        Button search = new Button("Search");
        search.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                List<String> skills = searchSkillsBox.getChildren().stream()
                        .map(node -> ((Button) node).getText())
                        .collect(Collectors.toList());
                if (onSearch != null) {
                    onSearch.accept(skills);
                }
            }
        });
        return search;
    }

    // ------------------- Méthodes accessibles par le contrôleur -------------------

    /**
     * Abonnement du contrôleur au bouton Search.
     *
     * @param handler Fonction de callback à exécuter.
     */
    public void setOnSearch(final Consumer<List<String>> handler) {
        this.onSearch = handler;
    }

    /**
     * Abonnement du contrôleur à l'ajout de candidat.
     *
     * @param handler Fonction de callback à exécuter.
     */
    public void setOnAddApplicant(final Consumer<Applicant> handler) {
        this.onAddApplicant = handler;
    }

    /**
     * Mise à jour de l'affichage des candidats.
     *
     * @param applicants Liste des candidats à afficher.
     */
    public void updateApplicantList(final List<Applicant> applicants) {
        resultBox.getChildren().clear();
        for (Applicant a : applicants) {
            resultBox.getChildren().add(new Label(a.getName()));
        }
    }

    /**
     * Récupère la stratégie sélectionnée par l'utilisateur.
     *
     * @return Nom de la stratégie choisie.
     */
    public String getSelectedStrategy() {
        return strategyComboBox.getValue();
    }
}

