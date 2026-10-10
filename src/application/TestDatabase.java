package application;

import database.DatabaseConnection;
import java.sql.Connection;

public class TestDatabase {

    public static void main(String[] args) {

        Connection connection =
                DatabaseConnection.getConnection();

        if (connection != null) {

            System.out.println(
                "Sports Club Database Connected!"
            );
        }
    }
}