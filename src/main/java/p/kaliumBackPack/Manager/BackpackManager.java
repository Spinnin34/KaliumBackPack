package p.kaliumBackPack.Manager;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import p.kaliumBackPack.Manager.DataBase.SQLiteDatabase;
import p.kaliumBackPack.Model.Backpack;
import p.kaliumBackPack.Model.DAO.BackpackDAO;
import p.kaliumBackPack.Model.Role;
import p.kaliumBackPack.Utils.LangUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BackpackManager {
    private final Map<UUID, Map<Integer, Backpack>> playerBackpacks = new HashMap<>();
    private final RoleManager roleManager;
    private final BackpackDAO backpackDAO;

    public BackpackManager(SQLiteDatabase database, RoleManager roleManager) {
        this.roleManager = roleManager;
        this.backpackDAO = new BackpackDAO(database);
    }

    public Backpack getOrCreateBackpack(Player player, int backpackId) {
        UUID playerUUID = player.getUniqueId();
        Role playerRole = roleManager.getRoleForPlayer(player);

        if (backpackId > playerRole.getMaxBackpacks()) {
            player.sendMessage(LangUtil.getString("messages.not-backpack", true));
            return null;
        }

        playerBackpacks.putIfAbsent(playerUUID, new HashMap<>());
        Map<Integer, Backpack> backpacks = playerBackpacks.get(playerUUID);

        if (!backpacks.containsKey(backpackId)) {
            int rows = playerRole.getRows();

            rows = Math.min(rows, 6);
            Backpack backpack = new Backpack(backpackId, rows);

            ItemStack[] contents = backpackDAO.loadBackpack(playerUUID.toString(), backpackId);
            int maxSize = rows * 9;

            if (contents.length > maxSize) {
                ItemStack[] adjustedContents = new ItemStack[maxSize];
                System.arraycopy(contents, 0, adjustedContents, 0, maxSize);
                backpack.getInventory().setContents(adjustedContents);
            } else {
                backpack.getInventory().setContents(contents);
            }

            backpacks.put(backpackId, backpack);
        }

        return backpacks.get(backpackId);
    }



    public void savePlayerBackpacks(Player player) {
        UUID playerUUID = player.getUniqueId();
        Map<Integer, Backpack> backpacks = playerBackpacks.get(playerUUID);

        if (backpacks != null) {
            for (Map.Entry<Integer, Backpack> entry : backpacks.entrySet()) {
                Backpack backpack = entry.getValue();

                if (backpack.hasChanged()) {
                    backpackDAO.saveBackpack(playerUUID.toString(), entry.getKey(), backpack.getInventory().getContents());
                    backpack.setHasChanged(false);
                }
            }
        }
    }

    public void onBackpackItemChanged(Player player, int backpackId) {
        UUID playerUUID = player.getUniqueId();
        Map<Integer, Backpack> backpacks = playerBackpacks.get(playerUUID);

        if (backpacks != null && backpacks.containsKey(backpackId)) {
            backpacks.get(backpackId).setHasChanged(true);

            backpackDAO.saveBackpack(playerUUID.toString(), backpackId, backpacks.get(backpackId).getInventory().getContents());
        }
    }

    public void openBackpack(Player player, int backpackId) {
        Backpack backpack = getOrCreateBackpack(player, backpackId);
        if (backpack != null) {
            player.openInventory(backpack.getInventory());
            player.sendMessage(LangUtil.getString("messages.open", true).replace("%backpack%", Integer.toString(backpackId)));
        }
    }
}

