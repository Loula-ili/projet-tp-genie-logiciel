package fr.univ_lyon1.info.m1.cv_search.view;

import java.io.File;
import java.util.List;

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

/** JfxView class. */
public class JfxView {
    private HBox searchSkillsBox;
    private VBox resultBox;
    private ComboBox<Strategy> strategyComboBox;

    /** Internal interface for selection strategies. */
    private interface Strategy {
        boolean select(Applicant a, List<String> skills);

        String toString(); // for displaying the name in the ComboBox
    }

    /** Constructs a JfxView instance and sets up the user interface. */
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

    /** Widget to add a new skill. */
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

    /** Area to display currently searched skills. */
    private Node createCurrentSearchSkillsWidget() {
        searchSkillsBox = new HBox();
        searchSkillsBox.setSpacing(5);
        return searchSkillsBox;
    }

    /** Area to display search results. */
    private Node createResultsWidget() {
        resultBox = new VBox();
        resultBox.setSpacing(5);
        return resultBox;
    }

    /** ComboBox to choose the selection strategy. */
    private Node createStrategySelector() {
        strategyComboBox = new ComboBox<>();

        // Strategy: All skills >= 50%
        strategyComboBox.getItems().add(new Strategy() {
            @Override
            public boolean select(final Applicant a, final List<String> skills) {
                for (String s : skills) {
                    if (a.getSkill(s) < 50) {
                        return false;
                    }
                }
                return true;
            }

            @Override
            public String toString() {
                return "All >= 50%";
            }
        });

        // Strategy: All skills >= 60%
        strategyComboBox.getItems().add(new Strategy() {
            @Override
            public boolean select(final Applicant a, final List<String> skills) {
                for (String s : skills) {
                    if (a.getSkill(s) < 60) {
                        return false;
                    }
                }
                return true;
            }

            @Override
            public String toString() {
                return "All >= 60%";
            }
        });

        // Strategy: Average >= 50%
        strategyComboBox.getItems().add(new Strategy() {
            @Override
            public boolean select(final Applicant a, final List<String> skills) {
                double sum = 0;
                for (String s : skills) {
                    sum += a.getSkill(s);
                }
                double avg = skills.isEmpty() ? 0 : sum / skills.size();
                return avg >= 50;
            }

            @Override
            public String toString() {
                return "Average >= 50%";
            }
        });

        strategyComboBox.getSelectionModel().selectFirst();
        return strategyComboBox;
    }

    /** Search button with classic EventHandler. */
    private Node createSearchWidget() {
        Button search = new Button("Search");
        search.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                ApplicantList listApplicants = new ApplicantListBuilder(new File(".")).build();
                List<String> skills = searchSkillsBox.getChildren().stream()
                        .map(node -> ((Button) node).getText())
                        .toList();

                Strategy strategy = strategyComboBox.getValue();

                resultBox.getChildren().clear();
                for (Applicant a : listApplicants) {
                    if (strategy.select(a, skills)) {
                        // Calculate average score on searched skills
                        double sum = 0;
                        for (String s : skills) {
                            sum += a.getSkill(s);
                        }
                        double avg = skills.isEmpty() ? 0 : sum / skills.size();

                        // Display name + average score
                        resultBox.getChildren().add(
                                new Label(a.getName() + " - Avg: " + String.format("%.2f", avg)));
                    }
                }
            }
        });
        return search;
    }
}
