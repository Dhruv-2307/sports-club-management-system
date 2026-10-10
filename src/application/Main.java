package application;

import coach.CoachManagement;
import athlete.AthleteManagement;
import facility.FacilityManagement;
import equipment.EquipmentManagement;
import attendance.AttendanceManagement;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        // =========================
        // TITLE
        // =========================

        Label title = new Label(
                "SPORTS CLUB"
        );

        title.setStyle(
                "-fx-font-size: 30px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: white;"
        );

        Label subtitle = new Label(
                "Management System"
        );

        subtitle.setStyle(
                "-fx-font-size: 16px;" +
                "-fx-text-fill: #dbeafe;"
        );

        VBox header = new VBox(
                5,
                title,
                subtitle
        );

        header.setAlignment(
                Pos.CENTER
        );

        header.setPadding(
                new Insets(25)
        );

        header.setStyle(
                "-fx-background-color: #1e3a8a;"
        );

        // =========================
        // BUTTONS
        // =========================

        Button coachButton =
                createMenuButton(
                        "Coach Management",
                        "Manage coaches and specializations"
                );

        Button athleteButton =
                createMenuButton(
                        "Athlete Management",
                        "Manage athletes and coach mapping"
                );

        Button facilityButton =
                createMenuButton(
                        "Facility Management",
                        "Manage courts, gyms and facilities"
                );

        Button equipmentButton =
                createMenuButton(
                        "Equipment Management",
                        "Manage sports equipment and inventory"
                );

        Button attendanceButton =
                createMenuButton(
                        "Attendance Management",
                        "Manage athlete and coach attendance"
                );

        // =========================
        // BUTTON ACTIONS
        // =========================

        coachButton.setOnAction(e -> {

            CoachManagement coachManagement =
                    new CoachManagement();

            coachManagement.show(stage);
        });

        athleteButton.setOnAction(e -> {

            AthleteManagement athleteManagement =
                    new AthleteManagement();

            athleteManagement.show(stage);
        });

        facilityButton.setOnAction(e -> {

            FacilityManagement facilityManagement =
                    new FacilityManagement();

            facilityManagement.show(stage);
        });

        equipmentButton.setOnAction(e -> {

            EquipmentManagement equipmentManagement =
                    new EquipmentManagement();

            equipmentManagement.show(stage);
        });

        attendanceButton.setOnAction(e -> {

            AttendanceManagement attendanceManagement =
                    new AttendanceManagement();

            attendanceManagement.show(stage);
        });

        // =========================
        // MENU GRID
        // =========================

        GridPane menuGrid =
                new GridPane();

        menuGrid.setHgap(20);
        menuGrid.setVgap(20);

        menuGrid.setAlignment(
                Pos.CENTER
        );

        menuGrid.add(
                coachButton,
                0,
                0
        );

        menuGrid.add(
                athleteButton,
                1,
                0
        );

        menuGrid.add(
                facilityButton,
                0,
                1
        );

        menuGrid.add(
                equipmentButton,
                1,
                1
        );

        menuGrid.add(
                attendanceButton,
                0,
                2
        );

        // =========================
        // WELCOME TEXT
        // =========================

        Label welcomeLabel =
                new Label(
                        "Welcome to Sports Club Management"
                );

        welcomeLabel.setStyle(
                "-fx-font-size: 20px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #1e293b;"
        );

        Label instructionLabel =
                new Label(
                        "Select a module below to manage club operations."
                );

        instructionLabel.setStyle(
                "-fx-font-size: 14px;" +
                "-fx-text-fill: #64748b;"
        );

        VBox centerContent =
                new VBox(
                        20,
                        welcomeLabel,
                        instructionLabel,
                        menuGrid
                );

        centerContent.setAlignment(
                Pos.CENTER
        );

        centerContent.setPadding(
                new Insets(30)
        );

        // =========================
        // FOOTER
        // =========================

        Label footer =
                new Label(
                        "Sports Club Management System  •  Java + JavaFX + MySQL"
                );

        footer.setStyle(
                "-fx-font-size: 12px;" +
                "-fx-text-fill: #64748b;"
        );

        footer.setPadding(
                new Insets(12)
        );

        // =========================
        // MAIN LAYOUT
        // =========================

        BorderPane root =
                new BorderPane();

        root.setTop(header);
        root.setCenter(centerContent);
        root.setBottom(footer);

        root.setStyle(
                "-fx-background-color: #f8fafc;"
        );

        // =========================
        // SCENE
        // =========================

        Scene scene =
                new Scene(
                        root,
                        800,
                        650
                );

        stage.setTitle(
                "Sports Club Management System"
        );

        stage.setScene(scene);

        stage.show();
    }

    // =========================
    // CREATE MENU BUTTON
    // =========================

    private Button createMenuButton(
            String title,
            String description) {

        Button button =
                new Button();

        Label titleLabel =
                new Label(title);

        titleLabel.setStyle(
                "-fx-font-size: 17px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #1e3a8a;"
        );

        Label descriptionLabel =
                new Label(description);

        descriptionLabel.setStyle(
                "-fx-font-size: 11px;" +
                "-fx-text-fill: #64748b;"
        );

        VBox content =
                new VBox(
                        8,
                        titleLabel,
                        descriptionLabel
                );

        content.setAlignment(
                Pos.CENTER
        );

        button.setGraphic(
                content
        );

        button.setPrefSize(
                300,
                110
        );

        button.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 12px;" +
                "-fx-border-color: #dbeafe;" +
                "-fx-border-radius: 12px;" +
                "-fx-border-width: 1px;" +
                "-fx-cursor: hand;"
        );

        // Hover effect
        button.setOnMouseEntered(e -> {

            button.setStyle(
                    "-fx-background-color: #eff6ff;" +
                    "-fx-background-radius: 12px;" +
                    "-fx-border-color: #3b82f6;" +
                    "-fx-border-radius: 12px;" +
                    "-fx-border-width: 2px;" +
                    "-fx-cursor: hand;"
            );
        });

        button.setOnMouseExited(e -> {

            button.setStyle(
                    "-fx-background-color: white;" +
                    "-fx-background-radius: 12px;" +
                    "-fx-border-color: #dbeafe;" +
                    "-fx-border-radius: 12px;" +
                    "-fx-border-width: 1px;" +
                    "-fx-cursor: hand;"
            );
        });

        return button;
    }

    public static void main(String[] args) {

        launch(args);
    }
}