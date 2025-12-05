package fr.univ_lyon1.info.m1.cv_search.view;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import fr.univ_lyon1.info.m1.cv_search.model.Applicant;
import fr.univ_lyon1.info.m1.cv_search.model.CsvExportStrategy;
import fr.univ_lyon1.info.m1.cv_search.model.ExportService;
import fr.univ_lyon1.info.m1.cv_search.model.ExportStrategy;
import fr.univ_lyon1.info.m1.cv_search.model.JsonExportStrategy;
import fr.univ_lyon1.info.m1.cv_search.model.Shortlist;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Vue JavaFX enrichie pour l'application CV Search.
 * <p>
 * Cette vue affiche l'interface graphique principale permettant de :
 * - Ajouter des compétences à rechercher
 * - Choisir une stratégie de filtrage
 * - Visualiser les résultats de recherche
 * - Exporter les résultats (CSV/JSON)
 * - Gérer une shortlist de candidats favoris
 * </p>
 * 
 * Pattern: MVC (View) + Observer - affiche les données et notifie le contrôleur des actions.
 */
public class JfxView {

    /** Conteneur vertical affichant les compétences recherchées. */
    private VBox searchSkillsBox;
    
    /** Conteneur vertical affichant les résultats de recherche. */
    private VBox resultBox;
    
    /** Liste déroulante pour sélectionner la stratégie de filtrage. */
    private ComboBox<String> strategyComboBox;
    
    /** Case à cocher pour activer/désactiver la vue détaillée. */
    private CheckBox detailedViewCheckbox;
    
    /** Instance unique de la shortlist (liste de favoris). */
    private Shortlist shortlist;
    
    /** Vue dédiée à l'affichage de la shortlist. */
    private ShortlistView shortlistView;
    
    /** Liste actuelle des candidats affichés. */
    private List<Applicant> currentApplicants;

    // Callbacks pour communiquer avec le contrôleur
    /** Callback appelé lors d'une recherche (skills, strategy). */
    private BiConsumer<List<String>, String> onSearch;
    
    /** Callback appelé lors de l'ajout d'un candidat. */
    private Consumer<Applicant> onAddApplicant;
    
    /** Callback pour l'import PDF (non implémenté). */
    private Consumer<File> onImportPdf;
    
    /** Callback pour l'import LinkedIn (non implémenté). */
    private Consumer<File> onImportLinkedIn;
    
    /** Callback pour l'entraînement du ranker (non implémenté). */
    private Runnable onTrainRanker;
    
    /** Callback pour l'application du ranker (non implémenté). */
    private Runnable onApplyRanker;

    /**
     * Constructeur de la vue JavaFX.
     * 
     * @param stage  La fenêtre principale
     * @param width  Largeur de la fenêtre
     * @param height Hauteur de la fenêtre
     */
    public JfxView(final Stage stage, final int width, final int height) {
        stage.setTitle("🔍 CV Search - Application Professionnelle");

        this.shortlist = Shortlist.getInstance();
        this.shortlistView = new ShortlistView(stage);

        VBox root = new VBox();
        root.setSpacing(10);
        root.setPadding(new Insets(10));
        root.getStyleClass().add("root");

        root.getChildren().add(createTopToolbar(stage));
        root.getChildren().add(createNewSkillWidget());
        root.getChildren().add(createCurrentSearchSkillsWidget());
        root.getChildren().add(createStrategySelector());
        root.getChildren().add(createSearchWidget());
        root.getChildren().add(createResultsWidget());

        Scene scene = new Scene(root, width, height);

        // Appliquer le CSS moderne
        try {
            String css = getClass().getResource("/style.css").toExternalForm();
            scene.getStylesheets().add(css);
        } catch (Exception e) {
            System.err.println("Impossible de charger le CSS: " + e.getMessage());
        }

        stage.setScene(scene);
        stage.show();
    }

