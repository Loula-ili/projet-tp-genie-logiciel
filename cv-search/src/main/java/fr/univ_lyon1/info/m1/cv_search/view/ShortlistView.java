package fr.univ_lyon1.info.m1.cv_search.view;

import java.io.File;
import java.io.IOException;

import fr.univ_lyon1.info.m1.cv_search.model.Applicant;
import fr.univ_lyon1.info.m1.cv_search.model.CsvExportStrategy;
import fr.univ_lyon1.info.m1.cv_search.model.ExportService;
import fr.univ_lyon1.info.m1.cv_search.model.ExportStrategy;
import fr.univ_lyon1.info.m1.cv_search.model.JsonExportStrategy;
import fr.univ_lyon1.info.m1.cv_search.model.Shortlist;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Vue avancée pour gérer la shortlist (liste de favoris) des candidats.
 * Pattern: Observer (écoute les changements de la Shortlist).
 */
public class ShortlistView {

    private final Stage stage;
    private final VBox contentBox;
    private final Shortlist shortlist;

    /**
     * Crée une nouvelle vue de shortlist.
     *
     * @param parentStage La fenêtre parente.
     */
    public ShortlistView(final Stage parentStage) {
        this.shortlist = Shortlist.getInstance();
        this.stage = new Stage();
        this.contentBox = new VBox(10);

        stage.initModality(Modality.NONE);
        stage.initOwner(parentStage);
        stage.setTitle("Shortlist - Candidats Favoris");

        setupUI();

        // Observer: mise à jour automatique quand la shortlist change
        shortlist.addPropertyChangeListener(evt -> updateDisplay());
    }

    private void setupUI() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.getStyleClass().add("root");

        // Header
        Label title = new Label("📋 Shortlist des Candidats");
        title.getStyleClass().add("title");

        HBox headerBox = new HBox(10);
        headerBox.setAlignment(Pos.CENTER_LEFT);

        Label countLabel = new Label("0 candidat(s)");
        countLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #718096;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button exportCsvBtn = new Button("Exporter CSV");
        exportCsvBtn.getStyleClass().add("button");
        exportCsvBtn.setOnAction(e -> exportShortlist(new CsvExportStrategy()));

        Button exportJsonBtn = new Button("Exporter JSON");
        exportJsonBtn.getStyleClass().add("button");
        exportJsonBtn.setOnAction(e -> exportShortlist(new JsonExportStrategy()));

        Button clearBtn = new Button("Vider la liste");
        clearBtn.getStyleClass().addAll("button", "danger");
        clearBtn.setOnAction(e -> {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirmation");
            confirm.setHeaderText("Vider la shortlist ?");
            confirm.setContentText("Tous les candidats seront retirés de la shortlist.");
            confirm.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    shortlist.clear();
                }
            });
        });

        headerBox.getChildren().addAll(title, countLabel, spacer, exportCsvBtn,
                exportJsonBtn, clearBtn); // Content area
        contentBox.setPadding(new Insets(10));
        contentBox.setStyle("-fx-background-color: #f7fafc;");

        ScrollPane scrollPane = new ScrollPane(contentBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(400);
        scrollPane.getStyleClass().add("scroll-pane");

        root.getChildren().addAll(headerBox, new Separator(), scrollPane);

        Scene scene = new Scene(root, 700, 500);

        // Charger le CSS
        String css = getClass().getResource("/style.css").toExternalForm();
        scene.getStylesheets().add(css);

        stage.setScene(scene);

        // Mise à jour du label de comptage
        shortlist.addPropertyChangeListener(evt -> countLabel.setText(shortlist.size() + " candidat(s)"));

        updateDisplay();
    }

    private void updateDisplay() {
        contentBox.getChildren().clear();

        if (shortlist.size() == 0) {
            Label emptyLabel = new Label("Aucun candidat dans la shortlist.");
            emptyLabel.setStyle("-fx-text-fill: #a0aec0; -fx-font-size: 14px;");
            contentBox.getChildren().add(emptyLabel);
            return;
        }

        for (Applicant applicant : shortlist) {
            VBox card = createCandidateCard(applicant);
            contentBox.getChildren().add(card);
        }
    }

    private VBox createCandidateCard(final Applicant applicant) {
        VBox card = new VBox(8);
        card.getStyleClass().add("result-card");
        card.setPadding(new Insets(12));

        // Header with name and remove button
        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);

        Label nameLabel = new Label(applicant.getName());
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 15px;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button removeBtn = new Button("Retirer");
        removeBtn.getStyleClass().addAll("button", "danger");
        removeBtn.setOnAction(e -> shortlist.removeCandidate(applicant));

        header.getChildren().addAll(nameLabel, spacer, removeBtn);

        // Scores
        HBox scoresBox = new HBox(20);
        scoresBox.setAlignment(Pos.CENTER_LEFT);

        Label avgLabel = new Label(String.format("Moyenne: %.1f", applicant.getAverage()));
        Label scoreLabel = new Label(String.format("Score: %.1f", applicant.getTotalScore()));

        scoresBox.getChildren().addAll(avgLabel, scoreLabel);

        // Skills
        String skillsText = applicant.getSkillsMap().entrySet().stream()
                .map(e -> e.getKey() + ": " + e.getValue())
                .reduce((a, b) -> a + ", " + b)
                .orElse("Aucune compétence");
        Label skillsLabel = new Label("Compétences: " + skillsText);
        skillsLabel.setStyle("-fx-text-fill: #4a5568; -fx-font-size: 12px;");
        skillsLabel.setWrapText(true);

        // Rating
        HBox ratingBox = createRatingBox(applicant);

        // Comment
        TextArea commentArea = new TextArea(shortlist.getComment(applicant.getName()));
        commentArea.setPromptText("Ajouter un commentaire...");
        commentArea.setPrefRowCount(2);
        commentArea.setWrapText(true);
        commentArea.textProperty()
                .addListener((obs, oldVal, newVal) -> shortlist.setComment(applicant.getName(), newVal));

        card.getChildren().addAll(header, scoresBox, skillsLabel, ratingBox, commentArea);

        return card;
    }

    private HBox createRatingBox(final Applicant applicant) {
        HBox box = new HBox(5);
        box.setAlignment(Pos.CENTER_LEFT);

        Label ratingLabel = new Label("Note: ");
        box.getChildren().add(ratingLabel);

        int currentRating = shortlist.getRating(applicant.getName());

        for (int i = 1; i <= 5; i++) {
            final int rating = i;
            Label star = new Label("★");
            star.setStyle("-fx-font-size: 18px; -fx-cursor: hand;");

            if (i <= currentRating) {
                star.setStyle(star.getStyle() + " -fx-text-fill: #ffc107;");
            } else {
                star.setStyle(star.getStyle() + " -fx-text-fill: #cbd5e0;");
            }

            star.setOnMouseClicked(e -> {
                shortlist.setRating(applicant.getName(), rating);
                updateDisplay();
            });

            box.getChildren().add(star);
        }

        return box;
    }

    private void exportShortlist(final ExportStrategy strategy) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exporter la shortlist");
        fileChooser.setInitialFileName("shortlist." + strategy.getFileExtension());
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        strategy.getFileExtension().toUpperCase() + " Files",
                        "*." + strategy.getFileExtension()));

        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try {
                ExportService.getInstance().exportToFile(
                        shortlist.getCandidates(),
                        file,
                        strategy);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Export réussi");
                alert.setHeaderText(null);
                alert.setContentText("La shortlist a été exportée avec succès.");
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
     * Affiche la fenêtre de shortlist.
     */
    public void show() {
        stage.show();
        stage.toFront();
    }
}
