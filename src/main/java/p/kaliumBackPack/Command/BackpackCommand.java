package p.kaliumBackPack.Command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import p.kaliumBackPack.KaliumBackPack;
import p.kaliumBackPack.Manager.BackpackManager;
import p.kaliumBackPack.Utils.LangUtil;

public class BackpackCommand implements CommandExecutor {

    private final BackpackManager backpackManager;

    public BackpackCommand(KaliumBackPack plugin) {
        this.backpackManager = plugin.getBackpackManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(LangUtil.getString("messages.not-player", true));
            return true;
        }

        Player player = (Player) sender;

        if (args.length != 1) {
            player.sendMessage(LangUtil.getString("messages.use", true));
            return true;
        }

        try {
            int backpackId = Integer.parseInt(args[0]);
            backpackManager.openBackpack(player, backpackId);
        } catch (NumberFormatException e) {
            player.sendMessage(LangUtil.getString("messages.value-backpack-null", true));
        }

        return true;
    }
}