    private Node createTopToolbar(final Stage stage) {
        HBox toolbar = new HBox();
        toolbar.setSpacing(8);
        toolbar.setAlignment(Pos.CENTER_LEFT);
        toolbar.getStyleClass().add("toolbar");
        toolbar.setPadding(new Insets(5));

        Button shortlistBtn = new Button("📋 Shortlist (" + shortlist.size() + ")");
        shortlistBtn.getStyleClass().addAll("button", "success");
        shortlistBtn.setOnAction(e -> shortlistView.show());

        // Mise à jour du compteur
        shortlist.addPropertyChangeListener(evt -> shortlistBtn.setText("📋 Shortlist (" + shortlist.size() + ")"));

        Button exportCsvBtn = new Button("Export CSV");
        exportCsvBtn.getStyleClass().add("button");
        exportCsvBtn.setOnAction(e -> exportResults(new CsvExportStrategy()));

        Button exportJsonBtn = new Button("Export JSON");
        exportJsonBtn.getStyleClass().add("button");
        exportJsonBtn.setOnAction(e -> exportResults(new JsonExportStrategy()));

        detailedViewCheckbox = new CheckBox("Vue détaillée");
        detailedViewCheckbox.setSelected(true);

        toolbar.getChildren().addAll(
                shortlistBtn,
                new Separator(),
                exportCsvBtn,
                exportJsonBtn,
                new Separator(),
                detailedViewCheckbox);
        return toolbar;
    }

    private Node createNewSkillWidget() {
        HBox newSkillBox = new HBox();
        newSkillBox.setSpacing(10);
        Label labelSkill = new Label("Skill:");
        final TextField textField = new TextField();
        Button submitButton = new Button("Add skill");

        newSkillBox.getChildren().addAll(labelSkill, textField, submitButton);

        submitButton.setOnAction(event -> addSkill(textField));
        textField.setOnAction(event -> addSkill(textField));

        return newSkillBox;
    }

    private void addSkill(final TextField textField) {
        final String skill = textField.getText().trim();
        if (skill.isEmpty()) {
            return;
        }

        HBox skillBox = new HBox();
        skillBox.setSpacing(5);
        skillBox.setAlignment(Pos.CENTER_LEFT);
        skillBox.setStyle(
                "-fx-padding: 4; -fx-border-style: solid inside; "
                        + "-fx-border-width: 1; -fx-border-radius: 5; -fx-border-color: black;");

        Label skillLabel = new Label(skill);
        Button removeBtn = new Button("x");
        removeBtn.setOnAction(e -> searchSkillsBox.getChildren().remove(skillBox));

        skillBox.getChildren().addAll(skillLabel, removeBtn);
        searchSkillsBox.getChildren().add(skillBox);

        textField.setText("");
        textField.requestFocus();
    }

    private Node createCurrentSearchSkillsWidget() {
        searchSkillsBox = new VBox();
        searchSkillsBox.setSpacing(5);
        searchSkillsBox.setPadding(new Insets(5));
        searchSkillsBox.setStyle("-fx-background-color: #f8f8f8; "
                + "-fx-border-color: #ddd; -fx-border-radius: 4;");
        return searchSkillsBox;
    }

    private Node createResultsWidget() {
        resultBox = new VBox();
        resultBox.setSpacing(8);
        resultBox.setPadding(new Insets(5));
        resultBox.setStyle("-fx-background-color: #ffffff; -fx-border-color: #ccc;");
        ScrollPane sp = new ScrollPane(resultBox);
        sp.setFitToWidth(true);
        sp.setPrefHeight(300);
        return sp;
    }

    private Node createStrategySelector() {
        strategyComboBox = new ComboBox<>();
        // Utilise la factory pour obtenir toutes les stratégies disponibles
        strategyComboBox.getItems().addAll(
                fr.univ_lyon1.info.m1.cv_search.model.StrategyFactory.getAvailableStrategies());
        strategyComboBox.getSelectionModel().selectFirst();
        return strategyComboBox;
    }

    private Node createSearchWidget() {
        Button search = new Button("Search");
        search.setOnAction(event -> {
            final List<String> skills = searchSkillsBox.getChildren().stream()
                    .map(node -> ((HBox) node).getChildren().get(0))
                    .map(n -> ((Label) n).getText())
                    .collect(Collectors.toList());

            if (onSearch != null) {
                onSearch.accept(skills, getSelectedStrategy());
            }
        });
        return search;
    }

    public void setOnSearch(final BiConsumer<List<String>, String> handler) {
        this.onSearch = handler;
    }

    public void setOnAddApplicant(final Consumer<Applicant> handler) {
        this.onAddApplicant = handler;
    }

    public void setOnImportPdf(final Consumer<File> handler) {
        this.onImportPdf = handler;
    }

    public void setOnImportLinkedIn(final Consumer<File> handler) {
        this.onImportLinkedIn = handler;
    }

    public void setOnTrainRanker(final Runnable handler) {
        this.onTrainRanker = handler;
    }

    public void setOnApplyRanker(final Runnable handler) {
        this.onApplyRanker = handler;
    }

