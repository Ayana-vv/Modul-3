package dataBaseExample;

import java.sql.*;
import java.util.Scanner;

public class SQLInject {
    private static Connection connection;

    public static void main(String[] args) throws SQLException {
        connection = getConnection();
        Scanner scanner = new Scanner(System.in);
//        int id = scanner.nextInt();
//        ResultSet resultSet = getEmployeeById(id);
        String city = scanner.nextLine();
        ResultSet resultSet = getEmployeeByCity(city);

        while (resultSet.next()) {
            System.out.println(resultSet.getString("name"));
            System.out.println(resultSet.getString("surname"));
            System.out.println(resultSet.getString("position"));
            System.out.println("________________________________");
        }
    }

    public static Connection getConnection() throws SQLException {
        String connectionString = "jdbc:postgresql://dpg-d3qvhjndiees73am0fig-a.oregon-postgres.render.com/dbinnopolis";
        String username = "dbinnopolis_user";
        String password = "qsN9l1XpkfpSpDqeRJCFcIsBu95b2y6Y";
        return DriverManager.getConnection(connectionString, username, password);
    }

    public static ResultSet getEmployeeByCity(String city) throws SQLException {
        String SELECT_NAME_SURNAME = "SELECT name, surname, position From employee where city = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_NAME_SURNAME);
        preparedStatement.setString(1, city);
//        String UPDATE_NAME_SURNAME = "UPDATE employee set name = 'Test1' where id = ?";
//        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_NAME_SURNAME);
//        preparedStatement.setInt(1, id);
        return preparedStatement.executeQuery();
    }
}
