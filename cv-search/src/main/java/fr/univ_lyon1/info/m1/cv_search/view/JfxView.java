package fr.univ_lyon1.info.m1.cv_search.view;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import fr.univ_lyon1.info.m1.cv_search.model.Applicant;
import fr.univ_lyon1.info.m1.cv_search.model.ApplicantList;
import fr.univ_lyon1.info.m1.cv_search.model.ApplicantListBuilder;
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

/**
 * Main view of the application, implemented using JavaFX.
 */
public class JfxView {

    private HBox searchSkillsBox;
    private VBox resultBox;
    private ComboBox<SelectionStrategy> strategyBox; // Menu déroulant pour choisir la stratégie

    /**
     * Interface pour definir une strategie de sélection de candidat.
     */
    private interface SelectionStrategy {

        boolean select(Applicant a, List<String> skills);

        @Override
        String toString(); // Sert uniquement pour l'affichage dans le ComboBox
    }

    /**
     * Stratégie "Tout >= 50%".
     */
    private class AllAbove50Strategy implements SelectionStrategy {

        @Override
        public boolean select(Applicant a, List<String> skills) {
            for (String skill : skills) {
                if (a.getSkill(skill) < 50) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public String toString() {
            return "Tout >= 50%";
        }
    }

    /**
     * Stratégie "Tout >= 60%".
     */
    private class AllAbove60Strategy implements SelectionStrategy {

        @Override
        public boolean select(Applicant a, List<String> skills) {
            for (String skill : skills) {
                if (a.getSkill(skill) < 60) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public String toString() {
            return "Tout >= 60%";
        }
    }

    /**
     * Stratégie "Moyenne >= 50%".
     */
    private class AverageAbove50Strategy implements SelectionStrategy {

        @Override
        public boolean select(Applicant a, List<String> skills) {
            if (skills.isEmpty()) {
                return true;
            }
            double sum = 0;
            for (String skill : skills) {
                sum += a.getSkill(skill);
            }
            return (sum / skills.size()) >= 50;
        }

        @Override
        public String toString() {
            return "Moyenne >= 50%";
        }
    }

    /**
     * Create the main view of the application.
     */
    public JfxView(final Stage stage, final int width, final int height) {
        stage.setTitle("Search for CV");

        VBox root = new VBox();

        // Widget pour ajouter une nouvelle compétence
        Node newSkillBox = createNewSkillWidget();
        root.getChildren().add(newSkillBox);

        // Widget affichant les compétences recherchées
        Node searchSkillsBoxNode = createCurrentSearchSkillsWidget();
        root.getChildren().add(searchSkillsBoxNode);

        // Menu déroulant pour choisir la stratégie
        strategyBox = new ComboBox<>();
        strategyBox.getItems().addAll(new AllAbove60Strategy(), new AverageAbove50Strategy(), new AllAbove50Strategy());
        strategyBox.getSelectionModel().selectFirst(); // stratégie par défaut
        root.getChildren().add(strategyBox);

        // Bouton recherche
        Node search = createSearchWidget();
        root.getChildren().add(search);

        // Zone des résultats
        Node resultBoxNode = createResultsWidget();
        root.getChildren().add(resultBoxNode);

        // Scene et affichage
        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Create the text field to enter a new skill.
     */
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
                String text = textField.getText().strip();
                if (text.equals("")) {
                    return; // Do nothing
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

    /**
     * Create the widget showing the list of applicants.
     */
    private Node createResultsWidget() {
        resultBox = new VBox();
        return resultBox;
    }

    /**
     * Create the widget used to trigger the search.
     */
    private Node createSearchWidget() {
        Button search = new Button("Search");
        search.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                ApplicantList listApplicants
                        = new ApplicantListBuilder(new File(".")).build();
                resultBox.getChildren().clear();

                // Récupère la liste des compétences recherchées
                List<String> skills = searchSkillsBox.getChildren().stream()
                        .map(node -> ((Button) node).getText())
                        .collect(Collectors.toList());

                // Récupère la stratégie choisie
                SelectionStrategy strategy = strategyBox.getValue();

                // Filtrage et affichage des candidats avec note moyenne
                for (Applicant a : listApplicants) {
                    if (strategy.select(a, skills)) {
                        double average = 0;
                        if (!skills.isEmpty()) {
                            double sum = 0;
                            for (String skill : skills) {
                                sum += a.getSkill(skill);
                            }
                            average = sum / skills.size();
                        }
                        resultBox.getChildren().add(
                                new Label(a.getName() + " - Note moyenne: " + String.format("%.1f", average) + "%")
                        );
                    }
                }
            }
        });
        return search;
    }

    /**
     * Create the widget showing the list of skills currently searched for.
     */
    private Node createCurrentSearchSkillsWidget() {
        searchSkillsBox = new HBox();
        return searchSkillsBox;
    }
}
