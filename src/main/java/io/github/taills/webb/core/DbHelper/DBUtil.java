package io.github.taills.webb.core.DbHelper;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Slf4j
public class DBUtil {

    public static boolean tableExists(Connection connection, String tableName) {
        List<Map<String, Object>> result = DBUtil.query(connection, "SELECT COUNT(1) as result FROM sqlite_master WHERE name=?", new String[]{tableName});
        return result.get(0).get("result").equals(1);
    }

    public static void createIfNotExists(Connection connection, String tableName, String createTableSql) {
        if (!tableExists(connection, tableName)) {
            try {
                connection.createStatement().execute(createTableSql);
            } catch (SQLException e) {
                log.error("create table failed. {} ", e.getLocalizedMessage());
            }
        }
    }

    public static int execute(Connection connection, String sql, Object[] objects) {
        return execute(connection, sql, Arrays.asList(objects));
    }

    public static int execute(Connection connection, String sql, List<Object> params) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(i + 1, params.get(i));
            }
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("execute exception: {}", e.getLocalizedMessage());
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    log.error("preparedStatement close  exception: {}", e.getLocalizedMessage());
                }
            }

        }
        return 0;
    }

    public static List<Map<String, Object>> query(Connection connection, String sql) {
        return query(connection, sql, new ArrayList<>());
    }

    public static List<Map<String, Object>> query(Connection connection, String sql, Object[] params) {
        return query(connection, sql, Arrays.asList(params));
    }

    public static List<Map<String, Object>> query(Connection connection, String sql, List<Object> params) {
        PreparedStatement preparedStatement = null;
        List<Map<String, Object>> result = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(i + 1, params.get(i));
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            int columnCount = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()) {
                Map<String, Object> row = new LinkedHashMap<>();
                for (int i = 0; i < columnCount; i++) {
                    // 这里的下标从1开始
                    row.put(resultSet.getMetaData().getColumnName(i + 1), resultSet.getObject(i + 1));
                }
                result.add(row);
            }
        } catch (SQLException e) {
            log.error("execute exception: {}", e.getLocalizedMessage());
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    log.error("preparedStatement close  exception: {}", e.getLocalizedMessage());
                }
            }
        }
        return result;
    }
}
