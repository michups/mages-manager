package pl.michups.mages.database;

import lombok.extern.java.Log;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by michups on 03.07.17.
 */
@Log
public abstract class BaseDAO<T> {

    public abstract String getTableName();

    public abstract T parseValue(ResultSet result) throws SQLException;

    public abstract String[] getColumns();

    public abstract Object[] getColumnsValues(T value);

    public abstract int getPrimaryKeyValue(T value);

    private void setParams(PreparedStatement statement, Object[] values) throws SQLException {
        for (int i = 0; i < values.length; i++) {
            Object param = values[i];
            int paramIndex = i + 1;
            if (param instanceof String) {
                statement.setString(paramIndex, (String) param);
            } else if (param instanceof Integer) {
                statement.setInt(paramIndex, (Integer) param);
            } else if (param instanceof Date) {
                statement.setObject(paramIndex, param);
            } else if (param == null) {
                statement.setNull(paramIndex, Types.INTEGER);
            } else {
                throw new IllegalArgumentException("Unsupported class " + param.getClass());
            }
        }
    }

    public void execute(String sql, Object[] params) {
        try (Connection con = ConnectionFactory.createConnection();
             PreparedStatement statement = con.prepareStatement(sql.toString())) {
            setParams(statement, params);
            log.log(Level.FINE, statement.toString());
            statement.execute();
        } catch (SQLException ex) {
            log.log(Level.WARNING, ex.getMessage(), ex);
        }
    }

    public List<T> executeQuery(String sql, Object[] params) {
        List<T> values = new ArrayList<>();
        try (Connection con = ConnectionFactory.createConnection();
             PreparedStatement statement = con.prepareStatement(sql);) {
            setParams(statement, params);
            log.log(Level.FINE, statement.toString());
            try (ResultSet result = statement.executeQuery();) {
                while (result.next()) {
                    T value = parseValue(result);
                    values.add(value);
                }
            }
        } catch (SQLException ex) {
            log.log(Level.WARNING, ex.getMessage(), ex);
        }
        return values;
    }

    public void update(T value) {
        StringBuffer sql = new StringBuffer("UPDATE ");
        sql.append(getTableName()).append(" SET ");
        for (String column : getColumns()) {
            sql.append(column).append(" = ?, ");
        }
        sql.replace(sql.length() - 2, sql.length(), " ");
        sql.append("WHERE id = ?");
        Object[] params = getColumnsValues(value);
        params = Arrays.copyOf(params, params.length + 1);
        params[params.length - 1] = getPrimaryKeyValue(value);
        execute(sql.toString(), params);
    }

    public void insert(T value) {
        StringBuffer sql = new StringBuffer("INSERT INTO ");
        sql.append(getTableName()).append(" (");
        for (String column : getColumns()) {
            sql.append(column).append(", ");
        }
        sql.replace(sql.length() - 2, sql.length(), ")");
        sql.append(" VALUES (");
        for (int i = 0; i < getColumns().length; i++) {
            sql.append("?, ");
        }
        sql.replace(sql.length() - 2, sql.length(), ")");
        execute(sql.toString(), getColumnsValues(value));
    }

    public List<T> findALl() {
        String sql = "SELECT * FROM " + getTableName();
        return executeQuery(sql, new Object[0]);
    }

    public void delete(T value) {
        delete(getPrimaryKeyValue(value));
    }

    public void delete(int id) {
        String sql = "DELETE FROM " + getTableName() + "  WHERE id = ?";
        Object[] params = {id};
        execute(sql, params);
    }

    public T find(int id) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE id = ?";
        Object[] params = {id};
        List<T> tList = executeQuery(sql, params);
        return tList.size() == 0 ? null : tList.get(0);
    }

}
