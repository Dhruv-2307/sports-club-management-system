package facility;

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

public class FacilityManagement {

    public void show(Stage stage) {

        Label title = new Label("Facility Management");

        title.setStyle(
                "-fx-font-size: 24px;" +
                "-fx-font-weight: bold;"
        );

        Label nameLabel =
                new Label("Facility Name:");

        TextField nameField =
                new TextField();

        Label locationLabel =
                new Label("Location:");

        TextField locationField =
                new TextField();

        Label capacityLabel =
                new Label("Capacity:");

        TextField capacityField =
                new TextField();

        Button addButton =
                new Button("Add Facility");

        Button backButton =
                new Button("Back");

        // BACK BUTTON
        backButton.setOnAction(e -> {

            Main main = new Main();

            main.start(stage);
        });

        // ADD FACILITY
        addButton.setOnAction(e -> {

            String name =
                    nameField.getText();

            String location =
                    locationField.getText();

            String capacityText =
                    capacityField.getText();

            if (name.isEmpty()
                    || location.isEmpty()
                    || capacityText.isEmpty()) {

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

                int capacity =
                        Integer.parseInt(capacityText);

                if (capacity <= 0) {

                    Alert alert = new Alert(
                            Alert.AlertType.WARNING
                    );

                    alert.setTitle("Warning");
                    alert.setHeaderText(null);
                    alert.setContentText(
                            "Capacity must be greater than 0!"
                    );

                    alert.showAndWait();

                    return;
                }

                Connection connection =
                        DatabaseConnection.getConnection();

                String sql =
                        "INSERT INTO facilities " +
                        "(name, location, capacity) " +
                        "VALUES (?, ?, ?)";

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                statement.setString(1, name);
                statement.setString(2, location);
                statement.setInt(3, capacity);

                statement.executeUpdate();

                connection.close();

                Alert alert = new Alert(
                        Alert.AlertType.INFORMATION
                );

                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText(
                        "Facility added successfully!"
                );

                alert.showAndWait();

                nameField.clear();
                locationField.clear();
                capacityField.clear();

            } catch (NumberFormatException ex) {

                Alert alert = new Alert(
                        Alert.AlertType.ERROR
                );

                alert.setTitle("Invalid Capacity");
                alert.setHeaderText(null);
                alert.setContentText(
                        "Capacity must be a number!"
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
                        "Could not add facility!"
                );

                alert.showAndWait();
            }
        });

        GridPane form = new GridPane();

        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(
                new Insets(20)
        );

        form.add(
                nameLabel,
                0,
                0
        );

        form.add(
                nameField,
                1,
                0
        );

        form.add(
                locationLabel,
                0,
                1
        );

        form.add(
                locationField,
                1,
                1
        );

        form.add(
                capacityLabel,
                0,
                2
        );

        form.add(
                capacityField,
                1,
                2
        );

        VBox layout = new VBox(
                20,
                title,
                form,
                addButton,
                backButton
        );

        layout.setAlignment(
                Pos.CENTER
        );

        layout.setPadding(
                new Insets(20)
        );

        Scene scene = new Scene(
                layout,
                500,
                400
        );

        stage.setTitle(
                "Facility Management"
        );

        stage.setScene(scene);
    }
}