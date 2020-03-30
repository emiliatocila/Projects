package main.java.repository.dao;

import main.java.connection.Connect;
import main.java.model.Activity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;

public class ActivityDAO extends AbstractDAO<Activity> {

    private String createSelectActivityBetweenDatesQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM Activity WHERE date BETWEEN ? and ? and idEmployee =?");
        return sb.toString();
    }

    public ArrayList<Activity> viewAllActivitiesBetweenDates(String dateFrom, String dateTo, int idEmployee){
        ArrayList<Activity> all = new ArrayList<Activity>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectActivityBetweenDatesQuery();
        try {
            connection = Connect.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, dateFrom);
            statement.setString(2, dateTo);
            statement.setInt(3, idEmployee);
            resultSet = statement.executeQuery();
            all = createObjects(resultSet);
        } catch(SQLException e) {
            LOGGER.log(Level.WARNING, "Activity" + "DAO:viewAllActivitiesBetweenDates " + e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return all;
    }
}
