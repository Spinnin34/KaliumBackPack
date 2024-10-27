package p.kaliumBackPack.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.entity.Player;
import p.kaliumBackPack.Manager.BackpackManager;
import p.kaliumBackPack.Model.Backpack;

public class BackpackListener implements Listener {
    private final BackpackManager backpackManager;

    public BackpackListener(BackpackManager backpackManager) {
        this.backpackManager = backpackManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getInventory();

        if (inventory.getHolder() instanceof Backpack) {
            int backpackId = ((Backpack) inventory.getHolder()).getId();

            backpackManager.onBackpackItemChanged(player, backpackId);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        Inventory inventory = event.getInventory();

        if (inventory.getHolder() instanceof Backpack) {
            backpackManager.savePlayerBackpacks(player);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        backpackManager.savePlayerBackpacks(event.getPlayer());
    }
}
