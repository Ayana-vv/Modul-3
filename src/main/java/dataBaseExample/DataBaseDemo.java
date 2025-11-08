package dataBaseExample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBaseDemo {
    public static void main(String[] args) throws SQLException {
        String connectionString = "jdbc:postgresql://dpg-d3qvhjndiees73am0fig-a.oregon-postgres.render.com/dbinnopolis";
        String username = "dbinnopolis_user";
        String password = "qsN9l1XpkfpSpDqeRJCFcIsBu95b2y6Y";

        String SELECT_NAME_SURNAME = "SELECT name, surname From employee limit 10";
        Connection connection = DriverManager.getConnection(connectionString, username, password);
        ResultSet resultSet = connection.createStatement().executeQuery(SELECT_NAME_SURNAME);
        while (resultSet.next()) {
            System.out.println(resultSet.getString("name"));
            System.out.println(resultSet.getString("surname"));
        }
    }
}
