package main.java.repository.dao;

import main.java.connection.Connect;
import main.java.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;

public class UserDAO extends AbstractDAO<User> {

    private String createSelectAllEmployeesQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM User WHERE isAdmin IS 0");
        return sb.toString();
    }

    private String createFindEmployeeByIdQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM User WHERE isAdmin IS 0 and id =?");
        return sb.toString();
    }

    public ArrayList<User> viewAllEmployees(){
        ArrayList<User> all = new ArrayList<User>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectAllEmployeesQuery();
        try {
            connection = Connect.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            all = createObjects(resultSet);
        } catch(SQLException e) {
            LOGGER.log(Level.WARNING, "User" + "DAO:viewAllEmployees " + e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return all;
    }

    public User findEmployeeById(String field, int id){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createFindEmployeeByIdQuery();
        try {
            connection = Connect.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            ArrayList<User> found = createObjects(resultSet);
            if (found.size() != 0)
                return found.get(0);
        } catch(SQLException e) {
            LOGGER.log(Level.WARNING, "User " + "DAO:findby " + e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
