package attestation3.helpers;

import attestation3.enitities.EmployeeRequest;
import attestation3.enitities.EmployeeResponse;
import restAssured.helpers.AbstractHelper;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static io.restassured.RestAssured.given;

public class EmployeeHelperDB extends AbstractHelper {

    public EmployeeHelperDB() throws SQLException, IOException {
        connection = getConnection();
    }

    public int createEmployee(EmployeeRequest employee) throws SQLException {
        String INSERT_EMPLOYEE = "INSERT INTO employee(\"name\",\"surname\",\"position\",\"city\") values (?,?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EMPLOYEE, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, employee.getName());
        preparedStatement.setString(2, employee.getSurname());
        preparedStatement.setString(3, employee.getPosition());
        preparedStatement.setString(4, employee.getCity());

        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()) {
            return resultSet.getInt("id");
        }
        else {
            return -1;
        }
    }

    public EmployeeResponse getEmployee(int id) throws Exception {
        String SELECT_NAME_SURNAME = "SELECT * From employee where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_NAME_SURNAME);
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return new EmployeeResponse(
                    resultSet.getString("city"),
                    resultSet.getString("name"),
                    resultSet.getString("position"),
                    resultSet.getString("surname"),
                    resultSet.getInt("id")
            );
        }
        else {
            return new EmployeeResponse();
        }
    }

    public EmployeeResponse getEmployeeToName(String name) throws Exception {
        String SELECT_NAME = "SELECT * From employee where name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_NAME);
        preparedStatement.setString(1, name);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return new EmployeeResponse(
                    resultSet.getString("city"),
                    resultSet.getString("name"),
                    resultSet.getString("position"),
                    resultSet.getString("surname"),
                    resultSet.getInt("id")
            );
        }
        else {
            return new EmployeeResponse();
        }
    }

    public EmployeeResponse getEmployeeToNotExistName(String name) throws Exception {
        String SELECT_NAME = "SELECT * From employee where name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_NAME);
        preparedStatement.setString(1, "testXXX");              //несуществующий name

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return new EmployeeResponse(
                    resultSet.getString("city"),
                    resultSet.getString("name"),
                    resultSet.getString("position"),
                    resultSet.getString("surname"),
                    resultSet.getInt("id")
            );
        }
        else {
            return new EmployeeResponse();
        }
    }

    public int deleteEmployee(int id) throws SQLException {
        String DELETE_EMPLOYEE = "DELETE From employee where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_EMPLOYEE, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, id);

        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()) {
            return resultSet.getInt("id");
        }
        else {
            return -1;
        }
    }

    public int updateEmployeeName(int employeeId, String employeeNewName) throws SQLException {
        String UPDATE_EMPLOYEE = "UPDATE employee SET name = ? where id = ? ";
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_EMPLOYEE);
        preparedStatement.setString(1, employeeNewName);
        preparedStatement.setInt(2, employeeId);
        return preparedStatement.executeUpdate();
    }

    public EmployeeResponse updateEmployee(int employeeId, EmployeeRequest employee) throws Exception {
        String UPDATE_EMPLOYEE = "UPDATE employee SET name = ?, surname = ?, city = ?, position = ? where id = ? ";
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_EMPLOYEE);
        preparedStatement.setString(1, employee.getName());
        preparedStatement.setString(2, employee.getSurname());
        preparedStatement.setString(3, employee.getCity());
        preparedStatement.setString(4, employee.getPosition());
        preparedStatement.setInt(5, employeeId);

        int result = preparedStatement.executeUpdate();
        if (result > 0) {
            return getEmployee(employeeId);
        } else {
            return new EmployeeResponse();
        }
    }

    public int updateEmployeeWrongTypeField(int employeeId, int employeeNewCity) throws SQLException {
        String UPDATE_EMPLOYEE = "UPDATE employee SET city = ? where id = ? ";
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_EMPLOYEE);
        preparedStatement.setInt(1, employeeNewCity);
        preparedStatement.setInt(2, employeeId);
        return preparedStatement.executeUpdate();
    }
}
