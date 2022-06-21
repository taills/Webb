package io.github.taills.webb.core.DbHelper;

import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 模仿 redis
 */
public class KeyValueDatabase {

    private final Connection connection;
    private final static String TABLE_NAME = "KeyValueDatabase";

    public KeyValueDatabase(Connection connection) {
        this.connection = connection;
        DBUtil.createIfNotExists(connection, TABLE_NAME, "CREATE TABLE " + TABLE_NAME + " (\"k\" text NOT NULL,\"v\" TEXT NOT NULL,PRIMARY KEY (\"k\"))");
    }

    public int put(String db, String key, String value) {
        String sql = "INSERT OR REPLACE INTO " + TABLE_NAME + " VALUES(?||':'||?,?)";
        return DBUtil.execute(this.connection, sql, new String[]{db, key, value});
    }

    public String get(String db, String key) {
        String sql = "SELECT v FROM " + TABLE_NAME + " WHERE k = ?||':'||? ";
        List<Map<String, Object>> result = DBUtil.query(this.connection, sql, new String[]{db, key});
        if (result.size() > 0) {
            return result.get(0).get("v").toString();
        } else {
            return "";
        }
    }

    public int put(String key, String value) {
        return put("_DEFAULT_", key, value);
    }

    public String get(String key) {
        return get("_DEFAULT_", key);
    }

    public int destroy(String db, String key) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE k = ?||':'||?";
        return DBUtil.execute(this.connection, sql, new String[]{db, key});
    }

    public int destroy(String key) {
        return destroy("_DEFAULT_", key);
    }

    public Map<String, String> list(String db) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE k like ?||':%' ";
        List<Map<String, Object>> result = DBUtil.query(this.connection, sql, new String[]{db});
        Map<String, String> map = new LinkedHashMap<>();
        for (int i = 0; i < result.size(); i++) {
            map.put(result.get(i).get("k").toString(), result.get(i).get("v").toString());
        }
        return map;
    }

}
