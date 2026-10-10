package coach;

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

public class CoachManagement {

    public void show(Stage stage) {

        Label title = new Label("Coach Management");

        title.setStyle(
                "-fx-font-size: 24px;" +
                "-fx-font-weight: bold;"
        );

        Label nameLabel = new Label("Coach Name:");
        TextField nameField = new TextField();

        Label specializationLabel =
                new Label("Specialization:");
        TextField specializationField =
                new TextField();

        Label phoneLabel = new Label("Phone:");
        TextField phoneField = new TextField();

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();

        Button addButton =
                new Button("Add Coach");

        Button backButton =
                new Button("Back");
                backButton.setOnAction(e -> {

    Main main = new Main();

    main.start(stage);
});

        addButton.setOnAction(e -> {

            String name = nameField.getText();
            String specialization =
                    specializationField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();

            if (name.isEmpty()) {

                Alert alert = new Alert(
                        Alert.AlertType.WARNING
                );

                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText(
                        "Coach name is required!"
                );

                alert.showAndWait();

                return;
            }

            try {

                Connection connection =
                        DatabaseConnection.getConnection();

                String sql =
                        "INSERT INTO coaches " +
                        "(name, specialization, phone, email) " +
                        "VALUES (?, ?, ?, ?)";

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                statement.setString(1, name);
                statement.setString(
                        2,
                        specialization
                );
                statement.setString(3, phone);
                statement.setString(4, email);

                statement.executeUpdate();

                Alert alert = new Alert(
                        Alert.AlertType.INFORMATION
                );

                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText(
                        "Coach added successfully!"
                );

                alert.showAndWait();

                nameField.clear();
                specializationField.clear();
                phoneField.clear();
                emailField.clear();

                connection.close();

            } catch (SQLException ex) {

                ex.printStackTrace();

                Alert alert = new Alert(
                        Alert.AlertType.ERROR
                );

                alert.setTitle("Database Error");
                alert.setHeaderText(null);
                alert.setContentText(
                        "Could not add coach!"
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

        form.add(specializationLabel, 0, 1);
        form.add(specializationField, 1, 1);

        form.add(phoneLabel, 0, 2);
        form.add(phoneField, 1, 2);

        form.add(emailLabel, 0, 3);
        form.add(emailField, 1, 3);

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
                400
        );

        stage.setTitle("Coach Management");
        stage.setScene(scene);
    }
}