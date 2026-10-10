package athlete;

import application.Main;
import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AthleteManagement {

    public void show(Stage stage) {

        Label title = new Label("Athlete Management");

        title.setStyle(
                "-fx-font-size: 24px;" +
                "-fx-font-weight: bold;"
        );

        Label nameLabel = new Label("Athlete Name:");
        TextField nameField = new TextField();

        Label sportLabel = new Label("Sport:");
        TextField sportField = new TextField();

        Label dateLabel = new Label("Enrollment Date:");
        TextField dateField = new TextField();

        Label coachLabel = new Label("Coach ID:");
        TextField coachField = new TextField();

        Button addButton = new Button("Add Athlete");
        Button backButton = new Button("Back");

        backButton.setOnAction(e -> {

            Main main = new Main();

            main.start(stage);
        });

        addButton.setOnAction(e -> {

            String name = nameField.getText();
            String sport = sportField.getText();
            String date = dateField.getText();
            String coachIdText = coachField.getText();

            if (name.isEmpty() || sport.isEmpty()
                    || date.isEmpty() || coachIdText.isEmpty()) {

                Alert alert = new Alert(
                        Alert.AlertType.WARNING
                );

                alert.setTitle("Warning"); 
                alert.setHeaderText(null);
                alert.setContentText(
                        "Please fill all fields!"
                );

                alert.showAndWait();

                return;
            }

            try {

                int coachId =
                        Integer.parseInt(coachIdText);

                Connection connection =
                        DatabaseConnection.getConnection();

                String sql =
                        "INSERT INTO athletes " +
                        "(name, sport, enrollment_date, coach_id) " +
                        "VALUES (?, ?, ?, ?)";

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                statement.setString(1, name);
                statement.setString(2, sport);
                statement.setString(3, date);
                statement.setInt(4, coachId);

                statement.executeUpdate();

                Alert alert = new Alert(
                        Alert.AlertType.INFORMATION
                );

                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText(
                        "Athlete added successfully!"
                );

                alert.showAndWait();

                nameField.clear();
                sportField.clear();
                dateField.clear();
                coachField.clear();

                connection.close();

            } catch (NumberFormatException ex) {

                Alert alert = new Alert(
                        Alert.AlertType.ERROR
                );

                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText(
                        "Coach ID must be a number!"
                );

                alert.showAndWait();

            } catch (SQLException ex) {

                ex.printStackTrace();

                Alert alert = new Alert(
                        Alert.AlertType.ERROR
                );

                alert.setTitle("Database Error");
                alert.setHeaderText(null);
                alert.setContentText(
                        "Could not add athlete!"
                );

                alert.showAndWait();
            }
        });

        GridPane form = new GridPane();

        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(20));

        form.add(nameLabel, 0, 0);
        form.add(nameField, 1, 0);

        form.add(sportLabel, 0, 1);
        form.add(sportField, 1, 1);

        form.add(dateLabel, 0, 2);
        form.add(dateField, 1, 2);

        form.add(coachLabel, 0, 3);
        form.add(coachField, 1, 3);

        VBox layout = new VBox(
                20,
                title,
                form,
                addButton,
                backButton
        );

        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(
                layout,
                500,
                450
        );

        stage.setTitle("Athlete Management");
        stage.setScene(scene);
    }
}