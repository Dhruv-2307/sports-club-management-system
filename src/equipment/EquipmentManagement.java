package equipment;

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

public class EquipmentManagement {

    public void show(Stage stage) {

        Label title = new Label("Equipment Management");
        title.setStyle(
                "-fx-font-size: 24px;" +
                "-fx-font-weight: bold;"
        );

        Label nameLabel = new Label("Equipment Name:");
        TextField nameField = new TextField();

        Label totalLabel = new Label("Total Quantity:");
        TextField totalField = new TextField();

        Label locationLabel = new Label("Location:");
        TextField locationField = new TextField();

        Button addButton = new Button("Add Equipment");
        Button issueButton = new Button("Issue Equipment");
        Button returnButton = new Button("Return Equipment");
        Button backButton = new Button("Back");

        // ADD EQUIPMENT
        addButton.setOnAction(e -> {

            String name = nameField.getText();
            String totalText = totalField.getText();
            String location = locationField.getText();

            if (name.isEmpty() || totalText.isEmpty() || location.isEmpty()) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all fields!");
                alert.showAndWait();

                return;
            }

            try {

                int totalQuantity = Integer.parseInt(totalText);

                if (totalQuantity <= 0) {

                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText(null);
                    alert.setContentText(
                            "Quantity must be greater than 0!"
                    );
                    alert.showAndWait();

                    return;
                }

                Connection connection =
                        DatabaseConnection.getConnection();

                String sql =
                        "INSERT INTO equipment " +
                        "(name, total_quantity, available_quantity, location) " +
                        "VALUES (?, ?, ?, ?)";

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                statement.setString(1, name);
                statement.setInt(2, totalQuantity);

                // Initially everything is available
                statement.setInt(3, totalQuantity);

                statement.setString(4, location);

                statement.executeUpdate();

                connection.close();

                Alert alert =
                        new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText(
                        "Equipment added successfully!"
                );
                alert.showAndWait();

                nameField.clear();
                totalField.clear();
                locationField.clear();

            } catch (NumberFormatException ex) {

                Alert alert =
                        new Alert(Alert.AlertType.ERROR);

                alert.setTitle("Invalid Quantity");
                alert.setHeaderText(null);
                alert.setContentText(
                        "Quantity must be a number!"
                );
                alert.showAndWait();

            } catch (SQLException ex) {

                ex.printStackTrace();

                Alert alert =
                        new Alert(Alert.AlertType.ERROR);

                alert.setTitle("Database Error");
                alert.setHeaderText(null);
                alert.setContentText(
                        "Could not add equipment!"
                );
                alert.showAndWait();
            }
        });

        // ISSUE EQUIPMENT
        issueButton.setOnAction(e -> {

            TextField equipmentIdField =
                    new TextField();

            TextField quantityField =
                    new TextField();

            GridPane issueForm =
                    new GridPane();

            issueForm.setHgap(10);
            issueForm.setVgap(10);

            issueForm.add(
                    new Label("Equipment ID:"), 0, 0
            );

            issueForm.add(
                    equipmentIdField, 1, 0
            );

            issueForm.add(
                    new Label("Quantity:"), 0, 1
            );

            issueForm.add(
                    quantityField, 1, 1
            );

            VBox layout =
                    new VBox(15,
                            new Label("Issue Equipment"),
                            issueForm
                    );

            layout.setPadding(
                    new Insets(20)
            );

            Button confirmButton =
                    new Button("Confirm Issue");

            layout.getChildren().add(confirmButton);

            Scene scene =
                    new Scene(layout, 400, 250);

            stage.setScene(scene);
            stage.setTitle("Issue Equipment");

            confirmButton.setOnAction(event -> {

                try {

                    int equipmentId =
                            Integer.parseInt(
                                    equipmentIdField.getText()
                            );

                    int quantity =
                            Integer.parseInt(
                                    quantityField.getText()
                            );

                    if (quantity <= 0) {

                        showAlert(
                                Alert.AlertType.WARNING,
                                "Quantity must be greater than 0!"
                        );

                        return;
                    }

                    Connection connection =
                            DatabaseConnection.getConnection();

                    String sql =
                            "UPDATE equipment " +
                            "SET available_quantity = " +
                            "available_quantity - ? " +
                            "WHERE equipment_id = ? " +
                            "AND available_quantity >= ?";

                    PreparedStatement statement =
                            connection.prepareStatement(sql);

                    statement.setInt(1, quantity);
                    statement.setInt(2, equipmentId);
                    statement.setInt(3, quantity);

                    int rows =
                            statement.executeUpdate();

                    connection.close();

                    if (rows > 0) {

                        showAlert(
                                Alert.AlertType.INFORMATION,
                                "Equipment issued successfully!"
                        );

                        show(stage);

                    } else {

                        showAlert(
                                Alert.AlertType.WARNING,
                                "Equipment not found or insufficient quantity!"
                        );
                    }

                } catch (NumberFormatException ex) {

                    showAlert(
                            Alert.AlertType.ERROR,
                            "Please enter valid numbers!"
                    );

                } catch (SQLException ex) {

                    ex.printStackTrace();

                    showAlert(
                            Alert.AlertType.ERROR,
                            "Database error!"
                    );
                }
            });
        });

