package main.java.repository;

import main.java.connection.Connect;
import main.java.model.User;

import java.sql.*;

public class LoginRepository {

    public LoginRepository() {
    }

    public User findByUsername(String username){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM User WHERE username =?";
        try {
            connection = Connect.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, username);
            resultSet = statement.executeQuery();
            User u = null;
            try {
                if(resultSet.next()) {
                    u = new User();
                    u.setId(resultSet.getInt(1));
                    u.setFirstName(resultSet.getString(2));
                    u.setLastName(resultSet.getString(3));
                    u.setUsername(resultSet.getString(4));
                    u.setPassword(resultSet.getString(5));
                    u.setIsAdmin((resultSet.getInt(6)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (u != null)
                return u;
        } catch(SQLException e) {
            System.out.println("LoginRepository:findbyusername " + e.getMessage());
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


