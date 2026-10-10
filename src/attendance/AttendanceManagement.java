package attendance;

import application.Main;
import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AttendanceManagement {

    private TableView<Attendance> table;

    public void show(Stage stage) {

        Label title = new Label("Attendance Management");

        title.setStyle(
                "-fx-font-size: 24px;" +
                "-fx-font-weight: bold;"
        );

        // Person Type
        Label typeLabel = new Label("Person Type:");

        ComboBox<String> typeBox =
                new ComboBox<>();

        typeBox.getItems().addAll(
                "Athlete",
                "Coach"
        );

        typeBox.setValue("Athlete");

        // Person ID
        Label idLabel = new Label("Person ID:");

        TextField idField =
                new TextField();

        // Date
        Label dateLabel = new Label("Date:");

        DatePicker datePicker =
                new DatePicker();

        datePicker.setValue(
                java.time.LocalDate.now()
        );

        // Status
        Label statusLabel =
                new Label("Status:");

        ComboBox<String> statusBox =
                new ComboBox<>();

        statusBox.getItems().addAll(
                "Present",
                "Absent",
                "Late"
        );

        statusBox.setValue("Present");

        Button markButton =
                new Button("Mark Attendance");

        Button refreshButton =
                new Button("Refresh");

        Button backButton =
                new Button("Back");

        // MARK ATTENDANCE
        markButton.setOnAction(e -> {

            String personType =
                    typeBox.getValue();

            String personIdText =
                    idField.getText();

            String status =
                    statusBox.getValue();

            if (personIdText.isEmpty()) {

                showAlert(
                        Alert.AlertType.WARNING,
                        "Please enter Person ID!"
                );

                return;
            }

            try {

                int personId =
                        Integer.parseInt(personIdText);

                String date =
                        datePicker.getValue().toString();

                Connection connection =
                        DatabaseConnection.getConnection();

                // Check duplicate attendance
                String checkSql =
                        "SELECT attendance_id " +
                        "FROM attendance " +
                        "WHERE person_type = ? " +
                        "AND person_id = ? " +
                        "AND attendance_date = ?";

                PreparedStatement checkStatement =
                        connection.prepareStatement(checkSql);

                checkStatement.setString(
                        1, personType
                );

                checkStatement.setInt(
                        2, personId
                );

                checkStatement.setString(
                        3, date
                );

                ResultSet result =
                        checkStatement.executeQuery();

                if (result.next()) {

                    connection.close();

                    showAlert(
                            Alert.AlertType.WARNING,
                            "Attendance already marked for this person today!"
                    );

                    return;
                }

                String sql =
                        "INSERT INTO attendance " +
                        "(person_type, person_id, attendance_date, status) " +
                        "VALUES (?, ?, ?, ?)";

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                statement.setString(
                        1, personType
                );

                statement.setInt(
                        2, personId
                );

                statement.setString(
                        3, date
                );

                statement.setString(
                        4, status
                );

                statement.executeUpdate();

                connection.close();

                showAlert(
                        Alert.AlertType.INFORMATION,
                        "Attendance marked successfully!"
                );

                idField.clear();

                loadAttendance();

            } catch (NumberFormatException ex) {

                showAlert(
                        Alert.AlertType.ERROR,
                        "Person ID must be a number!"
                );

            } catch (SQLException ex) {

                ex.printStackTrace();

                showAlert(
                        Alert.AlertType.ERROR,
                        "Database error!"
                );
            }
        });

        // REFRESH
        refreshButton.setOnAction(e -> {
            loadAttendance();
        });

        // BACK
        backButton.setOnAction(e -> {

            Main main = new Main();

            main.start(stage);
        });

        // FORM
        GridPane form =
                new GridPane();

        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(
                new Insets(20)
        );

        form.add(
                typeLabel, 0, 0
        );

        form.add(
                typeBox, 1, 0
        );

        form.add(
                idLabel, 0, 1
        );

        form.add(
                idField, 1, 1
        );

        form.add(
                dateLabel, 0, 2
        );

        form.add(
                datePicker, 1, 2
        );

        form.add(
                statusLabel, 0, 3
        );

        form.add(
                statusBox, 1, 3
        );

        // TABLE
        table =
                new TableView<>();

        TableColumn<Attendance, Integer>
                idColumn =
                new TableColumn<>("ID");

        idColumn.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );

        TableColumn<Attendance, String>
                typeColumn =
                new TableColumn<>("Person Type");

        typeColumn.setCellValueFactory(
                new PropertyValueFactory<>("personType")
        );

        TableColumn<Attendance, Integer>
                personColumn =
                new TableColumn<>("Person ID");

        personColumn.setCellValueFactory(
                new PropertyValueFactory<>("personId")
        );

        TableColumn<Attendance, String>
                dateColumn =
                new TableColumn<>("Date");

        dateColumn.setCellValueFactory(
                new PropertyValueFactory<>("attendanceDate")
        );

        TableColumn<Attendance, String>
                statusColumn =
                new TableColumn<>("Status");

        statusColumn.setCellValueFactory(
                new PropertyValueFactory<>("status")
        );

        table.getColumns().add(idColumn);
table.getColumns().add(typeColumn);
table.getColumns().add(personColumn);
table.getColumns().add(dateColumn);
table.getColumns().add(statusColumn);

        table.setPrefHeight(250);

        VBox layout =
                new VBox(
                        15,
                        title,
                        form,
                        markButton,
                        refreshButton,
                        table,
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
                        700,
                        650
                );

        stage.setTitle(
                "Attendance Management"
        );

        stage.setScene(scene);

        loadAttendance();
    }

    // LOAD ATTENDANCE
    private void loadAttendance() {

        ObservableList<Attendance>
                attendanceList =
                FXCollections.observableArrayList();

        try {

            Connection connection =
                    DatabaseConnection.getConnection();

            String sql =
                    "SELECT * FROM attendance " +
                    "ORDER BY attendance_date DESC";

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            ResultSet result =
                    statement.executeQuery();

            while (result.next()) {

                Attendance attendance =
                        new Attendance(
                                result.getInt("attendance_id"),
                                result.getString("person_type"),
                                result.getInt("person_id"),
                                result.getString("attendance_date"),
                                result.getString("status")
                        );

                attendanceList.add(
                        attendance
                );
            }

            connection.close();

            if (table != null) {

                table.setItems(
                        attendanceList
                );
            }

        } catch (SQLException ex) {

            ex.printStackTrace();
        }
    }

    private void showAlert(
            Alert.AlertType type,
            String message) {

        Alert alert =
                new Alert(type);

        alert.setTitle(
                "Attendance"
        );

        alert.setHeaderText(null);

        alert.setContentText(
                message
        );

        alert.showAndWait();
    }
}