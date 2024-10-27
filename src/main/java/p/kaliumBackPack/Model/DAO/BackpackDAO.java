package p.kaliumBackPack.Model.DAO;

import org.bukkit.inventory.ItemStack;
import p.kaliumBackPack.InventorySerialization;
import p.kaliumBackPack.Manager.DataBase.SQLiteDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BackpackDAO {
    private final SQLiteDatabase database;

    public BackpackDAO(SQLiteDatabase database) {
        this.database = database;
    }

    public void saveBackpack(String playerUUID, int backpackId, ItemStack[] items) {
        try {
            database.ensureConnection();
            Connection connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "REPLACE INTO backpacks (player_uuid, backpack_id, items) VALUES (?, ?, ?)");
            statement.setString(1, playerUUID);
            statement.setInt(2, backpackId);
            statement.setBytes(3, InventorySerialization.serializeItems(items));
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public ItemStack[] loadBackpack(String playerUUID, int backpackId) {
        try {
            database.ensureConnection();
            Connection connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT items FROM backpacks WHERE player_uuid = ? AND backpack_id = ?");
            statement.setString(1, playerUUID);
            statement.setInt(2, backpackId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                byte[] data = resultSet.getBytes("items");
                statement.close();
                return InventorySerialization.deserializeItems(data);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ItemStack[0];
    }

}

