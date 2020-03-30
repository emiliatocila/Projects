package main.java.repository.dao;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.*;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.java.connection.Connect;

public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM " + type.getSimpleName() + " WHERE " + field + " =?");
        return sb.toString();
    }

    private String createSelectAllQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM " + type.getSimpleName());
        return sb.toString();
    }

    private String createInsertQuery() {
        StringBuilder sb = new StringBuilder();
        StringBuilder fields = new StringBuilder();
        StringBuilder values = new StringBuilder();
        sb.append("INSERT INTO " + type.getSimpleName() + "(");
        for (Field field : type.getDeclaredFields()) {
            fields.append(field.getName() + ", ");
            values.append("?,");
        }
        sb.append(fields.substring(0, fields.length() - 2) + ") VALUES(" + values.substring(0, values.length() - 1) + ") ");
        return sb.toString();
    }

    private String createDeleteQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM " + type.getSimpleName() + " WHERE id=?");
        return sb.toString();
    }

    private String createUpdateQuery(int id) {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE " + type.getSimpleName() + " SET ");
        for (Field field : type.getDeclaredFields()) {
            if(field.getName() != "id" && field.getName() != "password" && field.getName() != "isAdmin")
                sb.append(field.getName() + "=?, ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append(" WHERE id=" + id);
        return sb.toString();
    }

    public ArrayList<T> createObjects(ResultSet resultSet){
        ArrayList <T> list = new ArrayList<T>();
        try {
            while(resultSet.next()) {
                T instance = type.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    Object value = resultSet.getObject(field.getName());
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public T findById(String field, int id){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery(field);
        try {
            connection = Connect.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            ArrayList<T> found = createObjects(resultSet);
            if (found.size() != 0)
                return found.get(0);
        } catch(SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findby " + e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public int insert(T obj){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        PropertyDescriptor propertyDescriptor = null;
        String query = createInsertQuery();
        String value = null;
        int valueNr = 1;
        try {
            connection = Connect.getConnection();
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            for (Field field : obj.getClass().getDeclaredFields()) {
                propertyDescriptor = new PropertyDescriptor(field.getName(), obj.getClass());
                value = propertyDescriptor.getReadMethod().invoke(obj).toString();
                statement.setString(valueNr++, value);
            }
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next())
                return resultSet.getInt(1);
            return 0;
        } catch(SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    public int delete(int id){
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createDeleteQuery();
        try {
            connection = Connect.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
            return 0;
        } catch(SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findby " + e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    public int update(T obj, int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        PropertyDescriptor propertyDescriptor = null;
        String query = createUpdateQuery(id);
        String value = null;
        int valueNr = 1;
        try {
            connection = Connect.getConnection();
            statement = connection.prepareStatement(query);
            for (Field field : obj.getClass().getDeclaredFields()) {
                if (field.getName() != "id" && field.getName() != "password" && field.getName() != "isAdmin") {
                    propertyDescriptor = new PropertyDescriptor(field.getName(), obj.getClass());
                    value = propertyDescriptor.getReadMethod().invoke(obj).toString();
                    statement.setString(valueNr++, value);
                }
            }
            statement.executeUpdate();
            return 0;
        } catch(SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    public ArrayList<T> viewAll(){
        ArrayList<T> all = new ArrayList<T>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectAllQuery();
        try {
            connection = Connect.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            all = createObjects(resultSet);
        } catch(SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:viewAll " + e.getMessage());
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
