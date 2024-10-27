package p.kaliumBackPack.Manager.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteDatabase {
    private Connection connection;
    private final String databaseUrl;

    public SQLiteDatabase(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public void connect() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection("jdbc:sqlite:" + databaseUrl);
            createTableIfNotExists();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void ensureConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connect();
        }
    }


    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS backpacks (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "player_uuid TEXT NOT NULL," +
                "backpack_id INTEGER NOT NULL," +
                "items BLOB NOT NULL," +
                "UNIQUE(player_uuid, backpack_id)" +
                ");";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
