package p.kaliumBackPack.Model;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Bukkit;

public class Backpack implements InventoryHolder {
    private final int id;
    private final Inventory inventory;
    private boolean hasChanged;

    public Backpack(int id, int rows) {
        this.id = id;
        this.inventory = Bukkit.createInventory(this, rows * 9, "§x§F§B§0§0§0§0§nᴍ§x§F§C§1§B§0§0§nᴏ§x§F§C§3§6§0§0§nᴄ§x§F§D§5§1§0§0§nʜ§x§F§E§6§B§0§0§nɪ§x§F§E§8§6§0§0§nʟ§x§F§F§A§1§0§0§nᴀ " + id);
        this.hasChanged = false;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public boolean hasChanged() {
        return hasChanged;
    }

    public int getId() {
        return id;
    }

    public void setHasChanged(boolean hasChanged) {
        this.hasChanged = hasChanged;
    }
}