    /**
     * Met à jour la liste des candidats affichés.
     * 
     * @param applicants Liste des candidats à afficher
     */
    public void updateApplicantList(final List<Applicant> applicants) {
        this.currentApplicants = applicants;
        resultBox.getChildren().clear();

        if (applicants.isEmpty()) {
            Label emptyLabel = new Label("Aucun candidat ne correspond à la recherche.");
            emptyLabel.setStyle("-fx-text-fill: #a0aec0; -fx-font-size: 14px;");
            resultBox.getChildren().add(emptyLabel);
            return;
        }

        final String strategy = getSelectedStrategy();

        List<Applicant> sorted = applicants;
        // Tri basique selon stratégie (le controller peut fournir une liste triée
        // aussi)
        if ("Average >= 50%".equals(strategy)) {
            sorted = applicants.stream()
                    .sorted((a, b) -> Double.compare(b.getAverage(), a.getAverage()))
                    .collect(Collectors.toList());
        }

        boolean detailed = detailedViewCheckbox.isSelected();

        for (final Applicant a : sorted) {
            if (detailed) {
                VBox card = new VBox();
                card.setSpacing(6);
                card.setPadding(new Insets(10));
                card.getStyleClass().add("result-card");

                HBox header = new HBox(10);
                header.setAlignment(Pos.CENTER_LEFT);

                Label name = new Label(a.getName());
                name.setStyle("-fx-font-weight: bold; -fx-font-size: 15px;");

                Region spacer = new Region();
                HBox.setHgrow(spacer, Priority.ALWAYS);

                Button addBtn = new Button(
                        shortlist.contains(a) ? "✓ Dans shortlist" : "+ Shortlist");
                addBtn.getStyleClass().addAll("button",
                        shortlist.contains(a) ? "success" : "");
                addBtn.setOnAction(e -> {
                    if (!shortlist.contains(a)) {
                        shortlist.addCandidate(a);
                        addBtn.setText("✓ Dans shortlist");
                        addBtn.getStyleClass().add("success");
                    }
                });

                header.getChildren().addAll(name, spacer, addBtn);

                Label scoreLabel = new Label(
                        String.format("Score: %.2f | Moyenne: %.1f",
                                a.getTotalScore(), a.getAverage()));
                scoreLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #4a5568;");

                Label skills = new Label("Compétences: " + a.getSkillsMap().entrySet().stream()
                        .map(entry -> entry.getKey() + ":" + entry.getValue())
                        .collect(Collectors.joining(", ")));
                skills.setStyle("-fx-font-size: 12px; -fx-text-fill: #718096;");
                skills.setWrapText(true);

                int totalExpYears = a.getExperiences().stream()
                        .mapToInt(exp -> exp.getDuration())
                        .sum();
                Label exp = new Label(
                        String.format("Expérience: %d an(s) dans %d entreprise(s)",
                                totalExpYears, a.getExperiences().size()));
                exp.setStyle("-fx-font-size: 12px; -fx-text-fill: #718096;");

                card.getChildren().addAll(header, scoreLabel, skills, exp);
                resultBox.getChildren().add(card);
            } else {
                final String text = String.format(
                        "%-20s — Moyenne: %.1f | Score: %.2f",
                        a.getName(), a.getAverage(), a.getTotalScore());
                resultBox.getChildren().add(new Label(text));
            }
        }
    }

    private void exportResults(final ExportStrategy strategy) {
        if (currentApplicants == null || currentApplicants.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun résultat");
            alert.setHeaderText(null);
            alert.setContentText("Aucun candidat à exporter. Effectuez d'abord une recherche.");
            alert.showAndWait();
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exporter les résultats");
        fileChooser.setInitialFileName("results." + strategy.getFileExtension());
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        strategy.getFileExtension().toUpperCase() + " Files",
                        "*." + strategy.getFileExtension()));

        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                ExportService.getInstance().exportToFile(
                        currentApplicants, file, strategy);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Export réussi");
                alert.setHeaderText(null);
                alert.setContentText("Les résultats ont été exportés avec succès.");
                alert.showAndWait();
            } catch (IOException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur d'export");
                alert.setHeaderText("Impossible d'exporter");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        }
    }

    /**
     * Retourne la stratégie sélectionnée dans la vue.
     *
     * @return Le nom de la stratégie.
     */
    public String getSelectedStrategy() {
        return strategyComboBox.getValue();
    }
}
