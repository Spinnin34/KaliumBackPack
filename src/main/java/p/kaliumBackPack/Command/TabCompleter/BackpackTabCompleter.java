package p.kaliumBackPack.Command.TabCompleter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import p.kaliumBackPack.Manager.RoleManager;
import p.kaliumBackPack.Model.Role;

import java.util.ArrayList;
import java.util.List;

public class BackpackTabCompleter implements TabCompleter {
    private final RoleManager roleManager;

    public BackpackTabCompleter(RoleManager roleManager) {
        this.roleManager = roleManager;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (sender instanceof Player && args.length == 1) {
            Player player = (Player) sender;

            Role role = roleManager.getRoleForPlayer(player);
            if (role != null) {
                int maxBackpacks = role.getMaxBackpacks();

                for (int i = 1; i <= maxBackpacks; i++) {
                    suggestions.add(String.valueOf(i));
                }
            }
        }

        return suggestions;
    }
}
