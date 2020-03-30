package main.java.repository.dao;

import main.java.connection.Connect;
import main.java.model.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;

public class ClientDAO extends AbstractDAO<Client> {

    private String createFindClientByIdQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM Client WHERE id =?");
        return sb.toString();
    }

    public Client findClientById(int id){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createFindClientByIdQuery();
        try {
            connection = Connect.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            ArrayList<Client> found = createObjects(resultSet);
            if (found.size() != 0)
                return found.get(0);
        } catch(SQLException e) {
            LOGGER.log(Level.WARNING, "Client " + "DAO:findby " + e.getMessage());
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
