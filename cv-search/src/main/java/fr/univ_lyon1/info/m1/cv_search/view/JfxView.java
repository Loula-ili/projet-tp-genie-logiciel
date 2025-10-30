package fr.univ_lyon1.info.m1.cv_search.view;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import fr.univ_lyon1.info.m1.cv_search.model.Applicant;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.stage.Stage;

/** Vue JavaFX (pattern MVC). */
public class JfxView {
    private VBox searchSkillsBox; // contient HBox pour chaque skill
    private VBox resultBox;
    private ComboBox<String> strategyComboBox;

    // --- callbacks pour le contrôleur ---
    private BiConsumer<List<String>, String> onSearch; // Accepte skills + stratégie
    private Consumer<Applicant> onAddApplicant;

    public JfxView(final Stage stage, final int width, final int height) {
        stage.setTitle("Search for CV");

        VBox root = new VBox();
        root.setSpacing(10);

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
        newSkillBox.setSpacing(10);

        Label labelSkill = new Label("Skill:");
        TextField textField = new TextField();
        Button submitButton = new Button("Add skill");
        newSkillBox.getChildren().addAll(labelSkill, textField, submitButton);

        submitButton.setOnAction(event -> addSkill(textField));
        textField.setOnAction(event -> addSkill(textField));

        return newSkillBox;
    }

    /** fonctionnalité 2 : Ajoute une compétence dans la liste avec un bouton pour la supprimer. */
    private void addSkill(TextField textField) {
        String skill = textField.getText().trim();
        if (skill.isEmpty()) return;

        HBox skillBox = new HBox();
        skillBox.setSpacing(5);
        skillBox.setAlignment(Pos.CENTER_LEFT);
        skillBox.setStyle("-fx-padding: 2;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 1;" +
                "-fx-border-insets: 2;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: black;");

        Label skillLabel = new Label(skill);
        Button removeBtn = new Button("x");
        removeBtn.setOnAction(e -> searchSkillsBox.getChildren().remove(skillBox));

        skillBox.getChildren().addAll(skillLabel, removeBtn);
        searchSkillsBox.getChildren().add(skillBox);

        textField.setText("");
        textField.requestFocus();
    }

    /** Zone affichant les compétences recherchées. */
    private Node createCurrentSearchSkillsWidget() {
        searchSkillsBox = new VBox();
        searchSkillsBox.setSpacing(5);
        return searchSkillsBox;
    }

    /** fonctionalité 3: Zone affichant les résultats de recherche. */
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
        search.setOnAction(event -> {
            List<String> skills = searchSkillsBox.getChildren().stream()
                    .map(node -> ((HBox) node).getChildren().get(0)) // récupère le Label
                    .map(n -> ((Label) n).getText())
                    .collect(Collectors.toList());

            if (onSearch != null) {
                onSearch.accept(skills, getSelectedStrategy());
            }
        });
        return search;
    }

    // ------------------- Méthodes accessibles par le contrôleur -------------------

    public void setOnSearch(final BiConsumer<List<String>, String> handler) {
        this.onSearch = handler;
    }

    public void setOnAddApplicant(final Consumer<Applicant> handler) {
        this.onAddApplicant = handler;
    }

    /** Mise à jour de l'affichage des candidats. */
    public void updateApplicantList(final List<Applicant> applicants) {
        resultBox.getChildren().clear();

        if (applicants.isEmpty()) {
            resultBox.getChildren().add(new Label("Aucun candidat ne correspond à la recherche."));
            return;
        }

        String strategy = getSelectedStrategy();

        // fonctionnalité 4:  Tri décroissant par moyenne si stratégie Average
        List<Applicant> sorted = applicants;
        if ("Average >= 50%".equals(strategy)) {
            sorted = applicants.stream()
                    .sorted((a, b) -> Double.compare(b.getAverage(), a.getAverage()))
                    .collect(Collectors.toList());
        }

        for (Applicant a : sorted) {
            String text;
            if ("Average >= 50%".equals(strategy)) {
                text = String.format("%-20s — Moyenne: %.1f", a.getName(), a.getAverage());
            } else {
                text = a.getName() + " — Compétences: " +
                        a.getSkillsMap().entrySet().stream()
                                .map(entry -> entry.getKey() + ": " + entry.getValue())
                                .collect(Collectors.joining(", "));
            }
            resultBox.getChildren().add(new Label(text));
        }
    }

    public String getSelectedStrategy() {
        return strategyComboBox.getValue();
    }
}
