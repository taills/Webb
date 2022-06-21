package io.github.taills.webb.core.DbHelper;

import lombok.extern.slf4j.Slf4j;
import org.sqlite.core.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.UUID;

@Slf4j
public class MainDatabase {
    private final static String DB_TAG = "__TAG__";
    private final static String DB_PLUGIN = "__PLUGIN__";
    private final static String DB_ENV = "__ENV__";
    private final static String DB_SETTING = "__SETTING__";

    private static Connection dbConn;
    private static KeyValueDatabase keyValueDatabase;

    static {
        try {
            Class.forName("org.sqlite.JDBC");
            dbConn = DriverManager.getConnection("jdbc:sqlite:data.db");
            DBUtil.createIfNotExists(dbConn, "shell", "CREATE TABLE \"shell\" ( \"id\" text NOT NULL,  \"url\" TEXT NOT NULL,  \"password\" TEXT NOT NULL,  \"secretKey\" TEXT NOT NULL,  \"payload\" TEXT NOT NULL,  \"cryption\" TEXT NOT NULL,  \"encoding\" TEXT NOT NULL,  \"headers\" TEXT NOT NULL,  \"reqLeft\" TEXT NOT NULL,  \"reqRight\" TEXT NOT NULL,  \"connTimeout\" integer NOT NULL,  \"readTimeout\" integer NOT NULL,  \"proxyType\" TEXT NOT NULL,  \"proxyHost\" TEXT NOT NULL,  \"proxyPort\" TEXT NOT NULL,  \"remark\" TEXT NOT NULL,  \"note\" TEXT NOT NULL,  \"createTime\" TEXT NOT NULL,  \"updateTime\" text NOT NULL,  PRIMARY KEY (\"id\"))");
            keyValueDatabase = new KeyValueDatabase(dbConn);
            dbConn.setAutoCommit(true);
        } catch (ClassNotFoundException | SQLException e) {
            log.error("Database initialization failed: {}", e.getLocalizedMessage());
        }
    }

    public static KeyValueDatabase getKeyValueDatabase() {
        return keyValueDatabase;
    }

    public static String createTag(String tagName) {
        String uuid = UUID.randomUUID().toString();
        return updateTag(uuid, tagName);
    }

    public static String updateTag(String uuid, String tagName) {
        getKeyValueDatabase().put(DB_TAG, uuid, tagName);
        if (getKeyValueDatabase().get(DB_TAG, uuid).equals(tagName)) {
            return uuid;
        } else {
            return "";
        }
    }

    public static int destroyTag(String uuid) {
        return getKeyValueDatabase().destroy(DB_TAG, uuid);
    }


    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            String tagName = String.format("tag-%d", i);
            String uuid = createTag(tagName);
            if (i % 2 == 0) {
                updateTag(uuid, "tag-new-" + i);
            }
        }
    }

}