        // RETURN EQUIPMENT
        returnButton.setOnAction(e -> {

            TextField equipmentIdField =
                    new TextField();

            TextField quantityField =
                    new TextField();

            GridPane returnForm =
                    new GridPane();

            returnForm.setHgap(10);
            returnForm.setVgap(10);

            returnForm.add(
                    new Label("Equipment ID:"), 0, 0
            );

            returnForm.add(
                    equipmentIdField, 1, 0
            );

            returnForm.add(
                    new Label("Quantity:"), 0, 1
            );

            returnForm.add(
                    quantityField, 1, 1
            );

            Button confirmButton =
                    new Button("Confirm Return");

            VBox layout =
                    new VBox(
                            15,
                            new Label("Return Equipment"),
                            returnForm,
                            confirmButton
                    );

            layout.setAlignment(Pos.CENTER);
            layout.setPadding(
                    new Insets(20)
            );

            Scene scene =
                    new Scene(layout, 400, 250);

            stage.setScene(scene);
            stage.setTitle("Return Equipment");

            confirmButton.setOnAction(event -> {

                try {

                    int equipmentId =
                            Integer.parseInt(
                                    equipmentIdField.getText()
                            );

                    int quantity =
                            Integer.parseInt(
                                    quantityField.getText()
                            );

                    if (quantity <= 0) {

                        showAlert(
                                Alert.AlertType.WARNING,
                                "Quantity must be greater than 0!"
                        );

                        return;
                    }

                    Connection connection =
                            DatabaseConnection.getConnection();

                    String sql =
                            "UPDATE equipment " +
                            "SET available_quantity = " +
                            "available_quantity + ? " +
                            "WHERE equipment_id = ? " +
                            "AND available_quantity + ? <= total_quantity";

                    PreparedStatement statement =
                            connection.prepareStatement(sql);

                    statement.setInt(1, quantity);
                    statement.setInt(2, equipmentId);
                    statement.setInt(3, quantity);

                    int rows =
                            statement.executeUpdate();

                    connection.close();

                    if (rows > 0) {

                        showAlert(
                                Alert.AlertType.INFORMATION,
                                "Equipment returned successfully!"
                        );

                        show(stage);

                    } else {

                        showAlert(
                                Alert.AlertType.WARNING,
                                "Equipment not found or invalid return quantity!"
                        );
                    }

                } catch (NumberFormatException ex) {

                    showAlert(
                            Alert.AlertType.ERROR,
                            "Please enter valid numbers!"
                    );

                } catch (SQLException ex) {

                    ex.printStackTrace();

                    showAlert(
                            Alert.AlertType.ERROR,
                            "Database error!"
                    );
                }
            });
        });

        // BACK BUTTON
        backButton.setOnAction(e -> {

            Main main = new Main();
            main.start(stage);

        });

        // FORM
        GridPane form = new GridPane();

        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(
                new Insets(20)
        );

        form.add(
                nameLabel, 0, 0
        );

        form.add(
                nameField, 1, 0
        );

        form.add(
                totalLabel, 0, 1
        );

        form.add(
                totalField, 1, 1
        );

        form.add(
                locationLabel, 0, 2
        );

        form.add(
                locationField, 1, 2
        );

        VBox layout =
                new VBox(
                        15,
                        title,
                        form,
                        addButton,
                        issueButton,
                        returnButton,
                        backButton
                );

        layout.setAlignment(
                Pos.CENTER
        );

        layout.setPadding(
                new Insets(20)
        );

        Scene scene =
                new Scene(
                        layout,
                        500,
                        500
                );

        stage.setTitle(
                "Equipment Management"
        );

        stage.setScene(scene);
    }

    private void showAlert(
            Alert.AlertType type,
            String message) {

        Alert alert =
                new Alert(type);

        alert.setTitle("Equipment");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}